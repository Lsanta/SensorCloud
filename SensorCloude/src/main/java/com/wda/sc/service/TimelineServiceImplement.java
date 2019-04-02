package com.wda.sc.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.TimelineVO;
import com.wda.sc.mapper.TimelineMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TimelineServiceImplement implements TimelineService {

	private TimelineMapper mapper;

	@Override
	public int insert(TimelineVO vo) {
		// TODO Auto-generated method stub
		return mapper.insert(vo);
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
	public ArrayList<MemberVO> getInfo(String id) {
		// TODO Auto-generated method stub
		return null;
	}


}
