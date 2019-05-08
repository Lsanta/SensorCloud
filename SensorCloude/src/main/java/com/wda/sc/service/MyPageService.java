package com.wda.sc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.MemberFileVO;
import com.wda.sc.domain.MemberVO;


public interface MyPageService {
	
	public ArrayList<MemberVO> getInfo(String id);
	
	public ArrayList<CheckBoardVO> myList(Map<String, Object> parm);
	
	//앱용 점검이력
	public ArrayList<CheckBoardVO> mcheck();
	
	public ArrayList<MemberVO> confirmpasswd(String confirmid);
	
	public void updateuserinfo(MemberVO vo);
	
	public int getPageNum(String id);

	public ArrayList<CheckBoardVO> myListView(String board_no);
	
	public void insert(MemberVO member);
	
	public List<MemberFileVO> getAttachListmypage(String user_id);
	
	public void mypagedelete(String user_id);

}
