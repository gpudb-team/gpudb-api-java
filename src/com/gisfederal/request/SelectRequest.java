/**
 * 
 */
package com.gisfederal.request;

import com.gisfederal.Gaia;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import org.apache.log4j.Logger;

import avro.java.gaia.register_trigger_nai_request;
import avro.java.gaia.select_request;
import avro.java.gaia.sort_request;

/**
 * @author pjacobs
 *
 */
public class SelectRequest extends Request {

	
	public SelectRequest(Gaia gaia, String file, SetId in_set, SetId out_set, String expression) {
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(SelectRequest.class);
		
		// construct avro object and then convert to bytes
		select_request request = new select_request(expression, out_set.get_id(), in_set.get_id(), this.gaia.getUserAuth());
		
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(select_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}


}
