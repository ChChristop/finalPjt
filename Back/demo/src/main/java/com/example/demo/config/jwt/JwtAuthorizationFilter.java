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

import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.dao.AdminDAO;
import com.example.demo.dto.AdminDTO;
import com.example.demo.service.adminService.AdminService;
import com.example.demo.vo.Admin;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JwtAuthorizationFilter extends BasicAuthenticationFilter implements JwtProperties {

	private AdminDAO adminDAO;

	private AdminService adminService;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AdminDAO adminDAO) {
		super(authenticationManager);
		this.adminDAO = adminDAO;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		log.info("JwtAuthorizationFilter : 진입");

		String jwtHeader = request.getHeader(SECRETKEY_HEADER_STRING);

		String jwtRefreshHeader = request.getHeader(REFRESHKEY_HEADER_STRING);

		// Header가 있는지 확인
		if (jwtHeader == null || jwtRefreshHeader == null || !((String) jwtHeader).startsWith(TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		String jwtToken = jwtHeader.replace("Bearer ", "");

		String jwtRefreahToken = jwtRefreshHeader.replace("Bearer ", "");

		log.info("jwtToken 확인중 : " + jwtToken);



		String[] checkJWTToken, checkRefreshToken = new String[2];

		try {
			checkJWTToken = vaildateJwtToken(jwtToken, true);
			checkRefreshToken = vaildateJwtToken(jwtRefreahToken, false);

			if (checkJWTToken.length != 0) {

				if (check(checkJWTToken) == null) {

					throw new Exception();

				};

				chain.doFilter(request, response);

				return;
			}
			
			log.info("jwtRefreahToken 확인중 : " + jwtRefreahToken);
			
			if (checkRefreshToken.length != 0) {

				PrincipalDetails principalDetails = check(checkRefreshToken);
				
				if (principalDetails == null) {

					throw new Exception();

				};

				String newJWTToken = CreateJWTToken(principalDetails);
				
				log.info("newJWTToken : " + newJWTToken);

				response.addHeader(SECRETKEY_HEADER_STRING, newJWTToken);

				chain.doFilter(request, response);

				return;
			}

		} catch (Exception e) {

			e.printStackTrace();
			log.error(e.getMessage());
		}

	}

	public PrincipalDetails check(String[] jwt) {

		Optional<Admin> result = adminDAO.findByAdminId(jwt[1]);

		Admin admin = null;
		
		System.out.println(result.toString());

		if (!(result.isPresent())) {
			return null;
		}

		admin = result.get();

		AdminDTO adminDTO = AdminDTO.builder()
				.anum(admin.getAnum())
				.adminID(admin.getAdminID())
				.adminPW(admin.getAdminPW())
				.role(admin.getRoleList())
				.build();

		PrincipalDetails principalDetails = new PrincipalDetails(adminDTO, false);

		Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
				principalDetails.getAuthorities());
		
		System.out.println(principalDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return principalDetails;

	}

}
