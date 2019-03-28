package com.wda.sc.mapper;

import java.util.ArrayList;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.MemberVO;

public interface MyPageMapper {
	public ArrayList<MemberVO> getInfo(String id);
	
	public ArrayList<CheckBoardVO> myList(String id);
	
	public ArrayList<MemberVO> confirmpasswd(String confirmid);
	
	public void updateuserinfo(MemberVO vo);
	
}
