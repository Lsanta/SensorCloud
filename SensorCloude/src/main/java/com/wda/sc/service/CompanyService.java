package com.wda.sc.service;

import java.util.ArrayList;
import java.util.Map;

import com.wda.sc.domain.CompanyVO;
import com.wda.sc.domain.MysensorVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
import com.wda.sc.domain.SiteVO;

public interface CompanyService {
	public int getPageNum();

	public ArrayList<CompanyVO> getList(Paging page);

	public int insertCompany(CompanyVO vo);

	public int modCompany(CompanyVO vo);
	
	public ArrayList<CompanyVO> companySearch(Search s);
	
	public ArrayList<CompanyVO> CompanySearchResult(Map<Object, Object> parm);

	public ArrayList<CompanyVO> getAllCompany();

}
