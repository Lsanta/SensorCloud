package com.wda.sc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.SensorDataVO;
import com.wda.sc.domain.SiteVO;
import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.LoginService;
import com.wda.sc.service.SiteService;
import com.wda.sc.domain.Criteria;

import lombok.AllArgsConstructor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/*import org.json.JSONObject;*/

@Controller
@AllArgsConstructor
@RequestMapping("/app")
public class mLoginController {
	private LoginService loginservice;
	private SiteService siteservice;
	private CheckboardService checkboardservice;

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/login/mlog", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String mlogin(@RequestBody String idt, String passwordt, HttpSession session) throws Exception {
//		response.setHeader("Access-Control-Allow-Origin", "*");

//		response.setHeader("Access-Control-Allow-Credentials", "true");
//		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//		response.setHeader("Connection", "close");
//		response.setContentType("application/json");
//		response.setCharacterEncoding("UTF-8");

//		response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept");

		System.out.println("안와");

//		 String id = (String)map.get("id");
//		 String password = (String) map.get("password");
		System.out.println("====================================");
		System.out.println(idt);

		String[] array = idt.split("&");
		String[] id2 = array[0].split("=");
		String[] password2 = array[1].split("=");

		String id = id2[1];
		String password = password2[1];

		ArrayList<MemberVO> arr = new ArrayList<MemberVO>();
		arr = loginservice.login(id);

		JSONObject json = new JSONObject();

		if (arr.size() != 0) {

			if (arr.get(0).getPassword().equals(password)) {
				json.put("id", id);
				json.put("password", password);
				json.put("signal", "ok");
				session.setAttribute("id", id);
			} else {
				json.put("signal", "passfail");
			}
		} else {
			json.put("signal", "idfail");
		}
		System.out.println(json);
		return json.toString();
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/mmain", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ArrayList<SiteVO> mainlist(Locale locale, Model model) {

		ArrayList<SiteVO> result = siteservice.getList();

		return result;
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/sitemain", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ArrayList<SiteVO> sitemainlist(@RequestBody String site_id, Locale locale, Model model) {
		System.out.println(site_id);

		ArrayList<SiteVO> result01 = siteservice.getSite(site_id);

		return result01;
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/mSearch.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

	public @ResponseBody ArrayList<SiteVO> mSearch(@RequestBody Map<String, String> map, Criteria criteria) throws Exception {
		//페이징 하는중~~~
		int pagenum = criteria.getPagenum();

		Paging page = new Paging(); // 최대한 코드를 수정 안하기 위한 기존 페이징
		criteria.setTotalcount(checkboardservice.getPageNum()); // 전체 게시글 개수를 지정
		criteria.setContentnum(5);
		criteria.setPagenum(pagenum); // 현재 페이지를 페이지 객체에 지정
		criteria.setStartnum(pagenum); // 컨텐츠 시작 번호 지정
		criteria.setEndnum(pagenum); // 컨텐츠 끈 번호 지정
		criteria.setCurrentblock(pagenum); // 현재 페이지 블록이 몇번인지 현재 페이지 번호 통해 지정
		criteria.setLastblock(criteria.getTotalcount()); // 마지막 블록 번호를 전체 게시글 수를 통해 정함
		criteria.prevnext(pagenum); // 현재 페이지 번호로 화살표를 나타낼지 정함
		criteria.setStartPage(criteria.getCurrentblock()); // 시작 페이지를 페이지 블록번호로 정함
		criteria.setEndPage(criteria.getLastblock(), criteria.getCurrentblock()); // 마지막 페이지를 마지막 페이지 블록과 현재 페이지 블록으로 정함
		page.setEndnum(criteria.getEndnum() + 1); // 기존 코드를 유지하기 위해 +1함 (기존은 endnum이 5면 4까지 나온다 )
		page.setStartnum(criteria.getStartnum());

		String word = (String) map.get("word");
		ArrayList<SiteVO> arr = new ArrayList<SiteVO>();
		// 현장 이름 검색
		arr = siteservice.getAppSearch(word);
		return arr;
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/siterepairlist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ArrayList<CheckBoardVO> siterepairlist(@RequestBody String site_id, Locale locale, Model model) {

		ArrayList<CheckBoardVO> result02 = checkboardservice.apprepairList(site_id);

		return result02;
	}
	
	   @CrossOrigin(origins = "*", maxAge = 3600)
	   @RequestMapping(value ="/sitemainsensor", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	   @ResponseBody
	   public JSONObject drawG(@RequestBody String site_id) {
		   ArrayList<SensorDataVO> getGraph = siteservice.getSensingDate(site_id);
			ArrayList<SensorDataVO> getGraphName = siteservice.getGraphName(site_id);
		
			JSONArray name = JSONArray.fromObject(getGraphName);
			JSONArray graph = JSONArray.fromObject(getGraph);
		
	
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("graph", graph);
		
			
			JSONObject json = JSONObject.fromObject(map);
			System.out.println(json);
			return json;
	   }
	   
	   @CrossOrigin(origins = "*", maxAge = 3600)
	   @RequestMapping(value ="/sitedata", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	   @ResponseBody
	   public JSONObject sensordata(@RequestBody String site_id) {
		   ArrayList<SensorDataVO> dListname = siteservice.getDataName(site_id);
			ArrayList<SensorDataVO> dList = siteservice.getData(site_id);
			
			
			JSONArray dListJson = JSONArray.fromObject(dListname);
			JSONArray dJson = JSONArray.fromObject(dList);
		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", dListJson);
			map.put("data",dJson);
			JSONObject json = JSONObject.fromObject(map);

			return json;
	   }
	   
	   	@CrossOrigin(origins = "*" ,maxAge = 3600)
		@RequestMapping(value = "/appidFind", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
		@ResponseBody
		public String appidFind(@RequestBody String query ,Locale locale, Model model) throws UnsupportedEncodingException {
		     
	   
	   	String query2 = URLDecoder.decode(query, "UTF-8"); 
	   	String[] array = query2.split("&");
		String[] name2 = array[0].split("=");
		String[] tel2 = array[1].split("=");
		 
		String name = name2[1];
		String tel = tel2[1];
	   		
	   	ArrayList<MemberVO> arr2 = new ArrayList<MemberVO>();	 
		arr2 = loginservice.idFind(name);
		
		JSONObject json = new JSONObject();
		
			 if(arr2.size() == 0)
				 json.put("signal", "none");
				  
			 if(arr2.size() != 0) {
				 if(arr2.get(0).getPhone().equals(tel)) {
					 json.put("signal", arr2.get(0).getUser_id()); 
				 } else {
					 json.put("signal", "isN"); 
				 }
			 }
			 System.out.println(json);
			 return json.toString();
	   }
	   	
		@CrossOrigin(origins = "*" ,maxAge = 3600)
	   	@RequestMapping(value = "/signup_logincheck.do", method = RequestMethod.POST)
		@ResponseBody
		public String signidCheck(Model model, @RequestParam String id) {
			 
			 ArrayList<MemberVO> arr = new ArrayList<MemberVO>();	 
			 arr = loginservice.login(id);
		
			 if(arr.size() == 0) 
				  return "ok"; 
			 else
				 return "no";
			 
		 }
		@CrossOrigin(origins = "*" ,maxAge = 3600)
		@RequestMapping(value ="/signup", method = RequestMethod.POST)
		@ResponseBody
		public String signup(MemberVO m) {
				
			System.out.println(m);
			int checknum = loginservice.signup(m);
			
			if(checknum == 1) {
				return "ok";
			}
			else if(checknum == 0) {
				return "no";
			}
			
			return "no";
		}
		
		

}
