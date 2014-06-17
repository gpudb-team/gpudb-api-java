package com.gisfederal.request;
import com.gisfederal.Gaia;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import org.apache.log4j.Logger;

import avro.java.gaia.get_set_sorted_request;
import avro.java.gaia.max_min_request;

public class MaxMinRequest extends Request {
	public MaxMinRequest(Gaia gaia, String file, SetId id, String attribute) {
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(MaxMinRequest.class);
		log.debug("gaia:"+gaia.toString()+" file:"+file+" id:"+id.get_id()+" attr:"+attribute);
		
		max_min_request request = new max_min_request(attribute, id.get_id(), this.gaia.getUserAuth());

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(max_min_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}
