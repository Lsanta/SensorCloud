package com.wda.sc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.AlarmVO;
import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

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
	@RequestMapping(value = "/alarmadd.do")
	@ResponseBody
	public String alarmadd(AlarmVO vo, HttpSession session) {
		System.out.println("문자전송 컨트롤러");
		
		String content = vo.getAlarm_content();
		// 연락망 추가 폼을 이용한 추가
		String user = (String) session.getAttribute("id");
		vo.setSend_user(user);
		
		int a = siteservice.insertAlarm(vo);

		
		if (a == 0) {
			return "false";
		} else if (a == 1) {
			
		    String api_key = "NCSUHVPGXGVJOVA2";
		    String api_secret = "FCIGN0PTFU97R3KHOOD7HR6WUDRF6PPU";
		    Message coolsms = new Message(api_key, api_secret);
		    
		    
		    
		    // 4 params(to, from, type, text) are mandatory. must be filled
		    HashMap<String, String> params = new HashMap<String, String>();
		    params.put("to", "01051331449,01027901543");
		    params.put("from", "01046037101");
		    params.put("type", "SMS");
		    params.put("text", "테스트입니다.");
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
}