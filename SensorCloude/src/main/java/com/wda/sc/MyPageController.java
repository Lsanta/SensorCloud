package com.wda.sc;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wda.sc.domain.MemberVO;
import com.wda.sc.service.MyPageService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

	private MyPageService mypageservice;

	@RequestMapping(value = "modifymypage", method = RequestMethod.GET)
	public String modifymypage(Locale locale, Model model, HttpSession session) {
		String id = (String) session.getAttribute("id");
		model.addAttribute("userInfo", mypageservice.getInfo(id));
		return "mypage/modifymypage";
	}

	@RequestMapping(value = "modifymyinfo", method = RequestMethod.GET)
	public String modifymyinfo(Locale locale, Model model, HttpSession session) {
		String id = (String) session.getAttribute("id");
		model.addAttribute("userInfo", mypageservice.getInfo(id));
		return "mypage/modifymyinfo";
	}

	@RequestMapping(value = "mypagemodifymyinfo", method = RequestMethod.POST)
	@ResponseBody
	public String mypagemodifymyinfo(Locale locale, Model model, MemberVO vo) {

		if (vo.getPassword().equals("") || vo.getName().equals("") || vo.getUser_id().equals("") || vo.getPhone().equals("")) {

			return "false";

		} else {
			mypageservice.updateuserinfo(vo);
			return "success";
		}
	}

	@RequestMapping("mypageconfirmpasswd.do")
	@ResponseBody
	public String MyPageConfirmPasswd(Model model, HttpSession session, @RequestParam("password") String password) {

		String confirmid = (String) session.getAttribute("id");

		ArrayList<MemberVO> arr = new ArrayList<MemberVO>();
		arr = mypageservice.confirmpasswd(confirmid);

		if (arr.size() != 0) {
			if (arr.get(0).getPassword().equals(password)) {
				return "success";
			}
		}
		return confirmid;

	}

}
