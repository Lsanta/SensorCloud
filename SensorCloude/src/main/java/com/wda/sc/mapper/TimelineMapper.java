package com.wda.sc.mapper;

import java.util.ArrayList;

import com.wda.sc.domain.timelineVO;

public interface TimelineMapper {
    
	public ArrayList<timelineVO> timedesc();
	public ArrayList<timelineVO> getList();
	public int submit(timelineVO timeline);
}
