package com.gisfederal.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import avro.java.gpudb.update_set_metadata_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class UpdateSetMetadataRequest extends Request {
	public UpdateSetMetadataRequest(GPUdb gPUdb, String file, List<SetId> set_ids, Map<CharSequence, CharSequence> metadata_map) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(UpdateSetMetadataRequest.class);
		
		List<CharSequence> ids = new ArrayList<CharSequence>();
		for(SetId id : set_ids) {
			ids.add(id.toString());
		}
		update_set_metadata_request request = new update_set_metadata_request(ids, metadata_map); 
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(update_set_metadata_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setids=");
		msg.append(request.getSetIds().toString());
		msg.append("]");
		
		msg.append("metadata map=");
		msg.append(request.getMetadataMap());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}

}
