package com.wda.sc;



import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wda.sc.domain.AlarmMemberVO;
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
	
	
	// 보유센서 Insert
	@RequestMapping(value ="mysensoradd.do")
	@ResponseBody
		public String mysensoradd(MysensorVO vo) {
			//연락망 추가 폼을 이용한 추가
			
			int a = mysensorservice.insertmysensor(vo);
			
			if( a == 0) {
				return "false";
			} else if( a == 1){
				return "success";
			}
			
			return "false";
		}
	
}
