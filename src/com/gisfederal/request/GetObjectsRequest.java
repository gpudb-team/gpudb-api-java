package com.gisfederal.request;

import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gpudb.generate_video_request;
import avro.java.gpudb.get_objects_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class GetObjectsRequest extends Request {
	
	public GetObjectsRequest(GPUdb gPUdb, SetId id, String attribute, List<Double> attr_values, List<CharSequence> attr_str_values) {
		this.gPUdb = gPUdb;
		this.file = "/getobjects";
		this.log = Logger.getLogger(GetObjectsRequest.class);	

		// avro object		
		log.debug("build the get set sorted request");
		get_objects_request request = new get_objects_request(id.get_id(), attribute, attr_values, attr_str_values, gPUdb.getUserAuth());
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		this.log.debug("built request data "+this.requestData.toString());
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(get_objects_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}
