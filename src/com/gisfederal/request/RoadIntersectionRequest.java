package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gpudb.road_intersection_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class RoadIntersectionRequest extends Request {
	public RoadIntersectionRequest(GPUdb gPUdb, String file, SetId in_set, String x_attribute, String y_attribute, java.util.List<java.lang.Double> road_x_vector, java.util.List<java.lang.Double> road_y_vector, String output_attribute){
		
		throw new UnsupportedOperationException(" --- NOT IMPLEMENTED ----");
		
		/*
		this.gpudb = gpudb;
		this.file = file;
		this.log = Logger.getLogger(RoadIntersectionRequest.class);
		
		// construct avro object and then encode		
		road_intersection_request request = new road_intersection_request(in_set.get_id(), x_attribute, y_attribute, road_x_vector, road_y_vector, output_attribute, gpudb.getUserAuth());
		
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		*/
	}

}
