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
	public int filedelete(int board_no);
	
	//게시글 삭제
	public int checkboardDelete(int board_no);

	public int getsiteid(String board_no);
	
	//검색1
	public ArrayList<CheckBoardVO> checkSearch(Search s);
	//검색2
	public ArrayList<CheckBoardVO> getSearchResult(Map<Object, Object> parm);
	
	//수리내역 게시글 수정
	public int updateCheck(CheckBoardVO vo);
	
	//파일 수정(삭제 후 다시 추가)
	public void fileupdate(CheckBoardVO vo);
	
	//점검이력  게시글 수정
	public int updateCheckBoard(CheckBoardVO vo);
	
	//최근 ?개월
	public ArrayList<CheckBoardVO> dateChange(int data);
	
	//기간 검색 + 페이징
	public ArrayList<CheckBoardVO> getTermList(Map<Object, Object> parm);
	
	// 앱 현장 클릭시 해당 현장에대한 점검이력 글
	public ArrayList<CheckBoardVO> apprepairList(String site_id);
	
	public List<CheckBoardFileVO> mgetAttachList(int board_no);
}

