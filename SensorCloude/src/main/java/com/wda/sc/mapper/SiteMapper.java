package com.wda.sc.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.SiteVO;

public interface SiteMapper {
   
   public ArrayList<SiteVO> getList();
   
   public ArrayList<CheckBoardVO> repairList(Map<String, Object> parm);
   
   public ArrayList<SiteVO> getSite(String site_id);
   
   public ArrayList<AlarmMemberVO> getAlarm_member(String site_id);
   
   public int siteadd(SiteVO site);  
   
   public int networkadd(SiteVO site);
   
   public ArrayList<AlarmVO> getAlarm(String site_id);
   
   public int getPageNum();
   
   public ArrayList<SiteVO> getContent(Paging p);
   
   public int insertAlarmMember(AlarmMemberVO vo); 
   
   public int insertAlarm(AlarmVO vo);
   
   public int repairPageNum(String site_id);
   
   //연락망 사람 수정
   public int modAlarm(AlarmMemberVO vo);
   //연락망 사람 삭제
   public boolean delAlarm(AlarmMemberVO vo);
   
   //보유 센서 종류 조회
   public List<String> getSensorKind();
   

   //점검이력 글쓰기 현장이름 조회
   public ArrayList<SiteVO> getchecksite();

   //현장 상태 띄우기
   public ArrayList<SiteVO> getStatus(String site_id);
   	
}
