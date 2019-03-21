package com.wda.sc.service;

import java.util.ArrayList;
import com.wda.sc.domain.memberVO;

public interface LoginService {
	
	public ArrayList<memberVO> login(String id);

}
