package com.gisfederal.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gpudb.get_set_sorted_request;
import avro.java.gpudb.get_sorted_sets_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class GetSortedSetsRequest extends Request {
	public GetSortedSetsRequest(GPUdb gPUdb, String file, List<SetId> set_ids, String sort_attribute) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(GetSortedSetsRequest.class);
		
		log.debug("get sorted sets request");
		
		// construct avro object and then convert to bytes
		List<CharSequence> list = new ArrayList<CharSequence>();
		for(SetId set_id : set_ids) {
			list.add(set_id.get_id());
		}
		log.debug("get sorted sets build request object");
		get_sorted_sets_request request = new get_sorted_sets_request(list, sort_attribute, this.gPUdb.getUserAuth());
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(get_sorted_sets_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setids=");
		msg.append(request.getSetIds().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
}
