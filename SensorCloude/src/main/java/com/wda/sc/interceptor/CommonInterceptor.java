package com.wda.sc.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class CommonInterceptor extends HandlerInterceptorAdapter {


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		HttpSession session = request.getSession();
		// 해당 클래스가 로그인 안했을 시 다른 경로로 접근하려할시 컨트롤러에 접근하기 전에 가로챈다
		if (session.getAttribute("id") == null ) {
			
			// 로그인이 안되어 있는 상태임으로 로그인 폼으로 다시 돌려보냄(redirect)
//			System.out.println("id=null");
			response.sendRedirect("/login");
			return false; // 더이상 컨트롤러 요청으로 가지 않도록 false로 반환함
		} else {
//			System.out.println("id!=null");
			return true;
		}// preHandle의 return은 컨트롤러 요청 uri로 가도 되냐 안되냐를 허가하는 의미임
		// 따라서 true로하면 컨트롤러 uri로 가게 됨.
	}

}


