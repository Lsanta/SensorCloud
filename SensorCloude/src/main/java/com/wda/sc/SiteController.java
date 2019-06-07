package com.wda.sc;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.InstallSensorVO;
import com.wda.sc.domain.MysensorVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.ProcessPidVO;
import com.wda.sc.domain.Search;
import com.wda.sc.domain.SensorDataVO;
import com.wda.sc.domain.SiteVO;
import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.MysensorService;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



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

		if (mlevel == 1 || mlevel == 2) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다. \\n 3등급(쓰기권한)이상이 열람가능합니다'); history.go(-1);");
			out.println("</script>");

			// response.sendRedirect("/sitelist/1");
		}

		//보유 센서 넘기기
		ArrayList<MysensorVO> arr2 = mysensorservice.getMysensor();
		System.out.println(arr2);
		
		model.addAttribute("siteSensor", arr2);
		return "site/siteadd";

	}

	// 현장 수정
	@RequestMapping(value = "/sitemodify" + "/{site_id}", method = RequestMethod.GET)
	public String sitemodify(@PathVariable String site_id, Model model, HttpSession session,
			HttpServletResponse response) throws IOException {

		System.out.println(site_id);

		int mlevel = (int) session.getAttribute("mlevel");
		System.out.println("레벨" + mlevel);
		if (mlevel == 1 || mlevel == 2 || mlevel == 3) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n 4등급(수정권한)이상이 열람가능합니다'); history.go(-1);");
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

		int mlevel = (int) session.getAttribute("mlevel");

		if (mlevel == 1) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n 2등급(읽기권한)이상이 열람가능합니다');  location.href='/';");
			out.println("</script>");

		}

		System.out.println("현장 iD =" + site_id);
		model.addAttribute("siteInfo", siteservice.getSite(site_id));
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id));
		model.addAttribute("siteStatus", siteservice.getStatus(site_id));

		return "site/sitemain";
	}

	////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/graph/" + "{site_id}" + ".do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject drawG(@PathVariable String site_id) {
		
		ArrayList<SensorDataVO> getGraph = siteservice.getSensingDate(site_id);
		ArrayList<SensorDataVO> getGraphName = siteservice.getGraphName(site_id);
	
		JSONArray name = JSONArray.fromObject(getGraphName);
		JSONArray graph = JSONArray.fromObject(getGraph);
	
		System.out.println("이거 : "+name);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("graph", graph);
	
		
		JSONObject json = JSONObject.fromObject(map);
		System.out.println(json);
		return json;
	}
	////////////////////////////////////////////////////////////////////
	@RequestMapping(value = "/sdata/" + "{site_id}" + ".do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject sensordata(@PathVariable String site_id) {
		ArrayList<SensorDataVO> dListname = siteservice.getDataName(site_id);
		ArrayList<SensorDataVO> dList = siteservice.getData(site_id);
		System.out.println(dListname);
		System.out.println(dList);
		
		JSONArray dListJson = JSONArray.fromObject(dListname);
		JSONArray dJson = JSONArray.fromObject(dList);
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", dListJson);
		map.put("data",dJson);
		JSONObject json = JSONObject.fromObject(map);

		return json;
	}
