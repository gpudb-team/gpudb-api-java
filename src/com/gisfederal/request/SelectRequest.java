/**
 * 
 */
package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gpudb.select_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

/**
 * @author pjacobs
 *
 */
public class SelectRequest extends Request {

	
	public SelectRequest(GPUdb gPUdb, String file, SetId in_set, SetId out_set, String expression) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(SelectRequest.class);
		
		// construct avro object and then convert to bytes
		select_request request = new select_request(expression, out_set.get_id(), in_set.get_id(), this.gPUdb.getUserAuth());
		
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(select_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}


}
