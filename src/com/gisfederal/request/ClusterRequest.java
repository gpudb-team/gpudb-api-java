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
import avro.java.gpudb.cluster_request;

/**
 * @author pjacobs
 *
 */
public class ClusterRequest extends Request {

	public ClusterRequest(GPUdb gPUdb, String file, SetId world_id, SetId subworld_id, SetId result_set_id, String shared_attribute, String cluster_attribute) {
		this.gPUdb = gPUdb;
		this.file = file;		
		this.log = Logger.getLogger(ClusterRequest.class);
		
		this.log.debug("gpudb:"+gPUdb.toString()+" world_id:"+world_id.get_id()+" subworld_id:"+subworld_id.get_id());
		// NOTE: there are also the first "first_pass" and "list"
		cluster_request request = new cluster_request(cluster_attribute, new Boolean(true), new ArrayList<Double>(), result_set_id.get_id(), shared_attribute, subworld_id.get_id(), world_id.get_id(), this.gPUdb.getUserAuth());
		this.log.debug("Build request object");

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(cluster_request request) {
		
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[worldsetid=");
		msg.append(request.getWorldSet().toString());
		msg.append("]]");
		
		setAuditMessage(msg.toString());
	}
}