////////////////////////////////////////////////////////////////////

	@RequestMapping(value = "{site_id}" + "/sitealarm", method = RequestMethod.GET)
	public String sitealarm(@PathVariable String site_id, Model model, HttpSession session,
			HttpServletResponse response) throws IOException {

		System.out.println("알람페이지");

		int mlevel = (int) session.getAttribute("mlevel");
		System.out.println("레벨" + mlevel);
		// 관리자만 알림 메시지 보내기 가능
		if (mlevel != 5) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n 관리자만 열람가능합니다');history.go(-1);");
			out.println("</script>");

		}
		model.addAttribute("siteInfo", siteservice.getSite(site_id));
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id));
		model.addAttribute("alarm", siteservice.getAlarm(site_id)); // 알람 내용 정

		return "site/sitealarm";
	}

	@RequestMapping(value = "{site_id}" + "/siterepair" + "/{num}", method = RequestMethod.GET)
	public String siterepair(@PathVariable String num, @PathVariable Object site_id, Model model, HttpSession session,
			HttpServletResponse response) throws IOException {

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
		Map<String, Object> parm = new HashMap<String, Object>();
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> arr = new ArrayList<Integer>();

		int pageNum = 0;
		int mapNum = 0;
		int sendPageNum = 0;
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

		if (pageNum % 5 != 0) {
			mapNum = pageNum / 5 + 1;
		} else {
			mapNum = pageNum / 5;
		}

		for (int i = 0; i < mapNum; i++) {
			arr = new ArrayList<Integer>();
			for (int j = 0; j < 5; j++) {

				if ((i * 5) + j + 1 > pageNum) {
					break;
				}

				arr.add((i * 5) + j + 1);
			}
			map.put(i, arr);
		}

		sendPageNum = (realNum - 1) / 5;

		page.setEndnum((realNum * 10) + 1);
		page.setStartnum(page.getEndnum() - 10);

		parm.put("paging", page);
		parm.put("site_id", site_id);
		
		model.addAttribute("lastNum", pageNum);
		model.addAttribute("pageNum", map.get(sendPageNum));
		model.addAttribute("siteInfo", siteservice.getSite(site_id.toString())); // 현장정보
		model.addAttribute("checkboardlist", siteservice.repairList(parm));// 수리내역
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id.toString())); // 연락망

		if (realNum > pageNum) {
			System.out.println("pageNum : " + pageNum);
			return "redirect:/site/" + site_id + "/siterepair/" + pageNum;
		}

		return "site/siterepair";
	}

	@RequestMapping(value = "{site_id}" + "/sensormanage" + "/{num}", method = RequestMethod.GET)
	public String sensormanage(@PathVariable String num, @PathVariable String site_id, Model model, HttpSession session,
			HttpServletResponse response) throws IOException {

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
		Map<String, Object> parm = new HashMap<String, Object>();
		ArrayList<Integer> arr = null;
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

		int pageNum = 0;
		int mapNum = 0;
		int sendPageNum = 0;
		int realNum = Integer.parseInt(num);

		page.setTotalNum(siteservice.sensorPageNum(site_id.toString()));

		if (page.getTotalNum() < page.getOnePageBoard()) {
			pageNum = 1;
		} else {
			pageNum = page.getTotalNum() / page.getOnePageBoard();
			if (page.getTotalNum() % page.getOnePageBoard() > 0) {
				pageNum = pageNum + 1;
			}
		}

		if (pageNum % 5 != 0) {
			mapNum = pageNum / 5 + 1;
		} else {
			mapNum = pageNum / 5;
		}

		for (int i = 0; i < mapNum; i++) {
			arr = new ArrayList<Integer>();
			for (int j = 0; j < 5; j++) {

				if ((i * 5) + j + 1 > pageNum) {
					break;
				}

				arr.add((i * 5) + j + 1);
			}
			map.put(i, arr);
		}

		sendPageNum = (realNum - 1) / 5;

		page.setEndnum((realNum * 10) + 1);
		page.setStartnum(page.getEndnum() - 10);

		parm.put("paging", page);
		parm.put("site_id", site_id);

		model.addAttribute("lastNum", pageNum);
		System.out.println("pageNum : " + pageNum);
		model.addAttribute("pageNum", map.get(sendPageNum));
		model.addAttribute("siteInfo", siteservice.getSite(site_id)); // 현장정보
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id)); // 연락망
		model.addAttribute("sensor_kind", siteservice.getSensorKind()); // 센서종류
		model.addAttribute("sensorlist", siteservice.installSensorList(parm));

		if (realNum > pageNum) {
			System.out.println("pageNum : " + pageNum);
			return "redirect:/site/" + site_id + "/sensormanage/" + pageNum;
		}

		return "site/sensormanage";
	}

	@RequestMapping(value = "{site_id}" + "/sensoradd" + "/{num}", method = RequestMethod.GET)
	public String sensoradd(@PathVariable String site_id, Model model, HttpSession session,
			HttpServletResponse response) throws IOException {

		int mlevel = (int) session.getAttribute("mlevel");
		System.out.println("레벨" + mlevel);

		if (mlevel < 4) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n4등급(수정권한)이상이 열람가능합니다');history.go(-1);");
			out.println("</script>");

		}

		System.out.println("센서추가");
		model.addAttribute("siteInfo", siteservice.getSite(site_id)); // 현장정보
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id)); // 연락망
		model.addAttribute("sensor_kind", siteservice.getSensorKind()); // 센서종류

		return "site/sensoradd";
	}

	@RequestMapping(value = "{site_id}" + "/sensormodify" + "/{sensor_sn}", method = RequestMethod.GET)
	public String sensormodify(@PathVariable String site_id, @PathVariable String sensor_sn, Model model,
			HttpSession session, HttpServletResponse response) throws IOException {

		System.out.println("센서수정");
		model.addAttribute("siteInfo", siteservice.getSite(site_id)); // 현장정보
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id)); // 연락망
		model.addAttribute("sensor_kind", siteservice.getSensorKind()); // 센서종류
		model.addAttribute("sensorInfo", siteservice.getSensor(sensor_sn));

		System.out.println(sensor_sn);

		return "site/sensormodify";
	}

	@RequestMapping(value = "{site_id}" + "/download", method = RequestMethod.GET)
	public String download(@PathVariable String site_id, Model model, HttpSession session, HttpServletResponse response)
			throws IOException {

		int mlevel = (int) session.getAttribute("mlevel");

		if (mlevel == 1) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n2등급(읽기권한)이상이 열람가능합니다');history.go(-1);");
			out.println("</script>");

		}
		System.out.println("스크립트다운로드");
		model.addAttribute("siteInfo", siteservice.getSite(site_id)); // 현장정보
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id)); // 연락망

		return "site/download";
	}

	// 현장추가
	@RequestMapping(value = "siteadd.do", method = RequestMethod.POST)
	@ResponseBody
	public String insertSite(SiteVO site, @RequestBody Map<String,Object> map) {
		System.out.println("현장 추가");
		System.out.println(map);
		
		site.setType_no((String) map.get("type_no"));
		site.setAddress((String) map.get("address"));
		site.setSite_name((String)map.get("site_name"));

	    site.setRperiod((String) map.get("rperiod"));
	    site.setSig_port_num((String) map.get("sig_port_num"));
	    site.setVirtual_port((String) map.get("virtual_port"));
		
	    System.out.println("사이트" +site);
		ProcessPidVO setPid = new ProcessPidVO();
		
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
			int site_id = siteservice.getSiteNum();
						
			String command = "C:\\Users\\bon300-27\\Desktop\\TestExe\\ConsoleApp1.exe"+" "+site.getRperiod()+" "+site.getVirtual_port()+" "+site.getSig_port_num()+" "+site_id;
			ArrayList<String> rawPid = new Cmd().exeCmd(command);
			System.out.println(rawPid);
			ArrayList<ProcessPidVO> dbPid_object = siteservice.getProcessPid(); 
			ArrayList<String> temp = new ArrayList<String>();
			
			/* String형 list를 Int형으로 변환 */
			ArrayList<Integer> rawPid_int = new ArrayList<Integer>();
			ArrayList<Integer> dbPid_int = new ArrayList<Integer>();
			ArrayList<String> dbPid = new ArrayList<String>();
			
			System.out.println("1");
			for(int i = 0; i < dbPid_object.size(); i++) {
				dbPid.add(dbPid_object.get(i).getPid());
			}
			
			for(int i = 0; i < rawPid.size(); i++) {
				rawPid_int.add(Integer.parseInt(rawPid.get(i)));
			}
			
			for(int i = 0; i < dbPid.size(); i++) {
				dbPid_int.add(Integer.parseInt(dbPid.get(i)));
			}
			System.out.println("2");
			/* 배열 정렬 */
			
			Ascending ascending = new Ascending();
			
			Collections.sort(rawPid_int, ascending);
			Collections.sort(dbPid_int, ascending);
			
			System.out.println("3");
			/* db에 들어있지 않는 pid를 얻어와서 배열에 저장*/
			for(int i = 0; i < rawPid_int.size(); i++) {
				if(!(rawPid_int.get(i).equals(dbPid_int.get(i)))){
					setPid.setPid(rawPid_int.get(i).toString());
				}
			}
		
//			setPid.setSite_id(site.getSite_id());	//site_id 불러오기
			setPid.setSite_id(site_id);	//site_id 불러오기
			System.out.println("사이트아이디" + site_id);
			System.out.println("setPid" + setPid);
			siteservice.setProcessPid(setPid);

			//
			JSONArray naArr = JSONArray.fromObject(map.get("sensorData"));
			JSONObject na = JSONObject.fromObject(map.get("sensorData"));
			
			System.out.println(na);
			
			
			Iterator Iter = na.keys();
			InstallSensorVO test = new InstallSensorVO();
			System.out.println(naArr);
			  while(Iter.hasNext())
			    {
			        String b1 = Iter.next().toString();
			        test.setSensor_sn(b1);
			        
			        JSONArray na2 = JSONArray.fromObject(na.get(b1));
			        
			        for(int i = 0; i <na2.size(); i++) {
			        	JSONObject na3 = JSONObject.fromObject(na2.get(i));
			        	Iterator Iter2 = na3.keys();
			        	
			        	System.out.println(i+" : "+na2.get(i));
			        			        	
			        	for(int j = 0; j < 1; j++) {
			        		String c = Iter2.next().toString();
			        		test.setProgram_var(c);
			        		
				        	System.out.println(na3.get(c));
				        	
				        	String[] spl = ((String) na3.get(c)).split(",");
				        	test.setUpper_limit(spl[0]);
				        	test.setLower_limit(spl[1]);
				        	test.setSite_id(site_id);
				        	
				        	// insert DB 
				        	int resultNum = siteservice.addInstallSensor(test);
				        	
			        	}
			        }		        

			    }
			
			
			
			
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

	@RequestMapping(value = "sitealarmmod", method = RequestMethod.GET)
	public String sensormod(Locale locale, Model model, HttpSession session, HttpServletResponse response)
			throws IOException {

		return "site/sitealarmmod";
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
	public String checkin(Locale locale, @PathVariable String board_no, @PathVariable String site_id, Model model,
			HttpSession session, HttpServletResponse response) throws IOException {

		int mlevel = (int) session.getAttribute("mlevel");

		if (mlevel < 2) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n2등급(읽기권한)이상이 열람가능합니다');history.go(-1);");
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
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

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
		int mapNum = 0;
		int sendPageNum = 0;
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

		if (pageNum % 5 != 0) {
			mapNum = pageNum / 5 + 1;
		} else {
			mapNum = pageNum / 5;
		}

		for (int i = 0; i < mapNum; i++) {
			arr = new ArrayList<Integer>();
			for (int j = 0; j < 5; j++) {

				if ((i * 5) + j + 1 > pageNum) {
					break;
				}

				arr.add((i * 5) + j + 1);
			}
			map.put(i, arr);
		}

		sendPageNum = (realNum - 1) / 5;

		p.setEndnum((realNum * 10) + 1);
		p.setStartnum(p.getEndnum() - 10);

		parm.put("Paging", p);
		parm.put("Search", s);

		model.addAttribute("lastNum", pageNum);
		model.addAttribute("pageNum", map.get(sendPageNum));
		System.out.println("pageNum" + arr);

		model.addAttribute("site", siteservice.getSearchResult(parm));
		System.out.println("site" + siteservice.getSearchResult(parm));

		if (realNum > pageNum) {
			System.out.println("pageNum : " + pageNum);
			return "redirect:/site/search/" + pageNum + "/" + searchType + "/" + keyword;
		}

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
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

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
		int mapNum = 0;
		int sendPageNum = 0;
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

		if (pageNum % 5 != 0) {
			mapNum = pageNum / 5 + 1;
		} else {
			mapNum = pageNum / 5;
		}

		for (int i = 0; i < mapNum; i++) {
			arr = new ArrayList<Integer>();
			for (int j = 0; j < 5; j++) {

				if ((i * 5) + j + 1 > pageNum) {
					break;
				}

				arr.add((i * 5) + j + 1);
			}
			map.put(i, arr);
		}

		sendPageNum = (realNum - 1) / 5;

		p.setEndnum((realNum * 10) + 1);
		p.setStartnum(p.getEndnum() - 10);

		parm.put("Paging", p);
		parm.put("Search", s);
		
		model.addAttribute("lastNum", pageNum);
		model.addAttribute("pageNum", map.get(sendPageNum));
		System.out.println("pageNum" + arr);
		
		
		model.addAttribute("repair", siteservice.getSearchResultRepair(parm));
		model.addAttribute("siteInfo", siteservice.getSite(site_id.toString())); // 현장정보
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id.toString())); // 연락망

		if (realNum > pageNum) {
			System.out.println("pageNum : " + pageNum);

			try {
				keyword = URLEncoder.encode(keyword, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "redirect:/site/" + site_id + "/search/" + pageNum + "/" + searchType + "/" + keyword;
		}

		return "/site/siterepair";
	}

	// 센서관리 검색
	@RequestMapping(value = "/{site_id}" + "/search1" + "/{page}" + "/{searchType}"
			+ "/{keyword}", method = RequestMethod.GET)
	public String smSearch(@PathVariable int page, @PathVariable String searchType, @PathVariable String keyword,
			@PathVariable String site_id, Model model) {

		Paging p = new Paging();
		Search s = new Search();
		ArrayList<MysensorVO> searchArr = new ArrayList<MysensorVO>();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		Map<Object, Object> parm = new HashMap<Object, Object>();
		Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

		p.getOnePageBoard(); // 페이지 당 보여지는 데이터 수

		s.setPage(page);
		s.setKeyword(keyword);
		s.setSearchType(searchType);
		s.setSite_id(site_id);

		System.out.println(page);
		System.out.println(keyword);
		System.out.println(searchType);
		System.out.println(site_id);
		searchArr = siteservice.smSearch(s);

		System.out.println(searchArr);

		int pageNum = 0;
		int mapNum = 0;
		int sendPageNum = 0;
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

		if (pageNum % 5 != 0) {
			mapNum = pageNum / 5 + 1;
		} else {
			mapNum = pageNum / 5;
		}

		for (int i = 0; i < mapNum; i++) {
			arr = new ArrayList<Integer>();
			for (int j = 0; j < 5; j++) {

				if ((i * 5) + j + 1 > pageNum) {
					break;
				}

				arr.add((i * 5) + j + 1);
			}
			map.put(i, arr);
		}

		sendPageNum = (realNum - 1) / 5;

		p.setEndnum((realNum * 10) + 1);
		p.setStartnum(p.getEndnum() - 10);

		parm.put("Paging", p);
		parm.put("Search", s);

		model.addAttribute("lastNum", pageNum);
		model.addAttribute("pageNum", map.get(sendPageNum));
		System.out.println("pageNum" + arr);

		model.addAttribute("sensormanage", siteservice.getSearchResultSM(parm));
		model.addAttribute("siteInfo", siteservice.getSite(site_id.toString())); // 현장정보
		model.addAttribute("alarmMember", siteservice.getAlarm_member(site_id.toString())); // 연락망

		if (realNum > pageNum) {
			System.out.println("pageNum : " + pageNum);
			System.out.println("keyword : " + keyword);
			try {
				keyword = URLEncoder.encode(keyword, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "redirect:/site/" + site_id + "/search1/" + pageNum + "/" + searchType + "/" + keyword + "";
		}

		return "/site/sensormanage";
	}
}

class Ascending implements Comparator<Integer> { 
    @Override
    public int compare(Integer o1, Integer o2) {
        return o1.compareTo(o2);
    }
}