package com.wda.sc.service;

import java.util.ArrayList;
import java.util.Map;

import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.SiteVO;

public interface SiteService {

	public ArrayList<SiteVO> getList();
	public ArrayList<SiteVO> getSite(String site_id);
	public ArrayList<AlarmMemberVO> getAlarm_member(String site_id);
	public ArrayList<AlarmVO> getAlarm(String site_id);
	public int getPageNum();
	public ArrayList<SiteVO> getContent(Paging p);
	public int insertAlarmMember(AlarmMemberVO vo);
	public int insertAlarm(AlarmVO vo);
	public ArrayList<CheckBoardVO> repairList(Map<String, Object> parm);
	public int repairPageNum(String site_id);
	public int insertsite(SiteVO site);

}
