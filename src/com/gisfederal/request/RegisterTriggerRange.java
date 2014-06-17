package com.gisfederal.request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import avro.java.gaia.new_set_request;
import avro.java.gaia.register_trigger_range_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.Gaia;
import com.gisfederal.SetId;

public class RegisterTriggerRange extends Request {

	public RegisterTriggerRange(Gaia gaia, String file, List<SetId> set_ids, String attribute, double lowest, double highest, String grouping_attribute) {
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(RegisterTriggerRange.class);
		log.debug("gaia:"+gaia.toString()+" file:"+file+" ids.size():"+set_ids.size()+" attr:"+attribute);
		
		// convert list of set ids
		List<CharSequence> list = new ArrayList<CharSequence>();
		for(SetId set_id : set_ids) {
			list.add(set_id.get_id());
		}
		
		register_trigger_range_request request = new register_trigger_range_request(UUID.randomUUID().toString(),list, attribute, lowest, highest, grouping_attribute);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(register_trigger_range_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setids=");
		msg.append(request.getSetIds().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}
}
