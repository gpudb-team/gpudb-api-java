/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gpudb;  
@SuppressWarnings("all")
public class get_set_sorted_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"get_set_sorted_request\",\"namespace\":\"avro.java.gpudb\",\"fields\":[{\"name\":\"start\",\"type\":\"int\"},{\"name\":\"end\",\"type\":\"int\"},{\"name\":\"set_id\",\"type\":\"string\"},{\"name\":\"user_auth_string\",\"type\":\"string\"}]}");
  @Deprecated public int start;
  @Deprecated public int end;
  @Deprecated public java.lang.CharSequence set_id;
  @Deprecated public java.lang.CharSequence user_auth_string;

  /**
   * Default constructor.
   */
  public get_set_sorted_request() {}

  /**
   * All-args constructor.
   */
  public get_set_sorted_request(java.lang.Integer start, java.lang.Integer end, java.lang.CharSequence set_id, java.lang.CharSequence user_auth_string) {
    this.start = start;
    this.end = end;
    this.set_id = set_id;
    this.user_auth_string = user_auth_string;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return start;
    case 1: return end;
    case 2: return set_id;
    case 3: return user_auth_string;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: start = (java.lang.Integer)value$; break;
    case 1: end = (java.lang.Integer)value$; break;
    case 2: set_id = (java.lang.CharSequence)value$; break;
    case 3: user_auth_string = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'start' field.
   */
  public java.lang.Integer getStart() {
    return start;
  }

  /**
   * Sets the value of the 'start' field.
   * @param value the value to set.
   */
  public void setStart(java.lang.Integer value) {
    this.start = value;
  }

  /**
   * Gets the value of the 'end' field.
   */
  public java.lang.Integer getEnd() {
    return end;
  }

  /**
   * Sets the value of the 'end' field.
   * @param value the value to set.
   */
  public void setEnd(java.lang.Integer value) {
    this.end = value;
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

  /** Creates a new get_set_sorted_request RecordBuilder */
  public static avro.java.gpudb.get_set_sorted_request.Builder newBuilder() {
    return new avro.java.gpudb.get_set_sorted_request.Builder();
  }
  
  /** Creates a new get_set_sorted_request RecordBuilder by copying an existing Builder */
  public static avro.java.gpudb.get_set_sorted_request.Builder newBuilder(avro.java.gpudb.get_set_sorted_request.Builder other) {
    return new avro.java.gpudb.get_set_sorted_request.Builder(other);
  }
  
  /** Creates a new get_set_sorted_request RecordBuilder by copying an existing get_set_sorted_request instance */
  public static avro.java.gpudb.get_set_sorted_request.Builder newBuilder(avro.java.gpudb.get_set_sorted_request other) {
    return new avro.java.gpudb.get_set_sorted_request.Builder(other);
  }
  
  /**
   * RecordBuilder for get_set_sorted_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<get_set_sorted_request>
    implements org.apache.avro.data.RecordBuilder<get_set_sorted_request> {

    private int start;
    private int end;
    private java.lang.CharSequence set_id;
    private java.lang.CharSequence user_auth_string;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gpudb.get_set_sorted_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gpudb.get_set_sorted_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing get_set_sorted_request instance */
    private Builder(avro.java.gpudb.get_set_sorted_request other) {
            super(avro.java.gpudb.get_set_sorted_request.SCHEMA$);
      if (isValidValue(fields()[0], other.start)) {
        this.start = (java.lang.Integer) data().deepCopy(fields()[0].schema(), other.start);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.end)) {
        this.end = (java.lang.Integer) data().deepCopy(fields()[1].schema(), other.end);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.set_id)) {
        this.set_id = (java.lang.CharSequence) data().deepCopy(fields()[2].schema(), other.set_id);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.user_auth_string)) {
        this.user_auth_string = (java.lang.CharSequence) data().deepCopy(fields()[3].schema(), other.user_auth_string);
        fieldSetFlags()[3] = true;
      }
    }

    /** Gets the value of the 'start' field */
    public java.lang.Integer getStart() {
      return start;
    }
    
    /** Sets the value of the 'start' field */
    public avro.java.gpudb.get_set_sorted_request.Builder setStart(int value) {
      validate(fields()[0], value);
      this.start = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'start' field has been set */
    public boolean hasStart() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'start' field */
    public avro.java.gpudb.get_set_sorted_request.Builder clearStart() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'end' field */
    public java.lang.Integer getEnd() {
      return end;
    }
    
    /** Sets the value of the 'end' field */
    public avro.java.gpudb.get_set_sorted_request.Builder setEnd(int value) {
      validate(fields()[1], value);
      this.end = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'end' field has been set */
    public boolean hasEnd() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'end' field */
    public avro.java.gpudb.get_set_sorted_request.Builder clearEnd() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'set_id' field */
    public java.lang.CharSequence getSetId() {
      return set_id;
    }
    
    /** Sets the value of the 'set_id' field */
    public avro.java.gpudb.get_set_sorted_request.Builder setSetId(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.set_id = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'set_id' field has been set */
    public boolean hasSetId() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'set_id' field */
    public avro.java.gpudb.get_set_sorted_request.Builder clearSetId() {
      set_id = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'user_auth_string' field */
    public java.lang.CharSequence getUserAuthString() {
      return user_auth_string;
    }
    
    /** Sets the value of the 'user_auth_string' field */
    public avro.java.gpudb.get_set_sorted_request.Builder setUserAuthString(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.user_auth_string = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'user_auth_string' field has been set */
    public boolean hasUserAuthString() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'user_auth_string' field */
    public avro.java.gpudb.get_set_sorted_request.Builder clearUserAuthString() {
      user_auth_string = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    public get_set_sorted_request build() {
      try {
        get_set_sorted_request record = new get_set_sorted_request();
        record.start = fieldSetFlags()[0] ? this.start : (java.lang.Integer) defaultValue(fields()[0]);
        record.end = fieldSetFlags()[1] ? this.end : (java.lang.Integer) defaultValue(fields()[1]);
        record.set_id = fieldSetFlags()[2] ? this.set_id : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.user_auth_string = fieldSetFlags()[3] ? this.user_auth_string : (java.lang.CharSequence) defaultValue(fields()[3]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}