package com.wda.sc.domain;

import lombok.Data;

@Data
public class InstallSensorVO {
	private int sensor_id;
	private String sensor_sn;
	private int site_id;
	private String sig_port;
	private String pow_port;
	private String rx_time;
	private String upper_limit;
	private String lower_limit;
}
