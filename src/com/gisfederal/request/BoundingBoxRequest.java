package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gaia.bounding_box_request;

import com.gisfederal.Gaia;
import com.gisfederal.SetId;
import com.gisfederal.AvroUtils;

public class BoundingBoxRequest extends Request {
	public BoundingBoxRequest(Gaia gaia, String file, SetId in_set, SetId rs_set, String x_attribute, String y_attribute, double min_x, double max_x, double min_y, double max_y){
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(BoundingBoxRequest.class);
		
		// construct avro object and then encode		
		bounding_box_request request = new bounding_box_request(min_x, max_x, min_y, max_y, x_attribute, y_attribute, in_set.get_id(), rs_set.get_id(), this.gaia.getUserAuth());

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(bounding_box_request request) {
		
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);
		
		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
		
	}
}
