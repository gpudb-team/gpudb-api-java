/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gpudb;  
@SuppressWarnings("all")
public class register_trigger_range_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"register_trigger_range_request\",\"namespace\":\"avro.java.gpudb\",\"fields\":[{\"name\":\"request_id\",\"type\":\"string\"},{\"name\":\"set_ids\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"attr\",\"type\":\"string\"},{\"name\":\"lowest\",\"type\":\"double\"},{\"name\":\"highest\",\"type\":\"double\"}]}");
  @Deprecated public java.lang.CharSequence request_id;
  @Deprecated public java.util.List<java.lang.CharSequence> set_ids;
  @Deprecated public java.lang.CharSequence attr;
  @Deprecated public double lowest;
  @Deprecated public double highest;

  /**
   * Default constructor.
   */
  public register_trigger_range_request() {}

  /**
   * All-args constructor.
   */
  public register_trigger_range_request(java.lang.CharSequence request_id, java.util.List<java.lang.CharSequence> set_ids, java.lang.CharSequence attr, java.lang.Double lowest, java.lang.Double highest) {
    this.request_id = request_id;
    this.set_ids = set_ids;
    this.attr = attr;
    this.lowest = lowest;
    this.highest = highest;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return request_id;
    case 1: return set_ids;
    case 2: return attr;
    case 3: return lowest;
    case 4: return highest;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: request_id = (java.lang.CharSequence)value$; break;
    case 1: set_ids = (java.util.List<java.lang.CharSequence>)value$; break;
    case 2: attr = (java.lang.CharSequence)value$; break;
    case 3: lowest = (java.lang.Double)value$; break;
    case 4: highest = (java.lang.Double)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'request_id' field.
   */
  public java.lang.CharSequence getRequestId() {
    return request_id;
  }

  /**
   * Sets the value of the 'request_id' field.
   * @param value the value to set.
   */
  public void setRequestId(java.lang.CharSequence value) {
    this.request_id = value;
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
   * Gets the value of the 'attr' field.
   */
  public java.lang.CharSequence getAttr() {
    return attr;
  }

  /**
   * Sets the value of the 'attr' field.
   * @param value the value to set.
   */
  public void setAttr(java.lang.CharSequence value) {
    this.attr = value;
  }

  /**
   * Gets the value of the 'lowest' field.
   */
  public java.lang.Double getLowest() {
    return lowest;
  }

  /**
   * Sets the value of the 'lowest' field.
   * @param value the value to set.
   */
  public void setLowest(java.lang.Double value) {
    this.lowest = value;
  }

  /**
   * Gets the value of the 'highest' field.
   */
  public java.lang.Double getHighest() {
    return highest;
  }

  /**
   * Sets the value of the 'highest' field.
   * @param value the value to set.
   */
  public void setHighest(java.lang.Double value) {
    this.highest = value;
  }

  /** Creates a new register_trigger_range_request RecordBuilder */
  public static avro.java.gpudb.register_trigger_range_request.Builder newBuilder() {
    return new avro.java.gpudb.register_trigger_range_request.Builder();
  }
  
  /** Creates a new register_trigger_range_request RecordBuilder by copying an existing Builder */
  public static avro.java.gpudb.register_trigger_range_request.Builder newBuilder(avro.java.gpudb.register_trigger_range_request.Builder other) {
    return new avro.java.gpudb.register_trigger_range_request.Builder(other);
  }
  
  /** Creates a new register_trigger_range_request RecordBuilder by copying an existing register_trigger_range_request instance */
  public static avro.java.gpudb.register_trigger_range_request.Builder newBuilder(avro.java.gpudb.register_trigger_range_request other) {
    return new avro.java.gpudb.register_trigger_range_request.Builder(other);
  }
  
  /**
   * RecordBuilder for register_trigger_range_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<register_trigger_range_request>
    implements org.apache.avro.data.RecordBuilder<register_trigger_range_request> {

    private java.lang.CharSequence request_id;
    private java.util.List<java.lang.CharSequence> set_ids;
    private java.lang.CharSequence attr;
    private double lowest;
    private double highest;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gpudb.register_trigger_range_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gpudb.register_trigger_range_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing register_trigger_range_request instance */
    private Builder(avro.java.gpudb.register_trigger_range_request other) {
            super(avro.java.gpudb.register_trigger_range_request.SCHEMA$);
      if (isValidValue(fields()[0], other.request_id)) {
        this.request_id = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.request_id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.set_ids)) {
        this.set_ids = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[1].schema(), other.set_ids);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.attr)) {
        this.attr = (java.lang.CharSequence) data().deepCopy(fields()[2].schema(), other.attr);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.lowest)) {
        this.lowest = (java.lang.Double) data().deepCopy(fields()[3].schema(), other.lowest);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.highest)) {
        this.highest = (java.lang.Double) data().deepCopy(fields()[4].schema(), other.highest);
        fieldSetFlags()[4] = true;
      }
    }

    /** Gets the value of the 'request_id' field */
    public java.lang.CharSequence getRequestId() {
      return request_id;
    }
    
    /** Sets the value of the 'request_id' field */
    public avro.java.gpudb.register_trigger_range_request.Builder setRequestId(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.request_id = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'request_id' field has been set */
    public boolean hasRequestId() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'request_id' field */
    public avro.java.gpudb.register_trigger_range_request.Builder clearRequestId() {
      request_id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'set_ids' field */
    public java.util.List<java.lang.CharSequence> getSetIds() {
      return set_ids;
    }
    
    /** Sets the value of the 'set_ids' field */
    public avro.java.gpudb.register_trigger_range_request.Builder setSetIds(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[1], value);
      this.set_ids = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'set_ids' field has been set */
    public boolean hasSetIds() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'set_ids' field */
    public avro.java.gpudb.register_trigger_range_request.Builder clearSetIds() {
      set_ids = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'attr' field */
    public java.lang.CharSequence getAttr() {
      return attr;
    }
    
    /** Sets the value of the 'attr' field */
    public avro.java.gpudb.register_trigger_range_request.Builder setAttr(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.attr = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'attr' field has been set */
    public boolean hasAttr() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'attr' field */
    public avro.java.gpudb.register_trigger_range_request.Builder clearAttr() {
      attr = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'lowest' field */
    public java.lang.Double getLowest() {
      return lowest;
    }
    
    /** Sets the value of the 'lowest' field */
    public avro.java.gpudb.register_trigger_range_request.Builder setLowest(double value) {
      validate(fields()[3], value);
      this.lowest = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'lowest' field has been set */
    public boolean hasLowest() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'lowest' field */
    public avro.java.gpudb.register_trigger_range_request.Builder clearLowest() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'highest' field */
    public java.lang.Double getHighest() {
      return highest;
    }
    
    /** Sets the value of the 'highest' field */
    public avro.java.gpudb.register_trigger_range_request.Builder setHighest(double value) {
      validate(fields()[4], value);
      this.highest = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'highest' field has been set */
    public boolean hasHighest() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'highest' field */
    public avro.java.gpudb.register_trigger_range_request.Builder clearHighest() {
      fieldSetFlags()[4] = false;
      return this;
    }

    @Override
    public register_trigger_range_request build() {
      try {
        register_trigger_range_request record = new register_trigger_range_request();
        record.request_id = fieldSetFlags()[0] ? this.request_id : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.set_ids = fieldSetFlags()[1] ? this.set_ids : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[1]);
        record.attr = fieldSetFlags()[2] ? this.attr : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.lowest = fieldSetFlags()[3] ? this.lowest : (java.lang.Double) defaultValue(fields()[3]);
        record.highest = fieldSetFlags()[4] ? this.highest : (java.lang.Double) defaultValue(fields()[4]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
