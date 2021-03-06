/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gpudb;  
@SuppressWarnings("all")
public class update_set_ttl_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"update_set_ttl_request\",\"namespace\":\"avro.java.gpudb\",\"fields\":[{\"name\":\"set_id\",\"type\":\"string\"},{\"name\":\"ttl\",\"type\":\"int\"}]}");
  @Deprecated public java.lang.CharSequence set_id;
  @Deprecated public int ttl;

  /**
   * Default constructor.
   */
  public update_set_ttl_request() {}

  /**
   * All-args constructor.
   */
  public update_set_ttl_request(java.lang.CharSequence set_id, java.lang.Integer ttl) {
    this.set_id = set_id;
    this.ttl = ttl;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return set_id;
    case 1: return ttl;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: set_id = (java.lang.CharSequence)value$; break;
    case 1: ttl = (java.lang.Integer)value$; break;
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
   * Gets the value of the 'ttl' field.
   */
  public java.lang.Integer getTtl() {
    return ttl;
  }

  /**
   * Sets the value of the 'ttl' field.
   * @param value the value to set.
   */
  public void setTtl(java.lang.Integer value) {
    this.ttl = value;
  }

  /** Creates a new update_set_ttl_request RecordBuilder */
  public static avro.java.gpudb.update_set_ttl_request.Builder newBuilder() {
    return new avro.java.gpudb.update_set_ttl_request.Builder();
  }
  
  /** Creates a new update_set_ttl_request RecordBuilder by copying an existing Builder */
  public static avro.java.gpudb.update_set_ttl_request.Builder newBuilder(avro.java.gpudb.update_set_ttl_request.Builder other) {
    return new avro.java.gpudb.update_set_ttl_request.Builder(other);
  }
  
  /** Creates a new update_set_ttl_request RecordBuilder by copying an existing update_set_ttl_request instance */
  public static avro.java.gpudb.update_set_ttl_request.Builder newBuilder(avro.java.gpudb.update_set_ttl_request other) {
    return new avro.java.gpudb.update_set_ttl_request.Builder(other);
  }
  
  /**
   * RecordBuilder for update_set_ttl_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<update_set_ttl_request>
    implements org.apache.avro.data.RecordBuilder<update_set_ttl_request> {

    private java.lang.CharSequence set_id;
    private int ttl;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gpudb.update_set_ttl_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gpudb.update_set_ttl_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing update_set_ttl_request instance */
    private Builder(avro.java.gpudb.update_set_ttl_request other) {
            super(avro.java.gpudb.update_set_ttl_request.SCHEMA$);
      if (isValidValue(fields()[0], other.set_id)) {
        this.set_id = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.set_id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.ttl)) {
        this.ttl = (java.lang.Integer) data().deepCopy(fields()[1].schema(), other.ttl);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the 'set_id' field */
    public java.lang.CharSequence getSetId() {
      return set_id;
    }
    
    /** Sets the value of the 'set_id' field */
    public avro.java.gpudb.update_set_ttl_request.Builder setSetId(java.lang.CharSequence value) {
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
    public avro.java.gpudb.update_set_ttl_request.Builder clearSetId() {
      set_id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'ttl' field */
    public java.lang.Integer getTtl() {
      return ttl;
    }
    
    /** Sets the value of the 'ttl' field */
    public avro.java.gpudb.update_set_ttl_request.Builder setTtl(int value) {
      validate(fields()[1], value);
      this.ttl = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'ttl' field has been set */
    public boolean hasTtl() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'ttl' field */
    public avro.java.gpudb.update_set_ttl_request.Builder clearTtl() {
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public update_set_ttl_request build() {
      try {
        update_set_ttl_request record = new update_set_ttl_request();
        record.set_id = fieldSetFlags()[0] ? this.set_id : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.ttl = fieldSetFlags()[1] ? this.ttl : (java.lang.Integer) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
