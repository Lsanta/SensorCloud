package com.wda.sc.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
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
	
	@Override
	public int updateuser(MemberVO vo) {
		return mapper.updateuser(vo);
	}

	@Override
	public int requestlevel(MemberVO vo) {
		// TODO Auto-generated method stub
		return mapper.requestlevel(vo);
	}

	@Override
	public ArrayList<MemberVO> getSearchResult(Map<Object, Object> parm) {
		// 검색2
		return mapper.getSearchResult(parm);
	}

	@Override
	public ArrayList<MemberVO> manageSearch(Search s) {
		// 검색1
		return mapper.manageSearch(s);
	}
	
}
