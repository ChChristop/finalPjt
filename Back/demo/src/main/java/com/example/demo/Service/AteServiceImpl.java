package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.AteDao;
import com.example.demo.dao.point.PointDAO;
import com.example.demo.dto.AteDTO;
import com.example.demo.pagelib.PageRequestDTO;
import com.example.demo.pagelib.PageResultVO;
import com.example.demo.vo.Ate;
import com.example.demo.vo.DishComm;
import com.example.demo.vo.point.PointDescription;
import com.example.demo.vo.point.UserPointVO;

import lombok.RequiredArgsConstructor;

@Service
@lombok.extern.slf4j.Slf4j
@RequiredArgsConstructor
public class AteServiceImpl implements AteService, PointDescription {

	@Autowired
	AteDao ateDao;

	private final PointDAO pointDAO;
	

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int add(Ate ate) {

		int result = ateDao.add(ate);

		UserPointVO vo = new UserPointVO();

		vo.setMnum(ate.getMnum());
		vo.setPointID(ATE_PLUS);
		vo.setPoint(ATE_POINT);
		vo.setRCP_SEQ(Integer.parseInt(ate.getRCP_SEQ()));

		pointDAO.registerPoint(vo);

		log.info("[AteServiceImpl] [add] [{}]", ate.getMnum());

		return result;
	}


	@Override
	public List<Ate> get() {

		List<Ate> list = ateDao.get();

		return list;
	}

	@Override
	public Map<String, Object> getOne(int ate_num) {

		return ateDao.getOne(ate_num);
	}

	@Override
	public int editAte(Ate ate) {

		return ateDao.editAte(ate);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delete(int ate_num, int mnum) {

		int result = ateDao.delete(ate_num,mnum);

//		UserPointVO vo = new UserPointVO();
//
//		vo.setMnum(mnum);
//		vo.setPointID(ATE_MINUS);
//		vo.setPoint(ATE_POINT * -1);
//
//		pointDAO.registerPoint(vo);
//
//		log.info("[AteServiceImpl] [delete 성공] [{}]", mnum);
//		
		return result;
	}

	@Override
	public void upHit(int ate_num) {
		ateDao.upHit(ate_num);

	}

	@Override
	public int ateLike(int mnum, int ate_num) {

		return ateDao.ateLike(mnum, ate_num);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void goAteLike(int ate_num, int mnum) {

		ateDao.goAteLike(ate_num, mnum);
		
		UserPointVO vo = new UserPointVO();
		vo.setMnum(mnum);
		vo.setPointID(ATE_LIKE_PLUS);
		vo.setPoint(ATE_LIKE_POINT);
		vo.setAte_num(ate_num);;
		
		pointDAO.registerPointbyAte_num(vo);
		
		log.info("[AteServiceImpl] [goAteLike] [{}]", mnum);
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void goAteDislike(int ate_num, int mnum) {

		ateDao.goAteDislike(ate_num, mnum);
		
		UserPointVO vo = new UserPointVO();
		vo.setMnum(mnum);
		vo.setPointID(ATE_LIKE_MINUS);
		vo.setPoint(ATE_LIKE_POINT * -1);
		vo.setAte_num(ate_num);;
		
		pointDAO.registerPointbyAte_num(vo);
		
		log.info("[AteServiceImpl] [goAteDislike] [{}]", mnum);
	}

	@Override
	public List<Ate> getAllList(long mnum) {

		List<Ate> result = ateDao.getAllList(mnum);

		return result;
	}

	@Override
	public PageResultVO<AteDTO> getUserAteList(PageRequestDTO pageRequestDTO, long mnum) {
	
		// 기준
		if (pageRequestDTO.getBasis() == "pk") {

			pageRequestDTO.setBasis("mnum");
		}

		pageRequestDTO.setterChange();
		
		List<AteDTO> result = null;
	
		int count = 0;

		try {

			// 조회 메서드
			result = ateDao.getAteListbyUser(pageRequestDTO,mnum);
			
			if(result.size()<1) return null;
			
			count = ateDao.ateCount(mnum);

			return new PageResultVO<>(result,pageRequestDTO,count);
			
		} catch (Exception e)  {
			
			e.printStackTrace();
			return null;}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int commAdd(DishComm dishComm) {
		
		int result  = ateDao.commAdd(dishComm);
		
		UserPointVO vo = new UserPointVO();
		vo.setMnum(dishComm.getMnum());
		vo.setPointID(ATE_COMMENT_PLUS);
		vo.setPoint(ATE_COMMENT_POINT);
		vo.setAte_num(dishComm.getAte_num());;
		
		pointDAO.registerPointbyAte_num(vo);
		
		log.info("[AteServiceImpl] [commAdd] [{}]", dishComm.getMnum());
		
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int commDelete(DishComm dishComm) {
		
		int result = ateDao.commDelete(dishComm);
		
		UserPointVO vo = new UserPointVO();
		vo.setMnum(dishComm.getMnum());
		vo.setPointID(ATE_COMMENT_MINUS);
		vo.setPoint(ATE_COMMENT_POINT * -1);
		vo.setAte_num(dishComm.getAte_num());;
		
		pointDAO.registerPointbyAte_num(vo);
		
		log.info("[AteServiceImpl] [commDelete] [{}]", dishComm.getMnum());
		
		return result;
	}

	@Override
	public int commEdit(DishComm dishComm) {
		
	
		return 	ateDao.commEdit(dishComm);
	}

	@Override
	public List<DishComm> commGet(int ate_num) {
		
		return ateDao.commGet(ate_num);
	}

	@Override
	public List<Map<String, Object>> search(String select) {

		return ateDao.search(select);
	}

	
}
