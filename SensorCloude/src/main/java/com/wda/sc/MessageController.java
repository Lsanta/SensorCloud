package com.wda.sc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.service.AndroidPushNotificationService;
import lombok.AllArgsConstructor;



@Controller
@AllArgsConstructor
@RequestMapping("/send")
public class MessageController {
	//특정 푸시메시지 요청이 들어오면 수행되는 부분
	//메시지 데이터에 대해 설정을 하고 알림(notification) 구조를 JSON으로 만들어 post 요청에 전달
	//토큰값들은 DB에 저장을 하고 있는 값들을 불러오면 된다.
		
	@Autowired
	AndroidPushNotificationService androidPushNotificationservice;
	
	@RequestMapping(value="/message.do", method= RequestMethod.POST, produces = {"application/json"})
	  public @ResponseBody ResponseEntity<String> send(@RequestBody Map<String, Object> paramInfo, HttpSession session) throws JSONException {
		Map<String, Object> retVal = new HashMap<String, Object>();
		
		System.out.println("오냐");
		System.out.println("token" + paramInfo.get("token"));
		System.out.println("message" + paramInfo.get("message"));
		
		
		//FCM 메시지 전송//
	    JSONObject body = new JSONObject();
	    
//	    //db에 저장된 여러개의 토큰(수신자)를 가져와서 설정할 수 있따.
//	    List<String> tokenlist = new ArrayList<String>();
//	    //DB과정 생략 (직접 대입) //
//	    tokenlist.add("token value 1");
//
//	    
//	    JSONArray array = new JSONArray();
//	    
//	    for(int i=0; i<tokenlist.size(); i++) {
//	    	array.put(tokenlist.get(i));
//	    }
	    body.put("to", paramInfo.get("token")); // 여러개의 메시지일경우 registration_ids 단일 to
	 
	    JSONObject notification = new JSONObject();
	    notification.put("title", "FCM test");
	    notification.put("body", paramInfo.get("message"));
	    notification.put("click_action", "https://localhost:8443/");
	    notification.put("user_id",session.getAttribute("id"));
	 
//	    JSONObject fcm_options = new JSONObject();
//	    JSONObject link = new JSONObject();
//	    
//	    link.put("link","https://dummypage.com");
//	    fcm_options.put("fcm_options", link);
//	    
//	    
//	    notification.put("webpush", fcm_options);

	    body.put("notification", notification);
	    System.out.println(body.toString());
	    
	    HttpEntity<String> request = new HttpEntity<>(body.toString());
	    
	    CompletableFuture<String> pushNotification = androidPushNotificationservice.send(request);
	    System.out.println(body.toString());
	    CompletableFuture.allOf(pushNotification).join();
	   
	    try {
	      System.out.println("3333333333333333333");
	      String firebaseResponse = pushNotification.get();
	      
	      return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    } catch (ExecutionException e) {
	      e.printStackTrace();
	    }
	 
	    return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
	  }
}