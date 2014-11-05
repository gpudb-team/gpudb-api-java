/**
 * 
 */
package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gpudb.select_delete_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.NamedSet;

public class SelectDeleteRequest extends Request {

	
	public SelectDeleteRequest(GPUdb gpudb, String file, NamedSet ns, String expression) {
		this.gPUdb = gpudb;
		this.file = file;
		this.log = Logger.getLogger(SelectDeleteRequest.class);
		
		this.mutable = ns.isMutable();
		this.setId = ns.get_setid().toString();
		
		// construct avro object and then convert to bytes
		select_delete_request request = new select_delete_request(ns.get_setid().get_id(), expression, this.gPUdb.getUserAuth());
		
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(select_delete_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}


}
