package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.TimelineVO;

public interface TimelineService {

	public ArrayList<TimelineVO> getList();
	public ArrayList<TimelineVO> timedesc();
	public int submit(TimelineVO timeline);
	public Object getInfo(String id);
}
