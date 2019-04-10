package com.wda.sc.mapper;

import java.util.ArrayList;

import com.wda.sc.domain.InstallSensorVO;
import com.wda.sc.domain.MysensorVO;

public interface MysensorMapper {

	public ArrayList<MysensorVO> getList();
	public int insertmysensor(MysensorVO vo);
	
	public ArrayList<InstallSensorVO> installSelect(String sensor_sn);
	// 보유 센서 수정
	public int modmysensor(MysensorVO vo);
	
	
}
