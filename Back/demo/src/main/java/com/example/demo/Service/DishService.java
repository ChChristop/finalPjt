package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.vo.Dish;
import com.example.demo.vo.DishComm;
import com.example.demo.vo.DishDB;

public interface DishService {

	/*
	 * 음식 추가 
	 */
	public void add(DishDB dish, int mnum);
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
	public void edit(DishDB dish, Dish dish1, int rCP_SEQ, int mnum);
	/*
	 *  조회수 +1
	 */
	public void upHit(int RCP_SEQ);
	/*
	 * 음식 정보 삭제하기 
	 */
	public void delete(int RCP_SEQ);
	/*
	 * 본 게시물 좋아요 눌렀나 확인
	 */
	public int dishLike(int mnum, int RCP_SEQ);
	/*
	 * 좋아요 등록하러 가기
	 */
	public void goDishLike(int RCP_SEQ, int mnum);
	/*
	 * 좋아요 해제하러 가기 
	 */
	public void goDishDislike(int RCP_SEQ, int mnum);
	/*
	 * 글 추가시, 글번호
	 */
	public int getNum();
	/*
	 * 음식 게시물 댓글 - 추가 
	 */
	public void commAdd(DishComm dishComm);
	/*
	 * 음식 게시물 댓글 - 삭제
	 */
	public void commDelete(DishComm dishComm);
	/*
	 * 음식 게시물 댓글 - 수정
	 */
	public void commEdit(DishComm dishComm);
	/*
	 * 음식 게시물 댓글 - 전체 가져오기 
	 */
	public List<DishComm> commGet(int rCP_SEQ);
	/*
	 * 레시피 게시물 검색 기능
	 */
	public List<Map<String, Object>> search(String select, String searchI);

	
	
}
