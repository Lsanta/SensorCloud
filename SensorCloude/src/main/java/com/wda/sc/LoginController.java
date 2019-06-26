package com.wda.sc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.wda.sc.domain.CompanyVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.LoginService;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@SessionAttributes("id")
@RequestMapping("/login")
public class LoginController {

	private LoginService loginservice;
	private SiteService siteservice;
	private CheckboardService checkboardservice;
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "login/login";
	}

	@RequestMapping(value = "", method = RequestMethod.POST) 
	@ResponseBody
	public String loginCheck(Model model,HttpSession session, @RequestParam String id, @RequestParam String password) {

		ArrayList<MemberVO> arr = new ArrayList<MemberVO>();
		arr = loginservice.login(id);
		System.out.println(arr);


		if(arr.size() != 0) {
			if(arr.get(0).getPassword().equals(password)) {
				model.addAttribute("id",arr.get(0).getUser_id());
				session.setAttribute("id", arr.get(0).getUser_id());
				session.setAttribute("name", arr.get(0).getName());
				//권한별 페이지 interceptor 실행을 위해 m_level을 session에 저장//로그인 쿼리에 m_level추가
				session.setAttribute("mlevel" , arr.get(0).getM_level());
				System.out.println(session.getAttribute("mlevel"));

				return "success";
			}else {
				return "fail";
			}
		}else {
			return "fail";
		}
	}

	@RequestMapping(value = "help", method = RequestMethod.GET)
	public String help(Model model) {
		return "login/help";
	}

	@RequestMapping(value = "sign", method = RequestMethod.GET)
	public String sign(Model model) {
		return "login/sign";
	}

	@RequestMapping(value ="/signup", method = RequestMethod.POST)
	public String signup(MemberVO m) {

		System.out.println(m);
		String comName = m.getCompany();
		if(comName.equals("STR")) {
			System.out.println("STR이면 그냥 바로 회원가입");
			m.setCompany_num(1);
			
			//4. 회사 번호를 같이 넣음 (회원가입할때)
			int checknum = loginservice.signup(m);
			if(checknum == 1) {
				return "login/login";
			}
			else if(checknum == 0) {
				return "login/sign";
			}
		} else {
			CompanyVO vo = new CompanyVO();
			//회원에 대한 정보 + 회사에 대한 정보
			//1. 회사에 대한 정보 부터 db에 넣음
			//2. 회사 이름을 기준 으로 잡아서 DB에 있으면 안넣고 없으면 넣음
			ArrayList<CompanyVO> arr = new ArrayList<CompanyVO>();
			System.out.println(1);
			arr = loginservice.findCompany(m.getCompany());
			System.out.println("2 " + arr);
			
			if(arr.size() == 0) {
				//들고 온 회사가 없으면 DB에 추가
				vo.setName(m.getCompany());
				vo.setReg_number(m.getRegnumber());
				System.out.println("회사" + vo);
				loginservice.insertCompany(vo);
			}
			
			//들고 온 회사가 있으면 (DB에 이미 존재)
			//3. SELECT 하는데 이름으로 찾아서 회사 번호를 가져옴
			System.out.println("3");
			
			int num = loginservice.getCompanyNum(m.getCompany());
			System.out.println("회사 번호 : " + num);
			
			m.setCompany_num(num);
			System.out.println("멤버에 넣은 것 " + m.getCompany_num());
			
			//4. 회사 번호를 같이 넣음 (회원가입할때)
			int checknum = loginservice.signup(m);

			if(checknum == 1) {
				return "login/login";
			}
			else if(checknum == 0) {
				return "login/sign";
			}
		}
		System.out.println("if문을 벗어남");
		return "login/sign";	
		
	}

	@RequestMapping(value ="idFind.do")
	@ResponseBody
	public String idCheck(Model model, HttpSession session, @RequestParam String name,@RequestParam String tel) {
		ArrayList<MemberVO> arr2 = new ArrayList<MemberVO>();	 
		arr2 = loginservice.idFind(name);

		if(arr2.size() == 0)
			return "none"; 
		if(arr2.size() != 0) {
			if(arr2.get(0).getPhone().equals(tel)) {
				//model.addAttribute("find_id",arr2.get(0).getUser_id());
				session.setAttribute("find_id", arr2.get(0).getUser_id());
				return arr2.get(0).getUser_id(); 
			} else {
				return "isN"; 
			}
		}

		return "none";
	}

	@RequestMapping(value = "sendMsg.do", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	@ResponseBody
	public String sendMsg(Model model, HttpSession session, @RequestParam String email, @RequestParam String user_id ) {
		
		ArrayList<MemberVO> arr = loginservice.login(user_id);
		
		if(arr.size() > 0) {
			if(!(arr.get(0).getEmail().equals(email))) {
				return "2"; //아이디와 이메일이 같지 않음
			}
		}else {
			return "3";
		}
		
		String key = "";
		Random rd = new Random();

		for (int i = 0; i < 6; i++) {
			key += rd.nextInt(10);
		}
		
		System.out.println(user_id+" , "+key);
		Map<String, String> map = new HashMap<String, String>();
		map.put("name",user_id);
		map.put("key",key);

		System.out.println(loginservice.keyUpdate(map));
		
		// Mail Server 설정
		String charSet = "utf-8";
		String hostSMTP = "smtp.naver.com";
		String hostSMTPid = "s2k0j798";
		String hostSMTPpwd = "wkd0930~~!";

		// 보내는 사람 EMail, 제목, 내용
		String fromEmail = "s2k0j798@naver.com";
		String fromName = "Spring Homepage";
		String subject = "";
		String msg = "";

		// 회원가입 메일 내용
		subject = "Spring Homepage 인증 메일입니다.";
		msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
		msg += "<h3 style='color: blue;'>";
		msg += user_id + "님 인증코드 입니다.</h3>";
		msg += "<div style='font-size: 130%'>";
		msg += "하단의 인증번호를 홈페이지에 입력해주세요.</div><br/>";
		msg += "인증번호 : "+ key;

		// 받는 사람 E-Mail 주소
		String mail = email;
		try {
			HtmlEmail e = new HtmlEmail();
			e.setDebug(true);
			e.setCharset(charSet);
			e.setSSL(true);
			e.setHostName(hostSMTP);
			e.setSmtpPort(465);

			e.setAuthentication(hostSMTPid, hostSMTPpwd);
			e.setTLS(true);
			e.addTo(mail, charSet);
			e.setFrom(fromEmail, fromName, charSet);
			e.setSubject(subject);
			e.setHtmlMsg(msg);
			e.send();
		} catch (Exception e) {
			return "0"; // 메일 발송 실패
		}

		return "1"; //메일 발송 성공
	}

	@RequestMapping(value = "pwFind.do", method = RequestMethod.POST)
	@ResponseBody
	public String pwFind(Model model, MemberVO member) {

		ArrayList<MemberVO> arr = loginservice.login(member.getUser_id());	 

		if(arr.size() > 0) {
			if(arr.get(0).getKey().equals(member.getKey())) 
				return arr.get(0).getPassword(); // 키값 맞음
			else
				return "0"; // 키값 다름
		}else {
			return "2"; // 아이디 없음
		}
	}

	@RequestMapping(value = "/signup_logincheck.do", method = RequestMethod.POST)
	@ResponseBody
	public String signidCheck(Model model, @RequestParam String id) {

		ArrayList<MemberVO> arr = new ArrayList<MemberVO>();	 
		arr = loginservice.login(id);

		if(arr.size() == 0) 
			return "ok"; 
		else
			return "no";

	}
	
	//인증번호 전송
	@RequestMapping(value = "/sendAuth", method = RequestMethod.POST)
	@ResponseBody
	public String sendAuth(Model model, @RequestParam Map<String,String> map) {
		
		System.out.println(map);
		MemberVO vo = new MemberVO();
		vo.setUser_id(map.get("id"));
		vo.setPhone(map.get("phone"));
		
		SMSController sms = new SMSController(siteservice);
		String authnumber = sms.authSms(vo);
		
		System.out.println("인증번호" + authnumber);
		
		return authnumber;
	}
	
}


