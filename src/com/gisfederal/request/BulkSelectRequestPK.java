/**
 * Gisfederal - 2015
 */
package com.gisfederal.request;

import java.util.Map;

import org.apache.log4j.Logger;

import avro.java.gpudb.bulk_select_pk_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.NamedSet;

public class BulkSelectRequestPK extends Request {

	public BulkSelectRequestPK(GPUdb gPUdb, String file, NamedSet ns, Map<CharSequence,java.util.List<CharSequence>> pk_expressions,
			Map<java.lang.CharSequence,java.lang.CharSequence> params) {		
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(BulkSelectRequestPK.class);
		
		this.setId = ns.get_setid().toString();

		// build avro object
		bulk_select_pk_request request = new bulk_select_pk_request(ns.get_setid().get_id(), pk_expressions, params, gPUdb.getUserAuth());

		byte[] bulk_update_data_bytes = AvroUtils.convert_to_bytes(request);
		this.requestData = new RequestData(bulk_update_data_bytes);
		
		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(bulk_select_pk_request request) {
		
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
