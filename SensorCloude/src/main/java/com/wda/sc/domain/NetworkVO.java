package com.wda.sc.domain;

import lombok.Data;

@Data
public class NetworkVO {
	private int sig_id;
	private int site_id;
	private String sig_protocol;
	private String rperiod;
	private String sig_port_num;
}
