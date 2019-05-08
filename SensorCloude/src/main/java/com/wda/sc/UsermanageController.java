package com.wda.sc;



import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
import com.wda.sc.service.UsermanageService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/manage")
public class UsermanageController {
	
private UsermanageService usermanageservice;
	
	@RequestMapping(value = "usermodify" + "/{id}", method = RequestMethod.GET)
	public String address(@PathVariable String id, Model model, HttpSession session, HttpServletResponse response) throws IOException {
		
		

		int mlevel = (int) session.getAttribute("mlevel");
		
		
		if (mlevel !=5) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n 관리자만 열람가능합니다');history.go(-1);");
			out.println("</script>");

		}
		
		
		
		model.addAttribute("userInfo",usermanageservice.getInfo(id));
		System.out.println(usermanageservice.getInfo(id));
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
	  public String manageSearch(
			  @PathVariable int page, 
			  @PathVariable String searchType, 
			  @PathVariable String keyword, Model model) {
		  
		  Paging p = new Paging();
		  Search s = new Search();
		  ArrayList<MemberVO> searchArr = new ArrayList<MemberVO>();
		  ArrayList<Integer> arr = new ArrayList<Integer>();
		  Map<Object, Object> parm = new HashMap<Object, Object>();
		  Map<Integer, ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
		  
		  s.setPage(page);
		  s.setKeyword(keyword);
		  s.setSearchType(searchType);
		  
		  System.out.println(page); //현재 페이지 번호
		  System.out.println(searchType); //검색 옵션
		  System.out.println(keyword); //검색 키워드
		  
		  searchArr = usermanageservice.manageSearch(s);
		  
		  System.out.println("사용자 관리 검색 :" + searchArr);
		  
		  int pageNum = 0;
		  int mapNum=0;
		  int sendPageNum=0;
		  int realNum = page;
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
		  
		  if(pageNum%5 != 0) {
				mapNum=pageNum/5+1;
			}else {
				mapNum=pageNum/5;
			}

			for(int i=0; i<mapNum; i++) {
				arr = new ArrayList<Integer>();
				for(int j=0; j<5; j++) {
					
					if((i*5)+j+1 > pageNum) {
						break;
					}
					
					arr.add((i*5)+j+1);
				}
				map.put(i,arr);
			}
		  
		  p.setEndnum((realNum*10)+1);
		  p.setStartnum(p.getEndnum()-10);
		  
		  parm.put("Paging", p);
		  parm.put("Search", s);
		  
		  model.addAttribute("pageNum",map.get(sendPageNum));
		  model.addAttribute("manage",usermanageservice.getSearchResult(parm));
		  System.out.println("사용자 관리검색 결과 :" + usermanageservice.getSearchResult(parm));
		  
		  if(realNum > pageNum) {
				System.out.println("pageNum : " + pageNum);
				System.out.println("keyword : " + keyword);
				try {
					keyword = URLEncoder.encode(keyword, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "redirect:/manage/search/"+pageNum+"/"+searchType+"/"+keyword+"";
			}
		  
		  return "manage/manage";
	  }
	
}
