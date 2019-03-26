package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.alarm_memberVO;
import com.wda.sc.domain.siteVO;

public interface SiteService {

	public ArrayList<siteVO> getList();
	public ArrayList<siteVO> getSite(String site_id);
	public ArrayList<alarm_memberVO> getAlarm_member(String site_id);
	public int siteadd(siteVO site);
}
