package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gpudb.get_set_objects_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class ListRequest extends Request{

	public ListRequest(GPUdb gPUdb, SetId id, long start, long end) {
		this.gPUdb = gPUdb;
		this.file = "/getsetobjects";
		this.log = Logger.getLogger(ListRequest.class);	

		// avro object
		get_set_objects_request request = new get_set_objects_request(id.get_id(), start, end, "", gPUdb.getUserAuth());
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(get_set_objects_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}