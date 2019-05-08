package com.wda.sc;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.CheckBoardVO;
import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.MyPageService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/app")
public class mMypageController {
	private MyPageService mypageservice;
	
	//마이페이지 메인
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value ="/mypage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	   @ResponseBody
	   public ArrayList<CheckBoardVO> mainlist(Locale locale, Model model) {
			System.out.println("확인");
	    
			ArrayList<CheckBoardVO> result = mypageservice.mcheck();

			return result;
	   }
}