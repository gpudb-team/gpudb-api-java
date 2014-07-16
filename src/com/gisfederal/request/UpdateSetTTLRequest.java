package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gpudb.sort_request;
import avro.java.gpudb.update_set_ttl_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class UpdateSetTTLRequest extends Request {

	public UpdateSetTTLRequest(GPUdb gPUdb, String file, SetId set_id, int ttl) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(UpdateSetTTLRequest.class);
					
		update_set_ttl_request request = new update_set_ttl_request(set_id.get_id(), ttl);	
		
		this.log.debug("Build request object");
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(update_set_ttl_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}

}
