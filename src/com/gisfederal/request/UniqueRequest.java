/**
 * 
 */
package com.gisfederal.request;
import com.gisfederal.Gaia;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;
import com.gisfederal.NamedSet;

import org.apache.log4j.Logger;

import avro.java.gaia.sort_request;
import avro.java.gaia.status_request;
import avro.java.gaia.unique_request;

/**
 * @author gisfederal
 *
 */
public class UniqueRequest extends Request {

	public UniqueRequest(Gaia gaia, String file, SetId in_set, String attribute) {
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(UniqueRequest.class);
		
		// construct avro object and then convert to bytes
		unique_request request = new unique_request(in_set.get_id(), attribute, gaia.getUserAuth());
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(unique_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}

}
