package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.SiteVO;

public interface SiteService {

	public ArrayList<SiteVO> getList();
	public ArrayList<SiteVO> getSite(String site_id);
	public ArrayList<AlarmMemberVO> getAlarm_member(String site_id);
	public int siteadd(SiteVO site);
	public ArrayList<AlarmVO> getAlarm(String site_id);
<<<<<<< HEAD
	public ArrayList<CheckBoardVO> sitecheck(String site_id);
=======
	public int insertAlarmMember(AlarmMemberVO vo);
>>>>>>> 18382c4758778881e0ddd47198f78b62bf40f56e
}
