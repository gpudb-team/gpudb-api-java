package com.gisfederal.request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import avro.java.gpudb.get_set_sorted_request;
import avro.java.gpudb.merge_sets_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.SetId;

public class MergeSetsRequest extends Request {
	public MergeSetsRequest(GPUdb gPUdb, String file, Collection<SetId> in_sets, String commonType, SetId resultID) {
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(MergeSetsRequest.class);
		
		List<CharSequence> setIds = new ArrayList<CharSequence>();
		for( SetId setid : in_sets ) {
			setIds.add(setid.get_id());
		}
		merge_sets_request request = new merge_sets_request(setIds, resultID.get_id(), commonType);
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));

		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(merge_sets_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetIds().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}

		
}
