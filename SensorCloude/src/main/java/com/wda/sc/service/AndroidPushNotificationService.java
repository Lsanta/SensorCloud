package com.wda.sc.service;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AndroidPushNotificationService {
	// 네트워크 전송 데이터(헤더, 바디)를 만들어 POST로 구글 FCM서버에 데이터를 전송하는 역할
	// 헤더에 필요한 부분인 Authorization, Content-Type을 정의
	// 전송 데이터 타입은 application/json 으로 JSON 타입으로 전송
	// body 부분에 데이터는 HttpEntity<>타입으로 받아서 전송 포맷에 넣음
	// 전송하는 메소드는 Async로 비동기 수행이 된다. -> @Async 어노테이션 
	
	private static final String FIREBASE_SERVER_KEY = "AAAAfqsoojY:APA91bGHEcWGGrmiLubmDYF_BcY-S1IXL8XMnMPY0yp45tFsytw8pLx_paPac9irBwukU0M5XcpwkqXZiGxaF_1OUYYB3x1uLE8XzGbtqS2nFkx0AFPYlvyDIkBJ7Dk-MXNP7JBcaqLm";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
	
	@Async
	public CompletableFuture<String> send(HttpEntity<String> entity){
		
		RestTemplate restTemplate = new RestTemplate();
		/**
		 https://fcm.googleopis.com/fcm/send
		 Content-Type : application/json
		 Authorization : key=FIREBASE_SERVER_KEY
		 */
		
		
		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);
		
		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
		return CompletableFuture.completedFuture(firebaseResponse);
	}
}