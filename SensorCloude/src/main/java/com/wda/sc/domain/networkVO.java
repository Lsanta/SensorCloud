package com.wda.sc.domain;

import lombok.Data;

@Data
public class networkVO {
	private int sig_id;
	private String site_id;
	private String sig_protocol;
	private String host_ip;
	private String sig_port_num;
}
