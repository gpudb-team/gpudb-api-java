package com.gisfederal.request;

import org.apache.log4j.Logger;

import avro.java.gaia.group_by_map_page_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.Gaia;

public class GroupByMapPage extends Request {
	
	public GroupByMapPage(Gaia gaia, String file, String map_id, int page_number) {
		
		throw new UnsupportedOperationException(" --- NOT IMPLEMENTED ----");
		/*
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(GroupByMapPage.class);
		
		// construct avro object and then convert to bytes
		group_by_map_page_request request = new group_by_map_page_request(map_id, page_number, this.gaia.getUserAuth());
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		*/
	}
}
