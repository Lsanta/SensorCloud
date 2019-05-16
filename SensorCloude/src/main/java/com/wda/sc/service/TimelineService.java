package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.Paging;
import com.wda.sc.domain.TimelineVO;

public interface TimelineService {

	public ArrayList<TimelineVO> getList(Paging p);
	public ArrayList<TimelineVO> timedesc();
	public int insert(TimelineVO vo);
	public int timelinedelete(String content);
	public int timelinemodify(TimelineVO vo);
	public int getPageNum();
	public ArrayList<TimelineVO> getListm();
	public ArrayList<TimelineVO> getContent(String timeline_n);

	
	

}
