/**
 * 
 */
package com.gisfederal.request;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import avro.java.gpudb.populate_full_tracks_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

/**
 * @author sbardhan/eglaser
 *
 */
public class PopulateFullTracksRequest extends Request {

	public PopulateFullTracksRequest(GPUdb gpudb, String file, SetId setId, SetId worldSetId, SetId resultId) {
		this.gPUdb = gpudb;
		this.file = file;		
		this.log = Logger.getLogger(PopulateFullTracksRequest.class);
		
		this.log.debug("gaia:"+gpudb.toString()+" source_set:"+ setId.get_id() + " world_id:" + worldSetId);
		
		populate_full_tracks_request request = new populate_full_tracks_request(setId.get_id(), worldSetId.get_id(), resultId.get_id(), 
				new ArrayList(), gpudb.getUserAuth());
		
		this.log.debug("Build request object");
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(populate_full_tracks_request request) {
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
