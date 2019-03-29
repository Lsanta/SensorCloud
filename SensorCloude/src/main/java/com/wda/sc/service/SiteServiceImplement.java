package com.wda.sc.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.SiteVO;
import com.wda.sc.mapper.SiteMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SiteServiceImplement implements SiteService {
	
	private SiteMapper mapper;
	
	@Override
	public ArrayList<SiteVO> getList() {
		return mapper.getList();
	}

	@Override
	public ArrayList<SiteVO> getSite(String site_id) {
		//site_id를 통한 현장정보 가져오기
		return mapper.getSite(site_id);
	}

	@Override
	public ArrayList<AlarmMemberVO> getAlarm_member(String site_id) {
		// site_id를 통한 알람멤버 가져오기
		return mapper.getAlarm_member(site_id);
	}
	
	@Override
	public int siteadd(SiteVO site) {
		System.out.println(site);
		return mapper.siteadd(site);
	}

	@Override
	public ArrayList<AlarmVO> getAlarm(String site_id) {
		// TODO Auto-generated method stub
		return mapper.getAlarm(site_id);
	}
	

}
