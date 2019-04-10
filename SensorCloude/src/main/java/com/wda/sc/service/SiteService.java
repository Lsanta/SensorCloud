package com.wda.sc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.MysensorVO;
import com.wda.sc.domain.SiteVO;

public interface SiteService {

   public ArrayList<SiteVO> getList();
   
   public ArrayList<SiteVO> getSite(String site_id);
   
   public ArrayList<AlarmMemberVO> getAlarm_member(String site_id);
   //현장추가 site
   public int siteadd(SiteVO site);
   //현장추가 network
   public int networkadd(SiteVO site);
   
   //현장수정 select
   public ArrayList<SiteVO> joinSite(String site_id);
   //현장수정 site
   public int updatesite(SiteVO site);
   //현장수정 network
   public int updatenetwork(SiteVO site);
   
   public ArrayList<AlarmVO> getAlarm(String site_id);
   
   public int getPageNum();
   
   public ArrayList<SiteVO> getContent(Paging p);
   
   public int insertAlarmMember(AlarmMemberVO vo);
   
   public int insertAlarm(AlarmVO vo);
   
   public ArrayList<CheckBoardVO> repairList(Map<String, Object> parm);
   
   public int repairPageNum(String site_id);
   
   //연락망 사람 수정
   public int modAlarm(AlarmMemberVO vo);
   //연락망 사람 삭제
   public boolean delAlarm(AlarmMemberVO vo);
   //보유 센서 종류 조회
   public ArrayList<MysensorVO> getSensorKind();
   

   //점검이력 글쓰기 현장이름 조회
   public ArrayList<SiteVO> getchecksite();
   //현장 상태 띄우기
   public ArrayList<SiteVO> getStatus(String site_id);
   

}
  