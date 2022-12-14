package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.vo.Ate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
		
		//포인트 합계
		private int point;

		//최종접속시간
		private LocalDateTime lastAccessDate;
		
		//가입시간
		private LocalDateTime date;
		
		//회원정보 변경 시간
		private LocalDateTime modDate;
		
		//먹음 갯수
		private int ateCount;
		
		//냉장고 재료 개수
		private int refreCount;
		
		//댓글 갯수
		private int comment;
		
		//좋아요 갯수
		private int likeCount;
		
		//냉장고 재료 리스트
		private List<RefrigeratorDTO> refrigerator;


}
