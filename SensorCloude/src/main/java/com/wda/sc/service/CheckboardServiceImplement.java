package com.wda.sc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wda.sc.domain.CheckBoardFileVO;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
import com.wda.sc.mapper.BoardAttachMapper;
import com.wda.sc.mapper.CheckboardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Service
@AllArgsConstructor
public class CheckboardServiceImplement implements CheckboardService {

	@Setter(onMethod_ = @Autowired)
	private CheckboardMapper mapper;

	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	@Transactional
	@Override
	public void register(CheckBoardVO board) {

		mapper.insertSelectKey(board);
		
		if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}

		board.getAttachList().forEach(attach -> {

			attach.setBoard_no(board.getBoard_no());
			attachMapper.insert(attach);
		});
	}
	
	@Transactional
	@Override
	public void fileupdate(CheckBoardVO vo) {
		
		filedelete(vo.getBoard_no());
		
		System.out.println(vo.getBoard_no());
		
		if (vo.getAttachList() == null || vo.getAttachList().size() <= 0) {
	         return;
	      }

	      vo.getAttachList().forEach(attach -> {

	         attach.setBoard_no(vo.getBoard_no());
	         attachMapper.insert(attach);
	      });
	}
	
	
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
	
	@Override
	public ArrayList<CheckBoardVO> mainList(){
		return mapper.mainList();
	}
	
	@Override
	public int insertcheckboard(CheckBoardVO checkboard) {
		// TODO Auto-generated method stub
		System.out.println(checkboard);
		return mapper.insertcheckboard(checkboard);
	}


	@Override
	public int checkauthority(String user_id) {
		// TODO Auto-generated method stub
		return mapper.checkauthority(user_id);
	}


	@Override
	public int filedelete(int board_no) {
		// 첨부파일 삭제
		return mapper.filedelete(board_no);
	}


	@Override
	public int checkboardDelete(int board_no) {
		// 게시글 삭제
		return mapper.checkboardDelete(board_no);
	}
	
	@Override
	public List<CheckBoardFileVO> getAttachList(int board_no) {
		// 첨부파일 반환
		return attachMapper.findByBno(board_no);
	}
	@Override
	public List<CheckBoardFileVO> getAttachListmain(int board_no) {
		// 중복된 board_no 제거후 첨부파일 반환
		System.out.println("맵퍼" + board_no);
		return attachMapper.findByBnomain(board_no);
	}

	@Override
	public int getsiteid(String board_no) {
		// TODO Auto-generated method stub
		return mapper.getsiteid(board_no);
	}

	@Override
	public ArrayList<CheckBoardVO> checkSearch(Search s) {
		// 검색1
		return mapper.checkSearch(s);
	}


	@Override
	public ArrayList<CheckBoardVO> getSearchResult(Map<Object, Object> parm) {
		// 검색2
		return mapper.getSearchResult(parm);
	}


	@Override
	public int updateCheck(CheckBoardVO vo) {
		// 수리내역 게시글 수정
		return mapper.updateCheck(vo);
	}

	@Override
	public int updateCheckBoard(CheckBoardVO vo) {
		//점검이력  게시글 수정
		return mapper.updateCheckBoard(vo);
	}

	@Override
	public ArrayList<CheckBoardVO> dateChange(int data) {
		// 최근 ?개월 
		return mapper.dateChange(data);
	}

	@Override
	public ArrayList<CheckBoardVO> getTermList(Map<Object, Object> parm) {
		//기간 검색 + 페이징
		return mapper.getTermList(parm);
	}

	@Override
	   public ArrayList<CheckBoardVO> apprepairList(String site_id){
	      return mapper.apprepairList(site_id);
	}

	@Override
	public void mfileinsert(CheckBoardFileVO checkboardfilevo) {
		// TODO Auto-generated method stub
		attachMapper.mfileinsert(checkboardfilevo);
	}


}
