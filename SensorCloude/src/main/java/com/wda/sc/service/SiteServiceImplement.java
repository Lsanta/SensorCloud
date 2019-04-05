package com.wda.sc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.SiteVO;
import com.wda.sc.mapper.SiteMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SiteServiceImplement implements SiteService {
   
   private SiteMapper mapper;
   
   @Override
   public ArrayList<SiteVO> getList() {
      return mapper.getList();
   }
   
   @Override
   public ArrayList<CheckBoardVO> repairList(Map<String, Object> parm) {
      //site_id를 통한 해당현장에 대한 점검이력 가져오기
      return mapper.repairList(parm);
   }

   @Override
   public ArrayList<SiteVO> getSite(String site_id) {
      //site_id를 통한 현장정보 가져오기
      return mapper.getSite(site_id);
   }

   @Override
   public ArrayList<AlarmMemberVO> getAlarm_member(String site_id) {
      // site_id를 통한 알람멤버 가져오기
      return mapper.getAlarm_member(site_id);
   }
   
   @Override
   public int siteadd(SiteVO site) {
      return mapper.siteadd(site);
   }
   @Override
   public int networkadd(SiteVO site) {
      return mapper.networkadd(site);
   }
   

   @Override
   public ArrayList<AlarmVO> getAlarm(String site_id) {
      // 알람 테이블 반환
      return mapper.getAlarm(site_id);
   }

   @Override
   public int insertAlarmMember(AlarmMemberVO vo) {
      // 알람 멤버 추가
      
      return mapper.insertAlarmMember(vo);
      
   }
   
   @Override
   public int getPageNum() {
      // TODO Auto-generated method stub
      return mapper.getPageNum();
   }
   
   @Override
   public int repairPageNum(String site_id) {
      // TODO Auto-generated method stub
      return mapper.repairPageNum(site_id);
   }
   
   @Override
   public ArrayList<SiteVO> getContent(Paging p) {
      // TODO Auto-generated method stub
      return mapper.getContent(p);
   }

   @Override
   public int insertAlarm(AlarmVO vo) {
      // TODO Auto-generated method stub
      return mapper.insertAlarm(vo);
   }

@Override
public int modAlarm(AlarmMemberVO vo) {
    //연락망 사람 수정
	return mapper.modAlarm(vo);
}

@Override
public boolean delAlarm(AlarmMemberVO vo) {
	//연락망 사람 삭제
	return mapper.delAlarm(vo);
}

@Override
public List<String> getSensorKind() {
	//보유 센서 종류 조회
	return mapper.getSensorKind();
}

@Override
public ArrayList<SiteVO> getchecksite(){
	//점검이력 글쓰기 현장이름
	return mapper.getchecksite();
}

}