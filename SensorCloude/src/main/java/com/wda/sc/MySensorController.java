package com.wda.sc;



import java.util.ArrayList;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.wda.sc.domain.InstallSensorVO;
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
			//보유센서 추가
			
			int a = mysensorservice.insertmysensor(vo);
			
			if( a == 0) {
				return "false";
			} else if( a == 1){
				return "success";
			}
			
			return "false";
		}
	
	// 보유센서 delete
	@RequestMapping(value ="mysensordelete.do")
	@ResponseBody
		public String mysensordelete(MysensorVO vo) {
			String Sensor_sn = vo.getSensor_sn();
			
			ArrayList<InstallSensorVO> arr = new ArrayList<InstallSensorVO>();
			System.out.println(Sensor_sn);
			arr = mysensorservice.installSelect(Sensor_sn);
			
			if(arr.size() != 0) {
				
				boolean result = mysensorservice.deleteInstallSensor(Sensor_sn);
				boolean result2 = mysensorservice.deleteInfoSensor(Sensor_sn);
				boolean result3 = mysensorservice.deleteMySensor(Sensor_sn);
				
					if(result && result2 && result3) 
						return "success";
					else 
						return "false";
				
			} else {
				// delete 2개만 진행
				boolean result2 = mysensorservice.deleteInfoSensor(Sensor_sn);
				boolean result3 = mysensorservice.deleteMySensor(Sensor_sn);
			
				if(result2 && result3) 
					return "success";
				else 
					return "false";
			}
			

		}
}
