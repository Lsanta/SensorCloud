package com.wda.sc;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.stax2.ri.typed.ValueDecoderFactory.DecimalDecoder;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.SiteVO;
import com.wda.sc.service.LoginService;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/app")
public class mLoginController {
	private LoginService loginservice;
	private SiteService siteservice;
	
	@CrossOrigin(origins = "*" ,maxAge = 3600)
	@RequestMapping(value = "/mlog.do", method = RequestMethod.POST)
	@ResponseBody
	public String mlogin(@RequestBody Map<String, String> map) throws Exception {
		 System.out.println("안와"); 
		
		 String id = (String)map.get("id");
		 String password = (String) map.get("password");
		 System.out.println("====================================");
		 System.out.println(id);
		 System.out.println(password);
		 
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
	
	
}
