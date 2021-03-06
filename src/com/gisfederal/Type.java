package com.gisfederal;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.gisfederal.request.CreateTypeWithAnnotationsRequest;
import com.gisfederal.semantic.types.SemanticTypeEnum;
import com.gisfederal.utils.IgnoreOnIngest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * The Java representation of an object type.
 */
public class Type {
	
	public static final Log logger = LogFactory.getLog(CreateTypeWithAnnotationsRequest.class);

	private String type_id;
	private Class type_class;
	private Schema schema;
	private Logger log;

	private String label;
	// NOTE: we should be able to depreciate this string semanticType
	private String semanticType;
	private SemanticTypeEnum semanticTypeEnum;
	
	Field[] fields = null;
	GenericDatumReader<GenericData.Record> reader = null;
	DecoderFactory decoder_factory = null;

	public SemanticTypeEnum getSemanticTypeEnum() {
		return semanticTypeEnum;
	}

	/**
	 * Return the semantic type of this type.
	 * @return Semantic type string. NOTE: should be one of the public static strings in SemanticType
	 */
	@Deprecated
	public String getSemanticType() {
		return semanticType;
	}

	/**
	 * Return the type label.
	 * @return Type label string.
	 */
	public String getTypeLabel() {
		return label;
	}

	// parent types are special; they don't have schemas or classes but are actually a collection of underlying children sets
	private boolean isParent;

	/**
	 * Return true if this is a parent.
	 * @return Whether this set is a parent, i.e. has or can have children.
	 */
	public boolean isParent() { return isParent;}

	public Type(boolean parent) {
		this.isParent = parent;
	}

	/**
	 * Builds a parent type object.
	 * @return Type object.
	 */
	public static Type buildParentType() {
		return new Type(true);
	}

	/**
	 * Construct a Type object with the given ID and the associated Java class.
	 * @param _type_id The ID that GPUDB knows this type as.
	 * @param c The class that the Java API will use to build and decode this object.
	 * @param schema The avro schema for this type.
	 * @param label The type label.
	 * @param semanticType The semantic type string.
	 */
	public Type(String _type_id, Class c, Schema schema, String label, String semanticType){
		type_id = _type_id;
		type_class = c;
		this.schema = schema;
		this.log = Logger.getLogger(Type.class);
		this.isParent = false;
		this.label = label;
		this.semanticType = semanticType;
		this.semanticTypeEnum = SemanticTypeEnum.valueOfWithEmpty(this.semanticType);
		this.reader = new GenericDatumReader<GenericData.Record>(schema);
		this.decoder_factory = new DecoderFactory();
	}

	/**
	 * Construct a Type object with the given ID and the associated Java class. Empty type label and semantic type.
	 * @param type_id The ID that GPUDB knows this type as.
	 * @param c The class that the Java API will use to build and decode this object.
	 * @param schema The avro schema for this type.
	 */
	public Type(String type_id, Class c, Schema schema){
		this(type_id, c, schema, "", "");
	}

	/**
	 * Construct a Type object with the given ID and the associated Java class.
	 * @param _type_id The ID that GPUDB knows this type as.
	 * @param c The class that the Java API will use to build and decode this object.
	 */
	public Type(String _type_id, Class c){
		this(_type_id, c, null);
	}

	/**
	 * Get the ID for this type.
	 * @return The ID for this type.
	 */
	public String getID() {
		return type_id;
	}

	/**
	 * Get the Java class for this type.  Every object of this type will be represented by an instance of this class.
	 * @return The Java class for this type.
	 */
	public Class getTypeClass() {
		return type_class;
	}

	/**
	 * Get the schema for this type.
	 * @return The avro schema for this type.  Used for decoding and encoding the object.
	 */
	public Schema getAvroSchema() {
		return schema;
	}

