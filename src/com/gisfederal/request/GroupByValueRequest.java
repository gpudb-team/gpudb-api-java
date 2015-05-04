package com.gisfederal.request;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import avro.java.gpudb.group_by_value_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class GroupByValueRequest extends Request{

	public GroupByValueRequest(GPUdb gPUdb, String file, SetId id, List<String> attributes, String value_attribute,
			Map<CharSequence,CharSequence> params) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(GroupByValueRequest.class);

		this.log.debug("gpudb:"+gPUdb.toString()+" in_set:"+id.get_id());	
		group_by_value_request request = new group_by_value_request(id.get_id(), (List)attributes, value_attribute, 
				params, this.gPUdb.getUserAuth());	

		this.log.debug("Build request object");
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(group_by_value_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]");
		
		msg.append("[attributes=");
		msg.append(request.getAttributes());
		msg.append("]");
		
		msg.append("[value attribute=");
		msg.append(request.getValueAttribute());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}