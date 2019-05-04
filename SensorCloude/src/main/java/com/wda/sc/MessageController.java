package com.wda.sc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.internal.NonNull;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Message.Builder;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.google.firebase.messaging.internal.MessagingServiceErrorResponse;
import com.wda.sc.domain.TokenVO;
import com.wda.sc.service.AndroidPushNotificationService;
import com.wda.sc.service.MyPageService;

import lombok.AllArgsConstructor;



@Controller
@AllArgsConstructor
@RequestMapping("/send")
public class MessageController {
	private MyPageService mypageservice;
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
//	    notification.put("click_action", "https://localhost:8443/");
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
	      

	      String registrationToken = "";
	      String user_id = (String) session.getAttribute("id");
	      List<TokenVO> tokenlist = new ArrayList<TokenVO>();
	      
	      //token값 select 하기
	      tokenlist = mypageservice.getToken(user_id);
	      TokenVO tokenvo = new TokenVO();
	      if(tokenlist.size() == 0) {
	    	  	//토큰 저장
	    	  	tokenvo.setToken_id((String) paramInfo.get("token"));
	    	  	tokenvo.setUser_id(user_id);
	    	  	mypageservice.saveToken(tokenvo);
	    	  	
	    	  	registrationToken = tokenvo.getToken_id();
	    	  	System.out.println("db에 값이 없을때" + registrationToken); 
	      } else {
	    	  
	    	  String DBToken = tokenlist.get(0).getToken_id();
	    	  String CurToken = (String) paramInfo.get("token");
	    	  
	    	  if( DBToken.equals(CurToken) ) {
	    		  registrationToken = DBToken;
	    		  System.out.println("db에 값이 있고 비교했더니 같은값일때" + registrationToken); 
	    	  } else {
	    		  //새로운 토큰 저장
	    		  Map<String, String> map = new HashMap<String, String>();
	    		  map.put("token", CurToken);
	    		  map.put("user_id", user_id);
	    		  
	    		  mypageservice.updateToken(map);
	    		  registrationToken = CurToken;
	    		  System.out.println("db에 값이 있고 비교했더니 다른값일때" + registrationToken); 
		    	   
	    	  }
	      }
	      
		
	      Message message = Message.builder()
	    		  .setWebpushConfig(WebpushConfig.builder()
	    				  .setNotification(new WebpushNotification("승급",(String)paramInfo.get("message"),"https://www.google.com"))
	    				  .build()
	    		  )
	    		  .setToken(registrationToken)
	    		  .build();
	    
	      //addAllTokens에 보낼 토큰 전부 넣으면 보내질듯
//	      MulticastMessage message2 = MulticastMessage.builder()
//	    		  .setWebpushConfig(WebpushConfig.builder()
//	    				  .setNotification(new WebpushNotification("title", "body"))
//	    				  .build()
//	    		)
//	    		.addAllTokens(tokens);
//	    		.build();
	    		  
	    		
	     
	      try {
	         String response=FirebaseMessaging.getInstance().send(message);
	         System.out.println("Success : " + response);
	      } catch (FirebaseMessagingException e) {
	         System.out.println("Failed and the reason is behind");
	         e.printStackTrace();
	      }
	      return "ok";
	   }

	   
	   @CrossOrigin(maxAge = 3600)
	   @SuppressWarnings({ "null", "unused" })
	   @ResponseBody
	   @RequestMapping(value="/Appmessage.do", method= RequestMethod.POST, produces = {"application/json"})
	   public String pushTest2(@RequestBody String token, HttpSession session) {
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
	      
	      System.out.println(token);
	      
	      String registrationToken= token;
	     
	      
	      AndroidConfig config = AndroidConfig.builder()
	    		  .setTtl(3600 * 1000) // 1 hour in milliseconds
	              .setPriority(AndroidConfig.Priority.HIGH)
	              .setRestrictedPackageName("kr.yju.wdb.sensor")
	              .setNotification(AndroidNotification.builder()
	                  .setTitle("Cordova Test")
	                  .setBody("아아아나나마마마")
	                  .setIcon("/src/main/webapp/resources/img/str.png")
	                  .setColor("#f45342")
	                  .setClickAction("FCM_PLUGIN_ACTIVITY")
	                  
	                  .build())
	              .build();
	              
	
	      Message message = Message.builder()
	    		  .setAndroidConfig(config)
	    		  .putData("data1", "20")
	    		  .putData("data2", "30")
	    		  .setToken("erbJH05Re_Q:APA91bFUFpJS4Iai98W_62rCuB2aKdVXBSJKPSsx5dWlewInUHzx8U_3Lb4Gw5cd4hd4jsI1g0oJUB0FVZ1Lqd8tQD2JhU6lv8f6Xn9ldjRHbs4w69y4y75MxpYzcrEvn78MCCfFQLMw")
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