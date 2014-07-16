package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gpudb.new_set_request;
import avro.java.gpudb.predicate_join_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class PredicateJoinRequest extends Request {
	public PredicateJoinRequest(GPUdb gPUdb, String file, SetId leftID, SetId rightID, String commonType, String resultType, SetId resultID, String predicate) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(PredicateJoinRequest.class);
		
		predicate_join_request request = new predicate_join_request(leftID.get_id(), rightID.get_id(), commonType, resultType, resultID.get_id(), gPUdb.getUserAuth(), predicate);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(predicate_join_request request) {
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
