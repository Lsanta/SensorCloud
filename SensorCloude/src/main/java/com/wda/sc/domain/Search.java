package com.wda.sc.domain;

import lombok.Data;

@Data
public class Search {

	private int page;
	private String searchType;
	private String keyword;
	private String site_id;
	private int company_num;

}