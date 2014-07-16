package com.gisfederal.request;

import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

/// DELETE OR REWORK
public class DistinctRequest extends Request {

	public DistinctRequest(GPUdb gPUdb, SetId src_id, SetId dst_guid,
			String[] attrs) {
		
		throw new UnsupportedOperationException(" --- NOT IMPLEMENTED ----");
		
		/*
		this.gpudb = gpudb;
		this.file = "/distinct";
		this.log = Logger.getLogger(DistinctRequest.class);

		String req_uuid = UUID.randomUUID().toString();
		Gson gson = new Gson();

		// loop through the attr names and build a comma seperated string for
		// the attrs
		String s_attrs = "";
		for (int i = 0; i < attrs.length; i++) {
			s_attrs += attrs[i];
			if (i + 1 < attrs.length) {
				s_attrs += ",";
			}
		}
		log.debug(String.format("attr list |%s|", s_attrs));

		JsonObject jsonobj = new JsonObject();
		jsonobj.addProperty("request-id", req_uuid);
		jsonobj.addProperty("type", "distinct");
		jsonobj.addProperty("src_guid", src_id.get_id());
		jsonobj.addProperty("dst_guid", dst_guid.get_id());
		jsonobj.addProperty("attrs", s_attrs);

		// this.json_request = jsonobj.toString();
		this.requestData = new RequestData(new byte[] {});
		*/
	}

}