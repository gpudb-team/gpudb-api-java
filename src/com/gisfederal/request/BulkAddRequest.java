/**
 * 
 */
package com.gisfederal.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.xerial.snappy.Snappy;

import avro.java.gpudb.bulk_add_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.GPUdbException;
import com.gisfederal.NamedSet;

public class BulkAddRequest extends Request {

	public BulkAddRequest(GPUdb gPUdb, String file, List<Object> list_obj, NamedSet ns, Map<java.lang.CharSequence,java.lang.CharSequence> params) {		
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(BulkAddRequest.class);
		
		this.setId = ns.get_setid().toString();

		List<ByteBuffer> binaryList = new ArrayList<ByteBuffer>();

		for(Object obj : list_obj) {			
			// Use the static call from AddObjectRequest
			ByteBuffer serialized = AddObjectRequest.encodeObject(gPUdb, file, obj, ns, log);
			// add to list
			binaryList.add(serialized);
		}			
		
		// list of string encodings
		List<java.lang.CharSequence> list_str = new ArrayList<java.lang.CharSequence>();
		for(int i=0; i<binaryList.size(); i++) {
			list_str.add("");
		}

		// build avro object
		bulk_add_request request = new bulk_add_request(ns.get_setid().get_id(), binaryList, list_str, "BINARY", params);

		byte[] dataToSend = null;
		byte[] bulk_data_bytes = AvroUtils.convert_to_bytes(request);
		if( gPUdb.isSnappyCompress() ) {
			try {
				dataToSend = Snappy.compress(bulk_data_bytes);
				log.debug(" Compressed from " + bulk_data_bytes.length + " to :" + dataToSend.length);
			} catch (IOException e) {
				log.error(e);
				throw new GPUdbException(e.getMessage());
			}
		} else {
			dataToSend = bulk_data_bytes;
		}
		this.requestData = new RequestData(dataToSend);

		// Create log msg for audit
		createAuditMsg(request);
	}
	
	@Override
	protected void setContentType(HttpURLConnection connection) {
		if( gPUdb.isSnappyCompress() ) {
			connection.setRequestProperty("Content-type",
					"application/x-snappy");
		} else {
			connection.setRequestProperty("Content-type",
					"application/octet-stream");
		}
	}
	
	private void createAuditMsg(bulk_add_request request) {
		
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
	

}
