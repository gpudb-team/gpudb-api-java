package com.gisfederal.request;
import com.gisfederal.Gaia;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;
import com.gisfederal.NamedSet;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.UUID;

import org.apache.avro.Schema;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import avro.java.gaia.get_set_sorted_request;
import avro.java.gaia.new_set_request;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class NewSetRequest extends Request {

	public NewSetRequest(Gaia gaia, String file, NamedSet ns, SetId parent) {
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(NewSetRequest.class);			
		
		// set the avro object
		this.log.debug("Set id:"+ns.get_setid().get_id()+" parent:"+parent.get_id());
		new_set_request request = new new_set_request(ns.get_setid().get_id(), parent.get_id(), ns.getType().getID());

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}
	
	public NewSetRequest(Gaia gaia, String file, NamedSet ns) {
		this.gaia = gaia;
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
		
		msg.append("[parentsetid=");
		msg.append(request.getParentSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}
