/**
 * 
 */
package com.gisfederal.request;
import com.gisfederal.GPUdb;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gpudb.filter_by_bounds_request;
import avro.java.gpudb.filter_by_nai_request;

/**
 * @author pjacobs
 * The request for the NAI calculation.  Create a new set of "points" that are within the polygon.
 */
public class FilterByNAIRequest extends Request {

	public FilterByNAIRequest(GPUdb gPUdb, String file, SetId in_set, SetId result_set_id, String x_attribute, List<Double> x_vector, String y_attribute, List<Double> y_vector) {
		this.gPUdb = gPUdb;
		this.file = file;		
		this.log = Logger.getLogger(FilterByNAIRequest.class);
		
		this.log.debug("gpudb:"+gPUdb.toString()+" in_set:"+in_set.get_id());
		filter_by_nai_request request = new filter_by_nai_request(result_set_id.get_id(), in_set.get_id(), x_attribute, x_vector, y_attribute, y_vector, this.gPUdb.getUserAuth()); 
		this.log.debug("Build request object");

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(filter_by_nai_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}
