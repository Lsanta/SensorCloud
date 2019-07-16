package com.wda.sc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.AppTokenVO;
import com.wda.sc.domain.TokenVO;
import com.wda.sc.service.AndroidPushNotificationService;
import com.wda.sc.service.LoginService;
import com.wda.sc.service.MyPageService;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;



@Controller
@AllArgsConstructor
@RequestMapping("/app/send")
public class MessageController {
	private MyPageService mypageservice;
	private SiteService siteservice;
	private LoginService loginservice;
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
	    	 //serviceAccount = new FileInputStream("/home/ec2-user/sensorcloud-cb820-firebase-adminsdk-uiem3-c328071df6.json");
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
//	      String user_id = (String) session.getAttribute("id");
	      String user_id = "admin";
	      List<TokenVO> tokenlist = new ArrayList<TokenVO>();
	      
	      //token값 select 하기
	      tokenlist = mypageservice.getToken(user_id);
	      registrationToken = tokenlist.get(0).getToken_id();
	      
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
	      } catch (FirebaseMessagingException e) {
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
	         //serviceAccount = new FileInputStream("/home/ec2-user/sensorcloud-cb820-firebase-adminsdk-uiem3-c328071df6.json");
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
	      
	      String registrationToken= token;
	     
	      
	      AndroidConfig config = AndroidConfig.builder()
	    		  .setTtl(3600 * 1000) // 1 hour in milliseconds
	              .setPriority(AndroidConfig.Priority.HIGH)
	              .setRestrictedPackageName("kr.yju.wdb.sensor")
	              .setNotification(AndroidNotification.builder()
	                  .setTitle("Cordova Test")
	                  .setBody("아아아나나마마마")
	                  .setIcon("res/icon/android/hdpi.png")
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
	      
		  
	      try {
		         String response=FirebaseMessaging.getInstance().send(message);
		      } catch (FirebaseMessagingException e) {
		         System.out.println("Failed and the reason is behind");
		         e.printStackTrace();
		      }
		      return "ok";
	   }
	   
	   //앱->앱
	   @CrossOrigin(maxAge = 3600)
	   @SuppressWarnings({ "null", "unused" })
	   @ResponseBody
	   @RequestMapping(value="/Timelinemessage.do", method= RequestMethod.POST, produces = {"application/json"})
	   public String pushTest3(HttpSession session, @RequestBody Map<String, String> map) {
	      FirebaseApp defaultApp = null;
	      List<FirebaseApp> apps=FirebaseApp.getApps();
	      FileInputStream serviceAccount;
	      FirebaseOptions options=null;
	      //파이어베이스 옵션 설정
	      try {
	         serviceAccount = new FileInputStream("C:\\sensorcloud-cb820-firebase-adminsdk-uiem3-c328071df6.json");
	    	 //serviceAccount = new FileInputStream("/home/ec2-user/sensorcloud-cb820-firebase-adminsdk-uiem3-c328071df6.json");
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
	      
	      List<AppTokenVO> tokenlist = new ArrayList<AppTokenVO>();
		     
		  //token값 select 하기
		  tokenlist = mypageservice.allappToken();
		  
		  String user_id = (String)map.get("user_id");
		  String content = (String)map.get("content");
		  String name = loginservice.nameFind(user_id);
		  
			
//		  List<String> registrationTokens = new ArrayList<String>();
//		  
//		  for(int i = 0; i < tokenlist.size(); i++) {
//			  registrationTokens.add(tokenlist.get(i).getToken_id());
//		  }
//		
//		  System.out.println(registrationTokens);
		  

	      AndroidConfig config = AndroidConfig.builder()
	    		  .setTtl(3600 * 1000) // 1 hour in milliseconds
	              .setPriority(AndroidConfig.Priority.HIGH)
	              .setRestrictedPackageName("kr.yju.wdb.sensor")
	              .setNotification(AndroidNotification.builder()
	                  .setTitle(name +"님이 타임라인을 작성했습니다.")
	                  .setBody(content)
//	                  .setIcon("stock_ticker_update")
	                  .setIcon("res/icon/android/hdpi.png")
	                  .setColor("#f45342")
	                  .setClickAction("FCM_PLUGIN_ACTIVITY")
	               
	                  .build())
	              .build();
	      
	      for(int i = 0; i < tokenlist.size(); i++) {
			  
	      Message message = Message.builder()
	    		  .setAndroidConfig(config)
	    		  .putData("type", "Timeline") //넘어가는값
	    		  .setToken(tokenlist.get(i).getToken_id())
	    		  .build();
		  
	      try {
		         String response=FirebaseMessaging.getInstance().send(message);
		         System.out.println("Success : " + response);
		      } catch (FirebaseMessagingException e) {
		         System.out.println("Failed and the reason is behind");
		         e.printStackTrace();
		      }
	      } //for문 종료
	      
		      return "ok";
	   }
	   
	   //웹->앱
	   @CrossOrigin(maxAge = 3600)
	   @SuppressWarnings({ "null", "unused" })
	   @ResponseBody
	   @RequestMapping(value="/WebTimelinemessage.do", method= RequestMethod.POST,  consumes="application/json", produces = {"application/json"})
	   public String pushTest4(HttpSession session, @RequestBody String content) {
	      FirebaseApp defaultApp = null;
	      List<FirebaseApp> apps=FirebaseApp.getApps();
	      FileInputStream serviceAccount;
	      FirebaseOptions options=null;
	      //파이어베이스 옵션 설정
	      try {
	    	 //serviceAccount = new FileInputStream("/home/ec2-user/sensorcloud-cb820-firebase-adminsdk-uiem3-c328071df6.json");
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
	      
	      List<AppTokenVO> tokenlist = new ArrayList<AppTokenVO>();
		     
		  //token값 select 하기
		  tokenlist = mypageservice.allappToken();
		  
		  String user_id = (String) session.getAttribute("id");
		  String name = loginservice.nameFind(user_id);
		  
//		  List<String> registrationTokens = new ArrayList<String>();
//		  
//		  for(int i = 0; i < tokenlist.size(); i++) {
//			  registrationTokens.add(tokenlist.get(i).getToken_id());
//		  }
//		
//		  System.out.println(registrationTokens);
		 String rContent = null;
		 String reContent = null;
		  try {
			  rContent = URLDecoder.decode(content, "UTF-8");
			  String[] array = rContent.split("=");
			  reContent = array[1];
			  
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  
	      AndroidConfig config = AndroidConfig.builder()
	    		  .setTtl(3600 * 1000) // 1 hour in milliseconds
	              .setPriority(AndroidConfig.Priority.HIGH)
	              .setRestrictedPackageName("kr.yju.wdb.sensor")
	              .setNotification(AndroidNotification.builder()
	                  .setTitle(name +"님이 타임라인을 작성했습니다.")
	                  .setBody(reContent)
//	                  .setIcon("stock_ticker_update")
	                  .setIcon("res/icon/android/hdpi.png")
	                  .setColor("#f45342")
	                  .setClickAction("FCM_PLUGIN_ACTIVITY")
	               
	                  .build())
	              .build();
	      
	      for(int i = 0; i < tokenlist.size(); i++) {
			  
	      Message message = Message.builder()
	    		  .setAndroidConfig(config)
	    		  .putData("type", "Timeline") //넘어가는값
	    		  .setToken(tokenlist.get(i).getToken_id())
	    		  .build();
		  
	      try {
		         String response=FirebaseMessaging.getInstance().send(message);
		         System.out.println("Success : " + response);
		      } catch (FirebaseMessagingException e) {
		         System.out.println("Failed and the reason is behind");
		         e.printStackTrace();
		      }
	      } //for문 종료
	      
		      return "ok";
	   }
	   
	   
	   //웹->앱 현장에 임계값 들어옴
	   @SuppressWarnings({ "null", "unused" })
	   @ResponseBody
	   @RequestMapping(value="/WebLimit.do", method= RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"}, produces = {"application/json"})
	   @CrossOrigin(maxAge = 3600)
	   public String pushTest5(@RequestBody String content, HttpSession session) {
	      
		   String reContent = "";
	        try {
	           reContent = URLDecoder.decode(content, "UTF-8");
	      } catch (UnsupportedEncodingException e2) {
	         // TODO Auto-generated catch block
	         e2.printStackTrace();
	      }

	      //오차 계산 
	      String[] array = reContent.split(",");
	      String limit = array[0];
	      String data = array[1];
	      String site_id = array[2];
	      String sensor_sn = array[3];
	      
	      String site_name = siteservice.getSiteName(Integer.parseInt(site_id));
	      
	      String con = site_name + "에서 " + sensor_sn + "의  " +  limit + "이  초과되었습니다." + "Data :" + data;
	      
	        // 연락망 추가 폼을 이용한 추가
//	        System.out.println("11");
//	      	AlarmVO vo = new AlarmVO();
//			String user = (String) session.getAttribute("id");
//	        System.out.println("22");
//			vo.setAlarm_content(con);
//	        System.out.println("33");
//			vo.setSite_id(Integer.parseInt(site_id));
//	        System.out.println("44");
//			vo.setSend_user(user);
//	        System.out.println("55");
//			
//			int a = siteservice.insertAlarm(vo);
//	        System.out.println("66");
	      
		  FirebaseApp defaultApp = null;
	      List<FirebaseApp> apps=FirebaseApp.getApps(); 
	      FileInputStream serviceAccount;
	      FirebaseOptions options=null;
	      //파이어베이스 옵션 설정
	      try {
	    	 //serviceAccount = new FileInputStream("/home/ec2-user/sensorcloud-cb820-firebase-adminsdk-uiem3-c328071df6.json");
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
	      
	      List<String> tokenlist = new ArrayList<String>();
	      
	      // 1. 임계값이 벗어난 현장을 관리하는 관리자들을 가지고 옴.
	      List<AlarmMemberVO> member = new ArrayList<AlarmMemberVO>();
	      //site_id를 받아온다면 대입
	      member = siteservice.getLimitAlarm_member(site_id);
	      
	      // 2. 관리자들 중에서 APP_Token이 존재하면 앱 푸쉬, 존재하지 않으면 SMS알림
	      for(int i=0; i < member.size(); i++) {
	    	  //가져온 id값이 있다면 (회원 여부체크)
	    	  if( member.get(i).getUser_id() != null ) {
	    		  //id를 통해 토큰을 가져오고 토큰값이 없다면 sms 있으면 tokenlist에 저장
	    		  List<AppTokenVO> tokenL = mypageservice.getappToken(member.get(i).getUser_id());
	    		  //if문 한번더 
	    		  if(tokenL.size() == 0) {
	    			  //sms 푸쉬보내기
	    			  String tel = member.get(i).getTel();
	    			  SMSController sms = new SMSController(siteservice);
	    			  sms.sms(tel, con);
	    		  } else {
		    		  String token = tokenL.get(0).getToken_id();
		    		  tokenlist.add(token);
	    		  }
	    		  
	    	  } else {
	    		  String tel = member.get(i).getTel();
	    		  SMSController sms = new SMSController(siteservice);
	    		  sms.sms(tel, con);
	    	  }
	    	  
	      }
	      
		 String Content = null;
		 		  
	      AndroidConfig config = AndroidConfig.builder()
	    		  .setTtl(3600 * 1000) // 1 hour in milliseconds
	              .setPriority(AndroidConfig.Priority.HIGH)
	              .setRestrictedPackageName("kr.yju.wdb.sensor")
	              .setNotification(AndroidNotification.builder()
	                  .setTitle("센서 데이터 " + limit + " 초과")
	                  .setBody(site_name + "에서 " + sensor_sn + "의  " +  limit + "이  초과되었습니다. \n데이터 " + data)
//	                  .setIcon("stock_ticker_update")
	                  .setIcon("res/icon/android/hdpi.png")
	                  .setColor("#f45342")
	                  .setClickAction("FCM_PLUGIN_ACTIVITY")
	               
	                  .build())
	              .build();
	      
	      for(int i = 0; i < tokenlist.size(); i++) {
			  
	      Message message = Message.builder()
	    		  .setAndroidConfig(config)
	    		  .putData("type", "limit") //넘어가는값
	    		  .putData("site_id", site_id)
	    		  .putData("content", con)
	    		  .setToken(tokenlist.get(i))
	    		  .build();
		  
	      try {
		         String response=FirebaseMessaging.getInstance().send(message);
		         System.out.println("Success : " + response);
		      } catch (FirebaseMessagingException e) {
		         System.out.println("Failed and the reason is behind");
		         e.printStackTrace();
		      }
	      } //for문 종료
	      
		      return "ok";
	   }
	   

	   @SuppressWarnings({ "null", "unused" })
	   @ResponseBody
	   @RequestMapping(value="/WebTokenSave.do", method= RequestMethod.POST, produces = {"application/json"})
	   public String webtokenSave(@RequestBody String Token, HttpSession session) {
		 
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
		      } else {
		    	  String DBToken = tokenlist.get(0).getToken_id();
		    	  String CurToken = (String) webToken;
		    	  
		    	  if( DBToken.equals(CurToken) ) {

		    	  } else {
		    		  //새로운 토큰 저장
		    		  Map<String, String> map = new HashMap<String, String>();
		    		  map.put("token", CurToken);
		    		  map.put("user_id", user_id);
		    		  
		    		  mypageservice.updateToken(map);
		    	  }
		      }		   
		   return "";
	   }
	   
	   
	   @CrossOrigin(maxAge = 3600)
	   @SuppressWarnings({ "null", "unused" })
	   @ResponseBody
	   @RequestMapping(value="/AppTokenSave.do", method= RequestMethod.POST, produces = {"application/json"})
	   public String apptokenSave(@RequestBody  Map<String, String> map) {
		   String user_id = map.get("id");
		   String appToken = map.get("token");
		     
		   List<AppTokenVO> tokenlist = new ArrayList<AppTokenVO>();
		     
		   //token값 select 하기
		   tokenlist = mypageservice.getappToken(user_id);
		   AppTokenVO tokenvo = new AppTokenVO();
		   if(tokenlist.size() == 0) {
		    	//토큰 저장
		    	 tokenvo.setToken_id(appToken);
		    	 tokenvo.setUser_id(user_id);
		    	 mypageservice.saveappToken(tokenvo);
		      } else {
		    	  
		    	  String DBToken = tokenlist.get(0).getToken_id();
		    	  String CurToken = (String) appToken;
		    	  
		    	  if( DBToken.equals(CurToken) ) {
		    		 
		    	  } else {
		    		  //동일한 휴대폰으로 다른 아이디로 들어갔을때 토큰값이 같아 PK가 겹치는 DB에러 발생 
			    	  //update대신에 delete 와 insert로 수정해야함.
		    		  
//		    		  Map<String, String> map2 = new HashMap<String, String>();
//		    		  map2.put("token", appToken);
//		    		  map2.put("user_id", user_id);
//		    		  
//		    		  mypageservice.updateappToken(map2);
		    		  int result = mypageservice.deleteappToken(user_id);
		    		  
		    		  if( result == 1 ) {
		    			  //삭제가 성공했으면 insert 
		    			  tokenvo.setToken_id(appToken);
		 		    	  tokenvo.setUser_id(user_id);
		 		    	  mypageservice.saveappToken(tokenvo);
		 		    	
		    		  } else {
		    			  System.out.println("토큰 삭제 실패"); 
		    		  }
		    		  
		    		 
			    	   
		    	  }
		      }
		   
				   
		   return "";
	   }
	   
	   
	   
}