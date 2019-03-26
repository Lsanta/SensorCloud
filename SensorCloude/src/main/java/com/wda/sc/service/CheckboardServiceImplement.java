package com.wda.sc.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.checkboardVO;
import com.wda.sc.mapper.CheckboardMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CheckboardServiceImplement implements CheckboardService {

	private CheckboardMapper mapper;
	
	@Override
	public ArrayList<checkboardVO> getList(){
		return mapper.getList();
	}
}
