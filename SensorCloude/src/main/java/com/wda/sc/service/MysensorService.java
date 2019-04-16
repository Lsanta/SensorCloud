package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.InstallSensorVO;
import com.wda.sc.domain.MysensorVO;
import com.wda.sc.domain.Paging;

public interface MysensorService {

	public ArrayList<MysensorVO> getList(Paging page);

	public int insertmysensor(MysensorVO vo);

	public ArrayList<InstallSensorVO> installSelect(String sensor_sn);

	public int modmysensor(MysensorVO vo);

	public int getPageNum();


}
