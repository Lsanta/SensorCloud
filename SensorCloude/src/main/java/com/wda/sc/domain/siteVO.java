package com.wda.sc.domain;

import java.util.Date;

import lombok.Data;

@Data
public class siteVO {
	private String site_id;
	private String type_no;
	private String site_name;
	private String address;
	private Date start_date;
	private int site_status;
}
