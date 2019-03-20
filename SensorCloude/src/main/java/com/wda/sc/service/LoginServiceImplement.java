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
}
