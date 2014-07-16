/**
 * 
 */
package com.gisfederal.request;

import com.gisfederal.GPUdb;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import org.apache.log4j.Logger;

import avro.java.gpudb.convex_hull_request;
import avro.java.gpudb.copy_set_request;

/**
 * @author pjacobs
 *
 */
public class CopySetRequest extends Request {

	public CopySetRequest(GPUdb gPUdb, String file, SetId original_id, SetId new_id, String type_id, String selector) {
		this.gPUdb = gPUdb;
		this.file = file;		
		this.log = Logger.getLogger(CopySetRequest.class);
		
		this.log.debug("gpudb:"+gPUdb.toString()+" original_set:"+original_id.get_id()+" new_id:"+new_id);
		copy_set_request request = new copy_set_request(new_id.get_id(),original_id.get_id(), selector, type_id, gPUdb.getUserAuth());
		this.log.debug("Build request object");
		//this.json_request = AvroUtils.convert_to_string(request);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(copy_set_request request) {
		
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[originalsetid=");
		msg.append(request.getOriginalSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}

}
