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
	public ArrayList<timelineVO> getList(){
		return mapper.getList();
	}
}
