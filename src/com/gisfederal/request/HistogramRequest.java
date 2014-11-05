package com.gisfederal.request;
import java.util.Map;

import org.apache.log4j.Logger;

import avro.java.gpudb.histogram_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class HistogramRequest extends Request {
	public HistogramRequest(GPUdb gPUdb, String file, SetId id, String attribute, double interval, double start, double end, 
			Map<CharSequence, CharSequence> params) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(HistogramRequest.class);
		
		log.debug("file:"+file+" id:"+id.get_id()+" start:"+start+" end:"+end);
		histogram_request request = new histogram_request(id.get_id(), attribute, start, end, interval, params, this.gPUdb.getUserAuth());
		
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(histogram_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
}
