/**
 * 
 */
package com.gisfederal.request;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.log4j.Logger;

import avro.java.gaia.add_object_request;
import avro.java.gaia.bulk_add_request;

import com.google.gson.Gson;
import com.gisfederal.*;

public class BulkAddRequest extends Request {

	public BulkAddRequest(Gaia gaia, String file, List<Object> list_obj, NamedSet ns) {		
		this.gaia = gaia;
		this.file = file;
		this.log = Logger.getLogger(BulkAddRequest.class);

		List<ByteBuffer> binaryList = new ArrayList<ByteBuffer>();

		// grab schema
		Type type = ns.getType();
		Schema schema = type.getAvroSchema();

		for(Object obj : list_obj) {			
			//GenericDatumWriter writer = new GenericDatumWriter(schema);
			EncoderFactory encoderFactory = new EncoderFactory();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			log.debug("After building encoder factory");				

			// build the bytes		
			try {
				GenericDatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(schema);
				GenericRecord record = new GenericData.Record(schema);

				// different procedure if this is generic object
				if((Class)type.getTypeClass() == GenericObject.class) {
					log.debug("Type is a generic object");				

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
							log.debug("field_name:"+field_name+" field_value:"+field_value);
							record.put(field_name, field_value);
						} else if(Schema.Type.DOUBLE == field_type) {
							record.put(field_name, Double.valueOf(field_value));
						} else if(Schema.Type.FLOAT == field_type) {
							record.put(field_name, Float.valueOf(field_value));
						} else if(Schema.Type.INT == field_type) {
							record.put(field_name, Integer.valueOf(field_value));
						} else if(Schema.Type.LONG == field_type) {
							record.put(field_name, Long.valueOf(field_value));
						} else {
							log.error("Unsupported type:"+field_type);
						}
						// NOTE: no bytes														
						log.debug("Added:"+pairs.getKey()+" value:"+pairs.getValue());				    
						iter.remove();
					}
				} else {
					log.debug("Type is NOT a generic object");

					// need to figure out all the fields and add them to the record	
					// NOTE: in order for this to work the class must be public too
					Field[] fields = obj.getClass().getDeclaredFields();
					for(Field field : fields){
						String fieldType = field.getType().getSimpleName().toLowerCase();
						String fieldName = field.getName();
						log.debug("fieldName:"+fieldName+" fieldType:"+fieldType);
						if(fieldType.equals("double")){
							// field.getDouble(obj) will get, as a double, the field for this object; then we put it into the record with this name
							log.debug("record.put("+fieldName+","+field.getDouble(obj)+")");
							record.put(fieldName, field.getDouble(obj));
						} else if(fieldType.equals("float")){
							log.debug("record.put("+fieldName+","+field.getFloat(obj)+")");
							record.put(fieldName, field.getFloat(obj));
						} else if(fieldType.equals("int")){
							log.debug("record.put("+fieldName+","+field.getInt(obj)+")");
							record.put(fieldName, field.getInt(obj));
						} else if(fieldType.equals("long")){
							log.debug("record.put("+fieldName+","+field.getLong(obj)+")");
							record.put(fieldName, field.getLong(obj));
						} else if(fieldType.equals("string")){
							log.debug("record.put("+fieldName+","+field.get(obj).toString()+")");
							record.put(fieldName, field.get(obj).toString());
						}				
						// NOTE: anything else? bytes I guess
					}

				}
				Encoder encoder = encoderFactory.binaryEncoder(baos, null);//null just means give me a new one

				writer.write(record, encoder);	
				encoder.flush();

				baos.flush();
				baos.close();


			} catch(Exception e) {
				System.err.println(e.toString());
			}
			// put the resulting byte array into the bytebuffer 
			log.debug("baos.toByteArray().length:"+baos.toByteArray().length);
			ByteBuffer serialized = ByteBuffer.wrap(baos.toByteArray());
			log.debug("serialized:"+serialized.toString());

			// add to list
			binaryList.add(serialized);

		}			

		// list of string encodings
		List<java.lang.CharSequence> list_str = new ArrayList<java.lang.CharSequence>();
		for(int i=0; i<binaryList.size(); i++) {
			list_str.add("");
		}

		// build avro object
		bulk_add_request request = new bulk_add_request(ns.get_setid().get_id(), binaryList, list_str, "BINARY");				
		this.requestData = new RequestData(AvroUtils.convert_to_bytes(request));
		
		// Create log msg for audit
		createAuditMsg(request);
	}
	
	private void createAuditMsg(bulk_add_request request) {
		
		StringBuffer msg = new StringBuffer();
		getAuditPart(msg);

		msg.append("[params=[setid=");
		msg.append(request.getSetId().toString());
		msg.append("]]");

		setAuditMessage(msg.toString());
	}
	

}
