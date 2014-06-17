/**
 * 
 */
package com.gisfederal.request;
import org.apache.log4j.Logger;

import avro.java.gaia.join_incremental_request;
import avro.java.gaia.join_setup_request;

import com.gisfederal.Gaia;
import com.gisfederal.AvroUtils;
import com.gisfederal.SetId;
/**
 * @author pjacobs
 *
 */
public class JoinIncrementalRequest extends Request {

	/**
	 * 
	 */
	public JoinIncrementalRequest(Gaia gaia, String file, SetId left_subset, String left_attr, int left_index, SetId right_set, String right_attr, String result_type, SetId result_set) {
		
		throw new UnsupportedOperationException(" --- NOT IMPLEMENTED ----");
		
		/*
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(JoinIncrementalRequest.class);
		
		// construct avro object and then encode [NOTE: map is empty; used later by gaia]
		java.util.Map<CharSequence,java.util.List<java.lang.Double>> map = new java.util.HashMap<CharSequence,java.util.List<java.lang.Double>>();
		join_incremental_request request = new join_incremental_request(left_subset.get_id(),left_attr, new Integer(left_index), right_set.get_id(), right_attr, result_set.get_id(), result_type, map, this.gaia.getUserAuth());
		
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		*/
	}
}
