/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gaia;  
@SuppressWarnings("all")
public class exit_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"exit_request\",\"namespace\":\"avro.java.gaia\",\"fields\":[{\"name\":\"exit_type\",\"type\":\"string\"},{\"name\":\"authorization\",\"type\":\"string\"}]}");
  @Deprecated public java.lang.CharSequence exit_type;
  @Deprecated public java.lang.CharSequence authorization;

  /**
   * Default constructor.
   */
  public exit_request() {}

  /**
   * All-args constructor.
   */
  public exit_request(java.lang.CharSequence exit_type, java.lang.CharSequence authorization) {
    this.exit_type = exit_type;
    this.authorization = authorization;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return exit_type;
    case 1: return authorization;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: exit_type = (java.lang.CharSequence)value$; break;
    case 1: authorization = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'exit_type' field.
   */
  public java.lang.CharSequence getExitType() {
    return exit_type;
  }

  /**
   * Sets the value of the 'exit_type' field.
   * @param value the value to set.
   */
  public void setExitType(java.lang.CharSequence value) {
    this.exit_type = value;
  }

  /**
   * Gets the value of the 'authorization' field.
   */
  public java.lang.CharSequence getAuthorization() {
    return authorization;
  }

  /**
   * Sets the value of the 'authorization' field.
   * @param value the value to set.
   */
  public void setAuthorization(java.lang.CharSequence value) {
    this.authorization = value;
  }

  /** Creates a new exit_request RecordBuilder */
  public static avro.java.gaia.exit_request.Builder newBuilder() {
    return new avro.java.gaia.exit_request.Builder();
  }
  
  /** Creates a new exit_request RecordBuilder by copying an existing Builder */
  public static avro.java.gaia.exit_request.Builder newBuilder(avro.java.gaia.exit_request.Builder other) {
    return new avro.java.gaia.exit_request.Builder(other);
  }
  
  /** Creates a new exit_request RecordBuilder by copying an existing exit_request instance */
  public static avro.java.gaia.exit_request.Builder newBuilder(avro.java.gaia.exit_request other) {
    return new avro.java.gaia.exit_request.Builder(other);
  }
  
  /**
   * RecordBuilder for exit_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<exit_request>
    implements org.apache.avro.data.RecordBuilder<exit_request> {

    private java.lang.CharSequence exit_type;
    private java.lang.CharSequence authorization;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gaia.exit_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gaia.exit_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing exit_request instance */
    private Builder(avro.java.gaia.exit_request other) {
            super(avro.java.gaia.exit_request.SCHEMA$);
      if (isValidValue(fields()[0], other.exit_type)) {
        this.exit_type = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.exit_type);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.authorization)) {
        this.authorization = (java.lang.CharSequence) data().deepCopy(fields()[1].schema(), other.authorization);
        fieldSetFlags()[1] = true;
      }
    }

    /** Gets the value of the 'exit_type' field */
    public java.lang.CharSequence getExitType() {
      return exit_type;
    }
    
    /** Sets the value of the 'exit_type' field */
    public avro.java.gaia.exit_request.Builder setExitType(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.exit_type = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'exit_type' field has been set */
    public boolean hasExitType() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'exit_type' field */
    public avro.java.gaia.exit_request.Builder clearExitType() {
      exit_type = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'authorization' field */
    public java.lang.CharSequence getAuthorization() {
      return authorization;
    }
    
    /** Sets the value of the 'authorization' field */
    public avro.java.gaia.exit_request.Builder setAuthorization(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.authorization = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'authorization' field has been set */
    public boolean hasAuthorization() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'authorization' field */
    public avro.java.gaia.exit_request.Builder clearAuthorization() {
      authorization = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public exit_request build() {
      try {
        exit_request record = new exit_request();
        record.exit_type = fieldSetFlags()[0] ? this.exit_type : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.authorization = fieldSetFlags()[1] ? this.authorization : (java.lang.CharSequence) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
