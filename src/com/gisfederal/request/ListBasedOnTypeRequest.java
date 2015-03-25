package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gpudb.get_set_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class ListBasedOnTypeRequest extends Request{

	public ListBasedOnTypeRequest(GPUdb gPUdb, SetId id, long start, long end, String semantic_type) {
		this.gPUdb = gPUdb;
		this.file = "/getset";
		this.log = Logger.getLogger(ListBasedOnTypeRequest.class);	

		// avro object
		get_set_request request = new get_set_request(start, end, id.get_id(), semantic_type, gPUdb.getUserAuth());
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(get_set_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]");
		
		msg.append("[start=");
		msg.append(request.getStart());
		msg.append("]");

		msg.append("[end=");
		msg.append(request.getEnd());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}