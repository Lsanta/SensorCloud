package com.wda.sc.mapper;

import java.util.ArrayList;
import java.util.Map;

import com.wda.sc.domain.CompanyVO;
import com.wda.sc.domain.MemberVO;

public interface LoginMapper {

	public ArrayList<MemberVO> login(String id);
	public int signup(MemberVO member);
	public ArrayList<MemberVO> idFind(String name);
	public int keyUpdate(Map<String, String> map);
	//id 넣어서 이름찾기
	public String nameFind(String user_id);
}
