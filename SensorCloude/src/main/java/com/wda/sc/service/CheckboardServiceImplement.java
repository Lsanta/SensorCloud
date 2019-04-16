package com.wda.sc.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.Paging;
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
		System.out.println("오냐 시발럼아");
		mapper.insertSelectKey(board);
		
		if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}

		board.getAttachList().forEach(attach -> {

			attach.setBoard_no(board.getBoard_no());
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
	public int filedelete(String board_no) {
		// 첨부파일 삭제
		return mapper.filedelete(board_no);
	}


	@Override
	public int checkboardDelete(String board_no) {
		// 게시글 삭제
		return mapper.checkboardDelete(board_no);
	}


	@Override
	public int getsiteid(String board_no) {
		// TODO Auto-generated method stub
		return mapper.getsiteid(board_no);
	}

	

}
