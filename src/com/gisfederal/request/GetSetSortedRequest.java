/**
 * 
 */
package com.gisfederal.request;
import com.gisfederal.GPUdb;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import org.apache.log4j.Logger;

import avro.java.gpudb.add_object_request;
import avro.java.gpudb.get_set_sorted_request;
/**
 * @author pjacobs
 *
 */
public class GetSetSortedRequest extends Request {

	public GetSetSortedRequest(GPUdb gPUdb, SetId id, int start, int end) {
		this.gPUdb = gPUdb;
		this.file = "/getsetsorted";
		this.log = Logger.getLogger(GetSetSortedRequest.class);	

		// avro object		
		log.debug("build the get set sorted request");
		get_set_sorted_request request = new get_set_sorted_request(new Integer(start), new Integer(end), id.get_id(), gPUdb.getUserAuth());
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		this.log.debug("built request data "+this.requestData.toString());

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(get_set_sorted_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

}
