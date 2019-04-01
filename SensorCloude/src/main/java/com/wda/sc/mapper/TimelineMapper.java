package com.wda.sc.mapper;

import java.util.ArrayList;

import com.wda.sc.domain.TimelineVO;

public interface TimelineMapper {
    
	public ArrayList<TimelineVO> timedesc();
	public ArrayList<TimelineVO> getList();
	public int submit(TimelineVO vo);
}
