package com.wda.sc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.Paging;
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
		model.addAttribute("check" + status ,checkboardservice.getStatusList(page)); // open인 점검이력
		
		System.out.println("realNum" + realNum);
		System.out.println("페이지넘" + pageNum);
		if(realNum > pageNum) {
			System.out.println("pageNum : " + pageNum);
			
			return "false";
		}
		return "";
	}
	
	public String dataSearch(CheckboardService checkboardservice, Model model, int page, int data, int status) {
		Paging p = new Paging();
		ArrayList<CheckBoardVO> term = new ArrayList<CheckBoardVO>();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		Map<Object, Object> parm = new HashMap<Object, Object>();
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
		
		Map<String, Integer> date = new HashMap<String, Integer>();
		
			System.out.println("데이터가 0이아님");
					
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

			parm.put("paging", p);
			parm.put("data", date);
			
//			Paging pa = new Paging();
//			pa.setStartnum(0);
//			pa.setEndnum(10);
			
			model.addAttribute("check" + status, checkboardservice.getAdminTermList(parm));
			model.addAttribute("lastNum" + status, pageNum);
			model.addAttribute("pageNum" + status, map.get(sendPageNum)); //arr
			System.out.println("검색완료 점검이력으로");
			
			if (realNum > pageNum) {
				System.out.println("pageNum : " + pageNum);
				return "redirect:/checkboard/dataSearch/" + pageNum  + "/" + data + "/" + status;
			}	
			
			return "";
		
	}

	
}
