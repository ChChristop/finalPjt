package com.example.demo.config.auth;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.dto.AdminDTO;
import com.example.demo.dto.MemberDTO;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PrincipalDetails implements UserDetails {

	private AdminDTO adminDTO;

	private MemberDTO memberDTO;

	// 회원인 또는 관리자 체크
	private boolean check = false;

	public PrincipalDetails(AdminDTO adminDTO) {
		log.info("PrincipalDetails 객체 생성");
		this.adminDTO = adminDTO;
		this.check = true;
	}

	public PrincipalDetails(MemberDTO memberDTO) {
		log.info("PrincipalDetails 객체 생성");
		this.memberDTO = memberDTO;
		this.check = false;
	}

	public AdminDTO getAdminDTO() {
		return adminDTO;
	}

	public MemberDTO getMemberDTO() {
		return memberDTO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		//관리자 권한 설정
		if (check) {

			adminDTO.getRole().stream().forEach(role -> {
				authorities.add(new SimpleGrantedAuthority(role));
			});
			;

			return authorities;
		//회원 권한 설정
		} else {	
			
			authorities.add(new SimpleGrantedAuthority(memberDTO.getRole()));

			return authorities;
		}

	}

	@Override
	public String getPassword() {
		return (check)?adminDTO.getAdminPW():memberDTO.getMemberPW();
	}

	@Override
	public String getUsername() {
		return (check)?adminDTO.getAdminID():memberDTO.getMemberID();
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
