/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gpudb;  
@SuppressWarnings("all")
public class index_response extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"index_response\",\"namespace\":\"avro.java.gpudb\",\"fields\":[{\"name\":\"status\",\"type\":\"string\"}]}");
  @Deprecated public java.lang.CharSequence status;

  /**
   * Default constructor.
   */
  public index_response() {}

  /**
   * All-args constructor.
   */
  public index_response(java.lang.CharSequence status) {
    this.status = status;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return status;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: status = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'status' field.
   */
  public java.lang.CharSequence getStatus() {
    return status;
  }

  /**
   * Sets the value of the 'status' field.
   * @param value the value to set.
   */
  public void setStatus(java.lang.CharSequence value) {
    this.status = value;
  }

  /** Creates a new index_response RecordBuilder */
  public static avro.java.gpudb.index_response.Builder newBuilder() {
    return new avro.java.gpudb.index_response.Builder();
  }
  
  /** Creates a new index_response RecordBuilder by copying an existing Builder */
  public static avro.java.gpudb.index_response.Builder newBuilder(avro.java.gpudb.index_response.Builder other) {
    return new avro.java.gpudb.index_response.Builder(other);
  }
  
  /** Creates a new index_response RecordBuilder by copying an existing index_response instance */
  public static avro.java.gpudb.index_response.Builder newBuilder(avro.java.gpudb.index_response other) {
    return new avro.java.gpudb.index_response.Builder(other);
  }
  
  /**
   * RecordBuilder for index_response instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<index_response>
    implements org.apache.avro.data.RecordBuilder<index_response> {

    private java.lang.CharSequence status;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gpudb.index_response.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gpudb.index_response.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing index_response instance */
    private Builder(avro.java.gpudb.index_response other) {
            super(avro.java.gpudb.index_response.SCHEMA$);
      if (isValidValue(fields()[0], other.status)) {
        this.status = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.status);
        fieldSetFlags()[0] = true;
      }
    }

    /** Gets the value of the 'status' field */
    public java.lang.CharSequence getStatus() {
      return status;
    }
    
    /** Sets the value of the 'status' field */
    public avro.java.gpudb.index_response.Builder setStatus(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.status = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'status' field has been set */
    public boolean hasStatus() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'status' field */
    public avro.java.gpudb.index_response.Builder clearStatus() {
      status = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    public index_response build() {
      try {
        index_response record = new index_response();
        record.status = fieldSetFlags()[0] ? this.status : (java.lang.CharSequence) defaultValue(fields()[0]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
