package com.wda.sc.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wda.sc.domain.AppTokenVO;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.TokenVO;

public interface MyPageMapper {
	public ArrayList<MemberVO> getInfo(String id);
	
	public ArrayList<CheckBoardVO> myList(Map<String, Object> parm);
	
	public ArrayList<MemberVO> confirmpasswd(String confirmid);
	
	public void updateuserinfo(MemberVO vo);
	
	public int getPageNum(String id);
	
	public ArrayList<CheckBoardVO> myListView(String board_no);
	
	public List<TokenVO> getToken(String user_id);
	//토큰 DB에 저장
	public int saveToken(TokenVO tokenvo);
	
	public int updateToken(Map<String, String> map);
	//앱 토큰
	public List<AppTokenVO> getappToken(String id);
	
	public int saveappToken(AppTokenVO tokenvo);
	
//	public int updateappToken(Map<String, String> map2);
	
	// 앱 토큰 전부 가져오기
	public List<AppTokenVO> allappToken();
	
	public int deleteappToken(String user_id);
	
}
