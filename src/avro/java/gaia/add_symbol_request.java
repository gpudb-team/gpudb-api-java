/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gaia;  
@SuppressWarnings("all")
public class add_symbol_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"add_symbol_request\",\"namespace\":\"avro.java.gaia\",\"fields\":[{\"name\":\"symbol_id\",\"type\":\"string\"},{\"name\":\"symbol_format\",\"type\":\"string\"},{\"name\":\"symbol_data\",\"type\":\"bytes\"},{\"name\":\"params\",\"type\":{\"type\":\"map\",\"values\":\"string\"}}]}");
  @Deprecated public java.lang.CharSequence symbol_id;
  @Deprecated public java.lang.CharSequence symbol_format;
  @Deprecated public java.nio.ByteBuffer symbol_data;
  @Deprecated public java.util.Map<java.lang.CharSequence,java.lang.CharSequence> params;

  /**
   * Default constructor.
   */
  public add_symbol_request() {}

  /**
   * All-args constructor.
   */
  public add_symbol_request(java.lang.CharSequence symbol_id, java.lang.CharSequence symbol_format, java.nio.ByteBuffer symbol_data, java.util.Map<java.lang.CharSequence,java.lang.CharSequence> params) {
    this.symbol_id = symbol_id;
    this.symbol_format = symbol_format;
    this.symbol_data = symbol_data;
    this.params = params;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return symbol_id;
    case 1: return symbol_format;
    case 2: return symbol_data;
    case 3: return params;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: symbol_id = (java.lang.CharSequence)value$; break;
    case 1: symbol_format = (java.lang.CharSequence)value$; break;
    case 2: symbol_data = (java.nio.ByteBuffer)value$; break;
    case 3: params = (java.util.Map<java.lang.CharSequence,java.lang.CharSequence>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'symbol_id' field.
   */
  public java.lang.CharSequence getSymbolId() {
    return symbol_id;
  }

  /**
   * Sets the value of the 'symbol_id' field.
   * @param value the value to set.
   */
  public void setSymbolId(java.lang.CharSequence value) {
    this.symbol_id = value;
  }

  /**
   * Gets the value of the 'symbol_format' field.
   */
  public java.lang.CharSequence getSymbolFormat() {
    return symbol_format;
  }

  /**
   * Sets the value of the 'symbol_format' field.
   * @param value the value to set.
   */
  public void setSymbolFormat(java.lang.CharSequence value) {
    this.symbol_format = value;
  }

  /**
   * Gets the value of the 'symbol_data' field.
   */
  public java.nio.ByteBuffer getSymbolData() {
    return symbol_data;
  }

  /**
   * Sets the value of the 'symbol_data' field.
   * @param value the value to set.
   */
  public void setSymbolData(java.nio.ByteBuffer value) {
    this.symbol_data = value;
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

  /** Creates a new add_symbol_request RecordBuilder */
  public static avro.java.gaia.add_symbol_request.Builder newBuilder() {
    return new avro.java.gaia.add_symbol_request.Builder();
  }
  
  /** Creates a new add_symbol_request RecordBuilder by copying an existing Builder */
  public static avro.java.gaia.add_symbol_request.Builder newBuilder(avro.java.gaia.add_symbol_request.Builder other) {
    return new avro.java.gaia.add_symbol_request.Builder(other);
  }
  
  /** Creates a new add_symbol_request RecordBuilder by copying an existing add_symbol_request instance */
  public static avro.java.gaia.add_symbol_request.Builder newBuilder(avro.java.gaia.add_symbol_request other) {
    return new avro.java.gaia.add_symbol_request.Builder(other);
  }
  
  /**
   * RecordBuilder for add_symbol_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<add_symbol_request>
    implements org.apache.avro.data.RecordBuilder<add_symbol_request> {

    private java.lang.CharSequence symbol_id;
    private java.lang.CharSequence symbol_format;
    private java.nio.ByteBuffer symbol_data;
    private java.util.Map<java.lang.CharSequence,java.lang.CharSequence> params;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gaia.add_symbol_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gaia.add_symbol_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing add_symbol_request instance */
    private Builder(avro.java.gaia.add_symbol_request other) {
            super(avro.java.gaia.add_symbol_request.SCHEMA$);
      if (isValidValue(fields()[0], other.symbol_id)) {
        this.symbol_id = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.symbol_id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.symbol_format)) {
        this.symbol_format = (java.lang.CharSequence) data().deepCopy(fields()[1].schema(), other.symbol_format);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.symbol_data)) {
        this.symbol_data = (java.nio.ByteBuffer) data().deepCopy(fields()[2].schema(), other.symbol_data);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.params)) {
        this.params = (java.util.Map<java.lang.CharSequence,java.lang.CharSequence>) data().deepCopy(fields()[3].schema(), other.params);
        fieldSetFlags()[3] = true;
      }
    }

    /** Gets the value of the 'symbol_id' field */
    public java.lang.CharSequence getSymbolId() {
      return symbol_id;
    }
    
    /** Sets the value of the 'symbol_id' field */
    public avro.java.gaia.add_symbol_request.Builder setSymbolId(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.symbol_id = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'symbol_id' field has been set */
    public boolean hasSymbolId() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'symbol_id' field */
    public avro.java.gaia.add_symbol_request.Builder clearSymbolId() {
      symbol_id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'symbol_format' field */
    public java.lang.CharSequence getSymbolFormat() {
      return symbol_format;
    }
    
    /** Sets the value of the 'symbol_format' field */
    public avro.java.gaia.add_symbol_request.Builder setSymbolFormat(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.symbol_format = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'symbol_format' field has been set */
    public boolean hasSymbolFormat() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'symbol_format' field */
    public avro.java.gaia.add_symbol_request.Builder clearSymbolFormat() {
      symbol_format = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'symbol_data' field */
    public java.nio.ByteBuffer getSymbolData() {
      return symbol_data;
    }
    
    /** Sets the value of the 'symbol_data' field */
    public avro.java.gaia.add_symbol_request.Builder setSymbolData(java.nio.ByteBuffer value) {
      validate(fields()[2], value);
      this.symbol_data = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'symbol_data' field has been set */
    public boolean hasSymbolData() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'symbol_data' field */
    public avro.java.gaia.add_symbol_request.Builder clearSymbolData() {
      symbol_data = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'params' field */
    public java.util.Map<java.lang.CharSequence,java.lang.CharSequence> getParams() {
      return params;
    }
    
    /** Sets the value of the 'params' field */
    public avro.java.gaia.add_symbol_request.Builder setParams(java.util.Map<java.lang.CharSequence,java.lang.CharSequence> value) {
      validate(fields()[3], value);
      this.params = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'params' field has been set */
    public boolean hasParams() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'params' field */
    public avro.java.gaia.add_symbol_request.Builder clearParams() {
      params = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    public add_symbol_request build() {
      try {
        add_symbol_request record = new add_symbol_request();
        record.symbol_id = fieldSetFlags()[0] ? this.symbol_id : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.symbol_format = fieldSetFlags()[1] ? this.symbol_format : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.symbol_data = fieldSetFlags()[2] ? this.symbol_data : (java.nio.ByteBuffer) defaultValue(fields()[2]);
        record.params = fieldSetFlags()[3] ? this.params : (java.util.Map<java.lang.CharSequence,java.lang.CharSequence>) defaultValue(fields()[3]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
