/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gpudb;  
@SuppressWarnings("all")
public class bulk_select_pk_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"bulk_select_pk_request\",\"namespace\":\"avro.java.gpudb\",\"fields\":[{\"name\":\"set_id\",\"type\":\"string\"},{\"name\":\"pk_expressions\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"array\",\"items\":\"string\"}}},{\"name\":\"params\",\"type\":{\"type\":\"map\",\"values\":\"string\"}},{\"name\":\"user_auth_string\",\"type\":\"string\"}]}");
  @Deprecated public java.lang.CharSequence set_id;
  @Deprecated public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> pk_expressions;
  @Deprecated public java.util.Map<java.lang.CharSequence,java.lang.CharSequence> params;
  @Deprecated public java.lang.CharSequence user_auth_string;

  /**
   * Default constructor.
   */
  public bulk_select_pk_request() {}

  /**
   * All-args constructor.
   */
  public bulk_select_pk_request(java.lang.CharSequence set_id, java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> pk_expressions, java.util.Map<java.lang.CharSequence,java.lang.CharSequence> params, java.lang.CharSequence user_auth_string) {
    this.set_id = set_id;
    this.pk_expressions = pk_expressions;
    this.params = params;
    this.user_auth_string = user_auth_string;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return set_id;
    case 1: return pk_expressions;
    case 2: return params;
    case 3: return user_auth_string;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: set_id = (java.lang.CharSequence)value$; break;
    case 1: pk_expressions = (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>>)value$; break;
    case 2: params = (java.util.Map<java.lang.CharSequence,java.lang.CharSequence>)value$; break;
    case 3: user_auth_string = (java.lang.CharSequence)value$; break;
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
   * Gets the value of the 'pk_expressions' field.
   */
  public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> getPkExpressions() {
    return pk_expressions;
  }

  /**
   * Sets the value of the 'pk_expressions' field.
   * @param value the value to set.
   */
  public void setPkExpressions(java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> value) {
    this.pk_expressions = value;
  }

  /**
   * Gets the value of the 'params' field.
   */
  public java.util.Map<java.lang.CharSequence,java.lang.CharSequence> getParams() {
    return params;
  }

  /**
   * Sets the value of the 'params' field.
   * @param value the value to set.
   */
  public void setParams(java.util.Map<java.lang.CharSequence,java.lang.CharSequence> value) {
    this.params = value;
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

  /** Creates a new bulk_select_pk_request RecordBuilder */
  public static avro.java.gpudb.bulk_select_pk_request.Builder newBuilder() {
    return new avro.java.gpudb.bulk_select_pk_request.Builder();
  }
  
  /** Creates a new bulk_select_pk_request RecordBuilder by copying an existing Builder */
  public static avro.java.gpudb.bulk_select_pk_request.Builder newBuilder(avro.java.gpudb.bulk_select_pk_request.Builder other) {
    return new avro.java.gpudb.bulk_select_pk_request.Builder(other);
  }
  
  /** Creates a new bulk_select_pk_request RecordBuilder by copying an existing bulk_select_pk_request instance */
  public static avro.java.gpudb.bulk_select_pk_request.Builder newBuilder(avro.java.gpudb.bulk_select_pk_request other) {
    return new avro.java.gpudb.bulk_select_pk_request.Builder(other);
  }
  
  /**
   * RecordBuilder for bulk_select_pk_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<bulk_select_pk_request>
    implements org.apache.avro.data.RecordBuilder<bulk_select_pk_request> {

    private java.lang.CharSequence set_id;
    private java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> pk_expressions;
    private java.util.Map<java.lang.CharSequence,java.lang.CharSequence> params;
    private java.lang.CharSequence user_auth_string;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gpudb.bulk_select_pk_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gpudb.bulk_select_pk_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing bulk_select_pk_request instance */
    private Builder(avro.java.gpudb.bulk_select_pk_request other) {
            super(avro.java.gpudb.bulk_select_pk_request.SCHEMA$);
      if (isValidValue(fields()[0], other.set_id)) {
        this.set_id = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.set_id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.pk_expressions)) {
        this.pk_expressions = (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>>) data().deepCopy(fields()[1].schema(), other.pk_expressions);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.params)) {
        this.params = (java.util.Map<java.lang.CharSequence,java.lang.CharSequence>) data().deepCopy(fields()[2].schema(), other.params);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.user_auth_string)) {
        this.user_auth_string = (java.lang.CharSequence) data().deepCopy(fields()[3].schema(), other.user_auth_string);
        fieldSetFlags()[3] = true;
      }
    }

    /** Gets the value of the 'set_id' field */
    public java.lang.CharSequence getSetId() {
      return set_id;
    }
    
    /** Sets the value of the 'set_id' field */
    public avro.java.gpudb.bulk_select_pk_request.Builder setSetId(java.lang.CharSequence value) {
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
    public avro.java.gpudb.bulk_select_pk_request.Builder clearSetId() {
      set_id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'pk_expressions' field */
    public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> getPkExpressions() {
      return pk_expressions;
    }
    
    /** Sets the value of the 'pk_expressions' field */
    public avro.java.gpudb.bulk_select_pk_request.Builder setPkExpressions(java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> value) {
      validate(fields()[1], value);
      this.pk_expressions = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'pk_expressions' field has been set */
    public boolean hasPkExpressions() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'pk_expressions' field */
    public avro.java.gpudb.bulk_select_pk_request.Builder clearPkExpressions() {
      pk_expressions = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'params' field */
    public java.util.Map<java.lang.CharSequence,java.lang.CharSequence> getParams() {
      return params;
    }
    
    /** Sets the value of the 'params' field */
    public avro.java.gpudb.bulk_select_pk_request.Builder setParams(java.util.Map<java.lang.CharSequence,java.lang.CharSequence> value) {
      validate(fields()[2], value);
      this.params = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'params' field has been set */
    public boolean hasParams() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'params' field */
    public avro.java.gpudb.bulk_select_pk_request.Builder clearParams() {
      params = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'user_auth_string' field */
    public java.lang.CharSequence getUserAuthString() {
      return user_auth_string;
    }
    
    /** Sets the value of the 'user_auth_string' field */
    public avro.java.gpudb.bulk_select_pk_request.Builder setUserAuthString(java.lang.CharSequence value) {
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
    public avro.java.gpudb.bulk_select_pk_request.Builder clearUserAuthString() {
      user_auth_string = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    public bulk_select_pk_request build() {
      try {
        bulk_select_pk_request record = new bulk_select_pk_request();
        record.set_id = fieldSetFlags()[0] ? this.set_id : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.pk_expressions = fieldSetFlags()[1] ? this.pk_expressions : (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>>) defaultValue(fields()[1]);
        record.params = fieldSetFlags()[2] ? this.params : (java.util.Map<java.lang.CharSequence,java.lang.CharSequence>) defaultValue(fields()[2]);
        record.user_auth_string = fieldSetFlags()[3] ? this.user_auth_string : (java.lang.CharSequence) defaultValue(fields()[3]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
