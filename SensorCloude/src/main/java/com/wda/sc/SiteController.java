package com.wda.sc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wda.sc.domain.AlarmMemberVO;
import com.wda.sc.domain.AlarmVO;
import com.wda.sc.domain.MemberVO;
import com.wda.sc.domain.MysensorVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.SiteVO;
import com.wda.sc.service.CheckboardService;
import com.wda.sc.service.SiteService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/site")
public class SiteController {

	private SiteService siteservice;
	private CheckboardService checkboardservice;
	@RequestMapping(value = "/address", method = RequestMethod.GET)
	public String address(Locale locale, Model model) {

		return "site/address";
	}
	
	//현장 추가
	@RequestMapping(value = "/siteadd", method = RequestMethod.GET)
	public String siteadd(Locale locale, Model model) {

		return "site/siteadd";
	}
	
	//현장 수정
	@RequestMapping(value = "/sitemodify" + "/{site_id}", method = RequestMethod.GET)
	public String sitemodify(@PathVariable String site_id, Model model) {
		System.out.println(site_id);
		model.addAttribute("joinSite",siteservice.joinSite(site_id));
		return "site/sitemodify";
	}


	@RequestMapping(value = "{site_id}", method = RequestMethod.GET)
	public String siteclick(@PathVariable String site_id, Model model) {
		System.out.println("현장 iD =" + site_id);
		model.addAttribute("siteInfo",siteservice.getSite(site_id));
		model.addAttribute("alarmMember",siteservice.getAlarm_member(site_id)); 
		model.addAttribute("siteStatus" , siteservice.getStatus(site_id));
		System.out.println(siteservice.getStatus(site_id));//현장클릭시 상태정보
		return "site/sitemain";

	}


	@RequestMapping(value = "{site_id}" + "/sitealarm", method = RequestMethod.GET)
	public String sitealarm(@PathVariable String site_id, Model model) {
		System.out.println("알람페이지");
		model.addAttribute("siteInfo",siteservice.getSite(site_id));
		model.addAttribute("alarmMember",siteservice.getAlarm_member(site_id));
		model.addAttribute("alarm",siteservice.getAlarm(site_id)); //알람 내용 정보

		return "site/sitealarm";
	}
	

	@RequestMapping(value = "{site_id}" + "/siterepair"+"/{num}", method = RequestMethod.GET)
	public String siterepair(@PathVariable String num ,@PathVariable Object site_id, Model model) {

		int pageNum=0;
		Paging page = new Paging();
		Map<String, Object> parm = new HashMap<String, Object>();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int realNum = Integer.parseInt(num);

		page.setTotalNum(siteservice.repairPageNum(site_id.toString()));

		if(page.getTotalNum() < page.getOnePageBoard() ) {
			pageNum = 1;
		}else {
			pageNum = page.getTotalNum()/page.getOnePageBoard();
		}

		for(int i = 0; i < pageNum; i ++) {
			arr.add(i+1);
		}

		page.setEndnum((realNum*10)+1);
		page.setStartnum(page.getEndnum()-10);

		parm.put("paging", page);
		parm.put("site_id", site_id);

		model.addAttribute("pageNum",arr);
		model.addAttribute("siteInfo",siteservice.getSite(site_id.toString()));  //현장정보
		model.addAttribute("checkboardlist",siteservice.repairList(parm));// 수리내역
		model.addAttribute("alarmMember",siteservice.getAlarm_member(site_id.toString()));  //연락망
		return "site/siterepair";
	}

	@RequestMapping(value = "{site_id}" + "/sensoradd", method = RequestMethod.GET)
	public String sensoradd(@PathVariable String site_id, Model model) {
		System.out.println("센서추가");
		model.addAttribute("siteInfo",siteservice.getSite(site_id));  //현장정보
		model.addAttribute("alarmMember",siteservice.getAlarm_member(site_id)); //연락망
		model.addAttribute("sensor_kind", siteservice.getSensorKind()); // 센서종류
		System.out.println("안됨");
		return "site/sensoradd";
	}

	@RequestMapping(value = "{site_id}" + "/download", method = RequestMethod.GET)
	public String download(@PathVariable String site_id, Model model) {
		System.out.println("스크립트다운로드");
		model.addAttribute("siteInfo",siteservice.getSite(site_id)); //현장정보
		model.addAttribute("alarmMember",siteservice.getAlarm_member(site_id)); //연락망

		return "site/download";
	}   

	//현장추가
	@RequestMapping(value ="siteadd.do", method = RequestMethod.POST)
	@ResponseBody
	public String insertSite(SiteVO site) { 
		switch (site.getType_no()) {
		case "building":
			site.setType_no("1");
			break;
		case "mountain":
			site.setType_no("0");
			break;
		}

		int a = siteservice.siteadd(site);
		int b = siteservice.networkadd(site); 
		if( a == 0 && b == 0)  {
			return "false";
		} else {
			return "success";
		}   
	} 
	
	//현장수정
	@RequestMapping(value ="sitemodify.do", method = RequestMethod.POST)
	@ResponseBody
	public String updateSite(SiteVO site) { 
		System.out.println(site);
		switch (site.getType_no()) {
		case "building":
			site.setType_no("1");
			break;
		case "mountain":
			site.setType_no("0");
			break;
		}

		int a = siteservice.updatesite(site);
		int b = siteservice.updatenetwork(site); 
		if( a == 0 && b == 0)  {
			return "false";
		} else {
			return "success";
		}   
	}  

	// 알림 Insert
	@RequestMapping(value ="alarmMemberadd.do")
	@ResponseBody
	public String alarmMemberadd(AlarmMemberVO vo) {
		//연락망 추가 폼을 이용한 추가

		int a = siteservice.insertAlarmMember(vo);

		if( a == 0) {
			return "false";
		} else if( a == 1){
			return "success";
		}

		return "false";
	}

	@RequestMapping(value ="alarmadd.do")
	@ResponseBody
	public String alarmadd(AlarmVO vo, HttpSession session) {
		//연락망 추가 폼을 이용한 추가
		String user = (String) session.getAttribute("id");
		vo.setSend_user(user);

		int a = siteservice.insertAlarm(vo);

		if( a == 0) {
			return "false";
		} else if( a == 1){
			return "success";
		}

		return "false";
	}
	
	@RequestMapping(value ="alarmmod.do")
	   @ResponseBody
	   public String alarmmod(AlarmMemberVO vo) {
		 //연락망 사람 수정      
		   
		  int a = siteservice.modAlarm(vo);
		      
		      if( a == 0) {
		         return "false";
		      } else if( a == 1){
		         return "success";
		      }
		      
		      return "false";
		    
	   }
	   
	   
	 @RequestMapping(value ="alarmdel.do")
	 @ResponseBody
	   public String alarmdel(AlarmMemberVO vo) {
		 //연락망 사람 삭제

	      boolean result = siteservice.delAlarm(vo);
	      
	      if(result) 
	    	  return "success";
	      else
	    	  return "false";
	   }
	
	@RequestMapping(value="/sitecheckview/" +"{board_no}", method = RequestMethod.GET)
	public String checkin(Locale locale,@PathVariable  String board_no, Model model) {
	
		System.out.println("현장점검이력보드넘버=" +board_no);
		
		model.addAttribute("sitecheckview",checkboardservice.viewgetList(board_no));
		
		System.out.println(checkboardservice.viewgetList(board_no));
		
		return "site/sitecheckview";
	}
	
	
	
}