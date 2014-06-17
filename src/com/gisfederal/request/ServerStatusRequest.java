/**
 * 
 */
package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gaia.select_request;
import avro.java.gaia.server_status_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.Gaia;

/**
 * @author eglaser/sbardhan
 *
 */
public class ServerStatusRequest extends Request {

	/**
	 * 
	 */
	public ServerStatusRequest(Gaia gaia, String file, String option) {
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(ServerStatusRequest.class);
		
		server_status_request request = new server_status_request(option);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(server_status_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		setAuditMessage(msg.toString());
	}
}
