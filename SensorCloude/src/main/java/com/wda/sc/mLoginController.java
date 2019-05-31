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
		System.out.println(passwordt);

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
				json.put("level", arr.get(0).getM_level());
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
	@RequestMapping(value = "/appmain", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ArrayList<SiteVO> appmain(@RequestBody Map<String, String> quer) {
		String a = (String) quer.get("xlatitude");
		String b = (String) quer.get("xlongitude");

	
		double latitude = Double.valueOf(a);
		double longitude = Double.valueOf(b);
		
		Map<String,Double> map = new HashMap<String, Double>();
		map.put("latitude",latitude);
		map.put("longitude",longitude);
		
		
		
//		System.out.println(latitude);
//		System.out.println(longitude);
		
		ArrayList<SiteVO> list = siteservice.appmain(map);

		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getAddress().length() > 6) {
				list.get(i).setAddress(list.get(i).getAddress().substring(0,6)+"...");
			}
		}
		

//		      for(int i = 0; i < list.size(); i++) {
//		         if(list.get(i).getAddress().length() > 6) {
//		            list.get(i).setAddress(list.get(i).getAddress().substring(0,6)+"...");
//		         }
//		      }
		
		return list;
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
	@RequestMapping(value = "/installsensor", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ArrayList<SensorDataVO> installsensor(@RequestBody String site_id, Locale locale, Model model) {
		System.out.println(site_id);

		ArrayList<SensorDataVO> result02 = siteservice.getDataName(site_id);

		return result02;
	}

	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value = "/mSearch.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

	public @ResponseBody ArrayList<SiteVO> mSearch(@RequestBody Map<String, String> map, Criteria criteria) throws Exception {
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
	
	   @CrossOrigin(origins = "*", maxAge = 3600)
	   @RequestMapping(value = "/siterepairview", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	   @ResponseBody
	   public ArrayList<CheckBoardVO> siterepairview(@RequestBody String board_no, Locale locale, Model model) {
	      
	      System.out.println(board_no);
	      ArrayList<CheckBoardVO> result = checkboardservice.viewgetList(board_no);
	      System.out.println(result);
	      
	      return result;

	   }


}
