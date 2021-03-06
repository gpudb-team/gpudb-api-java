/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gpudb;  
@SuppressWarnings("all")
public class get_objects_response extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"get_objects_response\",\"namespace\":\"avro.java.gpudb\",\"fields\":[{\"name\":\"set_id\",\"type\":\"string\"},{\"name\":\"type_id\",\"type\":\"string\"},{\"name\":\"type_schema\",\"type\":\"string\"},{\"name\":\"list\",\"type\":{\"type\":\"array\",\"items\":\"bytes\"}},{\"name\":\"list_str\",\"type\":{\"type\":\"array\",\"items\":\"string\"}}]}");
  @Deprecated public java.lang.CharSequence set_id;
  @Deprecated public java.lang.CharSequence type_id;
  @Deprecated public java.lang.CharSequence type_schema;
  @Deprecated public java.util.List<java.nio.ByteBuffer> list;
  @Deprecated public java.util.List<java.lang.CharSequence> list_str;

  /**
   * Default constructor.
   */
  public get_objects_response() {}

  /**
   * All-args constructor.
   */
  public get_objects_response(java.lang.CharSequence set_id, java.lang.CharSequence type_id, java.lang.CharSequence type_schema, java.util.List<java.nio.ByteBuffer> list, java.util.List<java.lang.CharSequence> list_str) {
    this.set_id = set_id;
    this.type_id = type_id;
    this.type_schema = type_schema;
    this.list = list;
    this.list_str = list_str;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return set_id;
    case 1: return type_id;
    case 2: return type_schema;
    case 3: return list;
    case 4: return list_str;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: set_id = (java.lang.CharSequence)value$; break;
    case 1: type_id = (java.lang.CharSequence)value$; break;
    case 2: type_schema = (java.lang.CharSequence)value$; break;
    case 3: list = (java.util.List<java.nio.ByteBuffer>)value$; break;
    case 4: list_str = (java.util.List<java.lang.CharSequence>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'set_id' field.
   */
  public java.lang.CharSequence getSetId() {
    return set_id;
  }

  /**
   * Sets the value of the 'set_id' field.
   * @param value the value to set.
   */
  public void setSetId(java.lang.CharSequence value) {
    this.set_id = value;
  }

  /**
   * Gets the value of the 'type_id' field.
   */
  public java.lang.CharSequence getTypeId() {
    return type_id;
  }

  /**
   * Sets the value of the 'type_id' field.
   * @param value the value to set.
   */
  public void setTypeId(java.lang.CharSequence value) {
    this.type_id = value;
  }

  /**
   * Gets the value of the 'type_schema' field.
   */
  public java.lang.CharSequence getTypeSchema() {
    return type_schema;
  }

  /**
   * Sets the value of the 'type_schema' field.
   * @param value the value to set.
   */
  public void setTypeSchema(java.lang.CharSequence value) {
    this.type_schema = value;
  }

  /**
   * Gets the value of the 'list' field.
   */
  public java.util.List<java.nio.ByteBuffer> getList() {
    return list;
  }

  /**
   * Sets the value of the 'list' field.
   * @param value the value to set.
   */
  public void setList(java.util.List<java.nio.ByteBuffer> value) {
    this.list = value;
  }

  /**
   * Gets the value of the 'list_str' field.
   */
  public java.util.List<java.lang.CharSequence> getListStr() {
    return list_str;
  }

  /**
   * Sets the value of the 'list_str' field.
   * @param value the value to set.
   */
  public void setListStr(java.util.List<java.lang.CharSequence> value) {
    this.list_str = value;
  }

  /** Creates a new get_objects_response RecordBuilder */
  public static avro.java.gpudb.get_objects_response.Builder newBuilder() {
    return new avro.java.gpudb.get_objects_response.Builder();
  }
  
  /** Creates a new get_objects_response RecordBuilder by copying an existing Builder */
  public static avro.java.gpudb.get_objects_response.Builder newBuilder(avro.java.gpudb.get_objects_response.Builder other) {
    return new avro.java.gpudb.get_objects_response.Builder(other);
  }
  
  /** Creates a new get_objects_response RecordBuilder by copying an existing get_objects_response instance */
  public static avro.java.gpudb.get_objects_response.Builder newBuilder(avro.java.gpudb.get_objects_response other) {
    return new avro.java.gpudb.get_objects_response.Builder(other);
  }
  
  /**
   * RecordBuilder for get_objects_response instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<get_objects_response>
    implements org.apache.avro.data.RecordBuilder<get_objects_response> {

    private java.lang.CharSequence set_id;
    private java.lang.CharSequence type_id;
    private java.lang.CharSequence type_schema;
    private java.util.List<java.nio.ByteBuffer> list;
    private java.util.List<java.lang.CharSequence> list_str;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gpudb.get_objects_response.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gpudb.get_objects_response.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing get_objects_response instance */
    private Builder(avro.java.gpudb.get_objects_response other) {
            super(avro.java.gpudb.get_objects_response.SCHEMA$);
      if (isValidValue(fields()[0], other.set_id)) {
        this.set_id = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.set_id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.type_id)) {
        this.type_id = (java.lang.CharSequence) data().deepCopy(fields()[1].schema(), other.type_id);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.type_schema)) {
        this.type_schema = (java.lang.CharSequence) data().deepCopy(fields()[2].schema(), other.type_schema);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.list)) {
        this.list = (java.util.List<java.nio.ByteBuffer>) data().deepCopy(fields()[3].schema(), other.list);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.list_str)) {
        this.list_str = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[4].schema(), other.list_str);
        fieldSetFlags()[4] = true;
      }
    }

    /** Gets the value of the 'set_id' field */
    public java.lang.CharSequence getSetId() {
      return set_id;
    }
    
    /** Sets the value of the 'set_id' field */
    public avro.java.gpudb.get_objects_response.Builder setSetId(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.set_id = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'set_id' field has been set */
    public boolean hasSetId() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'set_id' field */
    public avro.java.gpudb.get_objects_response.Builder clearSetId() {
      set_id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'type_id' field */
    public java.lang.CharSequence getTypeId() {
      return type_id;
    }
    
    /** Sets the value of the 'type_id' field */
    public avro.java.gpudb.get_objects_response.Builder setTypeId(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.type_id = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'type_id' field has been set */
    public boolean hasTypeId() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'type_id' field */
    public avro.java.gpudb.get_objects_response.Builder clearTypeId() {
      type_id = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'type_schema' field */
    public java.lang.CharSequence getTypeSchema() {
      return type_schema;
    }
    
    /** Sets the value of the 'type_schema' field */
    public avro.java.gpudb.get_objects_response.Builder setTypeSchema(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.type_schema = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'type_schema' field has been set */
    public boolean hasTypeSchema() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'type_schema' field */
    public avro.java.gpudb.get_objects_response.Builder clearTypeSchema() {
      type_schema = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'list' field */
    public java.util.List<java.nio.ByteBuffer> getList() {
      return list;
    }
    
    /** Sets the value of the 'list' field */
    public avro.java.gpudb.get_objects_response.Builder setList(java.util.List<java.nio.ByteBuffer> value) {
      validate(fields()[3], value);
      this.list = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'list' field has been set */
    public boolean hasList() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'list' field */
    public avro.java.gpudb.get_objects_response.Builder clearList() {
      list = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'list_str' field */
    public java.util.List<java.lang.CharSequence> getListStr() {
      return list_str;
    }
    
    /** Sets the value of the 'list_str' field */
    public avro.java.gpudb.get_objects_response.Builder setListStr(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[4], value);
      this.list_str = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'list_str' field has been set */
    public boolean hasListStr() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'list_str' field */
    public avro.java.gpudb.get_objects_response.Builder clearListStr() {
      list_str = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    @Override
    public get_objects_response build() {
      try {
        get_objects_response record = new get_objects_response();
        record.set_id = fieldSetFlags()[0] ? this.set_id : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.type_id = fieldSetFlags()[1] ? this.type_id : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.type_schema = fieldSetFlags()[2] ? this.type_schema : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.list = fieldSetFlags()[3] ? this.list : (java.util.List<java.nio.ByteBuffer>) defaultValue(fields()[3]);
        record.list_str = fieldSetFlags()[4] ? this.list_str : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[4]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
