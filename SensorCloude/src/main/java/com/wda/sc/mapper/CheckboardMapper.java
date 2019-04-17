package com.wda.sc.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wda.sc.domain.CheckBoardFileVO;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;

public interface CheckboardMapper {

	public ArrayList<CheckBoardVO> getList(Paging p);
	
	public ArrayList<CheckBoardVO> viewgetList(String board_no);
	
	public int getPageNum();

	public ArrayList<CheckBoardVO> mainList();
	
	public int insertcheckboard(CheckBoardVO checkboard);
	
	public int insertSelectKey(CheckBoardVO checkboard);

	public int checkauthority(String user_id);
	
	public List<CheckBoardFileVO> findByBno(int board_no);

	//첨부파일 테이블 삭제
	public int filedelete(String board_no);
	
	//게시글 삭제
	public int checkboardDelete(String board_no);

	public int getsiteid(String board_no);
	
	//검색1
	public ArrayList<CheckBoardVO> checkSearch(Search s);
	//검색2
	public ArrayList<CheckBoardVO> getSearchResult(Map<Object, Object> parm);
	
}
