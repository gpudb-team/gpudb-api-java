package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gpudb.register_parent_set_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class RegisterParentSetRequest extends Request {

	public RegisterParentSetRequest(GPUdb gPUdb, String file, SetId set_id, boolean allowDuplicateChildren) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(RegisterParentSetRequest.class);
		log.debug("gPUdb:"+gPUdb.toString()+" file:"+file+" setid:"+set_id);
				
		register_parent_set_request request = new register_parent_set_request(set_id.get_id(), allowDuplicateChildren);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(register_parent_set_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		
		msg.append("]");
		
		msg.append("[duplicateChildren=");
		msg.append(request.getAllowDuplicateChildren());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}


}
