package com.wda.sc.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.mapper.MyPageMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MyPageServiceImplement implements MyPageService {
	private MyPageMapper mapper;

	@Override
	public ArrayList<MemberVO> getInfo(String id) {
		// TODO Auto-generated method stub
		return mapper.getInfo(id);
	}
	
	public ArrayList<CheckBoardVO> myList(Map<String, Object> parm){
		return mapper.myList(parm);
	}
	
	@Override
	public ArrayList<MemberVO> confirmpasswd(String confirmid) {
		// TODO Auto-generated method stub
		return mapper.confirmpasswd(confirmid);
	}
	
	@Override
	public void updateuserinfo(MemberVO vo) {
		// TODO Auto-generated method stub
		mapper.updateuserinfo(vo);
	}
	
	@Override
	public int getPageNum(String id) {
		// TODO Auto-generated method stub
		return mapper.getPageNum(id);
	}

	@Override
	public ArrayList<CheckBoardVO> myListView(String board_no) {
		// TODO Auto-generated method stub
		return mapper.myListView(board_no);
	}
}
