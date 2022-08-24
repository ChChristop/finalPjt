package com.example.demo.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO extends BaseVO{
	
	//회원식별자
	private int mnum;
	
	//아이디이자 이메일
	private String memberID;
	
	//비밀번호
	private String pw;
	
	//닉네임
	private String nickname;
	
	//권한
	private String role;
	
	//전화번호
	private String phoneNumber;
	
	//포인트
	private int point;

	//최종접속시간
	private LocalDateTime lastAccessDate;

}
