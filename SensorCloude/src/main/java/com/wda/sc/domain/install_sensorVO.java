package com.wda.sc.domain;

import lombok.Data;

@Data
public class install_sensorVO {
	private String sensor_id;
	private String sensor_sn;
	private String site_id;
	private String sig_port;
	private String pow_port;
	private String rx_time;
	private String upper_limit;
	private String lower_limit;
}
