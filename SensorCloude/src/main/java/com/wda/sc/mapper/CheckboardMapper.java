package com.wda.sc.mapper;

import java.util.ArrayList;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.Paging;

public interface CheckboardMapper {

	public ArrayList<CheckBoardVO> getList(Paging p);
	
	public ArrayList<CheckBoardVO> viewgetList(String board_no);
	
	public int getPageNum();
}
