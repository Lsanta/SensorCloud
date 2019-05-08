package com.wda.sc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.MemberFileVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.mapper.MemberAttachMapper;
import com.wda.sc.mapper.MyPageMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MyPageServiceImplement implements MyPageService {
	private MyPageMapper mapper;
	private MemberAttachMapper mymapper;

	@Override
	public ArrayList<MemberVO> getInfo(String id) {
		// TODO Auto-generated method stub
		return mapper.getInfo(id);
	}
	
	public ArrayList<CheckBoardVO> myList(Map<String, Object> parm){
		return mapper.myList(parm);
	}
	
	public ArrayList<CheckBoardVO> mcheck(){
		//앱용 점검이력
		return mapper.mcheck();
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
	
	@Override
	public void insert(MemberVO member) {
		if(member.getAttachList() == null || member.getAttachList().size() <=0) {
			return;
		}
		member.getAttachList().forEach(attach ->{
			attach.setUser_id(member.getUser_id());
			mymapper.insert(attach);
		});
	}
	
	@Override
	public List<MemberFileVO> getAttachListmypage(String user_id) {
		// 첨부파일 반환
		return mymapper.findByui(user_id);
	}
	
	@Override
	public void mypagedelete(String user_id) {
		// TODO Auto-generated method stub
		 mymapper.mypagedelete(user_id);
	}

	
}
