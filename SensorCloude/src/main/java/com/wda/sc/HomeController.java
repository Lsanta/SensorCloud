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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.Paging;
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
	public String main(Locale locale, Model model) {

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
		model.addAttribute("sitelist",siteservice.getList());
		model.addAttribute("timelinelist",timelineservice.timedesc());
		model.addAttribute("mainchecklist",arr);
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
		int pageNum = 0;
		ArrayList<Integer> arr = new ArrayList<Integer>();
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

		for(int i = 0; i < pageNum; i ++) {
			arr.add(i+1);
		}

		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);

		model.addAttribute("pageNum",arr);
		model.addAttribute("checkboardlist",checkboardservice.getList(page));

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


		Paging page = new Paging();
		int pageNum = 0;
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int realNum = Integer.parseInt(num);
		page.setTotalNum(siteservice.getPageNum());

		if(page.getTotalNum() <= page.getOnePageBoard() ) {
			pageNum = 1;
		}else {
			pageNum = page.getTotalNum()/page.getOnePageBoard();
			if(page.getTotalNum()%page.getOnePageBoard() > 0) {
				pageNum = pageNum + 1;
			}
		}


		for(int i = 0; i < pageNum; i ++) {
			arr.add(i+1);
		}


		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);

		model.addAttribute("content",siteservice.getContent(page));
		model.addAttribute("pageNum",arr);
		model.addAttribute("sitelist",siteservice.getList());

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
		int pageNum = 0;
		ArrayList<Integer> arr = new ArrayList<Integer>();
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

		for(int i = 0; i < pageNum; i ++) {
			arr.add(i+1);
		}

		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);

		//사용자관리에 승급요청등급과 승급요청한 아이디를 넘긴다.


		//

		model.addAttribute("pageNum",arr);
		model.addAttribute("userlist",usermanageservice.getList(page));

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
		int pageNum = 0;
		ArrayList<Integer> arr = new ArrayList<Integer>();
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

		for(int i = 0; i < pageNum; i ++) {
			arr.add(i+1);
		}

		page.setEndnum((realNum*5)+1);
		page.setStartnum(page.getEndnum()-5);

		model.addAttribute("pageNum",arr);
		model.addAttribute("timelinelist",timelineservice.getList(page));
		System.out.println(timelineservice.getList(page));



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
		int pageNum = 0;

		ArrayList<Integer> arr = new ArrayList<Integer>();
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

		for(int i = 0; i < pageNum; i ++) {
			arr.add(i+1);
		}

		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);


		model.addAttribute("pageNum",arr);
		model.addAttribute("sensorlist",mysensorservice.getList(page));

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

		//		for(int i = 0; i < pageNum; i ++) {
		//			arr.add(i+1);
		//		}

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
		System.out.println("num : "+num );
		System.out.println("realNum : "+realNum);
		sendPageNum = realNum/5;
		

		page.setEndnum((realNum*5)+1);
		page.setStartnum(page.getEndnum()-5);

		parm.put("paging", page);
		parm.put("user_id", id);

		//model.addAttribute("pageNum",arr);
		model.addAttribute("userInfo",mypageservice.getInfo(id.toString()));
		model.addAttribute("mychecklist",mypageservice.myList(parm));
		System.out.println(mypageservice.getInfo(id.toString()));
		model.addAttribute("pageNum",map.get(sendPageNum));
		System.out.println("sendpageNum : "+sendPageNum);
		System.out.println(map.get(sendPageNum));

		return "mypage/mypage";
	}

}

