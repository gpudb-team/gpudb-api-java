package com.gisfederal.request;
import com.gisfederal.GPUdb;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gpudb.add_object_request;
import avro.java.gpudb.group_by_request;

public class GroupByRequest extends Request{

	public GroupByRequest(GPUdb gPUdb, String file, SetId id, List<String> attributes) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(GroupByRequest.class);

		this.log.debug("gpudb:"+gPUdb.toString()+" in_set:"+id.get_id());	
		group_by_request request = new group_by_request(id.get_id(), (List)attributes, this.gPUdb.getUserAuth());	

		this.log.debug("Build request object");
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(group_by_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}