package com.gisfederal.request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import avro.java.gpudb.register_trigger_nai_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class RegisterTriggerNAI extends Request {

	public RegisterTriggerNAI(GPUdb gPUdb, String file, List<SetId> set_ids, String x_attribute, List<Double> x_vector, String y_attribute, List<Double> y_vector, String grouping_attribute) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(RegisterTriggerNAI.class);
		log.debug("gpudb:"+gPUdb.toString()+" file:"+file+" ids.size():"+set_ids.size());
		
		// convert list of set ids
		List<CharSequence> list = new ArrayList<CharSequence>();
		for(SetId set_id : set_ids) {
			list.add(set_id.get_id());
		}
		
		register_trigger_nai_request request = new register_trigger_nai_request(UUID.randomUUID().toString(),list, 
				x_attribute, x_vector, y_attribute, y_vector);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(register_trigger_nai_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setids=");
		msg.append(request.getSetIds().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}


}
