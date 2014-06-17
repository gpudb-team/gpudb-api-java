/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gaia;  
@SuppressWarnings("all")
public class filter_by_nai_response extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"filter_by_nai_response\",\"namespace\":\"avro.java.gaia\",\"fields\":[{\"name\":\"count\",\"type\":\"int\"}]}");
  @Deprecated public int count;

  /**
   * Default constructor.
   */
  public filter_by_nai_response() {}

  /**
   * All-args constructor.
   */
  public filter_by_nai_response(java.lang.Integer count) {
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

  /** Creates a new filter_by_nai_response RecordBuilder */
  public static avro.java.gaia.filter_by_nai_response.Builder newBuilder() {
    return new avro.java.gaia.filter_by_nai_response.Builder();
  }
  
  /** Creates a new filter_by_nai_response RecordBuilder by copying an existing Builder */
  public static avro.java.gaia.filter_by_nai_response.Builder newBuilder(avro.java.gaia.filter_by_nai_response.Builder other) {
    return new avro.java.gaia.filter_by_nai_response.Builder(other);
  }
  
  /** Creates a new filter_by_nai_response RecordBuilder by copying an existing filter_by_nai_response instance */
  public static avro.java.gaia.filter_by_nai_response.Builder newBuilder(avro.java.gaia.filter_by_nai_response other) {
    return new avro.java.gaia.filter_by_nai_response.Builder(other);
  }
  
  /**
   * RecordBuilder for filter_by_nai_response instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<filter_by_nai_response>
    implements org.apache.avro.data.RecordBuilder<filter_by_nai_response> {

    private int count;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gaia.filter_by_nai_response.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gaia.filter_by_nai_response.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing filter_by_nai_response instance */
    private Builder(avro.java.gaia.filter_by_nai_response other) {
            super(avro.java.gaia.filter_by_nai_response.SCHEMA$);
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
    public avro.java.gaia.filter_by_nai_response.Builder setCount(int value) {
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
    public avro.java.gaia.filter_by_nai_response.Builder clearCount() {
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    public filter_by_nai_response build() {
      try {
        filter_by_nai_response record = new filter_by_nai_response();
        record.count = fieldSetFlags()[0] ? this.count : (java.lang.Integer) defaultValue(fields()[0]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
