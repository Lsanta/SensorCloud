package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.Paging;

public interface UsermanageService {

	public ArrayList<MemberVO> getList(Paging p);
	
	public ArrayList<MemberVO> getInfo(String id);
	
	public int getPageNum();
	
	public int updateuser(MemberVO vo);

	public int requestlevel(MemberVO vo);
}
