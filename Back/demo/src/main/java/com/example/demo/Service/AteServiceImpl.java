package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.AteDao;
import com.example.demo.dao.point.PointDAO;
import com.example.demo.vo.Ate;
import com.example.demo.vo.point.PointDescription;
import com.example.demo.vo.point.UserPointVO;

import groovy.util.logging.Slf4j;
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
	public void add(Ate ate) {

		ateDao.add(ate);

		UserPointVO vo = new UserPointVO();

		vo.setMnum(ate.getMnum());
		vo.setPointID(ATE_PLUS);
		vo.setPoint(ATE_POINT);
		vo.setRCP_SEQ(Integer.parseInt(ate.getRCP_SEQ()));

		pointDAO.registerPoint(vo);

		log.info("[add] [진입] [{}]", ate.getMnum());

	}

	@Override
	public List<Ate> get() {

		List<Ate> list = ateDao.get();

		return list;
	}

	@Override
	public Ate getOne(int ate_num) {

		return ateDao.getOne(ate_num);
	}

	@Override
	public void editAte(Ate ate) {

		ateDao.editAte(ate);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(int ate_num, int mnum) {

		ateDao.delete(ate_num,mnum);

		UserPointVO vo = new UserPointVO();

		vo.setMnum(mnum);
		vo.setPointID(ATE_MINUS);
		vo.setPoint(ATE_POINT * -1);

		pointDAO.registerPoint(vo);

		log.info("[delete] [진입] [{}]", mnum);

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
	public void goAteLike(int ate_num, int mnum) {

		ateDao.goAteLike(ate_num, mnum);
	}

	@Override
	public void goAteDislike(int ate_num, int mnum) {

		ateDao.goAteDislike(ate_num, mnum);
	}

	@Override
	public List<Ate> getAllList(long mnum) {

		List<Ate> result = ateDao.getAllList(mnum);

		return result;
	}

}
