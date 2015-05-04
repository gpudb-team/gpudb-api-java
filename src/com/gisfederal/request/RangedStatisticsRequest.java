/**
 * 
 */
package com.gisfederal.request;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import avro.java.gpudb.ranged_statistics_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

/**
 * @author sbardhan/eglaser
 *
 */
public class RangedStatisticsRequest extends Request {

	public RangedStatisticsRequest(GPUdb gPUdb, String file, SetId set_id, String attribute, String value_attribute,
			String stats, double interval, double start, double end, String select_expression, Map<String, String> params) {
		this.gPUdb = gPUdb;
		this.file = file;		
		this.log = Logger.getLogger(RangedStatisticsRequest.class);
		
		this.log.debug("gpudb:"+gPUdb.toString()+" set_id:"+set_id.get_id());
		
		Map<CharSequence, CharSequence> map = new HashMap<CharSequence, CharSequence>();
		for (String key : params.keySet()) {
			map.put(key, params.get(key));
		}
		
		ranged_statistics_request request = new ranged_statistics_request(set_id.get_id(), attribute, value_attribute, stats,
				start, end, interval, select_expression, map, this.gPUdb.getUserAuth());
		this.log.debug("Build request object");
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(ranged_statistics_request request) {
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
