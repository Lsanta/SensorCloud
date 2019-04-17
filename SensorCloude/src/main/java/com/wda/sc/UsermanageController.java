package com.wda.sc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
import com.wda.sc.domain.SiteVO;
import com.wda.sc.service.UsermanageService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/manage")
public class UsermanageController {
	
private UsermanageService usermanageservice;
	
	@RequestMapping(value = "usermodify" + "/{id}", method = RequestMethod.GET)
	public String address(@PathVariable String id, Model model) {
		
	
		model.addAttribute("userInfo",usermanageservice.getInfo(id));
		return "manage/usermodify";
	}
	
	//사용자 수정(update)
	@RequestMapping(value ="updateuser.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateUser(MemberVO vo) { 
		
		int a = usermanageservice.updateuser(vo);
		
		if( a == 0 )  {
			return "false";
		} else {
			return "success";
		}   
	}  
	
	/// 승급요청시 요청승급과 유저 아이디를 세션에 저장//levelup에서 호출
	@RequestMapping(value ="userlevelmanage.do", method = RequestMethod.POST)
	@ResponseBody
	public String userlevelup(MemberVO vo){
		
		
		int requestlevel =usermanageservice.requestlevel(vo);
		
		if(requestlevel == 0) {
		
		return "false";
		}
		
		else {
			return "success";
		}
	}  
	
	  //사용자관리 검색
	  @RequestMapping(value ="/search" + "/{page}" + "/{searchType}" + "/{keyword}", method = RequestMethod.GET)
	  public String mysensorSearch(
			  @PathVariable int page, 
			  @PathVariable String searchType, 
			  @PathVariable String keyword, Model model) {
		  
		  Paging p = new Paging();
		  Search s = new Search();
		  ArrayList<MemberVO> searchArr = new ArrayList<MemberVO>();
		  ArrayList<Integer> arr = new ArrayList<Integer>();
		  Map<Object, Object> parm = new HashMap<Object, Object>();
		  
		  s.setPage(page);
		  s.setKeyword(keyword);
		  s.setSearchType(searchType);
		  
		  System.out.println(page); //현재 페이지 번호
		  System.out.println(searchType); //검색 옵션
		  System.out.println(keyword); //검색 키워드
		  
		  searchArr = usermanageservice.manageSearch(s);
		  
		  System.out.println("사용자 관리 검색 :" + searchArr);
		  
		  int pageNum = 0;
		  int realNum = 1;
		  p.setTotalNum(searchArr.size());
		  
		  System.out.println("사용자 관리 전체숫자" +p.getTotalNum());
		  
		  if(p.getTotalNum() <= p.getOnePageBoard() ) {
				pageNum = 1;
			}else {
				pageNum = p.getTotalNum()/p.getOnePageBoard();
				if(p.getTotalNum()%p.getOnePageBoard() > 0) {
					pageNum = pageNum + 1;
				}
			}
		  
		  for(int i = 0; i < pageNum; i ++) {
				arr.add(i+1);
			}
		  
		  p.setEndnum((realNum*10)+1);
		  p.setStartnum(p.getEndnum()-10);
		  
		  parm.put("Paging", p);
		  parm.put("Search", s);
		  
		  model.addAttribute("pageNum",arr);
		  model.addAttribute("manage",usermanageservice.getSearchResult(parm));
		  System.out.println("사용자 관리검색 결과 :" + usermanageservice.getSearchResult(parm));
		  return "manage/manage";
	  }
	
}
