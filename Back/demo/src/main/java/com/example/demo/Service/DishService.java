package com.example.demo.Service;

import java.util.List;
import java.util.Map;

import com.example.demo.vo.Dish;
import com.example.demo.vo.DishDB;

public interface DishService {

	/*
	 * 음식 추가 
	 */
	public void add(Dish dish);
	/*
	 * 음식 전체 리스트 불러오기 
	 */
	public List<Map<String, Object>> get();
	/*
	 * 음식 정보 한개 불러오기
	 */
	public Map<String,Object> getOne(int RCP_SEQ);
	/*
	 * 음식 정보 수정하기
	 */
	public void edit(Dish dish);
	/*
	 *  조회수 +1
	 */
	public void upHit(int RCP_SEQ);
	/*
	 * 음식 정보 삭제하기 
	 */
	public void delete(int id);
	/*
	 * 사진들
	 */
	public void addPicture(Map<String, Object> param);
	/*
	 * 사진 수정하려면 그냥 기존 사진 삭제하기
	 */
	public void delPicture(int dnum);
	
	public void editPicture(Map<String, Object> param);
	
	/*
	 * 본 게시물 좋아요 눌렀나 확인
	 */
	public int dishLike(int mnum, int dnum);
	/*
	 * 좋아요 등록하러 가기
	 */
	public void goDishLike(int dnum, int mnum);
	/*
	 * 좋아요 해제하러 가기 
	 */
	public void goDishDislike(int dnum, int mnum);

	
	
}
