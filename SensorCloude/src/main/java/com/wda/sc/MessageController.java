package com.wda.sc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.wda.sc.service.AndroidPushNotificationService;
import lombok.AllArgsConstructor;



@Controller
@AllArgsConstructor
@RequestMapping("/send")
public class MessageController {
	//특정 푸시메시지 요청이 들어오면 수행되는 부분
	//메시지 데이터에 대해 설정을 하고 알림(notification) 구조를 JSON으로 만들어 post 요청에 전달
	//토큰값들은 DB에 저장을 하고 있는 값들을 불러오면 된다.
		
//	@Autowired
//	AndroidPushNotificationService androidPushNotificationservice;
//	
//	  @RequestMapping(value="/message.do", method= RequestMethod.POST, produces = {"application/json"})
//	  public @ResponseBody ResponseEntity<String> send(@RequestBody Map<String, Object> paramInfo, HttpSession session) throws JSONException {
//		Map<String, Object> retVal = new HashMap<String, Object>();
//		
//		System.out.println("token" + paramInfo.get("token"));
//		System.out.println("message" + paramInfo.get("message"));
//		
//		
//		//FCM 메시지 전송//
//	    JSONObject body = new JSONObject();
//	    
////	    //db에 저장된 여러개의 토큰(수신자)를 가져와서 설정할 수 있따.
////	    List<String> tokenlist = new ArrayList<String>();
////	    //DB과정 생략 (직접 대입) //
////	    tokenlist.add("token value 1");
////
////	    
////	    JSONArray array = new JSONArray();
////	    
////	    for(int i=0; i<tokenlist.size(); i++) {
////	    	array.put(tokenlist.get(i));
////	    }
//	    body.put("to", paramInfo.get("token")); // 여러개의 메시지일경우 registration_ids 단일 to
//	 
//	    JSONObject notification = new JSONObject();
//	    notification.put("title", "승급 요청 알림");
//	    notification.put("body", paramInfo.get("message"));
////	    notification.put("click_action", "https://localhost:8443/");
//	    notification.put("user_id",session.getAttribute("id"));
//	    notification.put("icon","/resources/img/logo.png");
//
//	    body.put("notification", notification);
//	    
//	    
//	    JSONObject data = new JSONObject();
//	    data.put("Room", "push alarm!!");
//	    body.put("data", data);
//	    
//	    JSONObject fcm_option = new JSONObject();
//	    fcm_option.put("link","https://localhost:8443/mypage/1");
//	    body.put("fcm_option", fcm_option);
//	    
//	    System.out.println(body.toString());
//	    
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//	   
//	    HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
//
//	    
//	    CompletableFuture<String> pushNotification = androidPushNotificationservice.send(request);
//	    System.out.println(body.toString());
//	    CompletableFuture.allOf(pushNotification).join();
//	   
//	    try {
//	      
//	      String firebaseResponse = pushNotification.get();
//	      
//	      return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
//	    } catch (InterruptedException e) {
//	      e.printStackTrace();
//	    } catch (ExecutionException e) {
//	      e.printStackTrace();
//	    }
//	 
//	    return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
//	  }
	
	
	
	   @SuppressWarnings({ "null", "unused" })
	   @ResponseBody
	   @RequestMapping(value="/message.do", method= RequestMethod.POST, produces = {"application/json"})
	   public String pushTest(@RequestBody Map<String, Object> paramInfo, HttpSession session) {
	      FirebaseApp defaultApp = null;
	      List<FirebaseApp> apps=FirebaseApp.getApps();
	      FileInputStream serviceAccount;
	      FirebaseOptions options=null;
	      //파이어베이스 옵션 설정
	      try {
	         serviceAccount = new FileInputStream("C:\\sensorcloud-cb820-firebase-adminsdk-uiem3-c328071df6.json");
	         options = new FirebaseOptions.Builder()
	               .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//	               .setDatabaseUrl("https://fir-test-f3fea.firebaseio.com/")
	               .build();
	      } catch (FileNotFoundException e1) {
	         e1.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	      //이미 관리자 defaultApp이 있는지 검사
	      if(apps!=null && !apps.isEmpty()) {
	         for(FirebaseApp app:apps) {
	            if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME))
	               defaultApp = app;
	         }
	      }else {
	         defaultApp = FirebaseApp.initializeApp(options);
	      }
	      

	      JSONObject link2 = new JSONObject();
	      link2.put("link","https://www.google.com/");
	      //푸쉬 보내기
	      String registrationToken= (String) paramInfo.get("token");
	      Message message = Message.builder()
	    		  .setWebpushConfig(WebpushConfig.builder() 
	    				  .setNotification(new WebpushNotification("떠라떠라",(String)paramInfo.get("message"), "/resources/img/logo.png"))
	    		          
	    				  .putData("link", "https://www.google.com/")
	    		          
	    		          .build())
	    		  .setToken(registrationToken)
	    		  .build();
	      
	      try {
	         String response=FirebaseMessaging.getInstance().send(message);
	         System.out.println("Success : " + response);
	      } catch (FirebaseMessagingException e) {
	         System.out.println("Failed and the reason is behind");
	         e.printStackTrace();
	      }
	      return "ok";
	   }

	   
}