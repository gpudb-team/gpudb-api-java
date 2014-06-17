package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gaia.get_objects_request;
import avro.java.gaia.get_orphans_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.Gaia;

public class GetOrphansRequest extends Request {
	public GetOrphansRequest(Gaia gaia, String file, String setNamespace) {
		this.gaia = gaia;
		this.file = file;		
		this.log = Logger.getLogger(GetOrphansRequest.class);
		
		get_orphans_request request = new get_orphans_request(setNamespace);		 
		this.log.debug("Build request object");

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(get_orphans_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[namespace=");
		msg.append(request.getSetNamespace().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
}
