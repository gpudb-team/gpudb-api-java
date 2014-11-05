package com.gisfederal.request;

import java.util.Map;

import org.apache.log4j.Logger;

import avro.java.gpudb.select_update_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.GPUdbException;
import com.gisfederal.NamedSet;

public class SelectUpdateRequest extends Request{

	public SelectUpdateRequest(GPUdb gpudb, String file, NamedSet ns, String expression, Map<CharSequence, CharSequence> data) throws GPUdbException{
		this.gPUdb = gpudb;
		this.file = file;
		this.log = Logger.getLogger(AddObjectRequest.class);
		
		this.mutable = ns.isMutable();
		this.setId = ns.get_setid().toString();

		
		select_update_request request = new select_update_request(ns.get_setid().get_id(), expression, data, gPUdb.getUserAuth());	

		log.debug("Select update request created");	

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(select_update_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setids=");
		msg.append(request.getSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}
}