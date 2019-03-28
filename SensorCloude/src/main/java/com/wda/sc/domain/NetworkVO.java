package com.wda.sc.domain;

import lombok.Data;

@Data
public class NetworkVO {
	private int sig_id;
	private String site_id;
	private String sig_protocol;
	private String host_ip;
	private String sig_port_num;
}
