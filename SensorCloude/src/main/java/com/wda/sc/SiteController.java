package com.wda.sc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.MysensorVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
import com.wda.sc.domain.SiteVO;
import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.MysensorService;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/site")
public class SiteController {

	private SiteService siteservice;
	private CheckboardService checkboardservice;
	private MysensorService mysensorservice;

	 @RequestMapping(value = "/address", method = RequestMethod.GET)
	 public String address(Locale locale, Model model) {

	 return "site/address";
	 }

	// 현장 추가
	@RequestMapping(value = "/siteadd", method = RequestMethod.GET)
	public String siteadd(Locale locale, Model model, HttpSession session, HttpServletResponse response)
			throws IOException {

		int mlevel = (int) session.getAttribute("mlevel");
		System.out.println("레벨" + mlevel);

		if (mlevel == 1 || mlevel == 2) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\n 3등급(쓰기권한)이상이 열람가능합니다'); location.href='/sitelist/1';");
			out.println("</script>");

			// response.sendRedirect("/sitelist/1");
		}

		return "site/siteadd";

	}

	// 현장 수정
	@RequestMapping(value = "/sitemodify" + "/{site_id}", method = RequestMethod.GET)
	public String sitemodify(@PathVariable String site_id, Model model, HttpSession session,
			HttpServletResponse response) throws IOException {
		
		System.out.println(site_id);
		
		int mlevel = (int) session.getAttribute("mlevel");
		System.out.println("레벨" + mlevel);
		if (mlevel == 1 || mlevel == 2 || mlevel==3) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\n 4등급(수정권한)이상이 열람가능합니다'); history.go(-1);");
			out.println("</script>");

			// response.sendRedirect("/sitelist/1");
		}

		model.addAttribute("joinSite", siteservice.joinSite(site_id));
		return "site/sitemodify";
	}

	@RequestMapping(value = "{site_id}", method = RequestMethod.GET)
	public String siteclick(@PathVariable String site_id, Model model, HttpSession session,
			HttpServletResponse response) throws IOException {
		System.out.println("현장 iD =" + site_id);
		model.addAttribute("siteInfo", siteservice.getSite(site_id));
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id));
		model.addAttribute("siteStatus", siteservice.getStatus(site_id));
		System.out.println(siteservice.getStatus(site_id));// 현장클릭시 상태정보
		model.addAttribute("sensordata", mysensorservice.getData(site_id));

