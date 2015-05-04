/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gpudb;  
@SuppressWarnings("all")
public class sort_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"sort_request\",\"namespace\":\"avro.java.gpudb\",\"fields\":[{\"name\":\"attribute\",\"type\":\"string\"},{\"name\":\"set_id\",\"type\":\"string\"}]}");
  @Deprecated public java.lang.CharSequence attribute;
  @Deprecated public java.lang.CharSequence set_id;

  /**
   * Default constructor.
   */
  public sort_request() {}

  /**
   * All-args constructor.
   */
  public sort_request(java.lang.CharSequence attribute, java.lang.CharSequence set_id) {
    this.attribute = attribute;
    this.set_id = set_id;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return attribute;
    case 1: return set_id;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: attribute = (java.lang.CharSequence)value$; break;
    case 1: set_id = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'attribute' field.
   */
  public java.lang.CharSequence getAttribute() {
    return attribute;
  }

  /**
   * Sets the value of the 'attribute' field.
   * @param value the value to set.
   */
  public void setAttribute(java.lang.CharSequence value) {
    this.attribute = value;
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

  /** Creates a new sort_request RecordBuilder */
  public static avro.java.gpudb.sort_request.Builder newBuilder() {
    return new avro.java.gpudb.sort_request.Builder();
  }
  
  /** Creates a new sort_request RecordBuilder by copying an existing Builder */
  public static avro.java.gpudb.sort_request.Builder newBuilder(avro.java.gpudb.sort_request.Builder other) {
    return new avro.java.gpudb.sort_request.Builder(other);
  }
  
  /** Creates a new sort_request RecordBuilder by copying an existing sort_request instance */
  public static avro.java.gpudb.sort_request.Builder newBuilder(avro.java.gpudb.sort_request other) {
    return new avro.java.gpudb.sort_request.Builder(other);
  }
  
  /**
   * RecordBuilder for sort_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<sort_request>
    implements org.apache.avro.data.RecordBuilder<sort_request> {

    private java.lang.CharSequence attribute;
    private java.lang.CharSequence set_id;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gpudb.sort_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gpudb.sort_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing sort_request instance */
    private Builder(avro.java.gpudb.sort_request other) {
            super(avro.java.gpudb.sort_request.SCHEMA$);
      if (isValidValue(fields()[0], other.attribute)) {
        this.attribute = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.attribute);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.set_id)) {
        this.set_id = (java.lang.CharSequence) data().deepCopy(fields()[1].schema(), other.set_id);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the 'attribute' field */
    public java.lang.CharSequence getAttribute() {
      return attribute;
    }
    
    /** Sets the value of the 'attribute' field */
    public avro.java.gpudb.sort_request.Builder setAttribute(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.attribute = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'attribute' field has been set */
    public boolean hasAttribute() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'attribute' field */
    public avro.java.gpudb.sort_request.Builder clearAttribute() {
      attribute = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'set_id' field */
    public java.lang.CharSequence getSetId() {
      return set_id;
    }
    
    /** Sets the value of the 'set_id' field */
    public avro.java.gpudb.sort_request.Builder setSetId(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.set_id = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'set_id' field has been set */
    public boolean hasSetId() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'set_id' field */
    public avro.java.gpudb.sort_request.Builder clearSetId() {
      set_id = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public sort_request build() {
      try {
        sort_request record = new sort_request();
        record.attribute = fieldSetFlags()[0] ? this.attribute : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.set_id = fieldSetFlags()[1] ? this.set_id : (java.lang.CharSequence) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
