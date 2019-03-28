package com.wda.sc.mapper;

import java.util.ArrayList;
import com.wda.sc.domain.MemberVO;

public interface LoginMapper {

	public ArrayList<MemberVO> login(String id);
	public int signup(MemberVO member);
	public ArrayList<MemberVO> idFind(String name);
}
