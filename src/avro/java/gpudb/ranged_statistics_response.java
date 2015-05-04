/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gpudb;  
@SuppressWarnings("all")
public class ranged_statistics_response extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"ranged_statistics_response\",\"namespace\":\"avro.java.gpudb\",\"fields\":[{\"name\":\"stats\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"array\",\"items\":\"double\"}}}]}");
  @Deprecated public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> stats;

  /**
   * Default constructor.
   */
  public ranged_statistics_response() {}

  /**
   * All-args constructor.
   */
  public ranged_statistics_response(java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> stats) {
    this.stats = stats;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return stats;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: stats = (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'stats' field.
   */
  public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> getStats() {
    return stats;
  }

  /**
   * Sets the value of the 'stats' field.
   * @param value the value to set.
   */
  public void setStats(java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> value) {
    this.stats = value;
  }

  /** Creates a new ranged_statistics_response RecordBuilder */
  public static avro.java.gpudb.ranged_statistics_response.Builder newBuilder() {
    return new avro.java.gpudb.ranged_statistics_response.Builder();
  }
  
  /** Creates a new ranged_statistics_response RecordBuilder by copying an existing Builder */
  public static avro.java.gpudb.ranged_statistics_response.Builder newBuilder(avro.java.gpudb.ranged_statistics_response.Builder other) {
    return new avro.java.gpudb.ranged_statistics_response.Builder(other);
  }
  
  /** Creates a new ranged_statistics_response RecordBuilder by copying an existing ranged_statistics_response instance */
  public static avro.java.gpudb.ranged_statistics_response.Builder newBuilder(avro.java.gpudb.ranged_statistics_response other) {
    return new avro.java.gpudb.ranged_statistics_response.Builder(other);
  }
  
  /**
   * RecordBuilder for ranged_statistics_response instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<ranged_statistics_response>
    implements org.apache.avro.data.RecordBuilder<ranged_statistics_response> {

    private java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> stats;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gpudb.ranged_statistics_response.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gpudb.ranged_statistics_response.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing ranged_statistics_response instance */
    private Builder(avro.java.gpudb.ranged_statistics_response other) {
            super(avro.java.gpudb.ranged_statistics_response.SCHEMA$);
      if (isValidValue(fields()[0], other.stats)) {
        this.stats = (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>>) data().deepCopy(fields()[0].schema(), other.stats);
        fieldSetFlags()[0] = true;
      }
    }

    /** Gets the value of the 'stats' field */
    public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> getStats() {
      return stats;
    }
    
    /** Sets the value of the 'stats' field */
    public avro.java.gpudb.ranged_statistics_response.Builder setStats(java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> value) {
      validate(fields()[0], value);
      this.stats = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'stats' field has been set */
    public boolean hasStats() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'stats' field */
    public avro.java.gpudb.ranged_statistics_response.Builder clearStats() {
      stats = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    public ranged_statistics_response build() {
      try {
        ranged_statistics_response record = new ranged_statistics_response();
        record.stats = fieldSetFlags()[0] ? this.stats : (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>>) defaultValue(fields()[0]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}