package com.wda.sc.service;

import java.util.ArrayList;

import com.wda.sc.domain.siteVO;

public interface SiteService {

	public ArrayList<siteVO> getList();
	public ArrayList<siteVO> getSite(String site_id);
}
