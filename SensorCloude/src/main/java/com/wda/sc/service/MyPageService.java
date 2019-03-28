package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.MemberVO;

public interface MyPageService {
	public ArrayList<MemberVO> getInfo(String id);
	
	public ArrayList<CheckBoardVO> myList(String id);
	
	public ArrayList<MemberVO> confirmpasswd(String confirmid);
	
	public void updateuserinfo(MemberVO vo);
}
