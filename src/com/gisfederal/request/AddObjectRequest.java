package com.gisfederal.request;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.log4j.Logger;

import avro.java.gpudb.add_object_request;
import avro.java.gpudb.bounding_box_request;

import com.gisfederal.AvroUtils;
import com.gisfederal.GPUdb;
import com.gisfederal.GPUdbException;
import com.gisfederal.GenericObject;
import com.gisfederal.NamedSet;
import com.gisfederal.Type;

public class AddObjectRequest extends Request{

	// Given the object to add and the set id of named set to add it too
	public AddObjectRequest(GPUdb gPUdb, String file, Object obj, NamedSet ns) throws GPUdbException{
		this.gPUdb = gPUdb;
		this.file = file;
		this.log = Logger.getLogger(AddObjectRequest.class);
		
		ByteBuffer serialized = encodeObject(gPUdb, file, obj, ns, log);

		// add to avro object
		add_object_request request = new add_object_request(serialized, "", "BINARY", ns.get_setid().get_id());	
		log.debug(request.getObjectData().toString());

		log.debug("Add object request created");	
		log.debug("Request byte[] length:"+AvroUtils.convert_to_bytes(request).length);

		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}

	private void createAuditMsg(add_object_request request) {
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
	
	public static ByteBuffer encodeObject(GPUdb gPUdb, String file, Object obj, NamedSet ns, Logger logger) {

		// grab schema
		Type type = ns.getType();
		Schema schema = type.getAvroSchema();

		//GenericDatumWriter writer = new GenericDatumWriter(schema);
		EncoderFactory encoderFactory = new EncoderFactory();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		logger.debug("After building encoder factory");				

		// build the bytes		
		try {
			GenericDatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(schema);
			GenericRecord record = new GenericData.Record(schema);

			// different procedure if this is generic object
			if((Class)type.getTypeClass() == GenericObject.class) {
				logger.debug("Type is a generic object");				
				
				GenericObject go = (GenericObject)obj;
				Iterator iter = go.dataMap.entrySet().iterator();
				while(iter.hasNext()) {
					Map.Entry pairs = (Map.Entry)iter.next();
					
					// figure out the type of this field; use that to decode the value
					String field_name = (String)pairs.getKey();
					String field_value = (String)pairs.getValue(); // stored as string in the map
					Schema.Field field = schema.getField(field_name);
					Schema.Type field_type =field.schema().getType();
					
					if(Schema.Type.STRING == field_type) {
						record.put(field_name, field_value);
					} else if(Schema.Type.FLOAT == field_type) {
						record.put(field_name, Float.valueOf(field_value));
					} else if(Schema.Type.DOUBLE == field_type) {
						record.put(field_name, Double.valueOf(field_value));
					} else if(Schema.Type.INT == field_type) {
						record.put(field_name, Integer.valueOf(field_value));
					} else if(Schema.Type.LONG == field_type) {
						record.put(field_name, Long.valueOf(field_value));
					} else {
						logger.error("Unsupported type:"+field_type);
					}
					// NOTE: no bytes														
					logger.debug("Added:"+pairs.getKey()+" value:"+pairs.getValue());				    
				    iter.remove();
				}
			} else {
				logger.debug("Type is NOT a generic object");
				// need to figure out all the fields and add them to the record	
				// NOTE: in order for this to work the class must be public too
				Field[] fields = obj.getClass().getDeclaredFields();
				for(Field field : fields){
					String fieldType = field.getType().getSimpleName().toLowerCase();
					String fieldName = field.getName();
					logger.debug("fieldName:"+fieldName+" fieldType:"+fieldType);
					if(fieldType.equals("double")){
						// field.getDouble(obj) will get, as a double, the field for this object; then we put it into the record with this name
						logger.debug("record.put("+fieldName+","+field.getDouble(obj)+")");
						record.put(fieldName, field.getDouble(obj));
					} else if(fieldType.equals("float")){
						logger.debug("record.put("+fieldName+","+field.getFloat(obj)+")");
						record.put(fieldName, field.getFloat(obj));
					} else if(fieldType.equals("int")){
						logger.debug("record.put("+fieldName+","+field.getInt(obj)+")");
						record.put(fieldName, field.getInt(obj));
					} else if(fieldType.equals("long")){
						logger.debug("record.put("+fieldName+","+field.getLong(obj)+")");
						record.put(fieldName, field.getLong(obj));
					} else if(fieldType.equals("string")){
						logger.debug("record.put("+fieldName+","+field.get(obj).toString()+")");
						record.put(fieldName, field.get(obj).toString());
					} else if (fieldType.equals("bytebuffer")) {
						logger.debug("record.put("+fieldName+","+field.get(obj).toString());
						record.put(fieldName, (ByteBuffer)field.get(obj));
					} else {
						logger.error("Unhandled field; fieldType:"+fieldType);
					}
				}
			}
			Encoder encoder = encoderFactory.binaryEncoder(baos, null);//null just means give me a new one

			writer.write(record, encoder);	
			encoder.flush();

			baos.flush();
			baos.close();

		} catch(Exception e) {
			logger.error(" Exception received " + e);
			throw new GPUdbException("Error writing avro encoding of object:"+e.toString());
		}
		// put the resulting byte array into the bytebuffer 
		logger.debug("baos.toByteArray().length:"+baos.toByteArray().length);
		ByteBuffer serialized = ByteBuffer.wrap(baos.toByteArray());
		logger.debug("serialized:"+serialized.toString());
		return serialized;
	}
}