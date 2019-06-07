package com.wda.sc.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.InstallSensorVO;
import com.wda.sc.domain.MysensorVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
import com.wda.sc.domain.SensorDataVO;
import com.wda.sc.mapper.MysensorMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MysensorServiceImplement implements MysensorService {

	private MysensorMapper mapper;
	
	@Override
	public ArrayList<MysensorVO> getList(Paging page){
		return mapper.getList(page);
	}

	@Override
	public int insertmysensor(MysensorVO vo) {
		// TODO Auto-generated method stub
		return mapper.insertmysensor(vo);
	}

	@Override
	public ArrayList<InstallSensorVO> installSelect(String sensor_sn) {
		//설치 센서 정보 조회
		return mapper.installSelect(sensor_sn);
	}

	@Override
	public int modmysensor(MysensorVO vo) {
		// 보유 센서 수정
		return mapper.modmysensor(vo);
	}

	@Override
	public int getPageNum() {
		return mapper.getPageNum();
	}

	@Override
	public ArrayList<MysensorVO> mysensorSearch(Search s) {
		return mapper.mysensorSearch(s);
	}

	@Override
	public ArrayList<MysensorVO> getSearchResult(Map<Object, Object> parm) {
		
		return mapper.getSearchResult(parm);
	}

	@Override
	public ArrayList<MysensorVO> getMysensor() {
		//현장 추가에 넘길 보유센서 목록
		
		return mapper.getMysensor();
	}

	
	
}
