/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gaia;  
@SuppressWarnings("all")
public class join_response extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"join_response\",\"namespace\":\"avro.java.gaia\",\"fields\":[{\"name\":\"count\",\"type\":\"int\"}]}");
  @Deprecated public int count;

  /**
   * Default constructor.
   */
  public join_response() {}

  /**
   * All-args constructor.
   */
  public join_response(java.lang.Integer count) {
    this.count = count;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return count;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: count = (java.lang.Integer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'count' field.
   */
  public java.lang.Integer getCount() {
    return count;
  }

  /**
   * Sets the value of the 'count' field.
   * @param value the value to set.
   */
  public void setCount(java.lang.Integer value) {
    this.count = value;
  }

  /** Creates a new join_response RecordBuilder */
  public static avro.java.gaia.join_response.Builder newBuilder() {
    return new avro.java.gaia.join_response.Builder();
  }
  
  /** Creates a new join_response RecordBuilder by copying an existing Builder */
  public static avro.java.gaia.join_response.Builder newBuilder(avro.java.gaia.join_response.Builder other) {
    return new avro.java.gaia.join_response.Builder(other);
  }
  
  /** Creates a new join_response RecordBuilder by copying an existing join_response instance */
  public static avro.java.gaia.join_response.Builder newBuilder(avro.java.gaia.join_response other) {
    return new avro.java.gaia.join_response.Builder(other);
  }
  
  /**
   * RecordBuilder for join_response instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<join_response>
    implements org.apache.avro.data.RecordBuilder<join_response> {

    private int count;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gaia.join_response.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gaia.join_response.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing join_response instance */
    private Builder(avro.java.gaia.join_response other) {
            super(avro.java.gaia.join_response.SCHEMA$);
      if (isValidValue(fields()[0], other.count)) {
        this.count = (java.lang.Integer) data().deepCopy(fields()[0].schema(), other.count);
        fieldSetFlags()[0] = true;
      }
    }

    /** Gets the value of the 'count' field */
    public java.lang.Integer getCount() {
      return count;
    }
    
    /** Sets the value of the 'count' field */
    public avro.java.gaia.join_response.Builder setCount(int value) {
      validate(fields()[0], value);
      this.count = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'count' field has been set */
    public boolean hasCount() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'count' field */
    public avro.java.gaia.join_response.Builder clearCount() {
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    public join_response build() {
      try {
        join_response record = new join_response();
        record.count = fieldSetFlags()[0] ? this.count : (java.lang.Integer) defaultValue(fields()[0]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
