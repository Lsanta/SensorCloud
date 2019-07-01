package com.wda.sc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.TimelineVO;
import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.MyPageService;
import com.wda.sc.service.MysensorService;
import com.wda.sc.service.SiteService;

import com.wda.sc.service.TimelineService;
import com.wda.sc.service.UsermanageService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {

	private SiteService siteservice;
	private TimelineService timelineservice;
	private CheckboardService checkboardservice;
	private MysensorService mysensorservice;
	private UsermanageService usermanageservice;
	private MyPageService mypageservice;

	@Autowired private ServletContext servletContext;

	@RequestMapping(value ="/", method = RequestMethod.GET)
	//사이트의 메인화면으로 보내주는 컨트롤러
	public String main(Locale locale, Model model, HttpSession session) {
		
		System.out.println(servletContext.getRealPath("/"));
		//메인화면 점검이력 제목 substring
		ArrayList<CheckBoardVO> arr = checkboardservice.mainList();

		for(int i = 0; i < arr.size(); i++) {
			if(arr.get(i).getTitle().length() > 6) {
				arr.get(i).setTitle(arr.get(i).getTitle().substring(0,6)+"...");
			}
			if(arr.get(i).getReg_date().length() >16) {
				arr.get(i).setReg_date(arr.get(i).getReg_date().substring(0,16));
			}
		}
		
		//예전에는 모든 사이트의 현장을 가지고왔다.
		//model.addAttribute("sitelist",siteservice.getList());
		
		String user_id = (String) session.getAttribute("id");
		int company_num = siteservice.getCompanyNum(user_id);
		if(company_num == 1) {
			//예전에는 모든 사이트의 현장을 가지고왔다.
			model.addAttribute("sitelist",siteservice.getList());
		} else {
			//지금은 해당 로그인한 유저의 회사가 관리하는 현장만 가지고온다.
			model.addAttribute("sitelist",siteservice.getCompanySiteList(company_num));
		}	
		model.addAttribute("timelinelist",timelineservice.timedesc());
		model.addAttribute("mainchecklist",arr);
		String id = (String)session.getAttribute("id");
		model.addAttribute("userInfo",mypageservice.getInfo(id));
		return "main";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect: /";
	}

	@RequestMapping(value = "/check"+"/{num}", method = RequestMethod.GET)
	public String check(@PathVariable String num, Model model,HttpSession session, HttpServletResponse response) throws IOException {

		int mlevel = (int) session.getAttribute("mlevel");

		if (mlevel < 2) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다. \\n 2등급(읽기권한)이상이 열람가능합니다'); history.go(-1);");
			out.println("</script>");


		}

		Paging page = new Paging();
		ArrayList<Integer> arr=null;
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
		
		int pageNum = 0;
		int mapNum=0;
		int sendPageNum=0;
		int realNum = Integer.parseInt(num);

		page.setTotalNum(checkboardservice.getPageNum());
		System.out.println(page.getTotalNum());
		
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

		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);
		
		model.addAttribute("lastNum", pageNum);
		model.addAttribute("pageNum",map.get(sendPageNum));
		model.addAttribute("checkboardlist",checkboardservice.getList(page));

		System.out.println("realNum : " + realNum);
		System.out.println("pageNum : " + pageNum);
		
		if(realNum > pageNum) {
			System.out.println("pageNum : " + pageNum);
			return "redirect:/check/"+pageNum;
		}
		
		return "check/check";
	}

	@RequestMapping(value = "/sitelist"+"/{num}", method = RequestMethod.GET)
	public String siteList(@PathVariable String num, Model model,HttpServletResponse response,HttpSession session) throws IOException {

		
		int mlevel = (int) session.getAttribute("mlevel");
		System.out.println("레벨" + mlevel);
		
		if (mlevel == 1) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n2등급(읽기권한)이상이 열람가능합니다');history.go(-1);");
			out.println("</script>");

		}

		String user_id = (String) session.getAttribute("id");
		int company_num = siteservice.getCompanyNum(user_id);
		
		Paging page = new Paging();
		ArrayList<Integer> arr=null;
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
		
		int pageNum = 0;
		int mapNum=0;
		int sendPageNum=0;
		int realNum = Integer.parseInt(num);
		
		if(company_num == 1) {
			page.setTotalNum(siteservice.getPageNum());
		} else {
			page.setTotalNum(siteservice.getCompanySitePageNum(company_num));
		}
		
		
		if(page.getTotalNum() <= page.getOnePageBoard() ) {
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

		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);
		page.setCompany_num(company_num);
		
		model.addAttribute("lastNum", pageNum);
		if(company_num == 1) {
			model.addAttribute("content",siteservice.getContent(page));
		} else {
			model.addAttribute("content",siteservice.getCompanySiteContent(page));
		}
		model.addAttribute("pageNum",map.get(sendPageNum));
		model.addAttribute("sitelist",siteservice.getList());

		if(realNum > pageNum) {
			System.out.println("pageNum : " + pageNum);
			return "redirect:/sitelist/"+pageNum;
		}
		
		return "site/sitelist";
	}


	@RequestMapping(value = "/checkadd", method = RequestMethod.GET)
	public String checkadd(Locale locale, Model model ,HttpServletResponse response,HttpSession session) throws IOException {


		int mlevel = (int) session.getAttribute("mlevel");


		if (mlevel < 3) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n3등급(쓰기권한)이상이 열람가능합니다');history.go(-1);");
			out.println("</script>");

		}


		return "check/checkadd";
	}

	@RequestMapping(value = "/manage"+"/{num}", method = RequestMethod.GET)
	public String manage(@PathVariable String num, Model model ,HttpSession session,HttpServletResponse response) throws IOException {

		int mlevel = (int) session.getAttribute("mlevel");
		System.out.println("레벨" + mlevel);

		if (mlevel !=5) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n 관리자만 열람가능합니다');history.go(-1);");
			out.println("</script>");

		}

		Paging page = new Paging();
		ArrayList<Integer> arr=null;
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
		
		int pageNum = 0;
		int mapNum=0;
		int sendPageNum=0;
		int realNum = Integer.parseInt(num);
		
		page.setTotalNum(usermanageservice.getPageNum());

		if(page.getTotalNum() <= page.getOnePageBoard() ) {
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
		
		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);

		//사용자관리에 승급요청등급과 승급요청한 아이디를 넘긴다.


		//
		model.addAttribute("lastNum", pageNum);
		model.addAttribute("pageNum",map.get(sendPageNum));
		model.addAttribute("userlist",usermanageservice.getList(page));

		if(realNum > pageNum) {
			System.out.println("pageNum : " + pageNum);
			return "redirect:/manage/"+pageNum;
		}
		
		return "manage/manage";
	}

	@RequestMapping(value = "/time"+"/{num}", method = RequestMethod.GET)
	public String timeline(@PathVariable String num, Locale locale, Model model,HttpSession session , HttpServletResponse response) throws IOException {

		int mlevel = (int) session.getAttribute("mlevel");

		if (mlevel ==1) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n 2등급(읽기권한)이상이 열람가능합니다');history.go(-1);");
			out.println("</script>");

		}

		Paging page = new Paging();
		ArrayList<Integer> arr=null;
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

		int pageNum = 0;
		int mapNum=0;
		int sendPageNum=0;
		int realNum = Integer.parseInt(num);

		page.setTotalNum(timelineservice.getPageNum());
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

		model.addAttribute("lastNum", pageNum);
		model.addAttribute("pageNum",map.get(sendPageNum));
		model.addAttribute("timelinelist",timelineservice.getList(page));
		System.out.println(timelineservice.getList(page));

		if(realNum > pageNum) {
			System.out.println("pageNum : " + pageNum);
			return "redirect:/time/"+pageNum;
		}


		return "timeline/timeline";
	}


	@RequestMapping(value = "/timelinemodify", method = RequestMethod.GET)
	public String timelinemodify(Locale locale, Model model,HttpServletResponse response,HttpSession session) throws IOException {

		int mlevel = (int) session.getAttribute("mlevel");

		if (mlevel ==1) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n 2등급(읽기권한)이상이 열람가능합니다');history.go(-1);");
			out.println("</script>");

		}


		return "timeline/timelinemodify";
	}


	@RequestMapping(value = "/mysensor"+"/{num}", method = RequestMethod.GET)
	public String mysensor(@PathVariable String num, Locale locale, Model model, HttpSession session,HttpServletResponse response) throws IOException {

		int mlevel = (int) session.getAttribute("mlevel");
		System.out.println("레벨" + mlevel);

		if (mlevel == 1) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n2등급(읽기권한)이상이 열람가능합니다');history.go(-1);");
			out.println("</script>");

		}


		Paging page = new Paging();
		ArrayList<Integer> arr=null;
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

		int pageNum = 0;
		int mapNum=0;
		int sendPageNum=0;
		int realNum = Integer.parseInt(num);

		page.setTotalNum(mysensorservice.getPageNum());

		if(page.getTotalNum() <= page.getOnePageBoard() ) {
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

		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);

		model.addAttribute("lastNum", pageNum);
		model.addAttribute("pageNum",map.get(sendPageNum));
		model.addAttribute("sensorlist",mysensorservice.getList(page));

		if(realNum > pageNum) {
			System.out.println("pageNum : " + pageNum);
			return "redirect:/mysensor/"+pageNum;
		}

		return "mysensor/mysensor";
	}

	@RequestMapping(value = "/mypage"+"/{num}", method = RequestMethod.GET)
	public String mypage(@PathVariable String num, Model model,HttpSession session) {

		Paging page = new Paging();
		Map<String, Object> parm = new HashMap<String, Object>();
		ArrayList<Integer> arr=null;
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

		Object id = (Object)session.getAttribute("id");

		int pageNum=0;
		int mapNum=0;
		int sendPageNum=0;
		int realNum = Integer.parseInt(num);


		page.setTotalNum(mypageservice.getPageNum(id.toString()));
		page.setOnePageBoard(5);

		if(page.getTotalNum() <= page.getOnePageBoard() ) {
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

		parm.put("paging", page);
		parm.put("user_id", id);
		
		model.addAttribute("lastNum", pageNum);
		model.addAttribute("userInfo",mypageservice.getInfo(id.toString()));
		model.addAttribute("mychecklist",mypageservice.myList(parm));
		model.addAttribute("pageNum",map.get(sendPageNum));

		if(realNum > pageNum) {
			System.out.println("pageNum : " + pageNum);
			return "redirect:/mypage/"+pageNum;
		}

		return "mypage/mypage";
	}
	

}

