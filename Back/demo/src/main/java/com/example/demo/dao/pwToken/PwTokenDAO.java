package com.example.demo.dao.pwToken;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.pwToken.PwTokenVO;

@Mapper
public interface PwTokenDAO {
	
	@Insert("INSERT INTO CHANGEPW VALUES(#{mnum}, #{pwToken})")
	public long createPwToken(PwTokenVO pwTokenVO);
	
	@Select("SELECT M.MNUM, C.PWTOKEN FROM MEMBER M LEFT OUTER JOIN CHANGEPW C ON M.MNUM=C.MNUM  WHERE M.MNUM = #{mnum}")
	public PwTokenVO checkPwTokenbyID(String memberID);
	
	@Select("SELECT * FROM CHANGEPW WHERE MNUM = #{mnum}")
	public PwTokenVO checkPwTokenbyMnum(long mnum);
	
	@Update("UPDATE CHANGEPW SET PWTOKEN = #{pwToken} WHERE MNUM = #{mnum}")
	public void updatePwToken(PwTokenVO pwTokenVO);
	
	@Delete("DELETE FROM CHANGEPW WHERE MNUM = #{mnum}")
	public void deletePwToken(PwTokenVO pwTokenVO);

}
