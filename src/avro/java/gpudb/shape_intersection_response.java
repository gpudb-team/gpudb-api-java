/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gpudb;  
@SuppressWarnings("all")
public class shape_intersection_response extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"shape_intersection_response\",\"namespace\":\"avro.java.gpudb\",\"fields\":[{\"name\":\"wkt_strings_1\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"group_ids_1\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"set_ids_1\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"wkt_strings_2\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"group_ids_2\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"set_ids_2\",\"type\":{\"type\":\"array\",\"items\":\"string\"}}]}");
  @Deprecated public java.util.List<java.lang.CharSequence> wkt_strings_1;
  @Deprecated public java.util.List<java.lang.CharSequence> group_ids_1;
  @Deprecated public java.util.List<java.lang.CharSequence> set_ids_1;
  @Deprecated public java.util.List<java.lang.CharSequence> wkt_strings_2;
  @Deprecated public java.util.List<java.lang.CharSequence> group_ids_2;
  @Deprecated public java.util.List<java.lang.CharSequence> set_ids_2;

  /**
   * Default constructor.
   */
  public shape_intersection_response() {}

  /**
   * All-args constructor.
   */
  public shape_intersection_response(java.util.List<java.lang.CharSequence> wkt_strings_1, java.util.List<java.lang.CharSequence> group_ids_1, java.util.List<java.lang.CharSequence> set_ids_1, java.util.List<java.lang.CharSequence> wkt_strings_2, java.util.List<java.lang.CharSequence> group_ids_2, java.util.List<java.lang.CharSequence> set_ids_2) {
    this.wkt_strings_1 = wkt_strings_1;
    this.group_ids_1 = group_ids_1;
    this.set_ids_1 = set_ids_1;
    this.wkt_strings_2 = wkt_strings_2;
    this.group_ids_2 = group_ids_2;
    this.set_ids_2 = set_ids_2;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return wkt_strings_1;
    case 1: return group_ids_1;
    case 2: return set_ids_1;
    case 3: return wkt_strings_2;
    case 4: return group_ids_2;
    case 5: return set_ids_2;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: wkt_strings_1 = (java.util.List<java.lang.CharSequence>)value$; break;
    case 1: group_ids_1 = (java.util.List<java.lang.CharSequence>)value$; break;
    case 2: set_ids_1 = (java.util.List<java.lang.CharSequence>)value$; break;
    case 3: wkt_strings_2 = (java.util.List<java.lang.CharSequence>)value$; break;
    case 4: group_ids_2 = (java.util.List<java.lang.CharSequence>)value$; break;
    case 5: set_ids_2 = (java.util.List<java.lang.CharSequence>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'wkt_strings_1' field.
   */
  public java.util.List<java.lang.CharSequence> getWktStrings1() {
    return wkt_strings_1;
  }

  /**
   * Sets the value of the 'wkt_strings_1' field.
   * @param value the value to set.
   */
  public void setWktStrings1(java.util.List<java.lang.CharSequence> value) {
    this.wkt_strings_1 = value;
  }

  /**
   * Gets the value of the 'group_ids_1' field.
   */
  public java.util.List<java.lang.CharSequence> getGroupIds1() {
    return group_ids_1;
  }

  /**
   * Sets the value of the 'group_ids_1' field.
   * @param value the value to set.
   */
  public void setGroupIds1(java.util.List<java.lang.CharSequence> value) {
    this.group_ids_1 = value;
  }

  /**
   * Gets the value of the 'set_ids_1' field.
   */
  public java.util.List<java.lang.CharSequence> getSetIds1() {
    return set_ids_1;
  }

  /**
   * Sets the value of the 'set_ids_1' field.
   * @param value the value to set.
   */
  public void setSetIds1(java.util.List<java.lang.CharSequence> value) {
    this.set_ids_1 = value;
  }

  /**
   * Gets the value of the 'wkt_strings_2' field.
   */
  public java.util.List<java.lang.CharSequence> getWktStrings2() {
    return wkt_strings_2;
  }

  /**
   * Sets the value of the 'wkt_strings_2' field.
   * @param value the value to set.
   */
  public void setWktStrings2(java.util.List<java.lang.CharSequence> value) {
    this.wkt_strings_2 = value;
  }

  /**
   * Gets the value of the 'group_ids_2' field.
   */
  public java.util.List<java.lang.CharSequence> getGroupIds2() {
    return group_ids_2;
  }

  /**
   * Sets the value of the 'group_ids_2' field.
   * @param value the value to set.
   */
  public void setGroupIds2(java.util.List<java.lang.CharSequence> value) {
    this.group_ids_2 = value;
  }

  /**
   * Gets the value of the 'set_ids_2' field.
   */
  public java.util.List<java.lang.CharSequence> getSetIds2() {
    return set_ids_2;
  }

  /**
   * Sets the value of the 'set_ids_2' field.
   * @param value the value to set.
   */
  public void setSetIds2(java.util.List<java.lang.CharSequence> value) {
    this.set_ids_2 = value;
  }

  /** Creates a new shape_intersection_response RecordBuilder */
  public static avro.java.gpudb.shape_intersection_response.Builder newBuilder() {
    return new avro.java.gpudb.shape_intersection_response.Builder();
  }
  
  /** Creates a new shape_intersection_response RecordBuilder by copying an existing Builder */
  public static avro.java.gpudb.shape_intersection_response.Builder newBuilder(avro.java.gpudb.shape_intersection_response.Builder other) {
    return new avro.java.gpudb.shape_intersection_response.Builder(other);
  }
  
  /** Creates a new shape_intersection_response RecordBuilder by copying an existing shape_intersection_response instance */
  public static avro.java.gpudb.shape_intersection_response.Builder newBuilder(avro.java.gpudb.shape_intersection_response other) {
    return new avro.java.gpudb.shape_intersection_response.Builder(other);
  }
  
  /**
   * RecordBuilder for shape_intersection_response instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<shape_intersection_response>
    implements org.apache.avro.data.RecordBuilder<shape_intersection_response> {

    private java.util.List<java.lang.CharSequence> wkt_strings_1;
    private java.util.List<java.lang.CharSequence> group_ids_1;
    private java.util.List<java.lang.CharSequence> set_ids_1;
    private java.util.List<java.lang.CharSequence> wkt_strings_2;
    private java.util.List<java.lang.CharSequence> group_ids_2;
    private java.util.List<java.lang.CharSequence> set_ids_2;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gpudb.shape_intersection_response.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gpudb.shape_intersection_response.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing shape_intersection_response instance */
    private Builder(avro.java.gpudb.shape_intersection_response other) {
            super(avro.java.gpudb.shape_intersection_response.SCHEMA$);
      if (isValidValue(fields()[0], other.wkt_strings_1)) {
        this.wkt_strings_1 = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[0].schema(), other.wkt_strings_1);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.group_ids_1)) {
        this.group_ids_1 = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[1].schema(), other.group_ids_1);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.set_ids_1)) {
        this.set_ids_1 = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[2].schema(), other.set_ids_1);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.wkt_strings_2)) {
        this.wkt_strings_2 = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[3].schema(), other.wkt_strings_2);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.group_ids_2)) {
        this.group_ids_2 = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[4].schema(), other.group_ids_2);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.set_ids_2)) {
        this.set_ids_2 = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[5].schema(), other.set_ids_2);
        fieldSetFlags()[5] = true;
      }
    }

    /** Gets the value of the 'wkt_strings_1' field */
    public java.util.List<java.lang.CharSequence> getWktStrings1() {
      return wkt_strings_1;
    }
    
    /** Sets the value of the 'wkt_strings_1' field */
    public avro.java.gpudb.shape_intersection_response.Builder setWktStrings1(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[0], value);
      this.wkt_strings_1 = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'wkt_strings_1' field has been set */
    public boolean hasWktStrings1() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'wkt_strings_1' field */
    public avro.java.gpudb.shape_intersection_response.Builder clearWktStrings1() {
      wkt_strings_1 = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'group_ids_1' field */
    public java.util.List<java.lang.CharSequence> getGroupIds1() {
      return group_ids_1;
    }
    
    /** Sets the value of the 'group_ids_1' field */
    public avro.java.gpudb.shape_intersection_response.Builder setGroupIds1(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[1], value);
      this.group_ids_1 = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'group_ids_1' field has been set */
    public boolean hasGroupIds1() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'group_ids_1' field */
    public avro.java.gpudb.shape_intersection_response.Builder clearGroupIds1() {
      group_ids_1 = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'set_ids_1' field */
    public java.util.List<java.lang.CharSequence> getSetIds1() {
      return set_ids_1;
    }
    
    /** Sets the value of the 'set_ids_1' field */
    public avro.java.gpudb.shape_intersection_response.Builder setSetIds1(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[2], value);
      this.set_ids_1 = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'set_ids_1' field has been set */
    public boolean hasSetIds1() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'set_ids_1' field */
    public avro.java.gpudb.shape_intersection_response.Builder clearSetIds1() {
      set_ids_1 = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'wkt_strings_2' field */
    public java.util.List<java.lang.CharSequence> getWktStrings2() {
      return wkt_strings_2;
    }
    
    /** Sets the value of the 'wkt_strings_2' field */
    public avro.java.gpudb.shape_intersection_response.Builder setWktStrings2(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[3], value);
      this.wkt_strings_2 = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'wkt_strings_2' field has been set */
    public boolean hasWktStrings2() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'wkt_strings_2' field */
    public avro.java.gpudb.shape_intersection_response.Builder clearWktStrings2() {
      wkt_strings_2 = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'group_ids_2' field */
    public java.util.List<java.lang.CharSequence> getGroupIds2() {
      return group_ids_2;
    }
    
    /** Sets the value of the 'group_ids_2' field */
    public avro.java.gpudb.shape_intersection_response.Builder setGroupIds2(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[4], value);
      this.group_ids_2 = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'group_ids_2' field has been set */
    public boolean hasGroupIds2() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'group_ids_2' field */
    public avro.java.gpudb.shape_intersection_response.Builder clearGroupIds2() {
      group_ids_2 = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'set_ids_2' field */
    public java.util.List<java.lang.CharSequence> getSetIds2() {
      return set_ids_2;
    }
    
    /** Sets the value of the 'set_ids_2' field */
    public avro.java.gpudb.shape_intersection_response.Builder setSetIds2(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[5], value);
      this.set_ids_2 = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'set_ids_2' field has been set */
    public boolean hasSetIds2() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'set_ids_2' field */
    public avro.java.gpudb.shape_intersection_response.Builder clearSetIds2() {
      set_ids_2 = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    @Override
    public shape_intersection_response build() {
      try {
        shape_intersection_response record = new shape_intersection_response();
        record.wkt_strings_1 = fieldSetFlags()[0] ? this.wkt_strings_1 : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[0]);
        record.group_ids_1 = fieldSetFlags()[1] ? this.group_ids_1 : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[1]);
        record.set_ids_1 = fieldSetFlags()[2] ? this.set_ids_1 : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[2]);
        record.wkt_strings_2 = fieldSetFlags()[3] ? this.wkt_strings_2 : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[3]);
        record.group_ids_2 = fieldSetFlags()[4] ? this.group_ids_2 : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[4]);
        record.set_ids_2 = fieldSetFlags()[5] ? this.set_ids_2 : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[5]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
