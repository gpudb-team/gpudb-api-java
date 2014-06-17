/**
 * 
 */
package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gaia.bounding_box_request;
import avro.java.gaia.clear_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.Gaia;

/**
 * @author pjacobs
 *
 */
public class ClearRequest extends Request {

	public ClearRequest(Gaia gaia, String file, String set_id, String pwd) {
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(ClearRequest.class);
		
		clear_request request = new clear_request(set_id, pwd);
		//this.json_request = AvroUtils.convert_to_string(request);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(clear_request request) {
		
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}
}
