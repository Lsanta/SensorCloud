package com.wda.sc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wda.sc.domain.CheckBoardFileVO;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;

public interface CheckboardService {

	public ArrayList<CheckBoardVO> getList(Paging p);
	
	public ArrayList<CheckBoardVO> viewgetList(String board_no);

	public int getPageNum();
	
	public ArrayList<CheckBoardVO> mainList();
	
	public int insertcheckboard(CheckBoardVO checkboard);

	public int checkauthority(String user_id);
	
	public void register(CheckBoardVO board);

	//첨부파일 조회
	public List<CheckBoardFileVO> getAttachList(int board_no);
	//첨부파일 테이블 삭제
	public int filedelete(int board_no);
	//메인 중복된 board_no 제거후 첨부파일 조회
	public List<CheckBoardFileVO> getAttachListmain(int board_no);
	//게시글 삭제
	public int checkboardDelete(int board_no);
	//현장아이디 넘기기
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

	public void mfileinsert(CheckBoardFileVO checkboardfilevo);
	
	public List<CheckBoardFileVO> mgetAttachList(int board_no);
	
	public int mfiledelete(Map<String ,Object> map);

	public List<String> findFilename(int board_no);
	
	// 상태가 open인 점검이력만 가져오기
	public ArrayList<CheckBoardVO> getOpenList(Paging page);

	// 상태가 fixed인 점검이력만 가져오기
	public ArrayList<CheckBoardVO> getFixedList(Paging page);

	// 상태가 close인 점검이력만 가져오기
	public ArrayList<CheckBoardVO> getCloseList(Paging page);

	// 점검이력 관리에서의 날짜 검색
	public ArrayList<CheckBoardVO> adminDateChange(Map<String, Integer> date);

	// 점검이력 관리에서의 기간 검색 + 페이징
	public ArrayList<CheckBoardVO> getAdminTermList(Map<Object, Object> parm);

	//점검이력 관리 에서 총 점검이력 개수 받기
	public int checkManagePageNum(int status);

	//상태에 따른 점검이력 리스트
	public ArrayList<CheckBoardVO> getStatusList(Paging page);

	//점검이력 관리에서의 검색
	public ArrayList<CheckBoardVO> checkManageSearch(Map<String, Object> data);

	//점검이력 관리에서의 검색2
	public ArrayList<CheckBoardVO> getcheckManageSearch(Map<Object, Object> parm);

	//fixed로 업데이트
	public int fixedUpdate(int board_no);
	//close로 업데이트
	public int closeUpdate(int board_no);


}
