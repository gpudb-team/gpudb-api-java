package com.gisfederal.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gpudb.set_info_request;
import avro.java.gpudb.shape_intersection_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class ShapeIntersectionRequest extends Request {
	public ShapeIntersectionRequest(GPUdb gPUdb, String file, List<SetId> set_ids, CharSequence wkt_attr_name, List<Double> x_vector, List<Double> y_vector, CharSequence geometry_type, CharSequence wkt_string) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(ShapeIntersectionRequest.class);

		this.log.debug("gpudb:"+gPUdb.toString());	
		List<CharSequence> c_set_ids = new ArrayList<CharSequence>();
		for(SetId si : set_ids) {
			c_set_ids.add(si.get_id());
		}
		
		shape_intersection_request request = new shape_intersection_request(c_set_ids, wkt_attr_name, x_vector, y_vector, geometry_type, wkt_string, gPUdb.getUserAuth());	

		this.log.debug("Build request object");
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(shape_intersection_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setids=");
		msg.append(request.getSetIds().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}

}
