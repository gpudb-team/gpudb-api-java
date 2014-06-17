package com.gisfederal.request;
import com.gisfederal.Gaia;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import org.apache.log4j.Logger;

import avro.java.gaia.get_set_sorted_request;
import avro.java.gaia.join_request;
import avro.java.gaia.store_group_by_request;

public class JoinRequest extends Request{

	public JoinRequest(Gaia gaia, String file, SetId left_set, String left_attribute, SetId right_set, String right_attribute, String result_type, SetId result_set_id) {
		this.gaia = gaia;
		this.file = "/join";
		this.log = Logger.getLogger(JoinRequest.class);
		
		this.log.debug("gaia:"+gaia.toString()+" left_set:"+left_set.get_id());
		join_request request = new join_request(left_set.get_id(), left_attribute, right_set.get_id(), right_attribute, result_type, result_set_id.get_id(), this.gaia.getUserAuth());
		this.log.debug("Build request object");

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(join_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[leftsetid=");
		msg.append(request.getLeftSet().toString());
		msg.append("]");
		
		msg.append("[rightsetid=");
		msg.append(request.getRightSet().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
}