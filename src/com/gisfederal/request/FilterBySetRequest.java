package com.gisfederal.request;
import org.apache.log4j.Logger;

import avro.java.gpudb.filter_by_radius_request;
import avro.java.gpudb.filter_by_set_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

/**
 * @author sbardhan/eglaser
 * The request for the filter by set calculation.  Creates a new set with result data.
 */


//this.gpudb, file, in_set, result_set_id, isString, value, value_string, attribute

public class FilterBySetRequest extends Request {

	public FilterBySetRequest(GPUdb gPUdb, String file, SetId in_set, SetId result_set_id, String attribute, SetId sourceId, String src_attribute) {
		this.gPUdb = gPUdb;
		this.file = file;		
		this.log = Logger.getLogger(FilterBySetRequest.class);
		
		this.log.debug("gpudb:"+gPUdb.toString()+" in_set:"+in_set.get_id());
		filter_by_set_request request = new filter_by_set_request(in_set.get_id(), result_set_id.get_id(), attribute, sourceId.get_id(), src_attribute, this.gPUdb.getUserAuth()); 
		this.log.debug("Build request object");

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(filter_by_set_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}
