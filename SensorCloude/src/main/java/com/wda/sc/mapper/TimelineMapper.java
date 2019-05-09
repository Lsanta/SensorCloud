package com.wda.sc.mapper;

import java.util.ArrayList;

import com.wda.sc.domain.Paging;
import com.wda.sc.domain.TimelineVO;

public interface TimelineMapper {
    
	public ArrayList<TimelineVO> timedesc();
	public ArrayList<TimelineVO> getList(Paging p);
	public int insert(TimelineVO vo);
	public int timelinedelete(String content);
	public int timelinemodify(TimelineVO vo);
	public int getPageNum();
	public ArrayList<TimelineVO> getListm();

	
}
