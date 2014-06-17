package com.gisfederal.request;
import com.gisfederal.Gaia;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gaia.add_object_request;
import avro.java.gaia.group_by_request;

public class GroupByRequest extends Request{

	public GroupByRequest(Gaia gaia, String file, SetId id, List<String> attributes) {
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(GroupByRequest.class);

		this.log.debug("gaia:"+gaia.toString()+" in_set:"+id.get_id());	
		group_by_request request = new group_by_request(id.get_id(), (List)attributes, this.gaia.getUserAuth());	

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