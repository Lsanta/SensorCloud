package com.wda.sc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import net.sf.json.*;

/**
 * @class ExampleSend
 * @brief This sample code demonstrate how to send sms through CoolSMS Rest API PHP
 */
@Controller
@AllArgsConstructor
@RequestMapping("/SMS")
public class SMSController {
	private SiteService siteservice;
	
	//알람 전송을 누르면 
	@RequestMapping(value = "/alarmadd.do" ,method = RequestMethod.POST)
	@ResponseBody
	public String alarmadd(AlarmVO vo, @RequestBody Map<String,Object> map, HttpSession session) {

		String alarm_content = (String) map.get("alarm_content");
		String site_id = (String) map.get("site_id");
				
		// 연락망 추가 폼을 이용한 추가
		String user = (String) session.getAttribute("id");
		vo.setAlarm_content(alarm_content);
		vo.setSite_id(Integer.parseInt(site_id));
		vo.setSend_user(user);
		
		JSONArray jarr = new JSONArray();

//		System.out.println(map.get("send"));
				
		JSONArray name = JSONArray.fromObject(map);

		 net.sf.json.JSONObject jobj = name.getJSONObject(0);
		 
		 jarr.add(jobj.getString("send"));

		 net.sf.json.JSONObject jobj2 = jarr.getJSONObject(0);
			 
		 ArrayList<String> list = new ArrayList<String>();
		 
		 for(int i=0; i < jobj2.size(); i++) {
			 list.add(jobj2.getString(""+i));
		 }	
		 		 
		 int a = siteservice.insertAlarm(vo);

		 String phoneNumber = "";
		 for(int i=0; i < list.size(); i++) {
			 phoneNumber += list.get(i);
			 if(i != list.size()-1)
				 phoneNumber += ",";
		 }
		 
		
		if (a == 0) {
			return "false";
		} else if (a == 1) {
			
		    String api_key = "NCSUHVPGXGVJOVA2";
		    String api_secret = "FCIGN0PTFU97R3KHOOD7HR6WUDRF6PPU";
		    Message coolsms = new Message(api_key, api_secret);
		    
		    
		    // 4 params(to, from, type, text) are mandatory. must be filled
		    HashMap<String, String> params = new HashMap<String, String>();
		    params.put("to", phoneNumber);
		    params.put("from", "01046037101");
		    if(vo.getAlarm_content().length() <= 45)
		    params.put("type", "SMS");
		    else
		    params.put("type", "LMS");	
		    params.put("text", vo.getAlarm_content());
		    params.put("app_version", "test app 1.2"); // application name and version

		    try {
		      JSONObject obj = (JSONObject) coolsms.send(params);
		      System.out.println(obj.toString());
		    } catch (CoolsmsException e) {
		      System.out.println(e.getMessage());
		      System.out.println(e.getCode());
		    }
			

			return "success";
		}

		return "false";
	}
	
	public String sms(String tel, String con) {
				
		String api_key = "NCSUHVPGXGVJOVA2";
	    String api_secret = "FCIGN0PTFU97R3KHOOD7HR6WUDRF6PPU";
	    Message coolsms = new Message(api_key, api_secret);
	    
	    // 4 params(to, from, type, text) are mandatory. must be filled
	    HashMap<String, String> params = new HashMap<String, String>();
	    params.put("to", tel);
	    params.put("from", "01046037101");
	    params.put("type", "SMS");	
	    params.put("text", con);
	    params.put("app_version", "test app 1.2"); // application name and version

	    try {
	      JSONObject obj = (JSONObject) coolsms.send(params);
	      System.out.println(obj.toString());
	      return "success";
	    } catch (CoolsmsException e) {
	      System.out.println(e.getMessage());
	      System.out.println(e.getCode());
	      return "false";
	    }
	}
	
	public String authSms(MemberVO vo) {
				
		String api_key = "NCSUHVPGXGVJOVA2";
	    String api_secret = "FCIGN0PTFU97R3KHOOD7HR6WUDRF6PPU";
	    Message coolsms = new Message(api_key, api_secret);
	    
	    AuthNumber ge = new AuthNumber();
	    ge.setCertNumLength(6);
	    String key = ge.excuteGenerate();
	    
	    // 4 params(to, from, type, text) are mandatory. must be filled
	    HashMap<String, String> params = new HashMap<String, String>();
	    params.put("to", vo.getPhone());
	    params.put("from", "01046037101");
	    params.put("type", "SMS");	
	    params.put("text", "안녕하세요 SensorCloud입니다. 인증번호는 [" + key + "] 입니다.");
	    params.put("app_version", "test app 1.2"); // application name and version

	    try {
	      JSONObject obj = (JSONObject) coolsms.send(params);
	      System.out.println(obj.toString());
	      return key;
	    } catch (CoolsmsException e) {
	      System.out.println(e.getMessage());
	      System.out.println(e.getCode());
	      return "false";
	    }
	}
	
}