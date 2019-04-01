package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.Paging;

public interface CheckboardService {

	public ArrayList<CheckBoardVO> getList(Paging p);
	
	public ArrayList<CheckBoardVO> viewgetList(String board_no);

	public int getPageNum();
	
}
