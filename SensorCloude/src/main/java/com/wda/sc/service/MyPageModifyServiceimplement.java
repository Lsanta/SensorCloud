package com.wda.sc.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.memberVO;
import com.wda.sc.mapper.LoginMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MyPageModifyServiceimplement implements MyPageModifyService{

	private MyPageModifyService mapper;

	@Override
	public ArrayList<memberVO> confirmpasswd(String confirmid) {
		// TODO Auto-generated method stub
		return mapper.confirmpasswd(confirmid);
	}
	
	
}
