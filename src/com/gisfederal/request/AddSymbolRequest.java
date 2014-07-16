package com.gisfederal.request;

import java.nio.ByteBuffer;
import java.util.HashMap;

import org.apache.log4j.Logger;

import avro.java.gpudb.add_symbol_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.GPUdbException;

public class AddSymbolRequest extends Request{

	// Add a symbol code and its associated SVG element
	public AddSymbolRequest(GPUdb gpudb, String file, String symbol_id, String symbol_format, String svg_data) throws GPUdbException{
		this.gPUdb = gpudb;
		this.file = file;
		this.log = Logger.getLogger(AddSymbolRequest.class);
		
		ByteBuffer svg_bytes = ByteBuffer.wrap(svg_data.getBytes());

		// add to avro object
		add_symbol_request request = new add_symbol_request(symbol_id, symbol_format, svg_bytes, new HashMap());	
		log.debug(request.getSymbolId());

		log.debug("Add symbol request created");	
		log.debug("Request byte[] length:"+AvroUtils.convert_to_bytes(request).length);

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(add_symbol_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[symbolname=");
		msg.append(request.getSymbolId());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
}