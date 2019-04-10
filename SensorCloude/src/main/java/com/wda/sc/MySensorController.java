package com.wda.sc;



import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.wda.sc.domain.MysensorVO;
import com.wda.sc.service.MysensorService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/mysensor")
public class MySensorController {
	
	private MysensorService mysensorservice;
	
	@RequestMapping(value = "mysensoradd", method = RequestMethod.GET)
	public String address(Locale locale, Model model) {
	
		return "mysensor/mysensoradd";
	}
	
	@RequestMapping(value = "mysensormod", method = RequestMethod.GET)
	public String sensormod(Locale locale, Model model) {
		System.out.println("수정 팝업");
		return "mysensor/mysensormod";
	}
	
	// 보유센서 Insert
	@RequestMapping(value ="mysensoradd.do")
	@ResponseBody
		public String mysensoradd(MysensorVO vo) {
			//보유센서 추가
			
			int a = mysensorservice.insertmysensor(vo);
			
			if( a == 0) {
				return "false";
			} else if( a == 1){
				return "success";
			}
			
			return "false";
		}
	
		// 보유센서 수정
		@RequestMapping(value ="mysensormod.do")
		@ResponseBody
			public String mysensormod(MysensorVO vo) {
				//보유센서 수정
				
				int a = mysensorservice.modmysensor(vo);
				
				if( a == 0) {
					return "false";
				} else if( a == 1){
					return "success";
				}
				
				return "false";
			}
	
}
