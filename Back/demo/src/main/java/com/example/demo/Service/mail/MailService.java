package com.example.demo.service.mail;

import java.util.Date;
import java.util.Optional;

import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.dao.MemberDAO;
import com.example.demo.dao.pwToken.PwTokenDAO;
import com.example.demo.dto.MemberDTO;
import com.example.demo.vo.MemberVO;
import com.example.demo.vo.pwToken.PwTokenVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
	
	@Autowired
	private  JavaMailSender javaMailSender;
	
	@Value("${JwtProperties.changePW}")
	private String changePW;
	
	private String url = "http://127.0.0.1:5500/ttest/account/changePW.html?code=";
	
	private final PwTokenDAO pwTokenDAO;
	
	private final MemberDAO memberDAO;
	
	
	
	@Transactional(rollbackFor = Exception.class)
	public boolean sendMail(String memberID) {
		
		Optional<MemberVO> optionalVO = memberDAO.findMemberbyMemberIDForJWT(memberID);

	
		MemberVO vo = null;
		
		if(optionalVO.isEmpty()) {
			
			log.warn("[MailService] [sendMail 실패] [{}]",memberID);
			
			return false;
			
		}else {
			
			vo = optionalVO.get();
		}
		
		MemberDTO dto = MemberDTO.builder()
				.mnum(vo.getMnum())
				.memberID(vo.getMemberID())
				.build();			

		String[] id = {dto.getMemberID(),Long.toString(dto.getMnum())};
		
		String jwtToken = createToken(id);
		
		url += jwtToken;
		
		log.info("[MailService] [sendMail 진행 중] [{}]",memberID);
		
		try{
			
			MimeMessage msg = javaMailSender.createMimeMessage();
			
			String location = "https://chchristop.github.io/react08?id=";
			location += id[0];
			location += "&authcode=";
			location += jwtToken;

		
			
			msg.setSubject("냉장고 계정 암호 재설정","UTF-8");
			
			String htmlStr = " <div style=\"margin-left: 3rem; padding: 3rem;\">";
				htmlStr += "<h1>"+id[0].split("@")[0];
				htmlStr += " 냉장고 계정 암호 재설정 안내 </h1>";
				htmlStr += "<br>";
				htmlStr += "<div>";
				htmlStr += "<p style=\"margin-bottom: 1rem;\">인증코드입니다. 이용하고 계신 사이트에서 코드 입력바랍니다.</p>";
				htmlStr += "<p style=\"margin-bottom: 1rem;\">"+ jwtToken + "</p>";
				htmlStr += "<p style=\"margin-top: 1rem;\">모바일은 아래의 URL 주소로 직접 이동해주세요.</p>";		
				htmlStr += "<a style=\"text-decoration: none;\" href='"+location+"'>";
//				htmlStr += "link";
				htmlStr += "<button type='button' style=\"background-color: black; color: white;\">";
				htmlStr +=  "사이트 이동</button>";
				htmlStr += "</a>";
				htmlStr += "</div>";
				htmlStr += "</div>";
			
				msg.setText(htmlStr,"UTF-8","html");
				msg.addRecipients(RecipientType.TO, id[0]);;
			
			javaMailSender.send(msg);
			
			PwTokenVO pwToken = new PwTokenVO();
			pwToken.setMnum(dto.getMnum());
			pwToken.setAuthcode(jwtToken);

			PwTokenVO check = pwTokenDAO.checkPwTokenbyMnum(dto.getMnum());
					
			if(check != null) {
				pwTokenDAO.updatePwToken(pwToken);
			}else {
				pwTokenDAO.createPwToken(pwToken);
			}
		
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
			
		}
		
		log.info("[MailService] [sendMail 성공] [{}]",memberID);
		return true;
	}
	
	//토큰 생성
	public String createToken(String[] id) {
	
		String jwtToken = JWT.create().withSubject(id[0])
				.withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
				.withClaim("memberID", id[0])
				.withClaim("mnum",id[1])
				.sign(Algorithm.HMAC512(changePW));
		
		return jwtToken;
	}
	
	//토큰 유효성 체크
	public String[] checkToken(String token) {
		
		
		if (token == "") {
			return new String[] {};
		}

		String key = this.changePW;

		try {
			//토큰의 날짜가 유효한지 확인
			JWT.require(Algorithm.HMAC512(key)).build().verify(token).getExpiresAt();

			//토큰의 ID가 유효한지
			String[] getID = new String[2]; 
					
			getID[0] = JWT.require(Algorithm.HMAC512(key)).build().verify(token).getClaim("memberID").asString();		
			
			pwTokenDAO.checkPwTokenbyID(getID[0]);
		
			getID[1] = JWT.require(Algorithm.HMAC512(key)).build().verify(token).getClaim("mnum").asString() ;

			return getID;

		} catch (Exception e) {
			e.printStackTrace();
			return new String[] {};
		}
	}
	
}
