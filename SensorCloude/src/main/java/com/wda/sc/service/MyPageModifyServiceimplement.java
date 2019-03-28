package com.wda.sc.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.mapper.MyPageUserModify;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MyPageModifyServiceimplement implements MyPageModifyService {
	private MyPageUserModify mapper;

	@Override
	public ArrayList<MemberVO> confirmpasswd(String confirmid) {
		// TODO Auto-generated method stub
		return mapper.confirmpasswd(confirmid);
	}
	
	@Override
	public void updateuserinfo(MemberVO vo) {
		// TODO Auto-generated method stub
		mapper.updateuserinfo(vo);
	}
}