	/**
	 * Decode the binary data using the avro schema
	 * @param bytes Encoded object
	 * @return The decoded object
	 */
	public Object decode(ByteBuffer bytes) {
		// binary decode
		//GenericDatumReader<GenericData.Record> reader = new GenericDatumReader<GenericData.Record>(schema);
		//DecoderFactory decoder_factory = new DecoderFactory();

		//log.debug("After building binary decoder");
		// build the object
		try {
			
			//toStringMe(bytes.array());
			Decoder decoder = decoder_factory.binaryDecoder(bytes.array(), null);
			GenericData.Record record = reader.read(null, decoder);

			Object instance = decode(record);
			return instance;
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		//log.error("Returning empty object; was unable to decode");
		return new Object();
	}

	public void toStringMe(byte[] bs) { 
		 StringBuffer sb = new StringBuffer(3*bs.length);
		 for (int idx = 0; idx < bs.length; idx++) {
			 // if not the first, put a blank separator in
		      if (idx != 0) {
		        sb.append(' ');
		     }
		      String num = Integer.toHexString(0xff & bs[idx]);
		      // if it is only one digit, add a leading 0.
		      if (num.length() < 2) {
		        sb.append('0');
		      }
		      sb.append(num);
		    }
		    System.out.println(" count : " + bs.length + " data :" + sb.toString());
	}
	
	private Object decode(GenericData.Record record)
			throws InstantiationException, IllegalAccessException {
		Object instance = type_class.newInstance(); // we will be transfering record into this object
		if(type_class == GenericObject.class) {
			log.debug("List of a generic object");
			GenericObject go = new GenericObject();
			// get the fields from the schema
			List<Schema.Field> list_of_fields = record.getSchema().getFields();
			for(Schema.Field field : list_of_fields) {
				String field_name = field.name();
				log.debug("field name:"+field_name+" value:"+record.get(field_name));
				go.addField(field_name, record.get(field_name).toString());
			}
			instance = go;
		} else {
			// now use the record to build an object of class "class_of_obj"
			// loop through the fields of the object these correspond to keys in the genericdata record
			if( fields == null ) {
				fields = type_class.getFields();
			}
			
			for(Field field : fields) {
				//String fieldType = field.getType().getSimpleName().toLowerCase(); // need to know how to set the fields
				
				Class fieldTypec = field.getType();
				
				//log.debug("record.get(field.getName()):"+record.get(field.getName()).toString()+" fieldType:"+fieldType);
				
				if(fieldTypec == Integer.TYPE){
					Integer value = (Integer)record.get(field.getName());
					field.setInt(instance, value.intValue());
				} else if(fieldTypec == Long.TYPE){
					Long value = (Long)record.get(field.getName());
					field.setLong(instance, value.longValue());
				} else if(fieldTypec == Double.TYPE){
					Double value = (Double)record.get(field.getName());
					field.setDouble(instance, value.doubleValue());
				} else if(fieldTypec == Float.TYPE){
					Float value = (Float)record.get(field.getName());
					field.setFloat(instance, value.floatValue());
				} else if(fieldTypec == String.class){
					org.apache.avro.util.Utf8 value = (org.apache.avro.util.Utf8)record.get(field.getName());
					field.set(instance, value.toString());
				} else if(fieldTypec == ByteBuffer.class){
					ByteBuffer value = (ByteBuffer)record.get(field.getName());
					field.set(instance, value);
				}
			}
		}
		return instance;
	}

	public List<Schema.Field> getFields() {
		return getAvroSchema().getFields();
	}

	@Override
	public int hashCode() {
		// doesn't have to be as strict as equals
		return this.semanticType.hashCode();
	}

	@Override
	public boolean equals(Object other){
		if(other == null) return false;
		if(other == this) return true;
		if(!(other instanceof Type)) return false;
		Type type = (Type)other;
		// uniquely defines a type as far as gpudb is concerned
		if(type.label.equals(this.label) && type.semanticType.equals(this.semanticType) && type.schema.equals(this.schema)) return true;

		return false;
	}

	/**
	 * Decode from JSON string
	 * @param data
	 * @param object_schema
	 * @return
	 */
	public Object decodeJson(CharSequence data, Schema object_schema) {
		// binary decode
		GenericDatumReader<GenericData.Record> reader = new GenericDatumReader<GenericData.Record>(schema);
		DecoderFactory decoder_factory = new DecoderFactory();

		log.debug("After building json decoder");
		// build the object
		try {
			//Decoder decoder = decoder_factory.binaryDecoder(bytes.array(), null);
			InputStream is = new ByteArrayInputStream(data.toString().getBytes());
			Decoder decoder = decoder_factory.jsonDecoder(object_schema, is);
			GenericData.Record record = reader.read(null, decoder);
			Object instance = decode(record);
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		log.error("Returning empty object; was unable to decode");
		return new Object();
	}
	
	public static String classToTypeDefinition(Class c) {
		// the name of the object/type
		String name = c.getSimpleName();
		
		logger.debug("Class name is : " + name);
		
		// gets only public but full up hierarchy
		Field[] fields = c.getFields();
		JsonArray json_fields = new JsonArray();
		JsonObject json_field;
		JsonObject json_type_def = new JsonObject();
		
		// type: record, name:..., 
		json_type_def.addProperty("type", "record");
		json_type_def.addProperty("name", name);
		
		// convert Field[] into the correct JsonArray version
		for(int i=0; i<fields.length; i++) {
			
			String fieldTypeName = fields[i].getType().getSimpleName().toLowerCase();
			
			Field field = fields[i];// obtain method object
			Annotation annotation = field.getAnnotation(IgnoreOnIngest.class);
		    logger.debug("Annotation for field : " + fieldTypeName + " is : " + annotation);
			if( annotation instanceof IgnoreOnIngest ) {
				// If the field is annotated IgnoreOnIngest, skip it
				continue;
			}
			
			// each field has name and type
			json_field = new JsonObject();
			json_field.addProperty("name", fields[i].getName());
			
			// go through adding valid fields
			
			logger.debug("fieldTypeName:"+fieldTypeName);
			if(fieldTypeName.equals("bytebuffer")){
				json_field.addProperty("type", "bytes");
			} else if(fieldTypeName.equals("string") || fieldTypeName.equals("double") 
					|| fieldTypeName.equals("int") || fieldTypeName.equals("float") 
					|| fieldTypeName.equals("long")){
				json_field.addProperty("type", fieldTypeName);
			} else {
				logger.error("Unsuported java data type:"+fieldTypeName);
				throw new GPUdbException("Unsuported java data type:"+fieldTypeName);
			}
			
			json_fields.add(json_field);
		}
		logger.debug("FIELDS:"+json_fields.toString());
		
		// add fields
		json_type_def.add("fields", json_fields);
				
		// need to convert the quotation marks into \" in the string
		String str_json_type_def = json_type_def.toString();
		logger.debug("json_type_def (Before):"+str_json_type_def);
						
		str_json_type_def.replaceAll("\"", "\\\"");
		logger.debug("str_json_type_def (after):"+str_json_type_def);
		return str_json_type_def;
	}
}
