package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.TimelineVO;

public interface TimelineService {

	public ArrayList<TimelineVO> getList();
	public ArrayList<TimelineVO> timedesc();
	public int insert(TimelineVO vo);
	public ArrayList<MemberVO> getInfo(String id);
}
