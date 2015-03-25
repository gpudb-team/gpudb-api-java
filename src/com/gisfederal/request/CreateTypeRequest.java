package com.gisfederal.request;
import org.apache.log4j.Logger;

import avro.java.gpudb.register_type_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.GPUdbException;
import com.gisfederal.Type;

public class CreateTypeRequest extends Request {
	
	// This will build a register type request.  The type definition is an avro schema (json) which is built from the fields of the java class that is passed in. 
	// {"type_definition":"{\\"type\\":\\"record\\",\\"name\\":\\"point\\",\\"fields\\":[{\\"name\\":\\"x\\",\\"type\\":\\"double\\"},{\\"name\\":\\"y\\",\\"type\\":\\"double\\"}]}"}'	
	// NOTE: the annotation should be some attribute in the class or ""
	public CreateTypeRequest(GPUdb gPUdb, String file, Class c, String annotation_attr, String label, String semanticType) throws GPUdbException{
		this.gPUdb = gPUdb;
		this.file = "/registertype"; 
		this.log = Logger.getLogger(CreateTypeRequest.class);		

		String str_json_type_def = Type.classToTypeDefinition(c);
		
		// build avro object
		register_type_request request = new register_type_request(str_json_type_def, annotation_attr, label, semanticType);
		
		// debug; checking the string encoding of the request
		/*
		if(log.getLevel() == Level.DEBUG) {
			String str_final = AvroUtils.convert_to_string(request);	
			log.debug("Avro encode type request:"+str_final);
		}
		*/
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}
	
	public CreateTypeRequest(GPUdb gPUdb, String file, String definition, String annotation_attr, String label, String semanticType) throws GPUdbException{
		this.gPUdb = gPUdb;
		this.file = "/registertype"; 
		this.log = Logger.getLogger(CreateTypeRequest.class);		

		// build avro object
		log.debug("definition:"+definition);
		register_type_request request = new register_type_request(definition, annotation_attr, label, semanticType);
		
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(register_type_request request) {
		
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[semantic_type=");
		msg.append(request.getSemanticType());
		msg.append("]");
		
		msg.append("[label=");
		msg.append(request.getLabel());
		msg.append("]]");
		
		/* THIS CAN BE TOO BIG
		msg.append("[schema=");
		msg.append(request.getTypeDefinition());
		msg.append("]]");
		*/
		setAuditMessage(msg.toString());
	}

}
