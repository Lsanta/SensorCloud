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
import com.wda.sc.domain.AppTokenVO;
import com.wda.sc.domain.TokenVO;
import com.wda.sc.service.AndroidPushNotificationService;
import com.wda.sc.service.MyPageService;

import lombok.AllArgsConstructor;



@Controller
@AllArgsConstructor
@RequestMapping("/app/send")
public class MessageController {
	private MyPageService mypageservice;
	   @CrossOrigin(maxAge = 3600)
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
	      
	      System.out.println("메시지 전송 들어옴.");
	      String registrationToken = "";
//	      String user_id = (String) session.getAttribute("id");
	      String user_id = "admin";
	      List<TokenVO> tokenlist = new ArrayList<TokenVO>();
	      
	      //token값 select 하기
	      tokenlist = mypageservice.getToken(user_id);
	      registrationToken = tokenlist.get(0).getToken_id();
	      System.out.println("현재 토큰 :" + registrationToken);
	      
	       
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
	    		  .setToken(registrationToken)
	    		  .build();
	      
	      System.out.println("ㅁㅇㄻㄴㄹㅇ");
		  
	      try {
		         String response=FirebaseMessaging.getInstance().send(message);
		         System.out.println("Success : " + response);
		      } catch (FirebaseMessagingException e) {
		         System.out.println("Failed and the reason is behind");
		         e.printStackTrace();
		      }
		      return "ok";
	   }
	   
	   @SuppressWarnings({ "null", "unused" })
	   @ResponseBody
	   @RequestMapping(value="/WebTokenSave.do", method= RequestMethod.POST, produces = {"application/json"})
	   public String webtokenSave(@RequestBody String Token, HttpSession session) {
		 
		   System.out.println("웹토큰 저장 들어옴.");
		   String webToken = Token;
		   
		   String user_id = (String) session.getAttribute("id");
		   List<TokenVO> tokenlist = new ArrayList<TokenVO>();
		     
		   //token값 select 하기
		   tokenlist = mypageservice.getToken(user_id);
		      TokenVO tokenvo = new TokenVO();
		      if(tokenlist.size() == 0) {
		    	  	//토큰 저장
		    	 tokenvo.setToken_id(webToken);
		    	 tokenvo.setUser_id(user_id);
		    	 mypageservice.saveToken(tokenvo);
		    	 System.out.println("db에 값이 없을때" ); 
		      } else {
		    	  String DBToken = tokenlist.get(0).getToken_id();
		    	  String CurToken = (String) webToken;
		    	  
		    	  if( DBToken.equals(CurToken) ) {
		    		  System.out.println("db에 값이 있고 비교했더니 같은값일때"); 
		    	  } else {
		    		  //새로운 토큰 저장
		    		  Map<String, String> map = new HashMap<String, String>();
		    		  map.put("token", CurToken);
		    		  map.put("user_id", user_id);
		    		  
		    		  mypageservice.updateToken(map);
		    		  System.out.println("db에 값이 있고 비교했더니 다른값일때"); 
		    	  }
		      }		   
		   return "";
	   }
	   
	   
	   @CrossOrigin(maxAge = 3600)
	   @SuppressWarnings({ "null", "unused" })
	   @ResponseBody
	   @RequestMapping(value="/AppTokenSave.do", method= RequestMethod.POST, produces = {"application/json"})
	   public String apptokenSave(@RequestBody  Map<String, String> map) {
		   String id = map.get("id");
		   String appToken = map.get("token");
		   
		   System.out.println(id + appToken);
		   
		   List<AppTokenVO> tokenlist = new ArrayList<AppTokenVO>();
		     
		   //token값 select 하기
		   tokenlist = mypageservice.getappToken(id);
		   AppTokenVO tokenvo = new AppTokenVO();
		   if(tokenlist.size() == 0) {
		    	//토큰 저장
		    	 tokenvo.setToken_id(appToken);
		    	 tokenvo.setUser_id(id);
		    	 mypageservice.saveappToken(tokenvo);
		    	 System.out.println("db에 값이 없을때"); 
		      } else {
		    	  
		    	  String DBToken = tokenlist.get(0).getToken_id();
		    	  String CurToken = (String) appToken;
		    	  
		    	  if( DBToken.equals(CurToken) ) {
		    		  System.out.println("db에 값이 있고 비교했더니 같은값일때"); 
		    	  } else {
		    		  //새로운 토큰 저장
		    		  Map<String, String> map2 = new HashMap<String, String>();
		    		  map2.put("token", appToken);
		    		  map2.put("user_id", id);
		    		  
		    		  mypageservice.updateappToken(map2);
		    		  System.out.println("db에 값이 있고 비교했더니 다른값일때"); 
			    	   
		    	  }
		      }
		   
				   
		   return "";
	   }
	   
	   
	   
}