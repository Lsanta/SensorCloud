package com.wda.sc.mapper;

import java.util.ArrayList;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.Paging;

public interface UsermanageMapper {

	public ArrayList<MemberVO> getList(Paging p);
	public ArrayList<MemberVO> getInfo(String id);
	public int getPageNum();
}
