package com.example.demo.service.ingredients;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dao.IngredientsDAO;
import com.example.demo.vo.IngredientsVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/*
 * @Service
 * 
 * 
 * 
 * @RequiredArgsConstructor public class IngredientsServiceImpl implements
 * IngredientsService{
 * 
 * private final IngredientsDAO ingredientsDAO;
 * 
 * @Override public long register(IngredientsVO ingredientsVO) {
 * 
 * Long ingrnum = ingredientsDAO.addIngredients(ingredientsVO);
 * 
 * log.info("재료 추가(서비스) : " + ingredientsVO.getIngrnum());
 * 
 * //업데이트가 잘 되었으면 1 반환 됨 return (ingrnum==1)?ingredientsVO.getIngrnum():ingrnum;
 * 
 * }
 * 
 * @Override public long remove(long ingrnum) {
 * 
 * ingredientsDAO.deleteIngredients(ingrnum);
 * 
 * log.info("재료 삭제(서비스) : " + ingrnum);
 * 
 * return ingrnum; }
 * 
 * 
 * @Override public List<IngredientsVO> findAllList() {
 * 
 * List<IngredientsVO> result = ingredientsDAO.ingredientsAllList();
 * 
 * log.info("재료 리스트(서비스) : " + result);
 * 
 * return result; }
 * 
 * @Override public long update(IngredientsVO ingredientsVO) {
 * 
 * ingredientsDAO.upgradeIngredients(ingredientsVO);
 * 
 * log.info("재료 수정(서비스) : " + ingredientsVO.getIngrnum() ) ;
 * 
 * return ingredientsVO.getIngrnum(); }
 * 
 * @Override public boolean findIngredeint(long ingrnum) { // TODO
 * Auto-generated method stub return false; }
 * 
 * @Override public boolean findIngredeint(String iname) { // TODO
 * Auto-generated method stub return false; }
 * 
 * 
 * 
 * 
 * }
 */
