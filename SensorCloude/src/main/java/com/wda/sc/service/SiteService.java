package com.wda.sc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.ProcessPidVO;
import com.wda.sc.domain.Search;
import com.wda.sc.domain.SensorDataVO;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.CompanyVO;
import com.wda.sc.domain.InstallSensorVO;
import com.wda.sc.domain.MysensorVO;
import com.wda.sc.domain.SiteVO;

public interface SiteService {

	public ArrayList<SiteVO> getList();

	public ArrayList<SiteVO> getSite(String site_id);

	public ArrayList<AlarmMemberVO> getAlarm_member(String site_id);

	// 현장추가 site
	public int siteadd(SiteVO site);

	// 현장추가 network
	public int networkadd(SiteVO site);

	// 현장수정 select
	public ArrayList<SiteVO> joinSite(String site_id);

	// 현장수정 site
	public int updatesite(SiteVO site);

	// 현장수정 network
	public int updatenetwork(SiteVO site);

	public ArrayList<AlarmVO> getAlarm(String site_id);

	public int getPageNum();

	public ArrayList<SiteVO> getContent(Paging p);

	public int insertAlarmMember(AlarmMemberVO vo);

	public int insertAlarm(AlarmVO vo);

	public ArrayList<CheckBoardVO> repairList(Map<String, Object> parm);

	public int repairPageNum(String site_id);

	// 연락망 사람 수정
	public int modAlarm(AlarmMemberVO vo);

	// 연락망 사람 삭제
	public boolean delAlarm(AlarmMemberVO vo);

	// 보유 센서 종류 조회
	public ArrayList<MysensorVO> getSensorKind();

	// 점검이력 글쓰기 현장이름 조회
	public ArrayList<SiteVO> getchecksite();

	// 현장 상태 띄우기
	public ArrayList<SiteVO> getStatus(String site_id);

	// 현장관리 검색1
	public ArrayList<SiteVO> siteSearch(Search s);

	// 현장관리 검색2
	public ArrayList<SiteVO> getSearchResult(Map<Object, Object> parm);

	// 수리내역 검색1
	public ArrayList<CheckBoardVO> repairSearch(Search s);

	// 수리내역 검색2
	public ArrayList<CheckBoardVO> getSearchResultRepair(Map<Object, Object> parm);

	// site id로 site name가져오기
	public String getSiteName(int site_id);

	// 설치센서조회
	public ArrayList<InstallSensorVO> installSensorList(Map<String, Object> parm);
	
	//install_sensor 받아오기
	public ArrayList<InstallSensorVO> getInstallSensor(int site_id);
	
	public int sensorPageNum(String site_id);
	//센서정보 가져오기
	public ArrayList<MysensorVO> getSensor(String sensor_sn);

	public ArrayList<InstallSensorVO> smSearch(Search s);

	public ArrayList<InstallSensorVO> getSearchResultSM(Map<Object, Object> parm);

	//그래프 그릴려고 데이터 가져오기
	public ArrayList<SensorDataVO> getSensingDate(String site_id);
	
	//그래프 밑에 이름들
	public ArrayList<SensorDataVO> getGraphName(String site_id);
	
	//센서데이터 표(이름)
	public ArrayList<SensorDataVO> getDataName(String site_id);
	
	//센서데이터 표(데이터)
	public ArrayList<SensorDataVO> getData(String site_id);

	//앱 검색
	public ArrayList<SiteVO> getAppSearch(String word);
	
	//앱 메인화면
	public ArrayList<SiteVO> appmain(Map<String,Double> map);
	
	//프로그램에 넘겨줄 현재 현장아이디 구하기
	public int getSiteNum();
	
	//프로그램 pid 얻어오기
	public ArrayList<ProcessPidVO> getProcessPid();
	
	//프로그램 pid 저장
	public int setProcessPid(ProcessPidVO processpid);
	
	//임계값 넘었을 시 가져올 연락망
	public List<AlarmMemberVO> getLimitAlarm_member(String site_id);

	//설치 센서에 센서 정보 추가
	public int addInstallSensor(InstallSensorVO test);

	//현장 수정눌렀을때 가져올 센서들에대한 정보 
	public List<InstallSensorVO> getsiteModSensor(int site_id);

	//현장에 대한 PID를 테이블에서 삭제
	public int deletePid(String site_id);
	
	//현장의 상태 변경
	public int modStatus(SiteVO sitevo);

	//현장에 맞는 pid를 불러온다.
	public String getSitePid(String site_id);

	//현장 비활성화 -> 활성화 pid 추가
	public int addProcessPid(ProcessPidVO setPid);

	//로그인한 유저의 회사 번호(시퀀스)
	public int getCompanyNum(String user_id);
	
	//로그인한 유저의 회사 현장
	public ArrayList<SiteVO> getCompanySiteList(int company_num);

	//현장 관리에서의 자신의 회사 현장 개수
	public int getCompanySitePageNum(int company_num);
	
	//현장 관리에서의 자신의 회사 현장 내용(리스트 개수)
	public ArrayList<SiteVO> getCompanySiteContent(Paging p);
	
	//현장 관리에서의 자신의 회사 검색 
	public ArrayList<SiteVO> companySiteSearch(Search s);
	
	//현장 관리에서의 자신의 회사 검색 최종
	public ArrayList<SiteVO> companySiteSearchResult(Map<Object, Object> parm);
	
	//현장 추가에서의 회사 검색 (이름으로 )
	public ArrayList<CompanyVO> CompanySearch(String name);
	
	//해당현장에 등록된 센서목록 가져오기
	public String[] insen(String site_id);


}
