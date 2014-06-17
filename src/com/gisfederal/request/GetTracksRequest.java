/**
 * 
 */
package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gaia.get_sorted_sets_request;
import avro.java.gaia.get_tracks_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.Gaia;
import com.gisfederal.SetId;

/**
 * @author sbardhan/eglaser
 *
 */
public class GetTracksRequest extends Request {

	public GetTracksRequest(Gaia gaia, String file, SetId original_id, SetId worldSetId, int start, int end,
			double min_x, double min_y, double max_x, double max_y, boolean doExtent) {
		this.gaia = gaia;
		this.file = file;		
		this.log = Logger.getLogger(GetTracksRequest.class);
		
		this.log.debug("gaia:"+gaia.toString()+" original_set:"+original_id.get_id()+" world_id:"+worldSetId);
		
		get_tracks_request request = new get_tracks_request(original_id.get_id(), worldSetId.get_id(), min_x, 
				max_x, min_y, max_y, "x", "y", doExtent, start, end, gaia.getUserAuth());
		this.log.debug("Build request object");
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(get_tracks_request request) {
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
