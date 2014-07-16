/**
 * 
 */
package com.gisfederal.request;
import java.util.Arrays;

import org.apache.log4j.Logger;

import avro.java.gpudb.generate_heatmap_video_request;
import avro.java.gpudb.generate_video_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.GPUdbVideoGenRequest;

/**
 * @author gisfederal
 * The request for the generate video endpoint.  Server will store png images and client will request thru KML.
 */
public class GenerateVideoRequest extends Request {

	public GenerateVideoRequest(GPUdb gPUdb, String file, GPUdbVideoGenRequest gvgr) {
		this.gPUdb = gPUdb;
		this.file = file;		
		this.log = Logger.getLogger(GenerateVideoRequest.class);
		
		this.log.debug("gpudb:"+gPUdb.toString()+" Video Gen Request packet is :"+gvgr);
		
		generate_video_request request = new generate_video_request();
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
		request.setWorldSetIds(Arrays.asList(gvgr.getWorld_set_ids().toArray(new CharSequence[gvgr.getWorld_set_ids().size()]))); 
		
		request.setTrackIds(gvgr.getTrack_ids()); 
		
		request.setDoPoints(gvgr.getDo_points());
		request.setDoShapes(gvgr.getDo_shapes());
		request.setDoTracks(gvgr.getDo_tracks());
		request.setPointcolors(gvgr.getPointcolors());
		request.setPointsizes(gvgr.getPointsizes());
		
		request.setPointshapes(Arrays.asList(gvgr.getPointshapes().toArray(new CharSequence[gvgr.getPointshapes().size()]))); 
		
		request.setShapelinewidths(gvgr.getShapelinewidths());
		request.setShapelinecolors(gvgr.getShapelinecolors());
		request.setShapefillcolors(gvgr.getShapefillcolors());
		request.setTracklinewidths(gvgr.getTracklinewidths());
		request.setTracklinecolors(gvgr.getTracklinecolors());
		request.setTrackmarkersizes(gvgr.getTrackmarkersizes());
		request.setTrackmarkercolors(gvgr.getTrackmarkercolors());

		request.setTrackmarkershapes(Arrays.asList(gvgr.getTrackmarkershapes().toArray(new CharSequence[gvgr.getTrackmarkershapes().size()]))); 
		
		request.setTrackheadcolors(gvgr.getTrackheadcolors());
		request.setTrackheadsizes(gvgr.getTrackheadsizes());
		request.setTrackheadshapes(Arrays.asList(gvgr.getTrackheadshapes().toArray(new CharSequence[gvgr.getTrackheadshapes().size()])));

		request.setTimeIntervals(gvgr.getTime_intervals());
		request.setVideoStyle(gvgr.getVideo_style());
		request.setSessionKey(gvgr.getSession_key());
		
		request.setUserAuthString(gPUdb.getUserAuth());
		
		this.log.debug("Build request object");

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(generate_video_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetIds().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}
