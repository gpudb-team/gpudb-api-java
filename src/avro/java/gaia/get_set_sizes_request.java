/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gaia;  
@SuppressWarnings("all")
public class get_set_sizes_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"get_set_sizes_request\",\"namespace\":\"avro.java.gaia\",\"fields\":[{\"name\":\"set_ids\",\"type\":{\"type\":\"array\",\"items\":\"string\"}}]}");
  @Deprecated public java.util.List<java.lang.CharSequence> set_ids;

  /**
   * Default constructor.
   */
  public get_set_sizes_request() {}

  /**
   * All-args constructor.
   */
  public get_set_sizes_request(java.util.List<java.lang.CharSequence> set_ids) {
    this.set_ids = set_ids;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return set_ids;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: set_ids = (java.util.List<java.lang.CharSequence>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'set_ids' field.
   */
  public java.util.List<java.lang.CharSequence> getSetIds() {
    return set_ids;
  }

  /**
   * Sets the value of the 'set_ids' field.
   * @param value the value to set.
   */
  public void setSetIds(java.util.List<java.lang.CharSequence> value) {
    this.set_ids = value;
  }

  /** Creates a new get_set_sizes_request RecordBuilder */
  public static avro.java.gaia.get_set_sizes_request.Builder newBuilder() {
    return new avro.java.gaia.get_set_sizes_request.Builder();
  }
  
  /** Creates a new get_set_sizes_request RecordBuilder by copying an existing Builder */
  public static avro.java.gaia.get_set_sizes_request.Builder newBuilder(avro.java.gaia.get_set_sizes_request.Builder other) {
    return new avro.java.gaia.get_set_sizes_request.Builder(other);
  }
  
  /** Creates a new get_set_sizes_request RecordBuilder by copying an existing get_set_sizes_request instance */
  public static avro.java.gaia.get_set_sizes_request.Builder newBuilder(avro.java.gaia.get_set_sizes_request other) {
    return new avro.java.gaia.get_set_sizes_request.Builder(other);
  }
  
  /**
   * RecordBuilder for get_set_sizes_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<get_set_sizes_request>
    implements org.apache.avro.data.RecordBuilder<get_set_sizes_request> {

    private java.util.List<java.lang.CharSequence> set_ids;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gaia.get_set_sizes_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gaia.get_set_sizes_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing get_set_sizes_request instance */
    private Builder(avro.java.gaia.get_set_sizes_request other) {
            super(avro.java.gaia.get_set_sizes_request.SCHEMA$);
      if (isValidValue(fields()[0], other.set_ids)) {
        this.set_ids = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[0].schema(), other.set_ids);
        fieldSetFlags()[0] = true;
      }
    }

    /** Gets the value of the 'set_ids' field */
    public java.util.List<java.lang.CharSequence> getSetIds() {
      return set_ids;
    }
    
    /** Sets the value of the 'set_ids' field */
    public avro.java.gaia.get_set_sizes_request.Builder setSetIds(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[0], value);
      this.set_ids = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'set_ids' field has been set */
    public boolean hasSetIds() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'set_ids' field */
    public avro.java.gaia.get_set_sizes_request.Builder clearSetIds() {
      set_ids = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    public get_set_sizes_request build() {
      try {
        get_set_sizes_request record = new get_set_sizes_request();
        record.set_ids = fieldSetFlags()[0] ? this.set_ids : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[0]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
