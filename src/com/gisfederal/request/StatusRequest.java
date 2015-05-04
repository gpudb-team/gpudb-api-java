/**
 * 
 */
package com.gisfederal.request;
import org.apache.log4j.Logger;

import avro.java.gpudb.status_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;

/**
 * @author gpudbdevs
 *
 */
public class StatusRequest extends Request {

	public StatusRequest(GPUdb gPUdb, String file, String set_id) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(StatusRequest.class);
		
		status_request request = new status_request(set_id);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(status_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}

}
