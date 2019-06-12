package com.wda.sc.mapper;

import java.util.ArrayList;
import java.util.Map;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;

public interface UsermanageMapper {

	public ArrayList<MemberVO> getList(Paging p);
	
	public ArrayList<MemberVO> getInfo(String id);
	
	public int getPageNum();
	
	public int updateuser(MemberVO vo);

	public int requestlevel(MemberVO vo);
	
	//검색1
	public ArrayList<MemberVO> manageSearch(Search s);
	//검색2
	public ArrayList<MemberVO> getSearchResult(Map<Object, Object> parm);
	
	public int mrequestlevel(Map<String, String> map);
	
	public int userlevelup(Map<String, String> map);
	
	public int releveldel(Map<String, String> map);
}
