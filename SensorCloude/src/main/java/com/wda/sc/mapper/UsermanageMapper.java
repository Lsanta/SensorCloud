package com.wda.sc.mapper;

import java.util.ArrayList;

import com.wda.sc.domain.MemberVO;

public interface UsermanageMapper {

	public ArrayList<MemberVO> getList();
	public ArrayList<MemberVO> getInfo(String id);
}
