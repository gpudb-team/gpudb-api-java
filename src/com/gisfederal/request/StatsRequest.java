/**
 * 
 */
package com.gisfederal.request;
import com.gisfederal.GPUdb;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;
import com.gisfederal.NamedSet;

import org.apache.log4j.Logger;

import avro.java.gpudb.sort_request;
import avro.java.gpudb.stats_request;

/**
 * @author pjacobs
 *
 */
public class StatsRequest extends Request {

	public StatsRequest(GPUdb gPUdb, String file, String set_id) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(StatsRequest.class);
		
		stats_request request = new stats_request(set_id);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(stats_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}
}
