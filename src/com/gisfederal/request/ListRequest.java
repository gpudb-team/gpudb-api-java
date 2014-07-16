package com.gisfederal.request;

import com.gisfederal.GPUdb;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import avro.java.gpudb.get_set_request;
import avro.java.gpudb.get_set_sorted_request;

public class ListRequest extends Request{

	public ListRequest(GPUdb gPUdb, SetId id, int start, int end, String semantic_type) {
		this.gPUdb = gPUdb;
		this.file = "/getset";
		this.log = Logger.getLogger(ListRequest.class);	

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
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}