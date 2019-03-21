package com.wda.sc.mapper;

import java.util.ArrayList;
import com.wda.sc.domain.memberVO;
public interface LoginMapper {

	public ArrayList<memberVO> login(String id);

}
