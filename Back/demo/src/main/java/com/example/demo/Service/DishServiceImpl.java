package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.DishDao;
import com.example.demo.dao.point.PointDAO;
import com.example.demo.vo.Dish;
import com.example.demo.vo.DishComm;
import com.example.demo.vo.DishDB;
import com.example.demo.vo.point.PointDescription;
import com.example.demo.vo.point.UserPointVO;

import lombok.RequiredArgsConstructor;

@Service
@lombok.extern.slf4j.Slf4j
@RequiredArgsConstructor
public class DishServiceImpl implements DishService, PointDescription {

	@Autowired
	DishDao dishDao;
	
	private final PointDAO pointDAO;

	@Override
	public List<Map<String, Object>> get() {
		
		List<Map<String, Object>> list =  dishDao.get();
		System.out.println("list::: " + list);
		return list;
	}

	@Override
	public void add(DishDB dish, int mnum) {
		
		dishDao.add(dish);
		int dnum= Integer.parseInt(dish.getRCP_SEQ());
		dishDao.addInfo(dnum, mnum);
	}
	
	@Override
	public Map<String,Object> getOne(int RCP_SEQ) {
		
		return dishDao.getOne(RCP_SEQ);
	}  

	@Override
	public void edit(DishDB dish, Dish dish1,int rCP_SEQ, int mnum) {
		
		dishDao.edit(dish);
		dishDao.editInfo(dish1, rCP_SEQ, mnum);
	}

	@Override
	public void upHit(int RCP_SEQ) {
	
		dishDao.upHit(RCP_SEQ);
	}

	@Override
	public void delete(int RCP_SEQ) {
		
		dishDao.delete(RCP_SEQ);
	}

	@Override
	public void delPicture(int dnum) {
		dishDao.delPicture(dnum);
		
	}

	@Override
	public void editPicture(Map<String, Object> param) {
		dishDao.editPicture(param);
		
	}

	@Override
	public int dishLike(int mnum, int RCP_SEQ) {
		
		return dishDao.dishLike(mnum,RCP_SEQ);
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void goDishLike(int RCP_SEQ, int mnum) {
	
		dishDao.goDishLike(RCP_SEQ,mnum);
		
		UserPointVO vo = new UserPointVO();
		vo.setMnum(mnum);
		vo.setPointID(LIKE_PLUS);
		vo.setPoint(LIKE_POINT);
		vo.setRCP_SEQ(RCP_SEQ);
		
		pointDAO.registerPoint(vo);
		
		log.info("[DishServiceImpl] [goDishLike] [{}]", mnum);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void goDishDislike(int RCP_SEQ, int mnum) {
		
		dishDao.goDishDislike(RCP_SEQ,mnum);
		
		UserPointVO vo = new UserPointVO();
		vo.setMnum(mnum);
		vo.setPointID(LIKE_MINUS);
		vo.setPoint(LIKE_POINT * -1);
		vo.setRCP_SEQ(RCP_SEQ);
		
		pointDAO.registerPoint(vo);
		
		log.info("[DishServiceImpl] [goDishDislike] [{}]", mnum);
	}

	@Override
	public int getNum() {
		
		return dishDao.getNum();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void commAdd(DishComm dishComm) {
		
		dishDao.commAdd(dishComm);
		
		UserPointVO vo = new UserPointVO();
		vo.setMnum(dishComm.getMnum());
		vo.setPointID(COMMENT_PLUS);
		vo.setPoint(COMMENT_POINT);
		vo.setRCP_SEQ(Integer.parseInt(dishComm.getRCP_SEQ()));
		
		pointDAO.registerPoint(vo);
		
		log.info("[DishServiceImpl] [commAdd] [{}]", dishComm.getMnum());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void commDelete(DishComm dishComm) {
		
		dishDao.commDelete(dishComm);
		
		UserPointVO vo = new UserPointVO();
		vo.setMnum(dishComm.getMnum());
		vo.setPointID(COMMENT_MINUS);
		vo.setPoint(COMMENT_POINT * -1);
		vo.setRCP_SEQ(Integer.parseInt(dishComm.getRCP_SEQ()));
		
		pointDAO.registerPoint(vo);
		
		log.info("[DishServiceImpl] [commDelete] [{}]", dishComm.getMnum());
	}

	@Override
	public void commEdit(DishComm dishComm) {
		
		dishDao.commEdit(dishComm);
	}

	@Override
	public List<DishComm> commGet(int rCP_SEQ) {

		return dishDao.commGet(rCP_SEQ);
	}

	@Override
	public List<Map<String, Object>> search(String select, String searchI) {
		
		
		return dishDao.search(select,searchI);
	}

	@Override
	public List<Map<String, Object>> topDish() {
		
		List<Map<String, Object>> result = dishDao.topDish();
		
		return result;
	}
	
	

	
}