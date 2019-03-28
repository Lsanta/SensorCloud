package com.wda.sc.mapper;

import java.util.ArrayList;

import com.wda.sc.domain.MemberVO;

public interface MyPageUserModify {
	public ArrayList<MemberVO> confirmpasswd(String confirmid);
	
	public void updateuserinfo(MemberVO vo);
	
}
