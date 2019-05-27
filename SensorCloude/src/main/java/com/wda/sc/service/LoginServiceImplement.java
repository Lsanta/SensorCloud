package com.wda.sc.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;
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
	
}
