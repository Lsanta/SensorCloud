package com.wda.sc.mapper;

import java.util.ArrayList;
import java.util.Map;

import com.wda.sc.domain.InstallSensorVO;
import com.wda.sc.domain.MysensorVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
import com.wda.sc.domain.SensorDataVO;

public interface MysensorMapper {

	public ArrayList<MysensorVO> getList(Paging page);
	
	public int insertmysensor(MysensorVO vo);
	
	public ArrayList<InstallSensorVO> installSelect(String sensor_sn);
	// 보유 센서 수정
	public int modmysensor(MysensorVO vo);
	
	public int getPageNum();
	
	//검색1
	public ArrayList<MysensorVO> mysensorSearch(Search s);
	//검색2
	public ArrayList<MysensorVO> getSearchResult(Map<Object, Object> parm);
	
	//현장 추가에 넘길 보유센서 목록
	public ArrayList<MysensorVO> getMysensor();
	
	//현장 설치되어있는 센서 추가
	public int insertInstallsensor(InstallSensorVO vo);

	//현장 설치되어 있는 센서 수정
	public int modInstallsensor(InstallSensorVO vo);
	
	//현장 설치되어 있는 센서 삭제
	public int delInstallsensor(int sensor_id);
	
	//현장에 있는 설치 센서 모두 삭제 (현장 비활성화 시)
	public int delSiteInstallsensor(String site_id);

}
