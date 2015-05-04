/**
 *
 */
package com.gisfederal.request;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import avro.java.gpudb.index_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

/**
 * @author erosenberger
 *
 */
public class IndexRequest extends Request {

	public IndexRequest(GPUdb gPUdb, String file, SetId set_id, String attribute, String action,
			Map<String, String> params) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(RangedStatisticsRequest.class);

		this.log.debug("gpudb:"+gPUdb.toString()+" set_id:"+set_id.get_id());

		Map<CharSequence, CharSequence> map = new HashMap<CharSequence, CharSequence>();
		for (String key : params.keySet()) {
			map.put(key, params.get(key));
		}

		index_request request = new index_request(set_id.get_id(), attribute, action, map);
		this.log.debug("Build request object");
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(index_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]");

		msg.append("[attribute=");
		msg.append(request.getAttribute());
		msg.append("]");

		msg.append("[action=");
		msg.append(request.getAction());
		msg.append("]");

		msg.append("[index params=");
		msg.append(request.getParams());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}
