package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.AdminVO;

@Mapper
public interface AdminDAO {
	
	//관리자 등록 쿼리
	@Insert("INSERT INTO ADMIN(ADMINID, NICKNAME, ADMINPW, ROLE, PHONENUMBER) "
			+ "VALUES(#{adminID},#{nickName},#{adminPW},#{role},#{phoneNumber})")
	@Options(useGeneratedKeys = true, keyProperty = "anum")
	Long addAdmin(AdminVO admin);
	
	//관리자 전체 리스트 조회
//	@Select("SELECT ANUM, ADMINID, NICKNAME, PHONNUMBER, DATE, LASTACCESSDATE, ROLE FROM ADMIN ORDER BY #{basis} #{align} LIMIT #{page},#{size}")
	List<AdminVO> getAdminList(String basis, String align, int page, int size);
	
	//관리자 아이디 중복체크
	@Select("SEELCT ADMINID FROM ADMIN WHERE ADMINID = #{adminID}")
	Optional<String> CheckByAdminId(String adminID);
	
	//관리자 식별자로 관리자 정보 조회
	@Select("SELECT ANUM, ADMINID, ADMINPW, NICKNAME, ROLE, DATE, MODDATE, LASTACCESSDATE FROM ADMIN WHERE ANUM = #{anum}")
	Optional<AdminVO> findAdminIdByAnum(long anum);
	
	//관리자 아이디로 관리자 정보 조회
	@Select("SELECT ANUM, ADMINID, ADMINPW, NICKNAME, ROLE, DATE, MODDATE, LASTACCESSDATE FROM ADMIN WHERE ADMINID = #{adminID}")
	Optional<AdminVO> findAdminIdByID(String adminID);
	
	//관리자 식별자로 관리자 정보 삭제
	@Delete("DELETE FROM MEMBER WHERE ANUM=#{anum}")
	Long removeAdminByAnum(long anum);
	
	//관리자 식별자로 관리자 정보 수정
	@Update("UPDATE ADMIN "
			+ "SET  ADMINPW=#{adminPW}, NICKNAME=#{nickname}, PHONENUMBER=#{phoneNumber}, Role=#{role} "
			+ "WHERE ANUM=#{anum} ")
	Long updateAdminByAnum(AdminVO admin);	
	
	//관리자 식별자로 최종 접속시간 수정
	@Update("UPDATE ADMIN "
			+ "SET lastAccessDate=current_timestamp " 
			+ "WHERE ANUM=#{anum}")
	void updateLastAceesDateByAnum(long anum);
	
	//관리자 아이디로로 최종 접속시간 수정
	@Update("UPDATE ADMIN "
			+ "SET lastAccessDate=current_timestamp " 
			+ "WHERE ADMINID=#{adminID}")
	void updateLastAceesDateByAdminID(String adminID);
	
	//관리자 총 숫자(조건 지정 가능하게 해야함)
	@Select("SELECT COUNT(*) FROM ADMIN")
	int countAdminAllList();
	

}
