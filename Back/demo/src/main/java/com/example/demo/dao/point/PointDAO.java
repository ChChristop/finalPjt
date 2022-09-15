package com.example.demo.dao.point;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.PointDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.vo.point.UserPointVO;

@Mapper
public interface PointDAO {
	
	//dish_num은 null이어도 됨
	@Insert("INSERT INTO USERPOINT(MNUM,POINTID,POINT,RCP_SEQ) "
			+ "VALUES(#{mnum},#{pointID},#{point},#{RCP_SEQ})")
	public void registerPoint(UserPointVO vo);
	
	//dish_num은 null이어도 됨
	@Insert("INSERT INTO USERPOINT(MNUM,POINTID,POINT,ATE_NUM) "
			+ "VALUES(#{mnum},#{pointID},#{point},#{ate_num})")
	public void registerPointbyAte_num(UserPointVO vo);
	
	//dish_num은 null이어도 됨
	@Insert("INSERT INTO USERPOINT(MNUM,POINTID,POINT,A_LNUM) "
			+ "VALUES(#{mnum},#{pointID},#{point},#{a_lnum})")
	public void registerPointbya_lnum(UserPointVO vo);

	public List<PointDTO> findUserPointbyMnum(PageRequestDTO dto,long mnum);
	
	public List<PointDTO> findUserPoint7dbyMnum(PageRequestDTO dto,long mnum);
	
	@Select("SELECT COUNT(*) FROM USERPOINT WHERE MNUM = #{mnum}")
	public int pointCountbyMnum(long mnum);
}
