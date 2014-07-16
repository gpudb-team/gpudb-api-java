package com.gisfederal.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gpudb.add_object_request;
import avro.java.gpudb.bounding_box_request;
import avro.java.gpudb.delete_object_request;

import com.gisfederal.GPUdb;
import com.gisfederal.SetId;
import com.gisfederal.AvroUtils;

public class DeleteObjectRequest extends Request {
	public DeleteObjectRequest(GPUdb gPUdb, String file, SetId in_set, String objId){
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(DeleteObjectRequest.class);
		
		// construct avro object and then encode
		List<CharSequence> setIds = new ArrayList<CharSequence>();
		setIds.add(in_set.get_id());
		delete_object_request request = new delete_object_request(setIds, objId, this.gPUdb.getUserAuth());

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(delete_object_request request) {
		
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setids=");
		msg.append(request.getSetIds().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}
