package com.wda.sc.mapper;

import java.util.ArrayList;
import java.util.Map;

import com.wda.sc.domain.InstallSensorVO;
import com.wda.sc.domain.MysensorVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
import com.wda.sc.domain.SensorInfoVO;

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
	
	//센서데이터 표
	public ArrayList<SensorInfoVO> getData(String site_id);

}
