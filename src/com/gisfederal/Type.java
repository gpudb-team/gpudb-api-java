package com.gisfederal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.List;

import org.apache.avro.Schema; 
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.log4j.Logger;

import com.gisfederal.semantic.types.SemanticType;
import com.gisfederal.semantic.types.SemanticTypeEnum;

/**
 * The Java representation of an object type. 
 */
public class Type {
	
	// Object ID Property
	public static String PROP_OBJECT_ID = "OBJECT_ID";

	private String type_id;
	private Class type_class;
	private Schema schema;
	private Logger log;
	
	private String label;
	// NOTE: we should be able to depreciate this string semanticType
	private String semanticType;
	private SemanticTypeEnum semanticTypeEnum;
	
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
	 * @param _type_id The ID that GAIA knows this type as.
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
	}
	
	/**
	 * Construct a Type object with the given ID and the associated Java class. Empty type label and semantic type.
	 * @param type_id The ID that GAIA knows this type as.
	 * @param c The class that the Java API will use to build and decode this object.
	 * @param schema The avro schema for this type.
	 */
	public Type(String type_id, Class c, Schema schema){
		this(type_id, c, schema, "", "");
	}

	/**
	 * Construct a Type object with the given ID and the associated Java class.
	 * @param _type_id The ID that GAIA knows this type as.
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
		GenericDatumReader<GenericData.Record> reader = new GenericDatumReader<GenericData.Record>(schema);			
		DecoderFactory decoder_factory = new DecoderFactory();			

		log.debug("After building binary decoder");
		// build the object					
		try {			
			Decoder decoder = decoder_factory.binaryDecoder(bytes.array(), null);
			GenericData.Record record = reader.read(null, decoder);

			Object instance = decode(record);
			return instance;
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		log.error("Returning empty object; was unable to decode");
		return new Object();
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
			Field[] fields = type_class.getFields();
			for(Field field : fields) {
				String fieldType = field.getType().getSimpleName().toLowerCase(); // need to know how to set the fields
				log.debug("record.get(field.getName()):"+record.get(field.getName()).toString()+" fieldType:"+fieldType); 
				if(fieldType.equals("int")){
					Integer value = (Integer)record.get(field.getName());
					field.setInt(instance, value.intValue());
				} else if(fieldType.equals("long")){
					Long value = (Long)record.get(field.getName());
					field.setLong(instance, value.longValue());
				} else if(fieldType.equals("double")){
					Double value = (Double)record.get(field.getName());
					field.setDouble(instance, value.doubleValue());
				} else if(fieldType.equals("float")){
					Float value = (Float)record.get(field.getName());
					field.setFloat(instance, value.floatValue());
				} else if(fieldType.equals("string")){
					org.apache.avro.util.Utf8 value = (org.apache.avro.util.Utf8)record.get(field.getName());						
					field.set(instance, value.toString());
				} else if(fieldType.equals("bytebuffer")){
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
		// uniquely defines a type as far as gaia is concerned
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
}
