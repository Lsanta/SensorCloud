package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.MemberVO;

public interface UsermanageService {

	public ArrayList<MemberVO> getList();
	public ArrayList<MemberVO> getInfo(String id);
}
