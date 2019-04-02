package com.wda.sc.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.mapper.UsermanageMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsermanageServiceImplement implements UsermanageService{

	private UsermanageMapper mapper;
	
	@Override
	public ArrayList<MemberVO> getList(Paging p){
		return mapper.getList(p);
	}
	
	@Override
	public ArrayList<MemberVO> getInfo(String id) {
		return mapper.getInfo(id);
	}
	@Override
	public int getPageNum() {
		// TODO Auto-generated method stub
		return mapper.getPageNum();
	}
}
