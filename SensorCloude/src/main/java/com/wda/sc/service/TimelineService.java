package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.TimelineVO;

public interface TimelineService {

	public ArrayList<TimelineVO> getList();
	public ArrayList<TimelineVO> timedesc();
	public int insert(TimelineVO vo);
	public int timelinedelete(String content);
	public int timelinemodify(TimelineVO vo);


	
	

}
