package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gaia.add_object_request;
import avro.java.gaia.get_sets_by_type_info_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.Gaia;

public class GetSetsByTypeInfoRequest extends Request {
	public GetSetsByTypeInfoRequest(Gaia gaia, String file, String typeID, String label, String semanticType) {
		this.gaia = gaia;
		this.file = file;		
		this.log = Logger.getLogger(GetSetsByTypeInfoRequest.class);
		
		this.log.debug("gaia:"+gaia.toString());
		get_sets_by_type_info_request request = new get_sets_by_type_info_request(typeID, label, semanticType);
		this.log.debug("Build request object");

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(get_sets_by_type_info_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[semantic_type=");
		msg.append(request.getSemanticType().toString());
		msg.append("]");
		
		msg.append("[params=[label=");
		msg.append(request.getLabel().toString());
		msg.append("]");
		
		msg.append("[type_id=");
		msg.append(request.getTypeId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
}
