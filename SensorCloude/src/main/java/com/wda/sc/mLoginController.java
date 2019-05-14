package com.wda.sc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Base64.Decoder;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.codehaus.stax2.ri.typed.ValueDecoderFactory.DecimalDecoder;
import org.json.JSONObject;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.SiteVO;
import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.LoginService;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/app")
public class mLoginController {
	private LoginService loginservice;
	private SiteService siteservice;
	private CheckboardService checkboardservice;
	
	@CrossOrigin(origins = "*" ,maxAge = 3600)
	@RequestMapping(value = "/mlog", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String mlogin(@RequestBody String idt, String passwordt) throws Exception {
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
		 
		 if(arr.size() != 0) {
			 	
				if(arr.get(0).getPassword().equals(password)) {
						json.put("id", id);
						json.put("password", password);
						json.put("signal", "ok");
				}else {
					json.put("signal", "passfail");
				}
			}
		 	else {
		 		json.put("signal", "idfail");
			}
		 System.out.println(json);
		 return json.toString();
	}
	
	   @CrossOrigin(origins = "*", maxAge = 3600)
	   @RequestMapping(value ="/mmain", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	   @ResponseBody
	   public ArrayList<SiteVO> mainlist(Locale locale, Model model) {
	   
	      
	      ArrayList<SiteVO> result = siteservice.getList();

	      return result;
	   }
	   
	   @CrossOrigin(origins = "*", maxAge = 3600)
	   @RequestMapping(value ="/sitemain", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	   @ResponseBody
	   public ArrayList<SiteVO> sitemainlist(@RequestBody String site_id,Locale locale, Model model) {
	      System.out.println(site_id);
	      
	      ArrayList<SiteVO> result01 = siteservice.getSite(site_id);
	      
	      return result01;
	   }
	  
	  
	   	@CrossOrigin(origins = "*", maxAge = 3600)
		@RequestMapping(value = "/mSearch.do", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
		
		public @ResponseBody ArrayList<SiteVO> mSearch(@RequestBody Map<String, String> map) throws Exception {
			
			 String word = (String) map.get("word");
			 ArrayList<SiteVO> arr = new ArrayList<SiteVO>();
			 //현장 이름 검색
			 arr = siteservice.getAppSearch(word);
			 return arr;
		}
	   	
	   	
	   	@CrossOrigin(origins = "*", maxAge = 3600)
	    @RequestMapping(value ="/siterepairlist", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	    @ResponseBody
	    public ArrayList<CheckBoardVO> siterepairlist(@RequestBody String site_id,Locale locale, Model model) {
	       
	       
	       ArrayList<CheckBoardVO> result02 = checkboardservice.apprepairList(site_id);
	       
	       return result02;
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
	
}
