/**
 * 
 */
package com.gisfederal.request;

import java.util.List;

import com.gisfederal.GPUdb;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import org.apache.log4j.Logger;

import avro.java.gpudb.new_set_request;
import avro.java.gpudb.plot2d_multiple_request;

/**
 * @author pjacobs
 *
 */
public class Plot2DMultipleRequest extends Request {

	/**
	 * 
	 */
	public Plot2DMultipleRequest(GPUdb gPUdb, String file, List<CharSequence> in_sets, List<Long> colors, List<Integer> sizes, String x_attribute, String y_attribute, double min_x, double max_x, double min_y, double max_y, double width, double height, String projection, long bg_color) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(Plot2DMultipleRequest.class);
		
		// construct avro object and then encode
		plot2d_multiple_request request = new plot2d_multiple_request(min_x, max_x, min_y, max_y, x_attribute, y_attribute, width, height, projection, in_sets, colors, sizes, bg_color, this.gPUdb.getUserAuth());
		
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
	
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(plot2d_multiple_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setids=");
		msg.append(request.getSetIds().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}


}
