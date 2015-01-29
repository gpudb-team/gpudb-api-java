package com.gisfederal.request;

import java.util.Map;

import org.apache.log4j.Logger;

import avro.java.gpudb.register_type_transform_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;

public class RegisterTypeTransformRequest extends Request {
	public RegisterTypeTransformRequest(GPUdb gPUdb, String file, CharSequence type_id, CharSequence new_type_id, Map<CharSequence,CharSequence> transform_map) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(RegisterTypeTransformRequest.class);

		this.log.debug("gpudb:"+gPUdb.toString());	
		
		register_type_transform_request request = new register_type_transform_request(type_id, new_type_id, transform_map);	

		this.log.debug("Build request object");
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(register_type_transform_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[oldtypeid=");
		msg.append(request.getTypeId().toString());
		msg.append("]");
		
		msg.append("[newtypeid=");
		msg.append(request.getNewTypeId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
}
