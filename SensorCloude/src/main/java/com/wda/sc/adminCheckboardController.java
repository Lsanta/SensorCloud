package com.wda.sc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/adminCheckPage")
public class adminCheckboardController {
	private CheckboardService Checkboardservice;
	private SiteService siteservice;
	
		@RequestMapping(value="/{num0}", method = RequestMethod.GET) 
		public String adminProxy(@PathVariable String num0){
			return "redirect:/adminCheckPage/1/1/1";
		}
	

		@RequestMapping(value = "/{num0}"+"/{num1}"+"/{num2}", method = RequestMethod.GET)
		public String admincheck(@PathVariable String num0, @PathVariable String num1, @PathVariable String num2, Model model, HttpSession session, HttpServletResponse response) throws IOException {
			
			int mlevel = (int) session.getAttribute("mlevel");

			if (mlevel != 6) {

				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script langauge='javascript'>");
				out.println("alert('권한이 없습니다. \\n 관리자만 열람가능합니다'); history.go(-1);");
				out.println("</script>");


			}
			String result = "";
			for(int i=0; i < 3 ; i++) {
				CheckboardManage cm = new CheckboardManage();
				if( i == 0) {
					result = cm.adminPaging(Checkboardservice, model, i , num0);
					if(result == "false") {
						int number = Integer.parseInt(num0)-1;
						return "redirect:/adminCheckPage/"+number+"/"+num1+"/"+num2;
					}
				} else if ( i == 1) {
					result = cm.adminPaging(Checkboardservice, model, i , num1);
					if(result == "false") {
						int number = Integer.parseInt(num1)-1;
						return "redirect:/adminCheckPage/"+num0+"/"+number+"/"+num2;
					}
				} else if ( i == 2 ) {
					result = cm.adminPaging(Checkboardservice, model, i , num2);
					if(result == "false") {
						int number = Integer.parseInt(num2)-1;
						return "redirect:/adminCheckPage/"+num0+"/"+num1+"/"+number;
					}
				}
			}
			
			return "adminCheck/adminCheck";
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
		      model.addAttribute("checkauthority", Checkboardservice.checkauthority(user_id));

		      model.addAttribute("cklist", Checkboardservice.viewgetList(board_no));
		      model.addAttribute("siteid", Checkboardservice.getsiteid(board_no));
		      model.addAttribute("board_no", board_no);
		      
		      model.addAttribute("depth0","메인화면");
			  model.addAttribute("depth1","점검이력관리");
			  
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
		      model.addAttribute("modlist", Checkboardservice.viewgetList(board_no));

		      // ID를 통해 권한레벨 가져오기
		      String user_id = (String) session.getAttribute("id");
		      model.addAttribute("auth", Checkboardservice.checkauthority(user_id));
		     
		      // 사이트 이름,점검이력 제목,점검이력내용 정보 가져오기
		      model.addAttribute("checksitelist", siteservice.getchecksite());

		      // 현장아이디
		      model.addAttribute("siteid", Checkboardservice.getsiteid(board_no));
		     
		      model.addAttribute("depth0","메인화면");
			  model.addAttribute("depth1","점검이력관리");
			  
		      return "check/checklistmodify";
		   }
}
