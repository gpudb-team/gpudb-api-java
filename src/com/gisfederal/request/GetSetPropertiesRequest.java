package com.gisfederal.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gpudb.get_set_properties_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class GetSetPropertiesRequest extends Request {
	public GetSetPropertiesRequest(GPUdb gPUdb, String file, List<SetId> set_ids) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(GetSetPropertiesRequest.class);
		
		List<CharSequence> ids = new ArrayList<CharSequence>();
		for(SetId id : set_ids) {
			ids.add(id.toString());
		}
		get_set_properties_request request = new get_set_properties_request(ids); 
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(get_set_properties_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setids=");
		msg.append(request.getSetIds().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}

}
