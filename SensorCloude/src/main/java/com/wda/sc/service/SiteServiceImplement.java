package com.wda.sc.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.siteVO;
import com.wda.sc.mapper.SiteMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SiteServiceImplement implements SiteService {
	
	private SiteMapper mapper;
	
	@Override
	public ArrayList<siteVO> getList() {
		return mapper.getList();
	}

	@Override
	public ArrayList<siteVO> getSite(String site_id) {
		//site_id를 통한 현장정보 가져오기
		return mapper.getSite(site_id);
	}
	

}
