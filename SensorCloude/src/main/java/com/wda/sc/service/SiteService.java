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
<<<<<<< HEAD
	public ArrayList<CheckBoardVO> repairList(String site_id);
	
=======
	public ArrayList<CheckBoardVO> repairList(Map<String, Object> parm);
	public int repairPageNum(String site_id);
<<<<<<< HEAD
	public int insertsite(SiteVO site);
=======
>>>>>>> 32d6b9934308e5c6194e05fd9d2a956166b1373e
>>>>>>> 281920c6edf1c3599a394091684898b86ae3b34b

}
