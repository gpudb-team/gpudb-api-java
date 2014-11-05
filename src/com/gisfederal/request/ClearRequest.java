/**
 *
 */
package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gpudb.clear_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.NamedSet;

public class ClearRequest extends Request {

	public ClearRequest(GPUdb gPUdb, String file, NamedSet ns, String pwd) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(ClearRequest.class);
		
		clear_request request = null;
		if( ns != null ) {
			this.mutable = ns.isMutable();
			this.setId = ns.get_setid().toString();
			request = new clear_request(ns.get_setid().get_id(), pwd);
		} else {
			this.mutable = false;
			this.setId = "";
			request = new clear_request("", "");
		}

		//this.json_request = AvroUtils.convert_to_string(request);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(clear_request request) {

		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
}
