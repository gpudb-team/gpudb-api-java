/**	
 * 
 */
package com.gisfederal.request;

import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gpudb.add_object_request;
import avro.java.gpudb.filter_then_histogram_request;
import avro.java.gpudb.histogram_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

/**
 * Perform a filter that will result in filter.size() filtered sets, now apply a histogram to each of these
 * @author pjacobs
 */
public class FilterThenHistogramRequest extends Request {

	public FilterThenHistogramRequest(GPUdb gPUdb, String file, SetId id, String filter_attribute, List<CharSequence> filter, String histogram_attribute, long interval, double start, double end) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(FilterThenHistogramRequest.class);
		
		log.debug("file:"+file+" id:"+id.get_id()+" start:"+start+" end:"+end);
		filter_then_histogram_request request = new filter_then_histogram_request(histogram_attribute, end, interval, id.get_id(), start, filter_attribute, filter, this.gPUdb.getUserAuth());
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(filter_then_histogram_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
}
