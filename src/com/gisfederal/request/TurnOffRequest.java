package com.gisfederal.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gpudb.turn_off_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;

public class TurnOffRequest extends Request {
	public TurnOffRequest(GPUdb gPUdb, String file, String set_id, String group_attribute, String sort_attribute, String x_attribute, String y_attribute, double threshold) {
		
		throw new UnsupportedOperationException(" --- NOT IMPLEMENTED ----");

		
		/*
		this.gpudb = gpudb;
		this.file = file;
		this.log = Logger.getLogger(TurnOffRequest.class);
		
		List<CharSequence> set_ids = new ArrayList<CharSequence>();
		set_ids.addAll(Arrays.asList(set_id));
		turn_off_request request = new turn_off_request(set_ids, group_attribute, sort_attribute, x_attribute, y_attribute, threshold, gpudb.getUserAuth());		
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		*/
	}
}
