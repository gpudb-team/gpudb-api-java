package com.gisfederal.request;
import com.gisfederal.GPUdb;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import org.apache.log4j.Logger;

import avro.java.gpudb.get_set_sorted_request;
import avro.java.gpudb.histogram_request;

public class HistogramRequest extends Request {
	public HistogramRequest(GPUdb gPUdb, String file, SetId id, String attribute, long interval, double start, double end) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(HistogramRequest.class);
		
		log.debug("file:"+file+" id:"+id.get_id()+" start:"+start+" end:"+end);
		histogram_request request = new histogram_request(attribute, end, interval, id.get_id(), start, this.gPUdb.getUserAuth());
	
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
