package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.MemberVO;

public interface MyPageModifyService {
	public ArrayList<MemberVO> confirmpasswd(String confirmid);
	
	public void updateuserinfo(memberVO vo);
}
