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

import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.dao.AdminDAO;
import com.example.demo.dao.JwtTokkenDAO;
import com.example.demo.dto.AdminDTO;
import com.example.demo.vo.AdminVO;
import com.example.demo.vo.JwtVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JwtAuthorizationFilter extends BasicAuthenticationFilter implements JwtProperties {

	private AdminDAO adminDAO;

	private JwtTokkenDAO jwtTokkenDAO;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AdminDAO adminDAO,
			JwtTokkenDAO jwtTokkenDAO) {
		super(authenticationManager);
		this.adminDAO = adminDAO;
		this.jwtTokkenDAO = jwtTokkenDAO;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		log.info("JwtAuthorizationFilter : 진입");

		String jwtHeader = request.getHeader(SECRETKEY_HEADER_STRING);

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

		String[] checkJWTToken, checkRefreshToken = new String[2];

		String ip = (String) request.getHeader("X-FORWARDED-FOR");

		if (ip == null)
			ip = request.getRemoteAddr();

		try {
			// jwtToken이 유효하지 않을 경우 checkJWTToken 길이는 0
			checkJWTToken = vaildateJwtToken(jwtToken, true);

			// jwtRefreahToken이 유효하지 않을 경우 checkRefreshToken 길이는 0
			checkRefreshToken = vaildateJwtToken(jwtRefreahToken, false);
			
			System.out.println(Arrays.toString(checkJWTToken));

			if (checkJWTToken.length != 0) {

				log.info("jwtToken 확인중 : " + jwtToken);

				PrincipalDetails principalDetails = check(checkJWTToken, ip, false);
				
				if (principalDetails == null) {

					throw new Exception();
				}
				;

				chain.doFilter(request, response);

				return;

				// Access 토근의 유효시간이 끝났다면 refreshToken 조회
			} else if (checkRefreshToken.length != 0) {

				log.info("jwtRefreahToken 확인중 : " + jwtRefreahToken);

				PrincipalDetails principalDetails = check(checkRefreshToken, ip, true);

				if (principalDetails == null) {

					throw new Exception();

				}
				;

				String newJWTToken = CreateJWTToken(principalDetails);

				log.info("newJWTToken : " + newJWTToken);

				response.addHeader(SECRETKEY_HEADER_STRING, newJWTToken);

				chain.doFilter(request, response);

				return;
			}

		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.getMessage());
			
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

	}

	public PrincipalDetails check(String[] jwt, String ip, boolean check) {

		Optional<AdminVO> result = adminDAO.findAdminIdByID(jwt[1]);

		AdminVO admin = null;

		log.info("result.isPresent() ");

		if (!(result.isPresent())) {
			return null;
		}

		admin = result.get();

		if (check == true) {

			System.out.println(jwt[1] + "  " + ip);

			Optional<JwtVO> result1 = jwtTokkenDAO.findJWTByIdandIp(jwt[1], ip);

			JwtVO jwtCheck = null;

			jwtCheck = result1.get();

			if (jwtCheck.getJwt() != jwt[0]) {
				return null;
			}
		}

		AdminDTO adminDTO = AdminDTO.builder().anum(admin.getAnum()).adminID(admin.getAdminID())
				.adminPW(admin.getAdminPW()).role(admin.getRoleList()).build();

		PrincipalDetails principalDetails = new PrincipalDetails(adminDTO, false);

		Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
				principalDetails.getAuthorities());

		System.out.println(principalDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return principalDetails;

	}

}
