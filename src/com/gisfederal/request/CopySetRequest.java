/**
 * 
 */
package com.gisfederal.request;

import com.gisfederal.Gaia;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import org.apache.log4j.Logger;

import avro.java.gaia.convex_hull_request;
import avro.java.gaia.copy_set_request;

/**
 * @author pjacobs
 *
 */
public class CopySetRequest extends Request {

	public CopySetRequest(Gaia gaia, String file, SetId original_id, SetId new_id, String type_id, String selector) {
		this.gaia = gaia;
		this.file = file;		
		this.log = Logger.getLogger(CopySetRequest.class);
		
		this.log.debug("gaia:"+gaia.toString()+" original_set:"+original_id.get_id()+" new_id:"+new_id);
		copy_set_request request = new copy_set_request(new_id.get_id(),original_id.get_id(), selector, type_id, gaia.getUserAuth());
		this.log.debug("Build request object");
		//this.json_request = AvroUtils.convert_to_string(request);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(copy_set_request request) {
		
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[originalsetid=");
		msg.append(request.getOriginalSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}

}
