package com.wda.sc.mapper;

import java.util.ArrayList;
import java.util.Map;

import com.wda.sc.domain.CompanyVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
import com.wda.sc.domain.SiteVO;

public interface CompanyMapper {
	public int getPageNum();
	
	public ArrayList<CompanyVO> getList(Paging page);
	
	public int insertCompany(CompanyVO vo);

	public int modCompany(CompanyVO vo);
	
	public ArrayList<CompanyVO> companySearch(Search s);
	
	public ArrayList<CompanyVO> CompanySearchResult(Map<Object, Object> parm);
	
	public ArrayList<CompanyVO> getAllCompany();
	
	//COMPANY_num으로 속한 현장이름 , site_id 가져오기
	public ArrayList<SiteVO> SiteCompany(int company_num);

}
