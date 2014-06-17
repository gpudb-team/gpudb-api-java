/**
 * 
 */
package com.gisfederal.request;
import com.gisfederal.Gaia;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;

import org.apache.log4j.Logger;

import avro.java.gaia.join_setup_request;

/**
 * @author pjacobs
 *
 */
public class JoinSetupRequest extends Request {

	/**
	 * 
	 */
	public JoinSetupRequest(Gaia gaia, String file, SetId left_set, String left_attr, SetId right_set, String right_attr, SetId subset) {
		
		throw new UnsupportedOperationException(" --- NOT IMPLEMENTED ----");
		
		/*
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(JoinSetupRequest.class);
		
		// construct avro object and then encode
		join_setup_request request = new join_setup_request(left_set.get_id(),left_attr, right_set.get_id(), right_attr, subset.get_id(), this.gaia.getUserAuth());
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		*/
	}
}
