package com.wda.sc.mapper;

import java.util.ArrayList;
import java.util.Map;

import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.MysensorVO;
import com.wda.sc.domain.SiteVO;

public interface SiteMapper {
	
	public ArrayList<SiteVO> getList();
	
	public ArrayList<CheckBoardVO> repairList(Map<String, Object> parm);
	
	public ArrayList<SiteVO> getSite(String site_id);
	
	public ArrayList<AlarmMemberVO> getAlarm_member(String site_id);
	
	public int siteadd(SiteVO site);
	
	public ArrayList<AlarmVO> getAlarm(String site_id);
	
	public int getPageNum();
	
	public ArrayList<SiteVO> getContent(Paging p);
	
	public int insertAlarmMember(AlarmMemberVO vo);
	
	public int insertAlarm(AlarmVO vo);
<<<<<<< HEAD
 
=======
	
	public int repairPageNum(String site_id);
	
>>>>>>> 32d6b9934308e5c6194e05fd9d2a956166b1373e
}
