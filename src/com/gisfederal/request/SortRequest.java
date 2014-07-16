/**
 * 
 */
package com.gisfederal.request;
import com.gisfederal.GPUdb;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;
import com.gisfederal.NamedSet;

import org.apache.log4j.Logger;

import avro.java.gpudb.select_request;
import avro.java.gpudb.sort_request;

/**
 * @author pjacobs
 *
 */
public class SortRequest extends Request {

	public SortRequest(GPUdb gPUdb, String file, SetId in_set, String attribute) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(SortRequest.class);
		
		// construct avro object and then convert to bytes
		sort_request request = new sort_request(attribute, in_set.get_id());
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(sort_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}


}
