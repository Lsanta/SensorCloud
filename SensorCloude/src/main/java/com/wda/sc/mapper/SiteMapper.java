package com.wda.sc.mapper;

import java.util.ArrayList;

import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
<<<<<<< HEAD
import com.wda.sc.domain.Paging;
=======
import com.wda.sc.domain.CheckBoardVO;
>>>>>>> 69c6fdae2e1fd28af35d072e5d47f6947f9f59a0
import com.wda.sc.domain.SiteVO;

public interface SiteMapper {
	
	public ArrayList<SiteVO> getList();
	public ArrayList<CheckBoardVO> sitecheck(String site_id);
	public ArrayList<SiteVO> getSite(String site_id);
	public ArrayList<AlarmMemberVO> getAlarm_member(String site_id);
	public int siteadd(SiteVO site);
	public ArrayList<AlarmVO> getAlarm(String site_id);
<<<<<<< HEAD
	public int getPageNum();
	public ArrayList<SiteVO> getContent(Paging p);
=======
	public int insertAlarmMember(AlarmMemberVO vo);
>>>>>>> 69c6fdae2e1fd28af35d072e5d47f6947f9f59a0
}
