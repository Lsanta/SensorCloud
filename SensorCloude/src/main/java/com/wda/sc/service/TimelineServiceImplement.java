package com.wda.sc.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.TimelineVO;
import com.wda.sc.mapper.TimelineMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TimelineServiceImplement implements TimelineService {

	private TimelineMapper mapper;

	@Override
	public int submit(TimelineVO vo) {
		// TODO Auto-generated method stub
		return mapper.submit(vo);
	}

	@Override
	public ArrayList<TimelineVO> getList() {
		// TODO Auto-generated method stub
		return mapper.getList();
	}
	
	@Override
	public ArrayList<TimelineVO> timedesc() {
		// TODO Auto-generated method stub
		return mapper.timedesc();
	}

	@Override
	public Object getInfo(String id) {
		// TODO Auto-generated method stub
		return null;
	}


}
