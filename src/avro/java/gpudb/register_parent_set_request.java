/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gpudb;  
@SuppressWarnings("all")
public class register_parent_set_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"register_parent_set_request\",\"namespace\":\"avro.java.gpudb\",\"fields\":[{\"name\":\"set_id\",\"type\":\"string\"},{\"name\":\"allow_duplicate_children\",\"type\":\"boolean\"}]}");
  @Deprecated public java.lang.CharSequence set_id;
  @Deprecated public boolean allow_duplicate_children;

  /**
   * Default constructor.
   */
  public register_parent_set_request() {}

  /**
   * All-args constructor.
   */
  public register_parent_set_request(java.lang.CharSequence set_id, java.lang.Boolean allow_duplicate_children) {
    this.set_id = set_id;
    this.allow_duplicate_children = allow_duplicate_children;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return set_id;
    case 1: return allow_duplicate_children;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: set_id = (java.lang.CharSequence)value$; break;
    case 1: allow_duplicate_children = (java.lang.Boolean)value$; break;
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
   * Gets the value of the 'allow_duplicate_children' field.
   */
  public java.lang.Boolean getAllowDuplicateChildren() {
    return allow_duplicate_children;
  }

  /**
   * Sets the value of the 'allow_duplicate_children' field.
   * @param value the value to set.
   */
  public void setAllowDuplicateChildren(java.lang.Boolean value) {
    this.allow_duplicate_children = value;
  }

  /** Creates a new register_parent_set_request RecordBuilder */
  public static avro.java.gpudb.register_parent_set_request.Builder newBuilder() {
    return new avro.java.gpudb.register_parent_set_request.Builder();
  }
  
  /** Creates a new register_parent_set_request RecordBuilder by copying an existing Builder */
  public static avro.java.gpudb.register_parent_set_request.Builder newBuilder(avro.java.gpudb.register_parent_set_request.Builder other) {
    return new avro.java.gpudb.register_parent_set_request.Builder(other);
  }
  
  /** Creates a new register_parent_set_request RecordBuilder by copying an existing register_parent_set_request instance */
  public static avro.java.gpudb.register_parent_set_request.Builder newBuilder(avro.java.gpudb.register_parent_set_request other) {
    return new avro.java.gpudb.register_parent_set_request.Builder(other);
  }
  
  /**
   * RecordBuilder for register_parent_set_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<register_parent_set_request>
    implements org.apache.avro.data.RecordBuilder<register_parent_set_request> {

    private java.lang.CharSequence set_id;
    private boolean allow_duplicate_children;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gpudb.register_parent_set_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gpudb.register_parent_set_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing register_parent_set_request instance */
    private Builder(avro.java.gpudb.register_parent_set_request other) {
            super(avro.java.gpudb.register_parent_set_request.SCHEMA$);
      if (isValidValue(fields()[0], other.set_id)) {
        this.set_id = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.set_id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.allow_duplicate_children)) {
        this.allow_duplicate_children = (java.lang.Boolean) data().deepCopy(fields()[1].schema(), other.allow_duplicate_children);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the 'set_id' field */
    public java.lang.CharSequence getSetId() {
      return set_id;
    }
    
    /** Sets the value of the 'set_id' field */
    public avro.java.gpudb.register_parent_set_request.Builder setSetId(java.lang.CharSequence value) {
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
    public avro.java.gpudb.register_parent_set_request.Builder clearSetId() {
      set_id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'allow_duplicate_children' field */
    public java.lang.Boolean getAllowDuplicateChildren() {
      return allow_duplicate_children;
    }
    
    /** Sets the value of the 'allow_duplicate_children' field */
    public avro.java.gpudb.register_parent_set_request.Builder setAllowDuplicateChildren(boolean value) {
      validate(fields()[1], value);
      this.allow_duplicate_children = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'allow_duplicate_children' field has been set */
    public boolean hasAllowDuplicateChildren() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'allow_duplicate_children' field */
    public avro.java.gpudb.register_parent_set_request.Builder clearAllowDuplicateChildren() {
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public register_parent_set_request build() {
      try {
        register_parent_set_request record = new register_parent_set_request();
        record.set_id = fieldSetFlags()[0] ? this.set_id : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.allow_duplicate_children = fieldSetFlags()[1] ? this.allow_duplicate_children : (java.lang.Boolean) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
