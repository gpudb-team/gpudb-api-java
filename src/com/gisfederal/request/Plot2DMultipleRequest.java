/**
 * 
 */
package com.gisfederal.request;

import java.util.List;

import org.apache.log4j.Logger;

import com.gisfederal.GPUdb;

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
	}

}
