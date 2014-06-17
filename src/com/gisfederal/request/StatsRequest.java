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
import avro.java.gaia.stats_request;

/**
 * @author pjacobs
 *
 */
public class StatsRequest extends Request {

	public StatsRequest(Gaia gaia, String file, String set_id) {
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(StatsRequest.class);
		
		stats_request request = new stats_request(set_id);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(stats_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}
}
