package com.wda.sc.mapper;

import java.util.ArrayList;

import com.wda.sc.domain.MysensorVO;

public interface MysensorMapper {

	public ArrayList<MysensorVO> getList();
	public int insertmysensor(MysensorVO vo);
	public int deletemysensor(MysensorVO vo);
}
