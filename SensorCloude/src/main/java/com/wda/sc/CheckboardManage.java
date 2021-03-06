package com.wda.sc;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
import com.wda.sc.service.CheckboardService;

public class CheckboardManage {
	
	public String adminPaging(CheckboardService checkboardservice, Model model, int status, String num) {
		
		Paging page = new Paging();
		ArrayList<Integer> arr=null;
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
		
		int pageNum = 0;
		int mapNum=0;
		int sendPageNum=0;
		int realNum = Integer.parseInt(num);

		page.setTotalNum(checkboardservice.checkManagePageNum(status));
		
		page.setOnePageBoard(5);
		if(page.getTotalNum() < page.getOnePageBoard()) {
			pageNum = 1;
		}else {
			pageNum = page.getTotalNum()/page.getOnePageBoard();
			if(page.getTotalNum()%page.getOnePageBoard() > 0) {
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

		sendPageNum = (realNum-1)/5;

		page.setEndnum((realNum*5)+1);
		page.setStartnum(page.getEndnum()-5);
		page.setBoard_status(status);
		
		model.addAttribute("lastNum" + status, pageNum);
		model.addAttribute("pageNum" + status ,map.get(sendPageNum));
		
		ArrayList<CheckBoardVO> result = checkboardservice.getStatusList(page);
		for(int i = 0; i < result.size(); i++) {
			if(result.get(i).getReg_date().length() >16) {
				result.get(i).setReg_date(result.get(i).getReg_date().substring(0,10));
			}
		}
		
		model.addAttribute("check" + status ,result); // open인 점검이력
		model.addAttribute("depth0","메인화면");
		model.addAttribute("depth1","점검이력관리");
		
		if(realNum > pageNum) {

			return "false";
		}
		return "";
	}
	
	public String dataSearch(CheckboardService checkboardservice, Model model, int page, int data, int status) {
		if ( data == 0) {
			Paging page2 = new Paging();
			ArrayList<Integer> arr=null;
			Map<Integer, ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
			
			int pageNum = 0;
			int mapNum=0;
			int sendPageNum=0;
			int realNum = page;

			page2.setTotalNum(checkboardservice.checkManagePageNum(status));
			
			page2.setOnePageBoard(5);
			if(page2.getTotalNum() < page2.getOnePageBoard()) {
				pageNum = 1;
			}else {
				pageNum = page2.getTotalNum()/page2.getOnePageBoard();
				if(page2.getTotalNum()%page2.getOnePageBoard() > 0) {
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

			sendPageNum = (realNum-1)/5;

			page2.setEndnum((realNum*5)+1);
			page2.setStartnum(page2.getEndnum()-5);
			page2.setBoard_status(status);
			
			ArrayList<CheckBoardVO> result = checkboardservice.getStatusList(page2);
			for(int i = 0; i < result.size(); i++) {
				if(result.get(i).getReg_date().length() >16) {
					result.get(i).setReg_date(result.get(i).getReg_date().substring(0,10));
				}
			}
			model.addAttribute("lastNum" + status, pageNum);
			model.addAttribute("pageNum" + status, map.get(sendPageNum)); //arr
			model.addAttribute("check" + status ,result); // open인 점검이력
			model.addAttribute("depth0","메인화면");
			model.addAttribute("depth1","점검이력관리");
			if (realNum > pageNum) {
				return "false";
			}	
			return "";
		} else {

		Paging p = new Paging();
		ArrayList<CheckBoardVO> term = new ArrayList<CheckBoardVO>();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		Map<Object, Object> parm = new HashMap<Object, Object>();
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
		
		Map<String, Integer> date = new HashMap<String, Integer>();
		
					
			date.put("data", data);
			date.put("status", status);
			
			term = checkboardservice.adminDateChange(date);
			int pageNum = 0;
			int mapNum=0;
			int sendPageNum=0;
			int realNum = page;
			p.setTotalNum(term.size());
			p.setOnePageBoard(5);
			if (p.getTotalNum() <= p.getOnePageBoard()) {
				pageNum = 1;
			} else {
				pageNum = p.getTotalNum() / p.getOnePageBoard();
				if (p.getTotalNum() % p.getOnePageBoard() > 0) {
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

			sendPageNum = (realNum-1)/5;
//			for (int i = 0; i < pageNum; i++) {
//				arr.add(i + 1);
//			}

			p.setEndnum((realNum * 5) + 1);
			p.setStartnum(p.getEndnum() - 5);
			p.setBoard_status(status);

			parm.put("paging", p);
			parm.put("data", date);
			
//			Paging pa = new Paging();
//			pa.setStartnum(0);
//			pa.setEndnum(10);
			ArrayList<CheckBoardVO> result = checkboardservice.getAdminTermList(parm);
			for(int i = 0; i < result.size(); i++) {
				if(result.get(i).getReg_date().length() >16) {
					result.get(i).setReg_date(result.get(i).getReg_date().substring(0,10));
				}
			}
			model.addAttribute("check" + status, result);
			model.addAttribute("lastNum" + status, pageNum);
			model.addAttribute("pageNum" + status, map.get(sendPageNum)); //arr
			model.addAttribute("depth0","메인화면");
			model.addAttribute("depth1","점검이력관리");
			
			if (realNum > pageNum) {
				return "false";		
			}	
			
			return "";
		}
	}
	
	public String checkSearch(CheckboardService checkboardservice, Model model, int page, String searchType, String keyword, int status) {
		Paging p = new Paging();
		Search s = new Search();
		ArrayList<CheckBoardVO> searchArr = new ArrayList<CheckBoardVO>();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		Map<Object, Object> parm = new HashMap<Object, Object>();
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();

		Map<String, Object> data = new HashMap<String, Object>();
		
		s.setPage(page);
		s.setKeyword(keyword);
		s.setSearchType(searchType);

		data.put("search" , s);
		data.put("status" , status);

		searchArr = checkboardservice.checkManageSearch(data);

		int pageNum = 0;
		int mapNum=0;
		int sendPageNum=0;
		int realNum = page;
		p.setTotalNum(searchArr.size());
		p.setOnePageBoard(5);


		if (p.getTotalNum() <= p.getOnePageBoard()) {
			pageNum = 1;
		} else {
			pageNum = p.getTotalNum() / p.getOnePageBoard();
			if (p.getTotalNum() % p.getOnePageBoard() > 0) {
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

		sendPageNum = (realNum-1)/5;

		p.setEndnum((realNum * 5) + 1);
		p.setStartnum(p.getEndnum() - 5);
		p.setBoard_status(status);
		
		parm.put("Paging", p);
		parm.put("Search", s);
		
		model.addAttribute("lastNum" + status , pageNum);
		model.addAttribute("pageNum" + status , map.get(sendPageNum));
		ArrayList<CheckBoardVO> result = checkboardservice.getcheckManageSearch(parm);
		for(int i = 0; i < result.size(); i++) {
			if(result.get(i).getReg_date().length() >16) {
				result.get(i).setReg_date(result.get(i).getReg_date().substring(0,10));
			}
		}
		
		model.addAttribute("check" + status , result);
		model.addAttribute("depth0","메인화면");
		model.addAttribute("depth1","점검이력관리");
		
		if(realNum > pageNum) {

			try {
				keyword = URLEncoder.encode(keyword, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "false";
//			return "redirect:/checkboard/search/"+pageNum+"/"+searchType+"/"+keyword+"";
		}
		
		
		
		return "";
	}

	
}
