package com.gisfederal.request;

import com.gisfederal.Gaia;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import org.apache.log4j.Logger;

import avro.java.gaia.add_object_request;
import avro.java.gaia.filter_by_bounds_request;

public class FilterByBoundsRequest extends Request {
	public FilterByBoundsRequest(Gaia gaia, String file, SetId in_set, SetId rs, String attribute, double lower_bounds, double upper_bounds){
		this.gaia = gaia;
		this.file = file;		
		this.log = Logger.getLogger(FilterByBoundsRequest.class);
		
		this.log.debug("gaia:"+gaia.toString()+" in_set:"+in_set.get_id());
		filter_by_bounds_request request = new filter_by_bounds_request(attribute, lower_bounds, rs.get_id(), in_set.get_id(), upper_bounds, this.gaia.getUserAuth());
		this.log.debug("Build request object");
		//
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(filter_by_bounds_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
}
