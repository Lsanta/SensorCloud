package com.wda.sc.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.TimelineVO;
import com.wda.sc.mapper.TimelineMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TimelineServiceImplement implements TimelineService {

	private TimelineMapper mapper;

	@Override
	public int insert(TimelineVO vo) {
		System.out.println(vo);
		return mapper.insert(vo);
	}

	@Override
	public ArrayList<TimelineVO> getList(Paging p) {

		return mapper.getList(p);
	}

	@Override
	public ArrayList<TimelineVO> timedesc() {

		return mapper.timedesc();
	}

	@Override
	public int timelinedelete(String content) {

		return mapper.timelinedelete(content);
	}

	@Override
	public int timelinemodify(TimelineVO vo) {

		return mapper.timelinemodify(vo);
	}

	@Override
	public int getPageNum() {

		return mapper.getPageNum();
	}

	@Override
	public ArrayList<TimelineVO> getListm() {
	
		return mapper.getListm();
	}

	@Override
	public ArrayList<TimelineVO> getContent(String timeline_n) {
	
		return mapper.getContent(timeline_n);
	}

}