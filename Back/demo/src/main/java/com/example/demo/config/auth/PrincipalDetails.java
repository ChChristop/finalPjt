package com.example.demo.config.auth;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.dto.AdminDTO;

public class PrincipalDetails implements UserDetails {

	private AdminDTO adminDTO;

	// private Member member;

	// 회원인 또는 관리자 체크
	private boolean check = false;

	public PrincipalDetails(AdminDTO adminDTO, boolean check) {
		this.adminDTO = adminDTO;
		this.check = check;
	}
	

	public AdminDTO getAdminDTO() {
		return adminDTO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		
		adminDTO.getRole().stream().forEach(role ->{
			authorities.add(new SimpleGrantedAuthority(role));
		});;
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return adminDTO.getAdminPW();
	}

	@Override
	public String getUsername() {
		return adminDTO.getAdminID();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
