/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gaia;  
@SuppressWarnings("all")
public class update_object_response extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"update_object_response\",\"namespace\":\"avro.java.gaia\",\"fields\":[{\"name\":\"status\",\"type\":\"string\"},{\"name\":\"OBJECT_ID\",\"type\":\"string\"}]}");
  @Deprecated public java.lang.CharSequence status;
  @Deprecated public java.lang.CharSequence OBJECT_ID;

  /**
   * Default constructor.
   */
  public update_object_response() {}

  /**
   * All-args constructor.
   */
  public update_object_response(java.lang.CharSequence status, java.lang.CharSequence OBJECT_ID) {
    this.status = status;
    this.OBJECT_ID = OBJECT_ID;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return status;
    case 1: return OBJECT_ID;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: status = (java.lang.CharSequence)value$; break;
    case 1: OBJECT_ID = (java.lang.CharSequence)value$; break;
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

  /**
   * Gets the value of the 'OBJECT_ID' field.
   */
  public java.lang.CharSequence getOBJECTID() {
    return OBJECT_ID;
  }

  /**
   * Sets the value of the 'OBJECT_ID' field.
   * @param value the value to set.
   */
  public void setOBJECTID(java.lang.CharSequence value) {
    this.OBJECT_ID = value;
  }

  /** Creates a new update_object_response RecordBuilder */
  public static avro.java.gaia.update_object_response.Builder newBuilder() {
    return new avro.java.gaia.update_object_response.Builder();
  }
  
  /** Creates a new update_object_response RecordBuilder by copying an existing Builder */
  public static avro.java.gaia.update_object_response.Builder newBuilder(avro.java.gaia.update_object_response.Builder other) {
    return new avro.java.gaia.update_object_response.Builder(other);
  }
  
  /** Creates a new update_object_response RecordBuilder by copying an existing update_object_response instance */
  public static avro.java.gaia.update_object_response.Builder newBuilder(avro.java.gaia.update_object_response other) {
    return new avro.java.gaia.update_object_response.Builder(other);
  }
  
  /**
   * RecordBuilder for update_object_response instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<update_object_response>
    implements org.apache.avro.data.RecordBuilder<update_object_response> {

    private java.lang.CharSequence status;
    private java.lang.CharSequence OBJECT_ID;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gaia.update_object_response.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gaia.update_object_response.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing update_object_response instance */
    private Builder(avro.java.gaia.update_object_response other) {
            super(avro.java.gaia.update_object_response.SCHEMA$);
      if (isValidValue(fields()[0], other.status)) {
        this.status = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.status);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.OBJECT_ID)) {
        this.OBJECT_ID = (java.lang.CharSequence) data().deepCopy(fields()[1].schema(), other.OBJECT_ID);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the 'status' field */
    public java.lang.CharSequence getStatus() {
      return status;
    }
    
    /** Sets the value of the 'status' field */
    public avro.java.gaia.update_object_response.Builder setStatus(java.lang.CharSequence value) {
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
    public avro.java.gaia.update_object_response.Builder clearStatus() {
      status = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'OBJECT_ID' field */
    public java.lang.CharSequence getOBJECTID() {
      return OBJECT_ID;
    }
    
    /** Sets the value of the 'OBJECT_ID' field */
    public avro.java.gaia.update_object_response.Builder setOBJECTID(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.OBJECT_ID = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'OBJECT_ID' field has been set */
    public boolean hasOBJECTID() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'OBJECT_ID' field */
    public avro.java.gaia.update_object_response.Builder clearOBJECTID() {
      OBJECT_ID = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public update_object_response build() {
      try {
        update_object_response record = new update_object_response();
        record.status = fieldSetFlags()[0] ? this.status : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.OBJECT_ID = fieldSetFlags()[1] ? this.OBJECT_ID : (java.lang.CharSequence) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}