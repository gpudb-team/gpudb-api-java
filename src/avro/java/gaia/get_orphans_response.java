/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gaia;  
@SuppressWarnings("all")
public class get_orphans_response extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"get_orphans_response\",\"namespace\":\"avro.java.gaia\",\"fields\":[{\"name\":\"set_ids\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"is_parent\",\"type\":{\"type\":\"array\",\"items\":\"boolean\"}}]}");
  @Deprecated public java.util.List<java.lang.CharSequence> set_ids;
  @Deprecated public java.util.List<java.lang.Boolean> is_parent;

  /**
   * Default constructor.
   */
  public get_orphans_response() {}

  /**
   * All-args constructor.
   */
  public get_orphans_response(java.util.List<java.lang.CharSequence> set_ids, java.util.List<java.lang.Boolean> is_parent) {
    this.set_ids = set_ids;
    this.is_parent = is_parent;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return set_ids;
    case 1: return is_parent;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: set_ids = (java.util.List<java.lang.CharSequence>)value$; break;
    case 1: is_parent = (java.util.List<java.lang.Boolean>)value$; break;
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

  /**
   * Gets the value of the 'is_parent' field.
   */
  public java.util.List<java.lang.Boolean> getIsParent() {
    return is_parent;
  }

  /**
   * Sets the value of the 'is_parent' field.
   * @param value the value to set.
   */
  public void setIsParent(java.util.List<java.lang.Boolean> value) {
    this.is_parent = value;
  }

  /** Creates a new get_orphans_response RecordBuilder */
  public static avro.java.gaia.get_orphans_response.Builder newBuilder() {
    return new avro.java.gaia.get_orphans_response.Builder();
  }
  
  /** Creates a new get_orphans_response RecordBuilder by copying an existing Builder */
  public static avro.java.gaia.get_orphans_response.Builder newBuilder(avro.java.gaia.get_orphans_response.Builder other) {
    return new avro.java.gaia.get_orphans_response.Builder(other);
  }
  
  /** Creates a new get_orphans_response RecordBuilder by copying an existing get_orphans_response instance */
  public static avro.java.gaia.get_orphans_response.Builder newBuilder(avro.java.gaia.get_orphans_response other) {
    return new avro.java.gaia.get_orphans_response.Builder(other);
  }
  
  /**
   * RecordBuilder for get_orphans_response instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<get_orphans_response>
    implements org.apache.avro.data.RecordBuilder<get_orphans_response> {

    private java.util.List<java.lang.CharSequence> set_ids;
    private java.util.List<java.lang.Boolean> is_parent;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gaia.get_orphans_response.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gaia.get_orphans_response.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing get_orphans_response instance */
    private Builder(avro.java.gaia.get_orphans_response other) {
            super(avro.java.gaia.get_orphans_response.SCHEMA$);
      if (isValidValue(fields()[0], other.set_ids)) {
        this.set_ids = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[0].schema(), other.set_ids);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.is_parent)) {
        this.is_parent = (java.util.List<java.lang.Boolean>) data().deepCopy(fields()[1].schema(), other.is_parent);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the 'set_ids' field */
    public java.util.List<java.lang.CharSequence> getSetIds() {
      return set_ids;
    }
    
    /** Sets the value of the 'set_ids' field */
    public avro.java.gaia.get_orphans_response.Builder setSetIds(java.util.List<java.lang.CharSequence> value) {
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
    public avro.java.gaia.get_orphans_response.Builder clearSetIds() {
      set_ids = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'is_parent' field */
    public java.util.List<java.lang.Boolean> getIsParent() {
      return is_parent;
    }
    
    /** Sets the value of the 'is_parent' field */
    public avro.java.gaia.get_orphans_response.Builder setIsParent(java.util.List<java.lang.Boolean> value) {
      validate(fields()[1], value);
      this.is_parent = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'is_parent' field has been set */
    public boolean hasIsParent() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'is_parent' field */
    public avro.java.gaia.get_orphans_response.Builder clearIsParent() {
      is_parent = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public get_orphans_response build() {
      try {
        get_orphans_response record = new get_orphans_response();
        record.set_ids = fieldSetFlags()[0] ? this.set_ids : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[0]);
        record.is_parent = fieldSetFlags()[1] ? this.is_parent : (java.util.List<java.lang.Boolean>) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
