package com.wda.sc;

import java.io.IOException;
import java.io.PrintWriter;

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
}
