package com.example.demo.pagelib;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class  PageResultVO<VO> {

	//DTO 리스트
	private List<VO> dtoList;
	
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
	
	public PageResultVO(List<VO> result, PageRequestDTO pageRequestDTO, int totalpage){

		this.dtoList = result;
		
		this.page = pageRequestDTO.getPage()+1;
		
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
