/**
 * 
 */
package com.gisfederal.request;
import com.gisfederal.Gaia;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import org.apache.log4j.Logger;

import avro.java.gaia.make_bloom_request;

/**
 * @author pjacobs
 *  
 */
public class MakeBloomRequest extends Request {

	/**
	 * 
	 */
	public MakeBloomRequest(Gaia gaia, String file, SetId id, String attribute) {
		
		throw new UnsupportedOperationException(" --- NOT IMPLEMENTED ----");
		/*
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(MakeBloomRequest.class);
		
		this.log.debug("gaia:"+gaia.toString()+" in_set:"+id.get_id());	
		make_bloom_request request = new make_bloom_request(id.get_id(), attribute);	
		
		this.log.debug("Build request object");
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		*/
	}
}
