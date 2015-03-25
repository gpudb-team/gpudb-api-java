package com.gisfederal.request;
import org.apache.log4j.Logger;

import avro.java.gpudb.new_set_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.NamedSet;
import com.gisfederal.SetId;

public class NewSetRequest extends Request {

	public NewSetRequest(GPUdb gPUdb, String file, NamedSet ns, SetId parent) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(NewSetRequest.class);			
		
		// set the avro object
		this.log.debug("Set id:"+ns.get_setid().get_id()+" parent:"+parent.get_id());
		new_set_request request = new new_set_request(ns.get_setid().get_id(), parent.get_id(), ns.getType().getID());

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}
	
	public NewSetRequest(GPUdb gPUdb, String file, NamedSet ns) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(NewSetRequest.class);			
		
		// set the avro object
		this.log.debug("Set id:"+ns.get_setid().get_id()+" no parent");
		// assume that the "parent" is just this set
		new_set_request request = new new_set_request(ns.get_setid().get_id(), ns.get_setid().get_id(), ns.getType().getID());

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(new_set_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]");

		msg.append("[typeid=");
		msg.append(request.getTypeId());
		msg.append("]");

		msg.append("[parentsetid=");
		msg.append(request.getParentSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}
