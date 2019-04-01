package com.wda.sc.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.mapper.CheckboardMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CheckboardServiceImplement implements CheckboardService {

	private CheckboardMapper mapper;
	
	@Override
	public ArrayList<CheckBoardVO> getList(Paging p){
		return mapper.getList(p);
	}

	@Override
	public ArrayList<CheckBoardVO> viewgetList(String board_no) {
		// TODO Auto-generated method stub
		return mapper.viewgetList(board_no);
	}
	
	@Override
	public int getPageNum() {
		// TODO Auto-generated method stub
		return mapper.getPageNum();
	}
}
