package com.wda.sc.service;

import java.util.ArrayList;
import java.util.Map;

import com.wda.sc.domain.CompanyVO;
import com.wda.sc.domain.MemberVO;

public interface LoginService {
	
	public ArrayList<MemberVO> login(String id);
	public int signup(MemberVO member);
	public ArrayList<MemberVO> idFind(String name);
	public int keyUpdate(Map<String, String> map);
	//id 넣어서 이름찾기
	public String nameFind(String user_id);
	
	//회사 이름 넣어서 존재여부 찾기
	public ArrayList<CompanyVO> findCompany(String name);
	//회사 추가
	public int insertCompany(CompanyVO vo);
	//이름으로 회사번호 찾기
	public int getCompanyNum(String name);
	//모든 회사 가져오기
	public ArrayList<CompanyVO> getAllCompany();
}
