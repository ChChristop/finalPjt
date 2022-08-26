package com.example.demo.pagelib;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Data;

@Data
public class PageResultDTO<VO,DTO> {
	
	//DTO 리스트
	private List<DTO> dtoList;
	
	//총 페이지 번호
	private int totalPage;
	
	//현재 페이지 번호
	private int page;
	
	//목록 사이즈
	private int size;
	
	//시작 페이지 번호, 끝 페이지 번호
	private int start, end;
	
	//이전, 다음
	private boolean prev, next;
	
	//페이지 번호 목록
	private List<Integer> pageList;
	
	public PageResultDTO(List<VO> result, Function<VO,DTO> fn, PageRequestDTO pageRequestDTO, int totalpage){

		dtoList = result.stream().map(fn).collect(Collectors.toList());
		
		this.page = pageRequestDTO.getPage();
		
		this.size = pageRequestDTO.getSize();
		
		this.totalPage = (int)Math.ceil(totalpage / (double)this.size);
		
		makePageList();
	}
	
	private void makePageList() {
		
		int tempEnd = (int)(Math.ceil(page/10.0)) * 10;
		
		start = tempEnd - 9;
		
		prev = start > 1;
		
		end = totalPage > tempEnd ? tempEnd : totalPage;
		
		next = totalPage > tempEnd;
		
		pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
		
	}
	
	
	
	


}
