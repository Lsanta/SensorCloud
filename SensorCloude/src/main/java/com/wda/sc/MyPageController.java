package com.wda.sc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wda.sc.domain.MemberFileVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.MyPageService;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

	private MyPageService mypageservice;
	private CheckboardService checkboardservice;
	private UploadController uploadController;
	private SiteService siteservice;
	
	
	@RequestMapping(value = "/levelup", method = RequestMethod.GET)
	public String address(Locale locale, Model model, HttpSession session) {
		String id = (String)session.getAttribute("id");
		model.addAttribute("userInfo",mypageservice.getInfo(id));
		return "mypage/levelup";
	}

	@RequestMapping(value = "modifymypage", method = RequestMethod.GET)
	public String modifymypage(Locale locale, Model model, HttpSession session) {
		String id = (String) session.getAttribute("id");
	
		model.addAttribute("userInfo", mypageservice.getInfo(id));
		return "mypage/modifymypage";
	}
	
	@RequestMapping(value = "/imgupload", method = RequestMethod.GET)
	public String address(Locale locale, Model model) {
		
		return "mypage/imgupload";
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
	@RequestMapping(value = "mypageimage.do", method = RequestMethod.POST)
	public String insertmypageimage(MemberVO member,MemberFileVO vo,HttpSession session, RedirectAttributes rttr) {
		String user_id = (String) session.getAttribute("id");
		String type = "true";
	
		ArrayList<MemberFileVO> arr = (ArrayList<MemberFileVO>) mypageservice.getAttachListmypage(user_id);
		String fileName = arr.get(0).getFile_name();
		
//		String fileName = ((MemberFileVO) mypageservice.getAttachListmypage(user_id)).getFile_name();

		String id = (String) session.getAttribute("id");
		member.setUser_id(id);
	
		if (member.getAttachList() != null) {
			//member.getAttachList().forEach(attach -> System.out.println(attach));
		}
		if(fileName != null) {
		uploadController.deleteFilemypage(fileName, type, user_id);
		}

		mypageservice.insert(member);

		 rttr.addFlashAttribute("result", member.getUser_id());

		return "redirect: /mypage/imagecomplete";
	}
	
	@RequestMapping(value = "/imagecomplete", method = RequestMethod.GET)
	public String imagecomplete(Locale locale, Model model) {
		
		return "mypage/imagecomplete";
	}

	@GetMapping(value = "/getAttachListmypage", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<MemberFileVO>> getAttachListmypage(String user_id) {

		return new ResponseEntity<>(mypageservice.getAttachListmypage(user_id), HttpStatus.OK);
	}
	
	   // 마이페이지에서 점검이력 content보기
	   @RequestMapping(value = "/checkboard/" + "{board_no}", method = RequestMethod.GET)
	   public String checkin(Locale locale, @PathVariable String board_no, Model model, HttpSession session ,HttpServletResponse response) throws IOException {
	      
	      int mlevel = (int) session.getAttribute("mlevel");

	      if (mlevel < 2) {

	         response.setContentType("text/html; charset=UTF-8");
	         PrintWriter out = response.getWriter();
	         out.println("<script langauge='javascript'>");
	         out.println("alert('권한이 없습니다. \\n 2등급(읽기권한)이상이 열람가능합니다'); history.go(-1);");
	         out.println("</script>");

	         
	      }

	      // 수정클릭시 권한체크 위해 id에 해당하는 m_level을 넘긴다.
	      String user_id = (String) session.getAttribute("id");
	      model.addAttribute("checkauthority", checkboardservice.checkauthority(user_id));

	      model.addAttribute("cklist", checkboardservice.viewgetList(board_no));
	      model.addAttribute("siteid", checkboardservice.getsiteid(board_no));
	      model.addAttribute("board_no", board_no);
  
	      model.addAttribute("depth0","메인화면");
		  model.addAttribute("depth1","내 점검이력");
		  
	      return "check/checkview";
	   }
	   
	   /// 마이페이지 -> 점검이력에서 해당글 클릭후 수정버튼을 누를시 글쓰기폼에 해당 데이터 전달
	   @RequestMapping(value = "/checkmod2" + "/{board_no}", method = RequestMethod.GET)
	   public String checkmod2(Locale locale, Model model, @PathVariable String board_no, HttpSession session,HttpServletResponse response) throws IOException {

	      int mlevel = (int) session.getAttribute("mlevel");

	      if (mlevel < 3) {

	         response.setContentType("text/html; charset=UTF-8");
	         PrintWriter out = response.getWriter();
	         out.println("<script langauge='javascript'>");
	         out.println("alert('권한이 없습니다. \\n 3등급(쓰기권한)이상이 열람가능합니다'); history.go(-1);");
	         out.println("</script>");

	         
	      }
	      
	      // board_no를 통해 점검이력 내용 가져오기
	      model.addAttribute("modlist", checkboardservice.viewgetList(board_no));

	      // ID를 통해 권한레벨 가져오기
	      String user_id = (String) session.getAttribute("id");
	      model.addAttribute("auth", checkboardservice.checkauthority(user_id));
	      // 사이트 이름,점검이력 제목,점검이력내용 정보 가져오기
	      model.addAttribute("checksitelist", siteservice.getchecksite());

	      // 현장아이디
	      model.addAttribute("siteid", checkboardservice.getsiteid(board_no));
	     
	      model.addAttribute("depth0","메인화면");
		  model.addAttribute("depth1","내 점검이력");
		  
	      return "check/checklistmodify";
	   }

}
