package com.wda.sc.mapper;

import java.util.ArrayList;

import com.wda.sc.domain.InstallSensorVO;
import com.wda.sc.domain.MysensorVO;

public interface MysensorMapper {

	public ArrayList<MysensorVO> getList();
	public int insertmysensor(MysensorVO vo);
	
	public ArrayList<InstallSensorVO> installSelect(String sensor_sn);
	
	public boolean deleteInstallSensor(String sensor_sn);

	public boolean deleteInfoSensor(String sensor_sn);

	public boolean deleteMySensor(String sensor_sn);
	
}
