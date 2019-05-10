package com.wda.sc.domain;

import lombok.Data;

@Data
public class SensorDataVO {

	private int sensor_data_num;
	private int site_id;
	private String current_date;
	private String sensor_name;
	private double sensing_data;

}

