/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gaia;  
@SuppressWarnings("all")
public class authenticate_users_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"authenticate_users_request\",\"namespace\":\"avro.java.gaia\",\"fields\":[{\"name\":\"user_auth_strings\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"set_ids\",\"type\":{\"type\":\"array\",\"items\":\"string\"}}]}");
  @Deprecated public java.util.List<java.lang.CharSequence> user_auth_strings;
  @Deprecated public java.util.List<java.lang.CharSequence> set_ids;

  /**
   * Default constructor.
   */
  public authenticate_users_request() {}

  /**
   * All-args constructor.
   */
  public authenticate_users_request(java.util.List<java.lang.CharSequence> user_auth_strings, java.util.List<java.lang.CharSequence> set_ids) {
    this.user_auth_strings = user_auth_strings;
    this.set_ids = set_ids;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return user_auth_strings;
    case 1: return set_ids;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: user_auth_strings = (java.util.List<java.lang.CharSequence>)value$; break;
    case 1: set_ids = (java.util.List<java.lang.CharSequence>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'user_auth_strings' field.
   */
  public java.util.List<java.lang.CharSequence> getUserAuthStrings() {
    return user_auth_strings;
  }

  /**
   * Sets the value of the 'user_auth_strings' field.
   * @param value the value to set.
   */
  public void setUserAuthStrings(java.util.List<java.lang.CharSequence> value) {
    this.user_auth_strings = value;
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

  /** Creates a new authenticate_users_request RecordBuilder */
  public static avro.java.gaia.authenticate_users_request.Builder newBuilder() {
    return new avro.java.gaia.authenticate_users_request.Builder();
  }
  
  /** Creates a new authenticate_users_request RecordBuilder by copying an existing Builder */
  public static avro.java.gaia.authenticate_users_request.Builder newBuilder(avro.java.gaia.authenticate_users_request.Builder other) {
    return new avro.java.gaia.authenticate_users_request.Builder(other);
  }
  
  /** Creates a new authenticate_users_request RecordBuilder by copying an existing authenticate_users_request instance */
  public static avro.java.gaia.authenticate_users_request.Builder newBuilder(avro.java.gaia.authenticate_users_request other) {
    return new avro.java.gaia.authenticate_users_request.Builder(other);
  }
  
  /**
   * RecordBuilder for authenticate_users_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<authenticate_users_request>
    implements org.apache.avro.data.RecordBuilder<authenticate_users_request> {

    private java.util.List<java.lang.CharSequence> user_auth_strings;
    private java.util.List<java.lang.CharSequence> set_ids;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gaia.authenticate_users_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gaia.authenticate_users_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing authenticate_users_request instance */
    private Builder(avro.java.gaia.authenticate_users_request other) {
            super(avro.java.gaia.authenticate_users_request.SCHEMA$);
      if (isValidValue(fields()[0], other.user_auth_strings)) {
        this.user_auth_strings = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[0].schema(), other.user_auth_strings);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.set_ids)) {
        this.set_ids = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[1].schema(), other.set_ids);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the 'user_auth_strings' field */
    public java.util.List<java.lang.CharSequence> getUserAuthStrings() {
      return user_auth_strings;
    }
    
    /** Sets the value of the 'user_auth_strings' field */
    public avro.java.gaia.authenticate_users_request.Builder setUserAuthStrings(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[0], value);
      this.user_auth_strings = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'user_auth_strings' field has been set */
    public boolean hasUserAuthStrings() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'user_auth_strings' field */
    public avro.java.gaia.authenticate_users_request.Builder clearUserAuthStrings() {
      user_auth_strings = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'set_ids' field */
    public java.util.List<java.lang.CharSequence> getSetIds() {
      return set_ids;
    }
    
    /** Sets the value of the 'set_ids' field */
    public avro.java.gaia.authenticate_users_request.Builder setSetIds(java.util.List<java.lang.CharSequence> value) {
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
    public avro.java.gaia.authenticate_users_request.Builder clearSetIds() {
      set_ids = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public authenticate_users_request build() {
      try {
        authenticate_users_request record = new authenticate_users_request();
        record.user_auth_strings = fieldSetFlags()[0] ? this.user_auth_strings : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[0]);
        record.set_ids = fieldSetFlags()[1] ? this.set_ids : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
