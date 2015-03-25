package com.gisfederal.request;
import org.apache.log4j.Logger;

import avro.java.gpudb.max_min_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class MaxMinRequest extends Request {
	public MaxMinRequest(GPUdb gPUdb, String file, SetId id, String attribute) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(MaxMinRequest.class);
		log.debug("gpudb:"+gPUdb.toString()+" file:"+file+" id:"+id.get_id()+" attr:"+attribute);
		
		max_min_request request = new max_min_request(attribute, id.get_id(), this.gPUdb.getUserAuth());

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(max_min_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]");
		
		msg.append("[attribute=");
		msg.append(request.getAttribute());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}
