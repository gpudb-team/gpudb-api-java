/**
 * 
 */
package com.gisfederal.request;
import org.apache.log4j.Logger;

import avro.java.gaia.filter_by_string_request;
import avro.java.gaia.filter_by_value_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.Gaia;
import com.gisfederal.SetId;

/**
 * @author sbardhan/eglaser
 * The request for the filter by value calculation.  Creates a new set with result data.
 */


//this.gaia, file, in_set, result_set_id, isString, value, value_string, attribute

public class FilterByValueRequest extends Request {

	public FilterByValueRequest(Gaia gaia, String file, SetId in_set, SetId result_set_id, boolean isString, Double value, 
			String value_string, String attribute) {
		this.gaia = gaia;
		this.file = file;		
		this.log = Logger.getLogger(FilterByValueRequest.class);
		
		this.log.debug("gaia:"+gaia.toString()+" in_set:"+in_set.get_id());
		filter_by_value_request request = new filter_by_value_request(in_set.get_id(), isString, value, value_string, attribute, result_set_id.get_id(), this.gaia.getUserAuth()); 
		this.log.debug("Build request object");

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(filter_by_value_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}
