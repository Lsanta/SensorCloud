package com.wda.sc.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.wda.sc.domain.CompanyVO;
import com.wda.sc.domain.Paging;
import com.wda.sc.domain.Search;
import com.wda.sc.domain.SiteVO;
import com.wda.sc.mapper.CompanyMapper;
import com.wda.sc.mapper.MyPageMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyServiceImplement implements CompanyService{
	private CompanyMapper mapper;
	@Override
	public int getPageNum() {
		// 페이지 개수 가져오기
		return mapper.getPageNum();
	}
	@Override
	public ArrayList<CompanyVO> getList(Paging page) {
		// TODO Auto-generated method stub
		System.out.println("회사 리스트 최종 " + page);
		return mapper.getList(page);
	}
	@Override
	public int insertCompany(CompanyVO vo) {
		// TODO Auto-generated method stub
		return mapper.insertCompany(vo);
	}
	@Override
	public int modCompany(CompanyVO vo) {
		// TODO Auto-generated method stub
		return mapper.modCompany(vo);
	}
	@Override
	public ArrayList<CompanyVO> companySearch(Search s) {
		// TODO Auto-generated method stub
		return mapper.companySearch(s);
	}
	@Override
	public ArrayList<CompanyVO> CompanySearchResult(Map<Object, Object> parm) {
		// TODO Auto-generated method stub
		return mapper.CompanySearchResult(parm);
	}
	@Override
	public ArrayList<CompanyVO> getAllCompany() {
		// TODO Auto-generated method stub
		return mapper.getAllCompany();
	}
	@Override
	public ArrayList<SiteVO> SiteCompany(int company_num) {
		//COMPANY_num으로 속한 현장이름 , site_id 가져오기
		System.out.println("회사번호" + company_num);
		return mapper.SiteCompany(company_num);
	}

}
