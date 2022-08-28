package com.example.demo.config.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.demo.config.auth.AdminCheck;
import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.dao.AdminDAO;
import com.example.demo.dao.JwtTokkenDAO;
import com.example.demo.dao.MemberDAO;
import com.example.demo.dto.AdminDTO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.vo.AdminVO;
import com.example.demo.vo.JwtVO;
import com.example.demo.vo.MemberVO;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JwtAuthorizationFilter extends BasicAuthenticationFilter implements JwtProperties {

	private AdminDAO adminDAO;

	private MemberDAO memberDAO;

	private JwtTokkenDAO jwtTokkenDAO;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AdminDAO adminDAO, MemberDAO memberDAO,
			JwtTokkenDAO jwtTokkenDAO) {
		super(authenticationManager);
		this.adminDAO = adminDAO;
		this.memberDAO = memberDAO;
		this.jwtTokkenDAO = jwtTokkenDAO;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		boolean requestURI = request.getRequestURI().matches("(/api/member.+)|(/api/admin.+)|(/api/logout/.+)");

	
		// 권한 필요 없는 요청이면 jwt 확인 필요 없음.
		if (!(requestURI)) {
			log.info("???/");
			chain.doFilter(request, response);

			return;
		}

		log.info("JwtAuthorizationFilter : 진입");

		String jwtHeader = request.getHeader(SECRETKEY_HEADER_STRING);
		
		System.out.println(request.getRequestURI()+" requestURI " + requestURI);

		log.info("jwtHeader : 확인 중 " + jwtHeader);

		String jwtRefreshHeader = request.getHeader(REFRESHKEY_HEADER_STRING) != null
				? request.getHeader(REFRESHKEY_HEADER_STRING)
				: "";

		// Header가 있는지 확인
		if (jwtHeader == null || !((String) jwtHeader).startsWith(TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		String jwtToken = jwtHeader.replace("Bearer ", "");

		String jwtRefreahToken = jwtRefreshHeader.replace("Bearer ", "");

		String[] checkJWTToken, checkRefreshToken = new String[3];

		String ip = (String) request.getHeader("X-FORWARDED-FOR");

		PrincipalDetails principalDetails = null;

		if (ip == null)
			ip = request.getRemoteAddr();

		try {
			// jwtToken이 날짜가 유효하지 않을 경우 checkJWTToken 길이는 0
			checkJWTToken = vaildateJwtToken(jwtToken, true);

			// jwtRefreahToken이 날짜가 유효하지 않을 경우 checkRefreshToken 길이는 0
			checkRefreshToken = vaildateJwtToken(jwtRefreahToken, false);

			
			if (checkJWTToken.length != 0) {

				log.info("jwtToken 확인중 : " + jwtToken);

				principalDetails = check(checkJWTToken, ip, false);

//				if (principalDetails == null) {
//
//					return;
//				};

				chain.doFilter(request, response);

				// Access 토근이 유효하지 않다면 refreshToken 조회
			}

			
			if (checkRefreshToken.length != 0) {

				log.info("jwtRefreahToken 확인중 : " + jwtRefreahToken);

				principalDetails = check(checkRefreshToken, ip, true);

				if (principalDetails == null) {

					throw new Exception();

				};

				String newJWTToken = CreateJWTToken(principalDetails);

				log.info("newJWTToken : " + newJWTToken);

				response.addHeader(SECRETKEY_HEADER_STRING, newJWTToken);

				chain.doFilter(request, response);

			}

		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.getMessage());

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

			return;

	}

	public PrincipalDetails check(String[] jwt, String ip, boolean check) {

		log.info("jwt 체크 확인 중");

		boolean adminCheck = AdminCheck.check;

		PrincipalDetails principalDetails = null;

		if (adminCheck) {

			Optional<AdminVO> result = adminDAO.findAdminIdByID(jwt[1]);

			AdminVO admin = null;

			if (!(result.isPresent())) {
				return null;
			}

			admin = result.get();

			AdminDTO adminDTO = AdminDTO.builder().anum(admin.getAnum()).adminID(admin.getAdminID())
					.adminPW(admin.getAdminPW()).role(admin.getRoleList()).build();

			principalDetails = new PrincipalDetails(adminDTO);

		} else {

			Optional<MemberVO> result = memberDAO.findMemberbyMemberID(jwt[1]);

			MemberVO member = null;

			if (!(result.isPresent())) {
				return null;
			}

			member = result.get();

			MemberDTO memberDTO = MemberDTO.builder().mnum(member.getMnum()).memberID(member.getMemberID())
					.memberPW(member.getMemberPW()).role("ROLE_" + member.getRole()).build();

			principalDetails = new PrincipalDetails(memberDTO);

		}

		if (check == true) {

			Optional<JwtVO> result1 = jwtTokkenDAO.findJWTByIdandIp(jwt[1], ip);

			JwtVO jwtCheck = null;

			jwtCheck = result1.get();

			if ((!jwtCheck.getJwt().equals(jwt[2]))) {

				System.out.println("jwt[2] = jwtCheck.getJwt() " + jwt[2].equals(jwtCheck.getJwt()));
				
				System.out.println(jwt[2]);
				System.out.println(jwtCheck.getJwt());

				return null;
			}
		}


		
		Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
				principalDetails.getAuthorities());
		
		System.out.println(authentication);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		

		return principalDetails;

	}

}
