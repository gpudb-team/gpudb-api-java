package com.gisfederal.request;

import java.util.List;

import com.gisfederal.GPUdb;

public class ShapeLiteralIntersectionRequest extends Request {
	public ShapeLiteralIntersectionRequest(GPUdb gPUdb, String file, List<Double> x_vector_1, List<Double> y_vector_1, CharSequence geometry_type_1, CharSequence wkt_string_1, List<Double> x_vector_2, List<Double> y_vector_2, CharSequence geometry_type_2, CharSequence wkt_string_2) {
		
		throw new UnsupportedOperationException(" --- NOT IMPLEMENTED ----");
		
		/*
		this.gpudb = gpudb;
		this.file = file;
		this.log = Logger.getLogger(ShapeLiteralIntersectionRequest.class);

		this.log.debug("gpudb:"+gpudb.toString());	
		shape_literal_intersection_request request = new shape_literal_intersection_request(x_vector_1, y_vector_1, geometry_type_1, wkt_string_1, x_vector_2, y_vector_2, geometry_type_2, wkt_string_2);	

		this.log.debug("Build request object");
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		*/
	}
}
