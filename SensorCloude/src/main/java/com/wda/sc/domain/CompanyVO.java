package com.wda.sc.domain;

import lombok.Data;

@Data
public class CompanyVO {
	private int company_num;
	private String name;
	private String reg_number;
	private String company_tel;
	
	//협력사와 몇개의 현장을 같이 하고 있는지 보여주기 위한 컬럼
	private int num;
}
