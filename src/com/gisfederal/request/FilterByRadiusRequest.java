/**
 * 
 */
package com.gisfederal.request;
import com.gisfederal.Gaia;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gaia.filter_by_list_request;
import avro.java.gaia.filter_by_radius_request;

/**
 * @author pjacobs
 * The request for the NAI calculation.  Create a new set of "points" that are within the polygon.
 */
public class FilterByRadiusRequest extends Request {

	public FilterByRadiusRequest(Gaia gaia, String file, SetId in_set, SetId result_set_id, String x_attribute, String y_attribute, Double x_center, Double y_center, Double radius) {
		this.gaia = gaia;
		this.file = file;		
		this.log = Logger.getLogger(FilterByRadiusRequest.class);
		
		this.log.debug("gaia:"+gaia.toString()+" in_set:"+in_set.get_id());
		filter_by_radius_request request = new filter_by_radius_request(result_set_id.get_id(), in_set.get_id(), x_attribute, y_attribute, x_center, y_center, radius, this.gaia.getUserAuth()); 
		this.log.debug("Build request object");

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(filter_by_radius_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}
