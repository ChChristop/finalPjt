package com.example.demo.config.jwt;

import java.io.IOException;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

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

		log.info("[JwtAuthorizationFilter] : 진입");

		// boolean requestURI =
		// request.getRequestURI().matches("(/api/member.+)|(/api/admin.+)|(/api/logout/.+)|(/api/refre/.+)");

		boolean requestURI = request.getRequestURI().matches("(/api/admin.+)|(/api/logout/.+)|(/api/refre/.+)");

		// 권한 필요 없는 요청이면 jwt 확인 필요 없음.
		if (!(requestURI)) {
			log.info("[JwtAuthorizationFilter] : 권한 필요 없는 주소 : " + request.getRequestURI());
			chain.doFilter(request, response);

			return;
		}

		String jwtHeader = request.getHeader(JwtProperties.SECRETKEY_HEADER_STRING);

		System.out.println(request.getRequestURI() + " requestURI " + requestURI);

		log.info("[JwtAuthorizationFilter] : Header JWTToken 확인 중 " + jwtHeader);

		String jwtRefreshHeader = request.getHeader(JwtProperties.REFRESHKEY_HEADER_STRING) != null
				? request.getHeader(JwtProperties.REFRESHKEY_HEADER_STRING)
				: "";

		// Header에 jwt token에 있는지 확인
		if (jwtHeader == null || !((String) jwtHeader).startsWith(JwtProperties.TOKEN_PREFIX)) {

			log.warn("[JwtAuthorizationFilter] : Header에 JWTToken 없음");

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
			checkJWTToken = JwtProperties.vaildateJwtToken(jwtToken, true);

			// jwtRefreahToken이 날짜가 유효하지 않을 경우 checkRefreshToken 길이는 0
			checkRefreshToken = JwtProperties.vaildateJwtToken(jwtRefreahToken, false);

			if (checkJWTToken.length != 0) {

				log.info("[JwtAuthorizationFilter] : JWTToken 확인 중 : " + jwtToken);

				principalDetails = check(checkJWTToken, ip, false);

				if (principalDetails == null) {

					log.warn("[JwtAuthorizationFilter] : JWTToken 인증 실패 : " + jwtToken);

				}

				chain.doFilter(request, response);

			}

			// Access 토근이 유효하지 않다면 refreshToken 조회
			if (checkRefreshToken.length != 0) {

				log.info("[JwtAuthorizationFilter] : JWTRefreshToken 확인 중 : " + jwtRefreahToken);

				principalDetails = check(checkRefreshToken, ip, true);

				if (principalDetails == null) {

					log.warn("[JwtAuthorizationFilter] : JWTRefreshToken 인증 실패 : " + jwtRefreahToken);

					throw new Exception();

				}

				String newJWTToken = JwtProperties.CreateJWTToken(principalDetails);

				// Refresh Token 유효시 새로운 Access Token 발급

				log.info("[JwtAuthorizationFilter] : JWTToken 재발급 : " + newJWTToken);

				response.addHeader(JwtProperties.SECRETKEY_HEADER_STRING, newJWTToken);

				if (AdminCheck.check)
					request.setAttribute("GetNumber", principalDetails.getAdminDTO().getAnum());
				else
					request.setAttribute("GetNumber", principalDetails.getMemberDTO().getMnum());

				chain.doFilter(request, response);

			}

		} catch (Exception e) {

			log.warn("[JwtAuthorizationFilter] : 인증 실패");

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		return;

	}

	public PrincipalDetails check(String[] jwt, String ip, boolean check) {

		boolean adminCheck = AdminCheck.check;

		PrincipalDetails principalDetails = null;

		if (adminCheck) {

			Optional<AdminVO> result = adminDAO.findAdminIdByIDForJWT(jwt[1]);

			AdminVO admin = null;

			if (!(result.isPresent())) {

				return null;
			}

			admin = result.get();

			AdminDTO adminDTO = AdminDTO.builder().anum(admin.getAnum()).adminID(admin.getAdminID())
					.adminPW(admin.getAdminPW()).role(admin.getRoleList()).build();

			principalDetails = new PrincipalDetails(adminDTO);

		} else {

			Optional<MemberVO> result = memberDAO.findMemberbyMemberIDForJWT(jwt[1]);

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

				return null;
			}
		}

		Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
				principalDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return principalDetails;

	}

}
