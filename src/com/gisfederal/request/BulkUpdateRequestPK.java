/**
 * Gisfederal - 2015
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

import avro.java.gpudb.bulk_update_pk_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.GPUdbException;
import com.gisfederal.NamedSet;
import com.gisfederal.utils.NullObject;

public class BulkUpdateRequestPK extends Request {

	public BulkUpdateRequestPK(GPUdb gPUdb, String file, NamedSet ns, Map<CharSequence,java.util.List<CharSequence>> pk_expressions,
			Map<java.lang.CharSequence, java.lang.CharSequence> newValueMaps, List<Object> insertObjects, 
			Map<java.lang.CharSequence,java.lang.CharSequence> params) {		
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(BulkUpdateRequestPK.class);
		
		this.setId = ns.get_setid().toString();

		List<ByteBuffer> binaryList = new ArrayList<ByteBuffer>();

		for(Object obj : insertObjects) {			
			// Use the static call from AddObjectRequest
			if( obj instanceof NullObject ) {
				ByteBuffer serialized = ByteBuffer.allocate(0);
				serialized.clear();
				binaryList.add(serialized);
			} else {
				ByteBuffer serialized = AddObjectRequest.encodeObject(gPUdb, file, obj, ns, log);
				binaryList.add(serialized);
			}
		}			

		/* list of string encodings
		List<java.lang.CharSequence> list_str = new ArrayList<java.lang.CharSequence>();
		for(int i=0; i<binaryList.size(); i++) {
			list_str.add("");
		}
		*/
		
		// build avro object
		bulk_update_pk_request request = new bulk_update_pk_request(ns.get_setid().get_id(), pk_expressions, newValueMaps,
				binaryList, new ArrayList<CharSequence>(), "BINARY", params, gPUdb.getUserAuth());

		byte[] dataToSend = null;
		byte[] bulk_update_data_bytes = AvroUtils.convert_to_bytes(request);
		if( gPUdb.isSnappyCompress() ) {
			try {
				dataToSend = Snappy.compress(bulk_update_data_bytes);
				log.info(" Compressed from " + bulk_update_data_bytes.length + " to :" + dataToSend.length);
			} catch (IOException e) {
				log.error(e);
				throw new GPUdbException(e.getMessage());
			}
		} else {
			dataToSend = bulk_update_data_bytes;
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
	
	private void createAuditMsg(bulk_update_pk_request request) {
		
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]");
		
		msg.append("[num predicates=");
		msg.append(request.getPkExpressions().size());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
	

}
