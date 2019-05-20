package com.wda.sc.domain;

import lombok.Data;

@Data
public class SiteVO {
	//site table
	private int site_id;
	private String type_no;
	private String site_name;
	private String address;
	private String start_date;
	private int site_status;
	private double x;
	private double y;
	
	//network table
	private int sig_id;
	private String sig_protocol;
	private String host_ip;
	private String sig_port_num;
	private String port_num;
}
