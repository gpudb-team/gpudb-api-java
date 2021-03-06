/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gpudb;  
@SuppressWarnings("all")
public class turn_off_response extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"turn_off_response\",\"namespace\":\"avro.java.gpudb\",\"fields\":[{\"name\":\"map\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"array\",\"items\":\"double\"}}}]}");
  @Deprecated public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> map;

  /**
   * Default constructor.
   */
  public turn_off_response() {}

  /**
   * All-args constructor.
   */
  public turn_off_response(java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> map) {
    this.map = map;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return map;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: map = (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'map' field.
   */
  public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> getMap() {
    return map;
  }

  /**
   * Sets the value of the 'map' field.
   * @param value the value to set.
   */
  public void setMap(java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> value) {
    this.map = value;
  }

  /** Creates a new turn_off_response RecordBuilder */
  public static avro.java.gpudb.turn_off_response.Builder newBuilder() {
    return new avro.java.gpudb.turn_off_response.Builder();
  }
  
  /** Creates a new turn_off_response RecordBuilder by copying an existing Builder */
  public static avro.java.gpudb.turn_off_response.Builder newBuilder(avro.java.gpudb.turn_off_response.Builder other) {
    return new avro.java.gpudb.turn_off_response.Builder(other);
  }
  
  /** Creates a new turn_off_response RecordBuilder by copying an existing turn_off_response instance */
  public static avro.java.gpudb.turn_off_response.Builder newBuilder(avro.java.gpudb.turn_off_response other) {
    return new avro.java.gpudb.turn_off_response.Builder(other);
  }
  
  /**
   * RecordBuilder for turn_off_response instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<turn_off_response>
    implements org.apache.avro.data.RecordBuilder<turn_off_response> {

    private java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> map;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gpudb.turn_off_response.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gpudb.turn_off_response.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing turn_off_response instance */
    private Builder(avro.java.gpudb.turn_off_response other) {
            super(avro.java.gpudb.turn_off_response.SCHEMA$);
      if (isValidValue(fields()[0], other.map)) {
        this.map = (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>>) data().deepCopy(fields()[0].schema(), other.map);
        fieldSetFlags()[0] = true;
      }
    }

    /** Gets the value of the 'map' field */
    public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> getMap() {
      return map;
    }
    
    /** Sets the value of the 'map' field */
    public avro.java.gpudb.turn_off_response.Builder setMap(java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> value) {
      validate(fields()[0], value);
      this.map = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'map' field has been set */
    public boolean hasMap() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'map' field */
    public avro.java.gpudb.turn_off_response.Builder clearMap() {
      map = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    public turn_off_response build() {
      try {
        turn_off_response record = new turn_off_response();
        record.map = fieldSetFlags()[0] ? this.map : (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>>) defaultValue(fields()[0]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
