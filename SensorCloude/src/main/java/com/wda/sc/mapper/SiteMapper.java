package com.wda.sc.mapper;

import java.util.ArrayList;

import com.wda.sc.domain.siteVO;

public interface SiteMapper {
	
	public ArrayList<siteVO> getList();
	public ArrayList<siteVO> getSite(String site_id);

}
