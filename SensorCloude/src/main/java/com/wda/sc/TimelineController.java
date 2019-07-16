package com.wda.sc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wda.sc.domain.InstallSensorVO;
import com.wda.sc.domain.TimelineVO;
import com.wda.sc.service.TimelineService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@SessionAttributes("id")
@RequestMapping("/timeline")
public class TimelineController {

	private TimelineService timelineservice;

	@RequestMapping(value = "timeline", method = RequestMethod.GET)
	public String timeline(Locale locale, Model model,HttpSession session, HttpServletResponse response) throws IOException {

		int mlevel = (int) session.getAttribute("mlevel");

		if (mlevel ==1) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n 2등급(읽기권한)이상이 열람가능합니다');history.go(-1);");
			out.println("</script>");

		}

		return "timeline/timeline";
	}

	@RequestMapping(value = "/timelinemodify", method = RequestMethod.GET, produces = "application/text; charset=utf8")
	public String timelinemodify(Locale locale, Model model,HttpSession session, HttpServletResponse response) throws IOException {

		int mlevel = (int) session.getAttribute("mlevel");

		if (mlevel ==1) {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script langauge='javascript'>");
			out.println("alert('권한이 없습니다.\\n 2등급(읽기권한)이상이 열람가능합니다');history.go(-1);");
			out.println("</script>");

		}
		return "timeline/timelinemodify";
	}

	@RequestMapping(value = "timeline.do")
	@ResponseBody
	public String timeline(TimelineVO vo, HttpSession session, Model model) {
		String id = (String) session.getAttribute("id");
		vo.setUser_id(id);

		int a = timelineservice.insert(vo);

		if (a == 0) {
			return "false";
		} else {
			return "success";
		}
	}

	@RequestMapping(value = "timelinedelete.do")
	@ResponseBody
	public String timelinedelete(TimelineVO vo) {
		String timeline_n = vo.getTimeline_n();

		int a = timelineservice.timelinedelete(timeline_n);

		if (a != 0) {

			return "success";

		} else {
			return "false";

		}
	}

	@RequestMapping(value = "timelinemodify.do")
	@ResponseBody
	public String timelinemodify(TimelineVO vo, HttpSession session, String timeline_n) {

		int a = timelineservice.timelinemodify(vo);

		if (a > 0) {
			return "success";
		} else {
			return "false";

		}

	}

}