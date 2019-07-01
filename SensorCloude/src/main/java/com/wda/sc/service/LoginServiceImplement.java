package com.wda.sc.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.CompanyVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.mapper.LoginMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginServiceImplement implements LoginService {
	
	private LoginMapper mapper;
	
	@Override
	public ArrayList<MemberVO> login(String id) {
		return mapper.login(id);
	}
	
	@Override
	public int signup(MemberVO member) {
		// TODO Auto-generated method stub
		System.out.println(member);
		return mapper.signup(member);
	}

	@Override
	public ArrayList<MemberVO> idFind(String name) {
		// TODO Auto-generated method stub
		return mapper.idFind(name);
	}
	
	@Override
	public int keyUpdate(Map<String, String> map) {
		// TODO Auto-generated method stub
		return mapper.keyUpdate(map);
	}

	@Override
	public String nameFind(String user_id) {
		// TODO Auto-generated method stub
		return mapper.nameFind(user_id);
	}

	@Override
	public ArrayList<CompanyVO> findCompany(String name) {
		// 회사 이름을 기준으로 회사 존재여부 찾기 
		return mapper.findCompany(name);
	}

	@Override
	public int insertCompany(CompanyVO vo) {
		// 회사 추가
		return mapper.insertCompany(vo);
	}

	@Override
	public int getCompanyNum(String name) {
		//이름으로 회사번호 찾기
		return mapper.getCompanyNum(name);
	}

	@Override
	public ArrayList<CompanyVO> getAllCompany() {
		//모든 회사 가져오기
		return mapper.getAllCompany();
	}
	
}
