/**
 * 
 */
package com.gisfederal.request;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import avro.java.gpudb.bulk_delete_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.NamedSet;

public class BulkDeleteRequest extends Request {

	public BulkDeleteRequest(GPUdb gPUdb, String file, NamedSet ns, String global_expression, List<java.lang.CharSequence> expressions,
			Map<java.lang.CharSequence,java.lang.CharSequence> params) {		
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(BulkDeleteRequest.class);
		
		this.setId = ns.get_setid().toString();

		// build avro object
		bulk_delete_request request = new bulk_delete_request(ns.get_setid().get_id(), global_expression, expressions, 
				params, gPUdb.getUserAuth());

		byte[] dataToSend = AvroUtils.convert_to_bytes(request);
		this.requestData = new RequestData(dataToSend);
		
		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(bulk_delete_request request) {
		
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
	

}
