/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gaia;  
@SuppressWarnings("all")
public class register_trigger_nai_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"register_trigger_nai_request\",\"namespace\":\"avro.java.gaia\",\"fields\":[{\"name\":\"request_id\",\"type\":\"string\"},{\"name\":\"set_ids\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"x_attribute\",\"type\":\"string\"},{\"name\":\"x_vector\",\"type\":{\"type\":\"array\",\"items\":\"double\"}},{\"name\":\"y_attribute\",\"type\":\"string\"},{\"name\":\"y_vector\",\"type\":{\"type\":\"array\",\"items\":\"double\"}},{\"name\":\"id_attr\",\"type\":\"string\"}]}");
  @Deprecated public java.lang.CharSequence request_id;
  @Deprecated public java.util.List<java.lang.CharSequence> set_ids;
  @Deprecated public java.lang.CharSequence x_attribute;
  @Deprecated public java.util.List<java.lang.Double> x_vector;
  @Deprecated public java.lang.CharSequence y_attribute;
  @Deprecated public java.util.List<java.lang.Double> y_vector;
  @Deprecated public java.lang.CharSequence id_attr;

  /**
   * Default constructor.
   */
  public register_trigger_nai_request() {}

  /**
   * All-args constructor.
   */
  public register_trigger_nai_request(java.lang.CharSequence request_id, java.util.List<java.lang.CharSequence> set_ids, java.lang.CharSequence x_attribute, java.util.List<java.lang.Double> x_vector, java.lang.CharSequence y_attribute, java.util.List<java.lang.Double> y_vector, java.lang.CharSequence id_attr) {
    this.request_id = request_id;
    this.set_ids = set_ids;
    this.x_attribute = x_attribute;
    this.x_vector = x_vector;
    this.y_attribute = y_attribute;
    this.y_vector = y_vector;
    this.id_attr = id_attr;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return request_id;
    case 1: return set_ids;
    case 2: return x_attribute;
    case 3: return x_vector;
    case 4: return y_attribute;
    case 5: return y_vector;
    case 6: return id_attr;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: request_id = (java.lang.CharSequence)value$; break;
    case 1: set_ids = (java.util.List<java.lang.CharSequence>)value$; break;
    case 2: x_attribute = (java.lang.CharSequence)value$; break;
    case 3: x_vector = (java.util.List<java.lang.Double>)value$; break;
    case 4: y_attribute = (java.lang.CharSequence)value$; break;
    case 5: y_vector = (java.util.List<java.lang.Double>)value$; break;
    case 6: id_attr = (java.lang.CharSequence)value$; break;
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
   * Gets the value of the 'x_attribute' field.
   */
  public java.lang.CharSequence getXAttribute() {
    return x_attribute;
  }

  /**
   * Sets the value of the 'x_attribute' field.
   * @param value the value to set.
   */
  public void setXAttribute(java.lang.CharSequence value) {
    this.x_attribute = value;
  }

  /**
   * Gets the value of the 'x_vector' field.
   */
  public java.util.List<java.lang.Double> getXVector() {
    return x_vector;
  }

  /**
   * Sets the value of the 'x_vector' field.
   * @param value the value to set.
   */
  public void setXVector(java.util.List<java.lang.Double> value) {
    this.x_vector = value;
  }

  /**
   * Gets the value of the 'y_attribute' field.
   */
  public java.lang.CharSequence getYAttribute() {
    return y_attribute;
  }

  /**
   * Sets the value of the 'y_attribute' field.
   * @param value the value to set.
   */
  public void setYAttribute(java.lang.CharSequence value) {
    this.y_attribute = value;
  }

  /**
   * Gets the value of the 'y_vector' field.
   */
  public java.util.List<java.lang.Double> getYVector() {
    return y_vector;
  }

  /**
   * Sets the value of the 'y_vector' field.
   * @param value the value to set.
   */
  public void setYVector(java.util.List<java.lang.Double> value) {
    this.y_vector = value;
  }

  /**
   * Gets the value of the 'id_attr' field.
   */
  public java.lang.CharSequence getIdAttr() {
    return id_attr;
  }

  /**
   * Sets the value of the 'id_attr' field.
   * @param value the value to set.
   */
  public void setIdAttr(java.lang.CharSequence value) {
    this.id_attr = value;
  }

  /** Creates a new register_trigger_nai_request RecordBuilder */
  public static avro.java.gaia.register_trigger_nai_request.Builder newBuilder() {
    return new avro.java.gaia.register_trigger_nai_request.Builder();
  }
  
  /** Creates a new register_trigger_nai_request RecordBuilder by copying an existing Builder */
  public static avro.java.gaia.register_trigger_nai_request.Builder newBuilder(avro.java.gaia.register_trigger_nai_request.Builder other) {
    return new avro.java.gaia.register_trigger_nai_request.Builder(other);
  }
  
  /** Creates a new register_trigger_nai_request RecordBuilder by copying an existing register_trigger_nai_request instance */
  public static avro.java.gaia.register_trigger_nai_request.Builder newBuilder(avro.java.gaia.register_trigger_nai_request other) {
    return new avro.java.gaia.register_trigger_nai_request.Builder(other);
  }
  
  /**
   * RecordBuilder for register_trigger_nai_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<register_trigger_nai_request>
    implements org.apache.avro.data.RecordBuilder<register_trigger_nai_request> {

    private java.lang.CharSequence request_id;
    private java.util.List<java.lang.CharSequence> set_ids;
    private java.lang.CharSequence x_attribute;
    private java.util.List<java.lang.Double> x_vector;
    private java.lang.CharSequence y_attribute;
    private java.util.List<java.lang.Double> y_vector;
    private java.lang.CharSequence id_attr;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gaia.register_trigger_nai_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gaia.register_trigger_nai_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing register_trigger_nai_request instance */
    private Builder(avro.java.gaia.register_trigger_nai_request other) {
            super(avro.java.gaia.register_trigger_nai_request.SCHEMA$);
      if (isValidValue(fields()[0], other.request_id)) {
        this.request_id = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.request_id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.set_ids)) {
        this.set_ids = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[1].schema(), other.set_ids);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.x_attribute)) {
        this.x_attribute = (java.lang.CharSequence) data().deepCopy(fields()[2].schema(), other.x_attribute);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.x_vector)) {
        this.x_vector = (java.util.List<java.lang.Double>) data().deepCopy(fields()[3].schema(), other.x_vector);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.y_attribute)) {
        this.y_attribute = (java.lang.CharSequence) data().deepCopy(fields()[4].schema(), other.y_attribute);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.y_vector)) {
        this.y_vector = (java.util.List<java.lang.Double>) data().deepCopy(fields()[5].schema(), other.y_vector);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.id_attr)) {
        this.id_attr = (java.lang.CharSequence) data().deepCopy(fields()[6].schema(), other.id_attr);
        fieldSetFlags()[6] = true;
      }
    }

    /** Gets the value of the 'request_id' field */
    public java.lang.CharSequence getRequestId() {
      return request_id;
    }
    
    /** Sets the value of the 'request_id' field */
    public avro.java.gaia.register_trigger_nai_request.Builder setRequestId(java.lang.CharSequence value) {
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
    public avro.java.gaia.register_trigger_nai_request.Builder clearRequestId() {
      request_id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'set_ids' field */
    public java.util.List<java.lang.CharSequence> getSetIds() {
      return set_ids;
    }
    
    /** Sets the value of the 'set_ids' field */
    public avro.java.gaia.register_trigger_nai_request.Builder setSetIds(java.util.List<java.lang.CharSequence> value) {
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
    public avro.java.gaia.register_trigger_nai_request.Builder clearSetIds() {
      set_ids = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'x_attribute' field */
    public java.lang.CharSequence getXAttribute() {
      return x_attribute;
    }
    
    /** Sets the value of the 'x_attribute' field */
    public avro.java.gaia.register_trigger_nai_request.Builder setXAttribute(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.x_attribute = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'x_attribute' field has been set */
    public boolean hasXAttribute() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'x_attribute' field */
    public avro.java.gaia.register_trigger_nai_request.Builder clearXAttribute() {
      x_attribute = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'x_vector' field */
    public java.util.List<java.lang.Double> getXVector() {
      return x_vector;
    }
    
    /** Sets the value of the 'x_vector' field */
    public avro.java.gaia.register_trigger_nai_request.Builder setXVector(java.util.List<java.lang.Double> value) {
      validate(fields()[3], value);
      this.x_vector = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'x_vector' field has been set */
    public boolean hasXVector() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'x_vector' field */
    public avro.java.gaia.register_trigger_nai_request.Builder clearXVector() {
      x_vector = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'y_attribute' field */
    public java.lang.CharSequence getYAttribute() {
      return y_attribute;
    }
    
    /** Sets the value of the 'y_attribute' field */
    public avro.java.gaia.register_trigger_nai_request.Builder setYAttribute(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.y_attribute = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'y_attribute' field has been set */
    public boolean hasYAttribute() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'y_attribute' field */
    public avro.java.gaia.register_trigger_nai_request.Builder clearYAttribute() {
      y_attribute = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'y_vector' field */
    public java.util.List<java.lang.Double> getYVector() {
      return y_vector;
    }
    
    /** Sets the value of the 'y_vector' field */
    public avro.java.gaia.register_trigger_nai_request.Builder setYVector(java.util.List<java.lang.Double> value) {
      validate(fields()[5], value);
      this.y_vector = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'y_vector' field has been set */
    public boolean hasYVector() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'y_vector' field */
    public avro.java.gaia.register_trigger_nai_request.Builder clearYVector() {
      y_vector = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'id_attr' field */
    public java.lang.CharSequence getIdAttr() {
      return id_attr;
    }
    
    /** Sets the value of the 'id_attr' field */
    public avro.java.gaia.register_trigger_nai_request.Builder setIdAttr(java.lang.CharSequence value) {
      validate(fields()[6], value);
      this.id_attr = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'id_attr' field has been set */
    public boolean hasIdAttr() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'id_attr' field */
    public avro.java.gaia.register_trigger_nai_request.Builder clearIdAttr() {
      id_attr = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    @Override
    public register_trigger_nai_request build() {
      try {
        register_trigger_nai_request record = new register_trigger_nai_request();
        record.request_id = fieldSetFlags()[0] ? this.request_id : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.set_ids = fieldSetFlags()[1] ? this.set_ids : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[1]);
        record.x_attribute = fieldSetFlags()[2] ? this.x_attribute : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.x_vector = fieldSetFlags()[3] ? this.x_vector : (java.util.List<java.lang.Double>) defaultValue(fields()[3]);
        record.y_attribute = fieldSetFlags()[4] ? this.y_attribute : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.y_vector = fieldSetFlags()[5] ? this.y_vector : (java.util.List<java.lang.Double>) defaultValue(fields()[5]);
        record.id_attr = fieldSetFlags()[6] ? this.id_attr : (java.lang.CharSequence) defaultValue(fields()[6]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
