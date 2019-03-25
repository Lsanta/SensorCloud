package com.wda.sc;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.memberVO;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String mypage(Locale locale, Model model) {
		return "mypage/mypage";
	}
	
	@RequestMapping(value = "/modifymypage", method = RequestMethod.GET)
	public String modifymypage(Locale locale, Model model) {
	
		return "mypage/modifymypage";
	}
	

	
}
