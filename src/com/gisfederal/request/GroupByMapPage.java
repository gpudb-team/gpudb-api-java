package com.gisfederal.request;

import com.gisfederal.GPUdb;

public class GroupByMapPage extends Request {
	
	public GroupByMapPage(GPUdb gPUdb, String file, String map_id, int page_number) {
		
		throw new UnsupportedOperationException(" --- NOT IMPLEMENTED ----");
		/*
		this.gpudb = gpudb;
		this.file = file;
		this.log = Logger.getLogger(GroupByMapPage.class);
		
		// construct avro object and then convert to bytes
		group_by_map_page_request request = new group_by_map_page_request(map_id, page_number, this.gpudb.getUserAuth());
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		*/
	}
}
