package com.example.demo.dao.point;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.PointDTO;
import com.example.demo.vo.point.UserPointVO;

@Mapper
public interface PointDAO {
	
	//dish_num은 null이어도 됨
	@Insert("INSERT INTO USERPOINT(MNUM,POINTID,POINT,RCP_SEQ) "
			+ "VALUES(#{mnum},#{pointID},#{point},#{RCP_SEQ})")
	public void registerPoint(UserPointVO vo);

	
	@Select("SELECT * FROM USERPOINT U LEFT OUTER JOIN POINT P "
			+ "ON U.POINTID = P.POINTID WHERE U.MNUM = #{mnum}")
	public List<PointDTO> findUserPointbyMnum(long mnum);
}
