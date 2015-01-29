package com.gisfederal.request;

import java.util.HashMap;

import org.apache.log4j.Logger;

import avro.java.gpudb.get_sorted_set_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.NamedSet;

public class GetSortedSetRequest extends Request {
	public GetSortedSetRequest(GPUdb gPUdb, String file, NamedSet ns, String sort_attribute, long start, long end) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(GetSortedSetRequest.class);
		
		log.debug("get sorted set request");
		
		get_sorted_set_request request = new get_sorted_set_request(ns.get_setid().get_id(), sort_attribute, 
				start, end, "BINARY", new HashMap<CharSequence, CharSequence>(), this.gPUdb.getUserAuth());
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(get_sorted_set_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
}
