package com.gisfederal.request;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gpudb.update_object_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.GPUdbException;
import com.gisfederal.NamedSet;

public class UpdateObjectRequest extends Request{

	// Given the object to add and the set id of named set to add it too
	public UpdateObjectRequest(GPUdb gPUdb, String file, Object obj, String objectId, NamedSet ns) throws GPUdbException{
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(UpdateObjectRequest.class);
		
		ByteBuffer serialized = AddObjectRequest.encodeObject(gPUdb, file, obj, ns, log);

		// add to avro object
		List<CharSequence> setids = new ArrayList<CharSequence>();
		setids.add(ns.get_setid().get_id());
		update_object_request request = new update_object_request(setids, objectId, serialized, "", "BINARY", gPUdb.getUserAuth());	
		log.debug(request.getObjectData().toString());

		log.debug("Add object request created");	
		log.debug("Request byte[] length:"+AvroUtils.convert_to_bytes(request).length);

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(update_object_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setids=");
		msg.append(request.getSetIds().toString());
		msg.append("]");
		
		msg.append("[object id =");
		msg.append(request.getOBJECTID());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}


}