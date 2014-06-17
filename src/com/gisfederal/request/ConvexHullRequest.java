/**
 * 
 */
package com.gisfederal.request;
import org.apache.log4j.Logger;

import avro.java.gaia.cluster_request;
import avro.java.gaia.convex_hull_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.Gaia;
import com.gisfederal.SetId;

/**
 * @author gisfed
 * The request for the convex hull calculation.  Create a new set of "points" that describes the smallest convex set which includes
 * all the points in the set. 
 */
public class ConvexHullRequest extends Request {

	public ConvexHullRequest(Gaia gaia, String file, SetId in_set, String x_attribute, String y_attribute) {
		this.gaia = gaia;
		this.file = file;		
		this.log = Logger.getLogger(ConvexHullRequest.class);
		
		this.log.debug("gaia:"+gaia.toString()+" in_set:"+in_set.get_id());
		convex_hull_request request = new convex_hull_request(x_attribute, y_attribute, in_set.get_id(), this.gaia.getUserAuth()); 
		this.log.debug("Build request object");

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(convex_hull_request request) {
		
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}
}
