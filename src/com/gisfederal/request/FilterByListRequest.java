/**
 * 
 */
package com.gisfederal.request;
import com.gisfederal.GPUdb;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gpudb.filter_by_list_request;
import avro.java.gpudb.filter_by_nai_request;

/**
 * @author pjacobs
 * Filter by list.  Given a map of attribute to values of that attribute.
 */
public class FilterByListRequest extends Request {

	public FilterByListRequest(GPUdb gPUdb, String file, SetId in_set, SetId rs, Map<CharSequence, List<CharSequence>> attribute_map) {
		this.gPUdb = gPUdb;
		this.file = file;		
		this.log = Logger.getLogger(FilterByListRequest.class);
		
		this.log.debug("gpudb:"+gPUdb.toString()+" in_set:"+in_set.get_id());
		filter_by_list_request request = new filter_by_list_request(attribute_map, rs.get_id(), in_set.get_id(), this.gPUdb.getUserAuth());
		this.log.debug("Build request object");

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(filter_by_list_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
}
