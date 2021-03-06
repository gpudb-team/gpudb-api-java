/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gpudb;  
@SuppressWarnings("all")
public class shape_intersection_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"shape_intersection_request\",\"namespace\":\"avro.java.gpudb\",\"fields\":[{\"name\":\"set_ids\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"wkt_attr_name\",\"type\":\"string\"},{\"name\":\"x_vector\",\"type\":{\"type\":\"array\",\"items\":\"double\"}},{\"name\":\"y_vector\",\"type\":{\"type\":\"array\",\"items\":\"double\"}},{\"name\":\"geometry_type\",\"type\":\"string\"},{\"name\":\"wkt_string\",\"type\":\"string\"},{\"name\":\"user_auth_string\",\"type\":\"string\"}]}");
  @Deprecated public java.util.List<java.lang.CharSequence> set_ids;
  @Deprecated public java.lang.CharSequence wkt_attr_name;
  @Deprecated public java.util.List<java.lang.Double> x_vector;
  @Deprecated public java.util.List<java.lang.Double> y_vector;
  @Deprecated public java.lang.CharSequence geometry_type;
  @Deprecated public java.lang.CharSequence wkt_string;
  @Deprecated public java.lang.CharSequence user_auth_string;

  /**
   * Default constructor.
   */
  public shape_intersection_request() {}

  /**
   * All-args constructor.
   */
  public shape_intersection_request(java.util.List<java.lang.CharSequence> set_ids, java.lang.CharSequence wkt_attr_name, java.util.List<java.lang.Double> x_vector, java.util.List<java.lang.Double> y_vector, java.lang.CharSequence geometry_type, java.lang.CharSequence wkt_string, java.lang.CharSequence user_auth_string) {
    this.set_ids = set_ids;
    this.wkt_attr_name = wkt_attr_name;
    this.x_vector = x_vector;
    this.y_vector = y_vector;
    this.geometry_type = geometry_type;
    this.wkt_string = wkt_string;
    this.user_auth_string = user_auth_string;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return set_ids;
    case 1: return wkt_attr_name;
    case 2: return x_vector;
    case 3: return y_vector;
    case 4: return geometry_type;
    case 5: return wkt_string;
    case 6: return user_auth_string;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: set_ids = (java.util.List<java.lang.CharSequence>)value$; break;
    case 1: wkt_attr_name = (java.lang.CharSequence)value$; break;
    case 2: x_vector = (java.util.List<java.lang.Double>)value$; break;
    case 3: y_vector = (java.util.List<java.lang.Double>)value$; break;
    case 4: geometry_type = (java.lang.CharSequence)value$; break;
    case 5: wkt_string = (java.lang.CharSequence)value$; break;
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
   * Gets the value of the 'wkt_attr_name' field.
   */
  public java.lang.CharSequence getWktAttrName() {
    return wkt_attr_name;
  }

  /**
   * Sets the value of the 'wkt_attr_name' field.
   * @param value the value to set.
   */
  public void setWktAttrName(java.lang.CharSequence value) {
    this.wkt_attr_name = value;
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
   * Gets the value of the 'geometry_type' field.
   */
  public java.lang.CharSequence getGeometryType() {
    return geometry_type;
  }

  /**
   * Sets the value of the 'geometry_type' field.
   * @param value the value to set.
   */
  public void setGeometryType(java.lang.CharSequence value) {
    this.geometry_type = value;
  }

  /**
   * Gets the value of the 'wkt_string' field.
   */
  public java.lang.CharSequence getWktString() {
    return wkt_string;
  }

  /**
   * Sets the value of the 'wkt_string' field.
   * @param value the value to set.
   */
  public void setWktString(java.lang.CharSequence value) {
    this.wkt_string = value;
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

  /** Creates a new shape_intersection_request RecordBuilder */
  public static avro.java.gpudb.shape_intersection_request.Builder newBuilder() {
    return new avro.java.gpudb.shape_intersection_request.Builder();
  }
  
  /** Creates a new shape_intersection_request RecordBuilder by copying an existing Builder */
  public static avro.java.gpudb.shape_intersection_request.Builder newBuilder(avro.java.gpudb.shape_intersection_request.Builder other) {
    return new avro.java.gpudb.shape_intersection_request.Builder(other);
  }
  
  /** Creates a new shape_intersection_request RecordBuilder by copying an existing shape_intersection_request instance */
  public static avro.java.gpudb.shape_intersection_request.Builder newBuilder(avro.java.gpudb.shape_intersection_request other) {
    return new avro.java.gpudb.shape_intersection_request.Builder(other);
  }
  
  /**
   * RecordBuilder for shape_intersection_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<shape_intersection_request>
    implements org.apache.avro.data.RecordBuilder<shape_intersection_request> {

    private java.util.List<java.lang.CharSequence> set_ids;
    private java.lang.CharSequence wkt_attr_name;
    private java.util.List<java.lang.Double> x_vector;
    private java.util.List<java.lang.Double> y_vector;
    private java.lang.CharSequence geometry_type;
    private java.lang.CharSequence wkt_string;
    private java.lang.CharSequence user_auth_string;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gpudb.shape_intersection_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gpudb.shape_intersection_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing shape_intersection_request instance */
    private Builder(avro.java.gpudb.shape_intersection_request other) {
            super(avro.java.gpudb.shape_intersection_request.SCHEMA$);
      if (isValidValue(fields()[0], other.set_ids)) {
        this.set_ids = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[0].schema(), other.set_ids);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.wkt_attr_name)) {
        this.wkt_attr_name = (java.lang.CharSequence) data().deepCopy(fields()[1].schema(), other.wkt_attr_name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.x_vector)) {
        this.x_vector = (java.util.List<java.lang.Double>) data().deepCopy(fields()[2].schema(), other.x_vector);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.y_vector)) {
        this.y_vector = (java.util.List<java.lang.Double>) data().deepCopy(fields()[3].schema(), other.y_vector);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.geometry_type)) {
        this.geometry_type = (java.lang.CharSequence) data().deepCopy(fields()[4].schema(), other.geometry_type);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.wkt_string)) {
        this.wkt_string = (java.lang.CharSequence) data().deepCopy(fields()[5].schema(), other.wkt_string);
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
    public avro.java.gpudb.shape_intersection_request.Builder setSetIds(java.util.List<java.lang.CharSequence> value) {
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
    public avro.java.gpudb.shape_intersection_request.Builder clearSetIds() {
      set_ids = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'wkt_attr_name' field */
    public java.lang.CharSequence getWktAttrName() {
      return wkt_attr_name;
    }
    
    /** Sets the value of the 'wkt_attr_name' field */
    public avro.java.gpudb.shape_intersection_request.Builder setWktAttrName(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.wkt_attr_name = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'wkt_attr_name' field has been set */
    public boolean hasWktAttrName() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'wkt_attr_name' field */
    public avro.java.gpudb.shape_intersection_request.Builder clearWktAttrName() {
      wkt_attr_name = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'x_vector' field */
    public java.util.List<java.lang.Double> getXVector() {
      return x_vector;
    }
    
    /** Sets the value of the 'x_vector' field */
    public avro.java.gpudb.shape_intersection_request.Builder setXVector(java.util.List<java.lang.Double> value) {
      validate(fields()[2], value);
      this.x_vector = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'x_vector' field has been set */
    public boolean hasXVector() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'x_vector' field */
    public avro.java.gpudb.shape_intersection_request.Builder clearXVector() {
      x_vector = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'y_vector' field */
    public java.util.List<java.lang.Double> getYVector() {
      return y_vector;
    }
    
    /** Sets the value of the 'y_vector' field */
    public avro.java.gpudb.shape_intersection_request.Builder setYVector(java.util.List<java.lang.Double> value) {
      validate(fields()[3], value);
      this.y_vector = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'y_vector' field has been set */
    public boolean hasYVector() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'y_vector' field */
    public avro.java.gpudb.shape_intersection_request.Builder clearYVector() {
      y_vector = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'geometry_type' field */
    public java.lang.CharSequence getGeometryType() {
      return geometry_type;
    }
    
    /** Sets the value of the 'geometry_type' field */
    public avro.java.gpudb.shape_intersection_request.Builder setGeometryType(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.geometry_type = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'geometry_type' field has been set */
    public boolean hasGeometryType() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'geometry_type' field */
    public avro.java.gpudb.shape_intersection_request.Builder clearGeometryType() {
      geometry_type = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'wkt_string' field */
    public java.lang.CharSequence getWktString() {
      return wkt_string;
    }
    
    /** Sets the value of the 'wkt_string' field */
    public avro.java.gpudb.shape_intersection_request.Builder setWktString(java.lang.CharSequence value) {
      validate(fields()[5], value);
      this.wkt_string = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'wkt_string' field has been set */
    public boolean hasWktString() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'wkt_string' field */
    public avro.java.gpudb.shape_intersection_request.Builder clearWktString() {
      wkt_string = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'user_auth_string' field */
    public java.lang.CharSequence getUserAuthString() {
      return user_auth_string;
    }
    
    /** Sets the value of the 'user_auth_string' field */
    public avro.java.gpudb.shape_intersection_request.Builder setUserAuthString(java.lang.CharSequence value) {
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
    public avro.java.gpudb.shape_intersection_request.Builder clearUserAuthString() {
      user_auth_string = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    @Override
    public shape_intersection_request build() {
      try {
        shape_intersection_request record = new shape_intersection_request();
        record.set_ids = fieldSetFlags()[0] ? this.set_ids : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[0]);
        record.wkt_attr_name = fieldSetFlags()[1] ? this.wkt_attr_name : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.x_vector = fieldSetFlags()[2] ? this.x_vector : (java.util.List<java.lang.Double>) defaultValue(fields()[2]);
        record.y_vector = fieldSetFlags()[3] ? this.y_vector : (java.util.List<java.lang.Double>) defaultValue(fields()[3]);
        record.geometry_type = fieldSetFlags()[4] ? this.geometry_type : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.wkt_string = fieldSetFlags()[5] ? this.wkt_string : (java.lang.CharSequence) defaultValue(fields()[5]);
        record.user_auth_string = fieldSetFlags()[6] ? this.user_auth_string : (java.lang.CharSequence) defaultValue(fields()[6]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
