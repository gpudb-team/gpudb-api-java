/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gaia;  
@SuppressWarnings("all")
public class turn_off_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"turn_off_request\",\"namespace\":\"avro.java.gaia\",\"fields\":[{\"name\":\"set_ids\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"group_attribute\",\"type\":\"string\"},{\"name\":\"sort_attribute\",\"type\":\"string\"},{\"name\":\"x_attribute\",\"type\":\"string\"},{\"name\":\"y_attribute\",\"type\":\"string\"},{\"name\":\"threshold\",\"type\":\"double\"},{\"name\":\"user_auth_string\",\"type\":\"string\"}]}");
  @Deprecated public java.util.List<java.lang.CharSequence> set_ids;
  @Deprecated public java.lang.CharSequence group_attribute;
  @Deprecated public java.lang.CharSequence sort_attribute;
  @Deprecated public java.lang.CharSequence x_attribute;
  @Deprecated public java.lang.CharSequence y_attribute;
  @Deprecated public double threshold;
  @Deprecated public java.lang.CharSequence user_auth_string;

  /**
   * Default constructor.
   */
  public turn_off_request() {}

  /**
   * All-args constructor.
   */
  public turn_off_request(java.util.List<java.lang.CharSequence> set_ids, java.lang.CharSequence group_attribute, java.lang.CharSequence sort_attribute, java.lang.CharSequence x_attribute, java.lang.CharSequence y_attribute, java.lang.Double threshold, java.lang.CharSequence user_auth_string) {
    this.set_ids = set_ids;
    this.group_attribute = group_attribute;
    this.sort_attribute = sort_attribute;
    this.x_attribute = x_attribute;
    this.y_attribute = y_attribute;
    this.threshold = threshold;
    this.user_auth_string = user_auth_string;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return set_ids;
    case 1: return group_attribute;
    case 2: return sort_attribute;
    case 3: return x_attribute;
    case 4: return y_attribute;
    case 5: return threshold;
    case 6: return user_auth_string;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: set_ids = (java.util.List<java.lang.CharSequence>)value$; break;
    case 1: group_attribute = (java.lang.CharSequence)value$; break;
    case 2: sort_attribute = (java.lang.CharSequence)value$; break;
    case 3: x_attribute = (java.lang.CharSequence)value$; break;
    case 4: y_attribute = (java.lang.CharSequence)value$; break;
    case 5: threshold = (java.lang.Double)value$; break;
    case 6: user_auth_string = (java.lang.CharSequence)value$; break;
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
   * Gets the value of the 'group_attribute' field.
   */
  public java.lang.CharSequence getGroupAttribute() {
    return group_attribute;
  }

  /**
   * Sets the value of the 'group_attribute' field.
   * @param value the value to set.
   */
  public void setGroupAttribute(java.lang.CharSequence value) {
    this.group_attribute = value;
  }

  /**
   * Gets the value of the 'sort_attribute' field.
   */
  public java.lang.CharSequence getSortAttribute() {
    return sort_attribute;
  }

  /**
   * Sets the value of the 'sort_attribute' field.
   * @param value the value to set.
   */
  public void setSortAttribute(java.lang.CharSequence value) {
    this.sort_attribute = value;
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
   * Gets the value of the 'threshold' field.
   */
  public java.lang.Double getThreshold() {
    return threshold;
  }

  /**
   * Sets the value of the 'threshold' field.
   * @param value the value to set.
   */
  public void setThreshold(java.lang.Double value) {
    this.threshold = value;
  }

  /**
   * Gets the value of the 'user_auth_string' field.
   */
  public java.lang.CharSequence getUserAuthString() {
    return user_auth_string;
  }

  /**
   * Sets the value of the 'user_auth_string' field.
   * @param value the value to set.
   */
  public void setUserAuthString(java.lang.CharSequence value) {
    this.user_auth_string = value;
  }

  /** Creates a new turn_off_request RecordBuilder */
  public static avro.java.gaia.turn_off_request.Builder newBuilder() {
    return new avro.java.gaia.turn_off_request.Builder();
  }
  
  /** Creates a new turn_off_request RecordBuilder by copying an existing Builder */
  public static avro.java.gaia.turn_off_request.Builder newBuilder(avro.java.gaia.turn_off_request.Builder other) {
    return new avro.java.gaia.turn_off_request.Builder(other);
  }
  
  /** Creates a new turn_off_request RecordBuilder by copying an existing turn_off_request instance */
  public static avro.java.gaia.turn_off_request.Builder newBuilder(avro.java.gaia.turn_off_request other) {
    return new avro.java.gaia.turn_off_request.Builder(other);
  }
  
  /**
   * RecordBuilder for turn_off_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<turn_off_request>
    implements org.apache.avro.data.RecordBuilder<turn_off_request> {

    private java.util.List<java.lang.CharSequence> set_ids;
    private java.lang.CharSequence group_attribute;
    private java.lang.CharSequence sort_attribute;
    private java.lang.CharSequence x_attribute;
    private java.lang.CharSequence y_attribute;
    private double threshold;
    private java.lang.CharSequence user_auth_string;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gaia.turn_off_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gaia.turn_off_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing turn_off_request instance */
    private Builder(avro.java.gaia.turn_off_request other) {
            super(avro.java.gaia.turn_off_request.SCHEMA$);
      if (isValidValue(fields()[0], other.set_ids)) {
        this.set_ids = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[0].schema(), other.set_ids);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.group_attribute)) {
        this.group_attribute = (java.lang.CharSequence) data().deepCopy(fields()[1].schema(), other.group_attribute);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.sort_attribute)) {
        this.sort_attribute = (java.lang.CharSequence) data().deepCopy(fields()[2].schema(), other.sort_attribute);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.x_attribute)) {
        this.x_attribute = (java.lang.CharSequence) data().deepCopy(fields()[3].schema(), other.x_attribute);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.y_attribute)) {
        this.y_attribute = (java.lang.CharSequence) data().deepCopy(fields()[4].schema(), other.y_attribute);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.threshold)) {
        this.threshold = (java.lang.Double) data().deepCopy(fields()[5].schema(), other.threshold);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.user_auth_string)) {
        this.user_auth_string = (java.lang.CharSequence) data().deepCopy(fields()[6].schema(), other.user_auth_string);
        fieldSetFlags()[6] = true;
      }
    }

    /** Gets the value of the 'set_ids' field */
    public java.util.List<java.lang.CharSequence> getSetIds() {
      return set_ids;
    }
    
    /** Sets the value of the 'set_ids' field */
    public avro.java.gaia.turn_off_request.Builder setSetIds(java.util.List<java.lang.CharSequence> value) {
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
    public avro.java.gaia.turn_off_request.Builder clearSetIds() {
      set_ids = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'group_attribute' field */
    public java.lang.CharSequence getGroupAttribute() {
      return group_attribute;
    }
    
    /** Sets the value of the 'group_attribute' field */
    public avro.java.gaia.turn_off_request.Builder setGroupAttribute(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.group_attribute = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'group_attribute' field has been set */
    public boolean hasGroupAttribute() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'group_attribute' field */
    public avro.java.gaia.turn_off_request.Builder clearGroupAttribute() {
      group_attribute = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'sort_attribute' field */
    public java.lang.CharSequence getSortAttribute() {
      return sort_attribute;
    }
    
    /** Sets the value of the 'sort_attribute' field */
    public avro.java.gaia.turn_off_request.Builder setSortAttribute(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.sort_attribute = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'sort_attribute' field has been set */
    public boolean hasSortAttribute() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'sort_attribute' field */
    public avro.java.gaia.turn_off_request.Builder clearSortAttribute() {
      sort_attribute = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'x_attribute' field */
    public java.lang.CharSequence getXAttribute() {
      return x_attribute;
    }
    
    /** Sets the value of the 'x_attribute' field */
    public avro.java.gaia.turn_off_request.Builder setXAttribute(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.x_attribute = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'x_attribute' field has been set */
    public boolean hasXAttribute() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'x_attribute' field */
    public avro.java.gaia.turn_off_request.Builder clearXAttribute() {
      x_attribute = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'y_attribute' field */
    public java.lang.CharSequence getYAttribute() {
      return y_attribute;
    }
    
    /** Sets the value of the 'y_attribute' field */
    public avro.java.gaia.turn_off_request.Builder setYAttribute(java.lang.CharSequence value) {
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
    public avro.java.gaia.turn_off_request.Builder clearYAttribute() {
      y_attribute = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'threshold' field */
    public java.lang.Double getThreshold() {
      return threshold;
    }
    
    /** Sets the value of the 'threshold' field */
    public avro.java.gaia.turn_off_request.Builder setThreshold(double value) {
      validate(fields()[5], value);
      this.threshold = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'threshold' field has been set */
    public boolean hasThreshold() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'threshold' field */
    public avro.java.gaia.turn_off_request.Builder clearThreshold() {
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'user_auth_string' field */
    public java.lang.CharSequence getUserAuthString() {
      return user_auth_string;
    }
    
    /** Sets the value of the 'user_auth_string' field */
    public avro.java.gaia.turn_off_request.Builder setUserAuthString(java.lang.CharSequence value) {
      validate(fields()[6], value);
      this.user_auth_string = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'user_auth_string' field has been set */
    public boolean hasUserAuthString() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'user_auth_string' field */
    public avro.java.gaia.turn_off_request.Builder clearUserAuthString() {
      user_auth_string = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    @Override
    public turn_off_request build() {
      try {
        turn_off_request record = new turn_off_request();
        record.set_ids = fieldSetFlags()[0] ? this.set_ids : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[0]);
        record.group_attribute = fieldSetFlags()[1] ? this.group_attribute : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.sort_attribute = fieldSetFlags()[2] ? this.sort_attribute : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.x_attribute = fieldSetFlags()[3] ? this.x_attribute : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.y_attribute = fieldSetFlags()[4] ? this.y_attribute : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.threshold = fieldSetFlags()[5] ? this.threshold : (java.lang.Double) defaultValue(fields()[5]);
        record.user_auth_string = fieldSetFlags()[6] ? this.user_auth_string : (java.lang.CharSequence) defaultValue(fields()[6]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
