package com.gisfederal.request;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import avro.java.gpudb.register_type_with_annotations_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.GPUdbException;
import com.gisfederal.Type;

public class CreateTypeWithAnnotationsRequest extends Request {
	/* REVIST */
	// This will build a register type request.  The type definition is an avro schema (json) which is built from the fields of the java class that is passed in. 
	// {"type_definition":"{\\"type\\":\\"record\\",\\"name\\":\\"point\\",\\"fields\\":[{\\"name\\":\\"x\\",\\"type\\":\\"double\\"},{\\"name\\":\\"y\\",\\"type\\":\\"double\\"}]}"}'	
	// NOTE: the annotation should be some attribute in the class or ""
	public CreateTypeWithAnnotationsRequest(GPUdb gPUdb, String file, Class c, 
			String label, String semanticType, Map<CharSequence, List<CharSequence>> annotations_map) throws GPUdbException{
		this.gPUdb = gPUdb;
		this.file = "/registertypewithannotations"; 
		this.log = Logger.getLogger(CreateTypeWithAnnotationsRequest.class);

		String str_json_type_def = Type.classToTypeDefinition(c);
		
		// build avro object
		register_type_with_annotations_request request = new register_type_with_annotations_request(str_json_type_def, label, semanticType, annotations_map);
		
		// debug; checking the string encoding of the request
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}
	
	public CreateTypeWithAnnotationsRequest(GPUdb gPUdb, String file, String definition, String label, String semanticType,
			Map<CharSequence, List<CharSequence>> annotations_map ) throws GPUdbException{
		this.gPUdb = gPUdb;
		this.file = "/registertypewithannotations"; 
		this.log = Logger.getLogger(CreateTypeWithAnnotationsRequest.class);		

		// build avro object
		log.debug("definition:"+definition);
		register_type_with_annotations_request request = new register_type_with_annotations_request(definition, label, semanticType, annotations_map);
		
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(register_type_with_annotations_request request) {
		
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[semantic_type=");
		msg.append(request.getSemanticType());
		msg.append("]");
		
		msg.append("[label=");
		msg.append(request.getLabel());
		msg.append("]");
		
		/*
		msg.append("schema=");
		msg.append(request.getTypeDefinition());
		msg.append("]");
		*/
		
		/* THIS CAN BE TOO BIG
		msg.append("[annotation_attr=");
		msg.append(request.getAnnotations());
		msg.append("]]");
		*/
		
		setAuditMessage(msg.toString());
	}

}