//		int mlevel = (int) session.getAttribute("mlevel");
//		System.out.println("레벨" + mlevel);
//		
//		if (mlevel == 1) {
//
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out = response.getWriter();
//			out.println("<script langauge='javascript'>");
//			out.println("alert('권한이 없습니다.\n 2등급(읽기권한)이상이 열람가능합니다');  location.href='/';");
//			out.println("</script>");
//
//		}
		return "site/sitemain";

	}

	@RequestMapping(value = "{site_id}" + "/sitealarm", method = RequestMethod.GET)
	public String sitealarm(@PathVariable String site_id, Model model,HttpSession session,
			HttpServletResponse response ) throws IOException {
	
		System.out.println("알람페이지");
		
//		int mlevel = (int) session.getAttribute("mlevel");
//		System.out.println("레벨" + mlevel);
//		//관리자만 알림 메시지 보내기 가능 
////		if (mlevel != 5) {
////
////			response.setContentType("text/html; charset=UTF-8");
////			PrintWriter out = response.getWriter();
////			out.println("<script langauge='javascript'>");
////			out.println("alert('권한이 없습니다.\n 관리자만 열람가능합니다');history.go(-1);");
////			out.println("</script>");
////
////		}
		model.addAttribute("siteInfo", siteservice.getSite(site_id));
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id));
		model.addAttribute("alarm", siteservice.getAlarm(site_id)); // 알람 내용 정
		
		return "site/sitealarm";
	}

	@RequestMapping(value = "{site_id}" + "/siterepair" + "/{num}", method = RequestMethod.GET)
	public String siterepair(@PathVariable String num, @PathVariable Object site_id, Model model,HttpSession session,
			HttpServletResponse response) throws IOException {

//		int mlevel = (int) session.getAttribute("mlevel");
//		System.out.println("레벨" + mlevel);
//		
//		if (mlevel == 1) {
//
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out = response.getWriter();
//			out.println("<script langauge='javascript'>");
//			out.println("alert('권한이 없습니다.\n2등급(읽기권한)이상이 열람가능합니다');history.go(-1);");
//			out.println("</script>");
//
//		}
//		
		
		int pageNum = 0;
		Paging page = new Paging();
		Map<String, Object> parm = new HashMap<String, Object>();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int realNum = Integer.parseInt(num);

		page.setTotalNum(siteservice.repairPageNum(site_id.toString()));

		if (page.getTotalNum() < page.getOnePageBoard()) {
			pageNum = 1;
		} else {
			pageNum = page.getTotalNum() / page.getOnePageBoard();
			if (page.getTotalNum() % page.getOnePageBoard() > 0) {
				pageNum = pageNum + 1;
			}
		}

		for (int i = 0; i < pageNum; i++) {
			arr.add(i + 1);
		}

		page.setEndnum((realNum * 10) + 1);
		page.setStartnum(page.getEndnum() - 10);

		parm.put("paging", page);
		parm.put("site_id", site_id);

		model.addAttribute("pageNum", arr);
		model.addAttribute("siteInfo", siteservice.getSite(site_id.toString())); // 현장정보
		model.addAttribute("checkboardlist", siteservice.repairList(parm));// 수리내역
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id.toString())); // 연락망
		return "site/siterepair";
	}

	@RequestMapping(value = "{site_id}" + "/sensormanage", method = RequestMethod.GET)
	public String sensormanage(@PathVariable String site_id, Model model,HttpSession session,
			HttpServletResponse response) throws IOException {
		
//		int mlevel = (int) session.getAttribute("mlevel");
//		System.out.println("레벨" + mlevel);
//		
//		if (mlevel == 1) {
//
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out = response.getWriter();
//			out.println("<script langauge='javascript'>");
//			out.println("alert('권한이 없습니다.\n2등급(읽기권한)이상이 열람가능합니다');history.go(-1);");
//			out.println("</script>");
//
//		}
		
		System.out.println("센서관리");

		model.addAttribute("siteInfo", siteservice.getSite(site_id)); // 현장정보
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id)); // 연락망
		model.addAttribute("sensor_kind", siteservice.getSensorKind()); // 센서종류


		
		model.addAttribute("siteInfo",siteservice.getSite(site_id));  //현장정보
		model.addAttribute("alarmMember",siteservice.getAlarm_member(site_id)); //연락망
		model.addAttribute("sensor_kind", siteservice.getSensorKind()); // 센서종류
		model.addAttribute("sensorlist",siteservice.installSensorList(site_id));

		return "site/sensormanage";
	}

	@RequestMapping(value = "{site_id}" + "/sensoradd", method = RequestMethod.GET)
	public String sensoradd(@PathVariable String site_id, Model model,HttpSession session,
			HttpServletResponse response) throws IOException {
		
//		int mlevel = (int) session.getAttribute("mlevel");
//		System.out.println("레벨" + mlevel);
//		
//		if (mlevel != 4 || mlevel !=5) {
//
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out = response.getWriter();
//			out.println("<script langauge='javascript'>");
//			out.println("alert('권한이 없습니다.\n4등급(수정권한)이상이 열람가능합니다');history.go(-1);");
//			out.println("</script>");
//
//		}
		
		
		System.out.println("센서추가");
		model.addAttribute("siteInfo", siteservice.getSite(site_id)); // 현장정보
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id)); // 연락망
		model.addAttribute("sensor_kind", siteservice.getSensorKind()); // 센서종류

		return "site/sensoradd";
	}

	@RequestMapping(value = "{site_id}" + "/download", method = RequestMethod.GET)
	public String download(@PathVariable String site_id, Model model,HttpSession session,
			HttpServletResponse response) throws IOException {
		
		
	int mlevel = (int) session.getAttribute("mlevel");
	System.out.println("레벨" + mlevel);
		
//		if (mlevel ==1 ) {
//
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out = response.getWriter();
//			out.println("<script langauge='javascript'>");
//			out.println("alert('권한이 없습니다.\n2등급(읽기권한)이상이 열람가능합니다');history.go(-1);");
//			out.println("</script>");
//
//		}
		System.out.println("스크립트다운로드");
		model.addAttribute("siteInfo", siteservice.getSite(site_id)); // 현장정보
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id)); // 연락망

		return "site/download";
	}

	// 현장추가
	@RequestMapping(value = "siteadd.do", method = RequestMethod.POST)
	@ResponseBody
	public String insertSite(SiteVO site) {
		switch (site.getType_no()) {
		case "building":
			site.setType_no("1");
			break;
		case "mountain":
			site.setType_no("0");
			break;
		}

		int a = siteservice.siteadd(site);
		int b = siteservice.networkadd(site);
		if (a == 0 && b == 0) {
			return "false";
		} else {
			return "success";
		}
	}

	// 현장수정
	@RequestMapping(value = "sitemodify.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateSite(SiteVO site) {
		System.out.println(site);
		switch (site.getType_no()) {
		case "building":
			site.setType_no("1");
			break;
		case "mountain":
			site.setType_no("0");
			break;
		}

		int a = siteservice.updatesite(site);
		int b = siteservice.updatenetwork(site);
		if (a == 0 && b == 0) {
			return "false";
		} else {
			return "success";
		}
	}

	// 알림 Insert
	@RequestMapping(value = "alarmMemberadd.do")
	@ResponseBody
	public String alarmMemberadd(AlarmMemberVO vo) {
		// 연락망 추가 폼을 이용한 추가

		int a = siteservice.insertAlarmMember(vo);

		if (a == 0) {
			return "false";
		} else if (a == 1) {
			return "success";
		}

		return "false";
	}

	@RequestMapping(value = "alarmadd.do")
	@ResponseBody
	public String alarmadd(AlarmVO vo, HttpSession session) {
		// 연락망 추가 폼을 이용한 추가
		String user = (String) session.getAttribute("id");
		vo.setSend_user(user);

		int a = siteservice.insertAlarm(vo);

		if (a == 0) {
			return "false";
		} else if (a == 1) {
			return "success";
		}

		return "false";
	}

	@RequestMapping(value = "alarmmod.do")
	@ResponseBody
	public String alarmmod(AlarmMemberVO vo) {
		// 연락망 사람 수정

		int a = siteservice.modAlarm(vo);

		if (a == 0) {
			return "false";
		} else if (a == 1) {
			return "success";
		}

		return "false";

	}

	@RequestMapping(value = "alarmdel.do")
	@ResponseBody
	public String alarmdel(AlarmMemberVO vo) {
		// 연락망 사람 삭제

		boolean result = siteservice.delAlarm(vo);

		if (result)
			return "success";
		else
			return "false";
	}

	@RequestMapping(value = "/" + "{site_id}" + "/sitecheckview/" + "{board_no}", method = RequestMethod.GET)
	public String checkin(Locale locale, @PathVariable String board_no, @PathVariable String site_id, Model model,HttpSession session,
			HttpServletResponse response) throws IOException {

		int mlevel = (int) session.getAttribute("mlevel");
		
		if (mlevel < 3) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\n3등급(쓰기권한)이상이 열람가능합니다');history.go(-1);");
			out.println("</script>");

		}
		
		
		model.addAttribute("sitecheckview", checkboardservice.viewgetList(board_no));

		return "site/sitecheckview";
	}

	// 현장관리 검색
	@RequestMapping(value = "/search" + "/{page}" + "/{searchType}" + "/{keyword}", method = RequestMethod.GET)
	public String sitelistSearch(@PathVariable int page, @PathVariable String searchType, @PathVariable String keyword,
			Model model) {

		Paging p = new Paging();
		Search s = new Search();
		ArrayList<SiteVO> searchArr = new ArrayList<SiteVO>();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		Map<Object, Object> parm = new HashMap<Object, Object>();

		p.getOnePageBoard(); // 페이지 당 보여지는 데이터 수

		s.setPage(page);
		s.setKeyword(keyword);
		s.setSearchType(searchType);

		System.out.println(page); // 현재 페이지 번호
		System.out.println(searchType); // 검색 옵션
		System.out.println(keyword); // 검색 키워드

		searchArr = siteservice.siteSearch(s);

		System.out.println(searchArr);

		int pageNum = 0;
		int realNum = page;
		p.setTotalNum(searchArr.size());

		System.out.println("전체숫자" + p.getTotalNum());

		if (p.getTotalNum() <= p.getOnePageBoard()) {
			pageNum = 1;
		} else {
			pageNum = p.getTotalNum() / p.getOnePageBoard();
			if (p.getTotalNum() % p.getOnePageBoard() > 0) {
				pageNum = pageNum + 1;
			}
		}

		for (int i = 0; i < pageNum; i++) {
			arr.add(i + 1);
		}

		p.setEndnum((realNum * 10) + 1);
		p.setStartnum(p.getEndnum() - 10);

		parm.put("Paging", p);
		parm.put("Search", s);

		model.addAttribute("pageNum", arr);
		System.out.println("pageNum" + arr);

		model.addAttribute("site", siteservice.getSearchResult(parm));
		System.out.println("site" + siteservice.getSearchResult(parm));

		return "/site/sitelist";
	}

	// 수리내역 검색
	@RequestMapping(value = "/{site_id}" + "/search" + "/{page}" + "/{searchType}"
			+ "/{keyword}", method = RequestMethod.GET)
	public String repairSearch(@PathVariable int page, @PathVariable String searchType, @PathVariable String keyword,
			@PathVariable String site_id, Model model) {

		Paging p = new Paging();
		Search s = new Search();
		ArrayList<CheckBoardVO> searchArr = new ArrayList<CheckBoardVO>();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		Map<Object, Object> parm = new HashMap<Object, Object>();

		p.getOnePageBoard(); // 페이지 당 보여지는 데이터 수

		s.setPage(page);
		s.setKeyword(keyword);
		s.setSearchType(searchType);
		s.setSite_id(site_id);

		System.out.println(page);
		System.out.println(keyword);
		System.out.println(searchType);
		System.out.println(site_id);
		searchArr = siteservice.repairSearch(s);

		System.out.println(searchArr);

		int pageNum = 0;
		int realNum = page;
		p.setTotalNum(searchArr.size());

		System.out.println("전체숫자" + p.getTotalNum());

		if (p.getTotalNum() <= p.getOnePageBoard()) {
			pageNum = 1;
		} else {
			pageNum = p.getTotalNum() / p.getOnePageBoard();
			if (p.getTotalNum() % p.getOnePageBoard() > 0) {
				pageNum = pageNum + 1;
			}
		}

		for (int i = 0; i < pageNum; i++) {
			arr.add(i + 1);
		}

		p.setEndnum((realNum * 10) + 1);
		p.setStartnum(p.getEndnum() - 10);

		parm.put("Paging", p);
		parm.put("Search", s);

		model.addAttribute("pageNum", arr);
		System.out.println("pageNum" + arr);

		model.addAttribute("repair", siteservice.getSearchResultRepair(parm));
		model.addAttribute("siteInfo", siteservice.getSite(site_id.toString())); // 현장정보
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id.toString())); // 연락망

		return "/site/siterepair";
	}

}