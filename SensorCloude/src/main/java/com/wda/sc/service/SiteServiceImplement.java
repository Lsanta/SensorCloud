package com.wda.sc.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.InstallSensorVO;
import com.wda.sc.domain.MysensorVO;
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
		// site_id를 통한 해당현장에 대한 점검이력 가져오기
		return mapper.repairList(parm);
	}

	@Override
	public ArrayList<SiteVO> getSite(String site_id) {
		// site_id를 통한 현장정보 가져오기
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
	public int sensorPageNum(String site_id) {
		// TODO Auto-generated method stub
		return mapper.sensorPageNum(site_id);
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
		// 연락망 사람 수정
		return mapper.modAlarm(vo);
	}

	@Override
	public boolean delAlarm(AlarmMemberVO vo) {
		// 연락망 사람 삭제
		return mapper.delAlarm(vo);
	}

	@Override
	public ArrayList<MysensorVO> getSensorKind() {
		// 보유 센서 종류 조회
		return mapper.getSensorKind();
	}

	@Override
	public ArrayList<SiteVO> getchecksite() {
		// 점검이력 글쓰기 현장이름
		return mapper.getchecksite();
	}

	@Override
	public ArrayList<SiteVO> joinSite(String site_id) {
		// site_id를 통한 현장정보 가져오기
		return mapper.joinSite(site_id);
	}

	@Override
	public int updatesite(SiteVO site) {
		//현장수정
		return mapper.updatesite(site);
	}

	@Override
	public int updatenetwork(SiteVO site) {
		//현장수정
		return mapper.updatenetwork(site);
	}

	@Override
	public ArrayList<SiteVO> getStatus(String site_id) {
		return mapper.getStatus(site_id);

	}

	@Override
	public ArrayList<SiteVO> siteSearch(Search s) {
		// 현장관리 검색1
		return mapper.siteSearch(s);
	}

	@Override
	public ArrayList<SiteVO> getSearchResult(Map<Object, Object> parm) {
		// 현장관리 검색2
		return mapper.getSearchResult(parm);
	}

	@Override
	public ArrayList<CheckBoardVO> repairSearch(Search s) {
		// 수리내역 검색1
		return mapper.repairSearch(s);
	}

	@Override
	public ArrayList<CheckBoardVO> getSearchResultRepair(Map<Object, Object> parm) {
		// 수리내역 검색2
		return mapper.getSearchResultRepair(parm);
	}

	@Override
	public String getSiteName(int site_id) {
		//site id로 site name가져오기
		return mapper.getSiteName(site_id);
	}

	@Override
	public ArrayList<InstallSensorVO> installSensorList(Map<String, Object> parm) {
		//현장에 설치된 센서 조회
		return mapper.installSensorList(parm);
	}





}
