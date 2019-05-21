package com.wda.sc.mapper;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
import com.wda.sc.domain.SensorDataVO;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.InstallSensorVO;
import com.wda.sc.domain.MysensorVO;
import com.wda.sc.domain.SiteVO;

public interface SiteMapper {

	public ArrayList<SiteVO> getList();

	public ArrayList<CheckBoardVO> repairList(Map<String, Object> parm);

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

	// 현장에 설치된 센서리스트 조회
	public ArrayList<InstallSensorVO> installSensorList(Map<String, Object> parm);

	public int sensorPageNum(String site_id);

	public ArrayList<MysensorVO> getSensor(String sensor_sn);

	public ArrayList<MysensorVO> smSearch(Search s);

	public ArrayList<MysensorVO> getSearchResultSM(Map<Object, Object> parm);
	
	//그래프 그릴려고 그래프 그려오기
	public ArrayList<SensorDataVO> getSensingDate(String site_id);
	
	//그래프 밑에 이름들
	public ArrayList<SensorDataVO> getGraphName(String site_id);
	
	//센서데이터 표(이름)
	public ArrayList<SensorDataVO> getDataName(String site_id);
	
	//센서데이터 표(데이터)
	public ArrayList<SensorDataVO> getData(String site_id);
	
	//앱 검색
	public ArrayList<SiteVO> getAppSearch(@Param("word") String word);
	
	//앱 메인화면
	public ArrayList<SiteVO> appmain(Map<String,Double> map);
}
