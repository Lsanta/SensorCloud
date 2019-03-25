package com.wda.sc.service;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.wda.sc.domain.memberVO;
import com.wda.sc.mapper.LoginMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginServiceImplement implements LoginService {
	
	private LoginMapper mapper;
	
	@Override
	public ArrayList<memberVO> login(String id) {
		return mapper.login(id);
	}
	
	@Override
	public int signup(memberVO member) {
		// TODO Auto-generated method stub
		System.out.println(member);
		return mapper.signup(member);
	}
}
