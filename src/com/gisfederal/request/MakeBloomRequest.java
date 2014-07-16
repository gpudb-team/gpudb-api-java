/**
 * 
 */
package com.gisfederal.request;
import com.gisfederal.GPUdb;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import org.apache.log4j.Logger;

import avro.java.gpudb.make_bloom_request;

/**
 * @author pjacobs
 *  
 */
public class MakeBloomRequest extends Request {

	/**
	 * 
	 */
	public MakeBloomRequest(GPUdb gPUdb, String file, SetId id, String attribute) {
		
		throw new UnsupportedOperationException(" --- NOT IMPLEMENTED ----");
		/*
		this.gpudb = gpudb;
		this.file = file;
		this.log = Logger.getLogger(MakeBloomRequest.class);
		
		this.log.debug("gpudb:"+gpudb.toString()+" in_set:"+id.get_id());	
		make_bloom_request request = new make_bloom_request(id.get_id(), attribute);	
		
		this.log.debug("Build request object");
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		*/
	}
}
