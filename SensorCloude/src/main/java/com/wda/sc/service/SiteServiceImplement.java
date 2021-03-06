package com.wda.sc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.ProcessPidVO;
import com.wda.sc.domain.Search;
import com.wda.sc.domain.SensorDataVO;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.CompanyVO;
import com.wda.sc.domain.InstallSensorVO;
import com.wda.sc.domain.MemberVO;
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
		// 현장수정
		return mapper.updatesite(site);
	}

	@Override
	public int updatenetwork(SiteVO site) {
		// 현장수정
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
		// site id로 site name가져오기
		return mapper.getSiteName(site_id);
	}

	@Override
	public ArrayList<InstallSensorVO> installSensorList(Map<String, Object> parm) {
		// 현장에 설치된 센서 조회
		return mapper.installSensorList(parm);
	}

	@Override
	public ArrayList<MysensorVO> getSensor(String sensor_sn) {
		// TODO Auto-generated method stub
		return mapper.getSensor(sensor_sn);
	}

	@Override
	public ArrayList<InstallSensorVO> smSearch(Search s) {
		// TODO Auto-generated method stub
		return mapper.smSearch(s);
	}

	@Override
	public ArrayList<InstallSensorVO> getSearchResultSM(Map<Object, Object> parm) {
		// TODO Auto-generated method stub
		return mapper.getSearchResultSM(parm);
	}
	
	@Override
	public ArrayList<SensorDataVO> getSensingDate(String site_id) {
		// TODO Auto-generated method stub
		return mapper.getSensingDate(site_id);
	}
	
	public ArrayList<SensorDataVO> getDataName(String site_id) {
		//센서데이터 표(이름)
		return mapper.getDataName(site_id);
	}
	
	public ArrayList<SensorDataVO> getData(String site_id) {
		//센서데이터 표(데이터)
		return mapper.getData(site_id);
	}

	@Override
	public ArrayList<SiteVO> getAppSearch(String word) {
		return mapper.getAppSearch(word);
	}
	
	@Override
	public ArrayList<SensorDataVO> getGraphName(String site_id) {
		// TODO Auto-generated method stub
		return mapper.getGraphName(site_id);
	}
	
	@Override
	public ArrayList<SiteVO> appmain(Map<String,Double> map) {
		//앱 메인화면
		return mapper.appmain(map);
	}
	
	@Override
	public int getSiteNum() {
		return mapper.getSiteNum();
	}
	
	@Override
	public ArrayList<ProcessPidVO> getProcessPid() {
		// TODO Auto-generated method stub
		return mapper.getProcessPid();
	}
	
	@Override
	public int setProcessPid(ProcessPidVO processpid) {
		// TODO Auto-generated method stub
		return mapper.setProcessPid(processpid);
	}

	@Override
	public List<AlarmMemberVO> getLimitAlarm_member(String site_id) {
		
		return mapper.getLimitAlarm_member(site_id);
	}

	@Override
	public int addInstallSensor(InstallSensorVO test) {
		// TODO Auto-generated method stub
		return mapper.addInstallSensor(test);
	}
	
	@Override
	public ArrayList<InstallSensorVO> getInstallSensor(int site_id) {
		// TODO Auto-generated method stub
		return mapper.getInstallSensor(site_id);
	}

	@Override
	public List<InstallSensorVO> getsiteModSensor(int site_id) {
		//현장 수정눌렀을때 가져올 센서들에대한 정보 
		return mapper.getsiteModSensor(site_id);
	}

	@Override
	public int deletePid(String site_id) {
		//현장에 대한 PID를 테이블에서 삭제
		return mapper.deletePid(site_id);
	}

	@Override
	public int modStatus(SiteVO sitevo) {
		//현장의 상태 변경
		return mapper.modStatus(sitevo);
	}

	@Override
	public String getSitePid(String site_id) {
		//현장에 맞는 pid를 불러온다.
		return mapper.getSitePid(site_id);
	}

	@Override
	public int addProcessPid(ProcessPidVO setPid) {
		// 현장 비활성화 -> 활성화 pid 추가
		return mapper.addProcessPid(setPid);
	}

	@Override
	public int getCompanyNum(String user_id) {
		//로그인한 유저의 회사 번호(시퀀스)
		return mapper.getCompanyNum(user_id);
	}

	@Override
	public ArrayList<SiteVO> getCompanySiteList(int company_num) {
		//로그인한 유저의 회사 현장
		return mapper.getCompanySiteList(company_num);
	}

	@Override
	public int getCompanySitePageNum(int company_num) {
		//현장 관리에서의 자신의 회사 현장 개수
		return mapper.getCompanySitePageNum(company_num);
	}

	@Override
	public ArrayList<SiteVO> getCompanySiteContent(Paging p) {
		// 현장 관리에서의 자신의 회사 현장 내용(리스트 개수)
		return mapper.getCompanySiteContent(p);
	}

	@Override
	public ArrayList<SiteVO> companySiteSearch(Search s) {
		//현장 관리에서의 자신의 회사 검색 
		return mapper.companySiteSearch(s);
	}

	@Override
	public ArrayList<SiteVO> companySiteSearchResult(Map<Object, Object> parm) {
		//현장 관리에서의 자신의 회사 검색 최종
		return mapper.companySiteSearchResult(parm);
	}

	@Override
	public ArrayList<CompanyVO> CompanySearch(String name) {
		//현장 추가에서의 회사 검색 (이름으로 )
		return mapper.CompanySearch(name);
	}
	@Override
	public String[] insen(String site_id) {
		return mapper.insen(site_id);
	}
	
	@Override
	public ArrayList<AlarmMemberVO> getCompany_Alarm_member(String site_id) {
		// TODO Auto-generated method stub
		return mapper.getCompany_Alarm_member(site_id);
	}
	
	@Override
	public ArrayList<MemberVO> getCompanyMember(int company_num) {
		// TODO Auto-generated method stub
		return mapper.getCompanyMember(company_num);
	}

	@Override
	public String getCompanyName(String site_id) {
		// TODO Auto-generated method stub
		return mapper.getCompanyName(site_id);
	}
}
