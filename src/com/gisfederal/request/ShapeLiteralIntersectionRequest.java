package com.gisfederal.request;

import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gaia.shape_literal_intersection_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.Gaia;

public class ShapeLiteralIntersectionRequest extends Request {
	public ShapeLiteralIntersectionRequest(Gaia gaia, String file, List<Double> x_vector_1, List<Double> y_vector_1, CharSequence geometry_type_1, CharSequence wkt_string_1, List<Double> x_vector_2, List<Double> y_vector_2, CharSequence geometry_type_2, CharSequence wkt_string_2) {
		
		throw new UnsupportedOperationException(" --- NOT IMPLEMENTED ----");
		
		/*
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(ShapeLiteralIntersectionRequest.class);

		this.log.debug("gaia:"+gaia.toString());	
		shape_literal_intersection_request request = new shape_literal_intersection_request(x_vector_1, y_vector_1, geometry_type_1, wkt_string_1, x_vector_2, y_vector_2, geometry_type_2, wkt_string_2);	

		this.log.debug("Build request object");
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		*/
	}
}
