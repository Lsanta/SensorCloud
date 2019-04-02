package com.wda.sc.service;

import java.util.ArrayList;
import java.util.Map;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.MemberVO;

public interface MyPageService {
	public ArrayList<MemberVO> getInfo(String id);
	
	public ArrayList<CheckBoardVO> myList(Map<String, Object> parm);
	
	public ArrayList<MemberVO> confirmpasswd(String confirmid);
	
	public void updateuserinfo(MemberVO vo);
	
	public int getPageNum(String id);
}
