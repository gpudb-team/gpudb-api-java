/**
 * 
 */
package com.gisfederal.request;
import org.apache.log4j.Logger;

import avro.java.gaia.stats_request;
import avro.java.gaia.status_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.Gaia;

/**
 * @author gaiadevs
 *
 */
public class StatusRequest extends Request {

	public StatusRequest(Gaia gaia, String file, String set_id) {
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(StatusRequest.class);
		
		status_request request = new status_request(set_id);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(status_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}

}
