package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.timelineVO;

public interface TimelineService {

	public ArrayList<timelineVO> getList();
	public ArrayList<timelineVO> timedesc();
	public int submit(timelineVO timeline);
}
