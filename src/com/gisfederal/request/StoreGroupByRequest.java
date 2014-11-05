/**
 * 
 */
package com.gisfederal.request;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import avro.java.gpudb.store_group_by_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

/**
 * @author pjacobs
 * Store the result of the group by; i.e. create all the result sets to partition the set
 */
public class StoreGroupByRequest extends Request {
	
	public StoreGroupByRequest(GPUdb gPUdb, String file, SetId in_set, String attribute, Map<CharSequence, List<CharSequence>> group_map, boolean sort, String sort_attribute) {
		this.gPUdb = gPUdb;
		this.file = file;		
		this.log = Logger.getLogger(StoreGroupByRequest.class);
		
		this.log.debug("gpudb:"+gPUdb.toString()+" in_set:"+in_set.get_id());
		store_group_by_request request = new store_group_by_request(attribute, group_map, sort, sort_attribute, in_set.get_id(), this.gPUdb.getUserAuth());
		this.log.debug("Build request object");
		
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(store_group_by_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}

}
