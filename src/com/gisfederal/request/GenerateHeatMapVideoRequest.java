/**
 * 
 */
package com.gisfederal.request;
import java.util.Arrays;

import org.apache.log4j.Logger;

import avro.java.gaia.filter_then_histogram_request;
import avro.java.gaia.generate_heatmap_video_request;
import avro.java.gaia.generate_video_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.Gaia;
import com.gisfederal.GaiaHeatMapVideoGenRequest;
import com.gisfederal.GaiaVideoGenRequest;

/**
 * @author gisfederal
 * The request for the generate heatmap video endpoint.  Server will store png images and client will request thru KML.
 */
public class GenerateHeatMapVideoRequest extends Request {

	public GenerateHeatMapVideoRequest(Gaia gaia, String file, GaiaHeatMapVideoGenRequest gvgr) {
		this.gaia = gaia;
		this.file = file;		
		this.log = Logger.getLogger(GenerateHeatMapVideoRequest.class);
		
		this.log.debug("gaia:"+gaia.toString()+" Video Gen Request packet is :"+gvgr);
		
		generate_heatmap_video_request request = new generate_heatmap_video_request();
		request.setMinX(gvgr.getMin_x());
		request.setMaxX(gvgr.getMax_x());
		request.setMinY(gvgr.getMin_y());
		request.setMaxY(gvgr.getMax_y());
		request.setXAttrName(gvgr.getX_attr_name());
		request.setYAttrName(gvgr.getY_attr_name());
		request.setWidth(gvgr.getWidth());
		request.setHeight(gvgr.getHeight());
		request.setProjection(gvgr.getProjection());
		request.setBgColor(gvgr.getBg_color());
		
		request.setSetIds(Arrays.asList(gvgr.getSet_ids().toArray(new CharSequence[gvgr.getSet_ids().size()]))); 

		request.setColormap(gvgr.getColormap());
		request.setBlurRadius(gvgr.getBlur_radius());
		request.setGradientEndColor(gvgr.getGradient_start_color());
		request.setGradientEndColor(gvgr.getGradient_end_color());
		
		request.setTimeIntervals(gvgr.getTime_intervals());
		request.setVideoStyle(gvgr.getVideo_style());
		request.setSessionKey(gvgr.getSession_key());
		
		request.setUserAuthString(gaia.getUserAuth());
		
		this.log.debug("Build request object");

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(generate_heatmap_video_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetIds().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
}
