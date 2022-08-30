package com.example.demo.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.vo.MemberVO;

@Mapper
public interface MemberDAO {
	
	//회원 전체 리스트 조회(페이지 설정 가능)
	List<Map<String,Object>> getMemberList2(PageRequestDTO pageReuqestDTO);
	
	List<MemberVO> getMemberList(PageRequestDTO pageReuqestDTO);
	
	//회원 디테일 리스트
	List<Map<String,Object>> getmemberDetails(String memberID);

	//회원 등록 쿼리
	@Insert("INSERT INTO MEMBER(MEMBERID, MEMBERPW, NICKNAME, PHONENUMBER, ROLE) "
			+ "VALUES(#{memberID},#{memberPW},#{nickname},#{phoneNumber},#{role})")
	@Options(useGeneratedKeys = true, keyProperty = "mnum")
	Long addMember(MemberVO member);

	//회원 아이디 중복 체크
	@Select("SELECT MEMBERID FROM MEMBER WHERE MEMBERID = #{memberID}")
	Optional<String> checkByMemberId(String memberID);
	
	//식별자로로 회원 정보 조회
	Map<String,Object> findMemberbyMnum(long mnum);
	
	//식별자로로 회원 정보 조회
	@Select("SELECT MNUM, MEMBERID, MEMBERPW, NICKNAME, ROLE, DATE, MODDATE, LASTACCESSDATE "
			+ "FROM MEMBER "
			+ "WHERE mnum = #{mnum}")
	Optional<MemberVO> findMemberbyMnumForJWT(long mnum);
	
	//아이디로 회원 정보 조회
	Map<String,Object> findMemberbyMemberID(String memberID);
	
	@Select("SELECT MNUM, MEMBERID, MEMBERPW, NICKNAME, ROLE, DATE, MODDATE, LASTACCESSDATE, PHONENUMBER, MODDATE "
			+ "FROM MEMBER "
			+ "WHERE MEMBERID = #{memberID}")
	Optional<MemberVO> findMemberbyMemberIDForJWT(String memberID);
	
	//회원 탈퇴
	@Delete("DELETE FROM MEMBER WHERE mnum=#{munm}")
	Long removeMemberbyMnum(long mnum);
	
	//회원 정보 수정
	@Update("UPDATE MEMBER "
			+ "SET  MEMBERPW=#{memberPW}, NICKNAME=#{nickname}, PHONENUMBER=#{phoneNumber} "
			+ "WHERE MNUM=#{mnum} ")
	Long updateAdminByMnum(MemberVO member);
	
	//식별자로 회원 최종 접속시간 업데이트
	@Update("UPDATE MEMBER "
			+ "SET LASTACCESSDATE=current_timestamp " 
			+ "WHERE MNUM=#{mnum}")
	void updateLastAceesDATEByMnum(long mnum);
	
	//아이디로 회원 최종 접속시간 업데이트
	@Update("UPDATE MEMBER "
			+ "SET LASTACCESSDATE=current_timestamp " 
			+ "WHERE MEMBERID=#{memberID}")
	void updateLastAceesDATEByMemberID(String memberID);
	
	
	@Update("UPDATE MEMBER "
			+ "SET modDate=current_timestamp " 
			+ "WHERE MNUM=#{mnum}")
	void updateModDateByMnum(long mnum);
	
	int countMemberAllList(PageRequestDTO pageReuqestDTO);
}
