/**
 * 
 */
package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gpudb.get_sorted_sets_request;
import avro.java.gpudb.get_tracks2_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

/**
 * @author sbardhan/eglaser
 *
 */
public class GetTracks2Request extends Request {

	public GetTracks2Request(GPUdb gPUdb, String file, SetId original_id, SetId worldSetId, int start, int end,
			double min_x, double min_y, double max_x, double max_y, boolean doExtent) {
		this.gPUdb = gPUdb;
		this.file = file;		
		this.log = Logger.getLogger(GetTracks2Request.class);
		
		this.log.debug("gpudb:"+gPUdb.toString()+" original_set:"+original_id.get_id()+" world_id:"+worldSetId);
		
		get_tracks2_request request = new get_tracks2_request(original_id.get_id(), worldSetId.get_id(), min_x, 
				max_x, min_y, max_y, "x", "y", doExtent, start, end, gPUdb.getUserAuth());
		this.log.debug("Build request object");
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(get_tracks2_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]");

		msg.append("[params=[worldSetid=");
		msg.append(request.getWorldSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}
