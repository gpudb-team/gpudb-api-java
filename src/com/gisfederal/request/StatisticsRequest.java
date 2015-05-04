/**
 * 
 */
package com.gisfederal.request;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import avro.java.gpudb.statistics_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

/**
 * @author sbardhan/eglaser
 *
 */
public class StatisticsRequest extends Request {

	public StatisticsRequest(GPUdb gPUdb, String file, String stats, Map<String, String> params, 
			String attribute, SetId set_id) {
		this.gPUdb = gPUdb;
		this.file = file;		
		this.log = Logger.getLogger(StatisticsRequest.class);
		
		this.log.debug("gpudb:"+gPUdb.toString()+" set_id:"+set_id.get_id());
		
		Map<CharSequence, CharSequence> map = new HashMap<CharSequence, CharSequence>();
		for (String key : params.keySet()) {
			map.put(key, params.get(key));
		}
		
		statistics_request request = new statistics_request(stats, map, attribute, set_id.get_id(), gPUdb.getUserAuth());
		this.log.debug("Build request object");
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(statistics_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]");

		msg.append("[stat params=");
		msg.append(request.getParams());
		msg.append("]");

		msg.append("[stats=");
		msg.append(request.getStats());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}
