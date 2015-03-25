/**
 * 
 */
package com.gisfederal.request;

import com.gisfederal.GPUdb;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gpudb.bounding_box_request;

/**
 * @author pjacobs
 *
 */
public class ClusterRequest extends Request {

	public ClusterRequest(GPUdb gPUdb, String file, SetId world_id, SetId subworld_id, SetId result_set_id, String shared_attribute, String cluster_attribute) {
		
		throw new UnsupportedOperationException(" --- NOT IMPLEMENTED ----");
		/*
		this.gPUdb = gPUdb;
		this.file = file;		
		this.log = Logger.getLogger(ClusterRequest.class);
		*/
	}

	/*
	private void createAuditMsg(cluster_request request) {
		
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[worldsetid=");
		msg.append(request.getWorldSet().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}
	*/
}
