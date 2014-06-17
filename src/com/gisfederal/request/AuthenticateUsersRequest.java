/**
 * 
 */
package com.gisfederal.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gaia.authenticate_users_request;
import avro.java.gaia.set_info_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.Gaia;
import com.gisfederal.SetId;

/**
 * @author pjacobs
 * 
 * LAME DUCK
 */
public class AuthenticateUsersRequest extends Request {

	/**
	 * 
	 */
	public AuthenticateUsersRequest(Gaia gaia, String file, List<String> user_authorizations, List<SetId> set_ids) {
		
		throw new UnsupportedOperationException(" --- NOT IMPLEMENTED ----");
		/*
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(AuthenticateUsersRequest.class);
		
		// convert set_ids
		List<CharSequence> list_auths = new ArrayList<CharSequence>();
		List<CharSequence> list_sets  = new ArrayList<CharSequence>();
		for(int i=0; i<user_authorizations.size(); i++) {
			list_auths.add(user_authorizations.get(i));
			list_sets.add(set_ids.get(i).get_id());
		}
		
		authenticate_users_request request = new authenticate_users_request(list_auths, list_sets);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		*/
	}

}
