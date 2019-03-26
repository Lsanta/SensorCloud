package com.wda.sc.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.mysensorVO;
import com.wda.sc.mapper.MysensorMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MysensorServiceImplement implements MysensorService {

	private MysensorMapper mapper;
	
	@Override
	public ArrayList<mysensorVO> getList(){
		return mapper.getList();
	}
}
