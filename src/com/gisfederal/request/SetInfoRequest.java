/**
 * 
 */
package com.gisfederal.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gaia.select_request;
import avro.java.gaia.set_info_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.Gaia;

/**
 * @author pjacobs
 *
 */
public class SetInfoRequest extends Request {

	/**
	 * 
	 */
	public SetInfoRequest(Gaia gaia, String file, String set_id) {
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(SetInfoRequest.class);
		
		List<CharSequence> set_ids = new ArrayList<CharSequence>();
		set_ids.add(set_id);
		set_info_request request = new set_info_request(set_ids);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(set_info_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setids=");
		msg.append(request.getSetIds().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}
}
