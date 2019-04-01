package com.wda.sc.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.MysensorVO;
import com.wda.sc.mapper.MysensorMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MysensorServiceImplement implements MysensorService {

	private MysensorMapper mapper;
	
	@Override
	public ArrayList<MysensorVO> getList(){
		return mapper.getList();
	}

	@Override
	public int insertmysensor(MysensorVO vo) {
		// TODO Auto-generated method stub
		return mapper.insertmysensor(vo);
	}

	@Override
	public int deletemysensor(MysensorVO vo) {

		return mapper.deletemysensor(vo);
	}
	
}
