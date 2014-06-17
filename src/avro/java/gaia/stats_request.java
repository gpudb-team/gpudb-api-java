/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gaia;  
@SuppressWarnings("all")
public class stats_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"stats_request\",\"namespace\":\"avro.java.gaia\",\"fields\":[{\"name\":\"set_id\",\"type\":\"string\"}]}");
  @Deprecated public java.lang.CharSequence set_id;

  /**
   * Default constructor.
   */
  public stats_request() {}

  /**
   * All-args constructor.
   */
  public stats_request(java.lang.CharSequence set_id) {
    this.set_id = set_id;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return set_id;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: set_id = (java.lang.CharSequence)value$; break;
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

  /** Creates a new stats_request RecordBuilder */
  public static avro.java.gaia.stats_request.Builder newBuilder() {
    return new avro.java.gaia.stats_request.Builder();
  }
  
  /** Creates a new stats_request RecordBuilder by copying an existing Builder */
  public static avro.java.gaia.stats_request.Builder newBuilder(avro.java.gaia.stats_request.Builder other) {
    return new avro.java.gaia.stats_request.Builder(other);
  }
  
  /** Creates a new stats_request RecordBuilder by copying an existing stats_request instance */
  public static avro.java.gaia.stats_request.Builder newBuilder(avro.java.gaia.stats_request other) {
    return new avro.java.gaia.stats_request.Builder(other);
  }
  
  /**
   * RecordBuilder for stats_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<stats_request>
    implements org.apache.avro.data.RecordBuilder<stats_request> {

    private java.lang.CharSequence set_id;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gaia.stats_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gaia.stats_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing stats_request instance */
    private Builder(avro.java.gaia.stats_request other) {
            super(avro.java.gaia.stats_request.SCHEMA$);
      if (isValidValue(fields()[0], other.set_id)) {
        this.set_id = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.set_id);
        fieldSetFlags()[0] = true;
      }
    }

    /** Gets the value of the 'set_id' field */
    public java.lang.CharSequence getSetId() {
      return set_id;
    }
    
    /** Sets the value of the 'set_id' field */
    public avro.java.gaia.stats_request.Builder setSetId(java.lang.CharSequence value) {
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
    public avro.java.gaia.stats_request.Builder clearSetId() {
      set_id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    public stats_request build() {
      try {
        stats_request record = new stats_request();
        record.set_id = fieldSetFlags()[0] ? this.set_id : (java.lang.CharSequence) defaultValue(fields()[0]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
