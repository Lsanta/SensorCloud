package com.wda.sc.domain;

import lombok.Data;

@Data
public class InstallSensorVO {
	private int sensor_id;
	private String sensor_sn;
	private int site_id;
	private String program_var;
	private String upper_limit;
	private String lower_limit;
}
