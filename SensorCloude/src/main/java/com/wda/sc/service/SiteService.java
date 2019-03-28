package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.SiteVO;

public interface SiteService {

	public ArrayList<SiteVO> getList();
	public ArrayList<SiteVO> getSite(String site_id);
	public ArrayList<AlarmMemberVO> getAlarm_member(String site_id);
	public int siteadd(SiteVO site);
}
