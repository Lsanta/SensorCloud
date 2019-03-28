package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.MemberVO;

public interface LoginService {
	
	public ArrayList<MemberVO> login(String id);
	public int signup(MemberVO member);
	public ArrayList<MemberVO> idFind(String name);
}
