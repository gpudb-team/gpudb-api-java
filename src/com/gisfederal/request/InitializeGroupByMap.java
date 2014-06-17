package com.gisfederal.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gaia.initialize_group_by_map_request;
import avro.java.gaia.sort_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.Gaia;
import com.gisfederal.SetId;

public class InitializeGroupByMap extends Request {

	
	public InitializeGroupByMap(Gaia gaia, String file, SetId in_set, String map_id, String attribute, int page_size) {
		
		throw new UnsupportedOperationException(" --- NOT IMPLEMENTED ----");
		
		/*
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(InitializeGroupByMap.class);
		
		// construct avro object and then convert to bytes
		initialize_group_by_map_request request = new initialize_group_by_map_request(in_set.get_id(),attribute, map_id, page_size, new java.util.HashMap<java.lang.CharSequence,java.util.List<java.lang.CharSequence>>(), this.gaia.getUserAuth());
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		*/
	}		

}
