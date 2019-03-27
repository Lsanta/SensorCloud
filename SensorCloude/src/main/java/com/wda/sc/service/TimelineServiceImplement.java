package com.wda.sc.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.timelineVO;
import com.wda.sc.mapper.TimelineMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TimelineServiceImplement implements TimelineService {

	private TimelineMapper mapper;

	@Override
	public int submit(timelineVO timeline) {
		// TODO Auto-generated method stub
		return mapper.submit(timeline);
	}

	@Override
	public ArrayList<timelineVO> getList() {
		// TODO Auto-generated method stub
		return null;
	}


}
