package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.SiteVO;

public interface SiteService {

	public ArrayList<SiteVO> getList();
	public ArrayList<SiteVO> getSite(String site_id);
	public ArrayList<AlarmMemberVO> getAlarm_member(String site_id);
	public int siteadd(SiteVO site);
	public ArrayList<AlarmVO> getAlarm(String site_id);
	public int getPageNum();
	public ArrayList<SiteVO> getContent(Paging p);
	public int insertAlarmMember(AlarmMemberVO vo);
	public int insertAlarm(AlarmVO vo);
	public ArrayList<CheckBoardVO> repairList(String site_id);
	

}
