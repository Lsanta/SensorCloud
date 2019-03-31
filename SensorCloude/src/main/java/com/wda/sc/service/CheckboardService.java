package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.CheckBoardVO;

public interface CheckboardService {

	public ArrayList<CheckBoardVO> getList();
	
	public ArrayList<CheckBoardVO> viewgetList(String board_no);

	
}
