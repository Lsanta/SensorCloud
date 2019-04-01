package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.MysensorVO;

public interface MysensorService {

	public ArrayList<MysensorVO> getList();

	public int insertmysensor(MysensorVO vo);
}
