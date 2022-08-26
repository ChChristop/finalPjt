package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	
	//회원식별자
		private long mnum;
		
		//아이디이자 이메일
		private String memberID;
		
		//비밀번호
		private String memberPW;
		
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
		
		//가입시간
		private LocalDateTime date;
		
		//회원정보 변경 시간
		private LocalDateTime modDate;
		
//		개인 냉장고(재료 리스트)
//		private List<재료> 재료;
		
//		좋아요
//		private 좋아요 좋아요;
		
//		먹음
//		private 먹음 먹음;
		
//		댓글
//		private 댓글 댓글;


}
