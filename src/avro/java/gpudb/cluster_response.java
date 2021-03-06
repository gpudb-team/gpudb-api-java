/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gpudb;  
@SuppressWarnings("all")
public class cluster_response extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"cluster_response\",\"namespace\":\"avro.java.gpudb\",\"fields\":[{\"name\":\"attributes\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"count_map\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"array\",\"items\":\"string\"}}},{\"name\":\"first_pass\",\"type\":\"boolean\"},{\"name\":\"list\",\"type\":{\"type\":\"array\",\"items\":\"double\"}},{\"name\":\"set_id\",\"type\":\"string\"}]}");
  @Deprecated public java.util.List<java.lang.CharSequence> attributes;
  @Deprecated public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> count_map;
  @Deprecated public boolean first_pass;
  @Deprecated public java.util.List<java.lang.Double> list;
  @Deprecated public java.lang.CharSequence set_id;

  /**
   * Default constructor.
   */
  public cluster_response() {}

  /**
   * All-args constructor.
   */
  public cluster_response(java.util.List<java.lang.CharSequence> attributes, java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> count_map, java.lang.Boolean first_pass, java.util.List<java.lang.Double> list, java.lang.CharSequence set_id) {
    this.attributes = attributes;
    this.count_map = count_map;
    this.first_pass = first_pass;
    this.list = list;
    this.set_id = set_id;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return attributes;
    case 1: return count_map;
    case 2: return first_pass;
    case 3: return list;
    case 4: return set_id;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: attributes = (java.util.List<java.lang.CharSequence>)value$; break;
    case 1: count_map = (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>>)value$; break;
    case 2: first_pass = (java.lang.Boolean)value$; break;
    case 3: list = (java.util.List<java.lang.Double>)value$; break;
    case 4: set_id = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'attributes' field.
   */
  public java.util.List<java.lang.CharSequence> getAttributes() {
    return attributes;
  }

  /**
   * Sets the value of the 'attributes' field.
   * @param value the value to set.
   */
  public void setAttributes(java.util.List<java.lang.CharSequence> value) {
    this.attributes = value;
  }

  /**
   * Gets the value of the 'count_map' field.
   */
  public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> getCountMap() {
    return count_map;
  }

  /**
   * Sets the value of the 'count_map' field.
   * @param value the value to set.
   */
  public void setCountMap(java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> value) {
    this.count_map = value;
  }

  /**
   * Gets the value of the 'first_pass' field.
   */
  public java.lang.Boolean getFirstPass() {
    return first_pass;
  }

  /**
   * Sets the value of the 'first_pass' field.
   * @param value the value to set.
   */
  public void setFirstPass(java.lang.Boolean value) {
    this.first_pass = value;
  }

  /**
   * Gets the value of the 'list' field.
   */
  public java.util.List<java.lang.Double> getList() {
    return list;
  }

  /**
   * Sets the value of the 'list' field.
   * @param value the value to set.
   */
  public void setList(java.util.List<java.lang.Double> value) {
    this.list = value;
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

  /** Creates a new cluster_response RecordBuilder */
  public static avro.java.gpudb.cluster_response.Builder newBuilder() {
    return new avro.java.gpudb.cluster_response.Builder();
  }
  
  /** Creates a new cluster_response RecordBuilder by copying an existing Builder */
  public static avro.java.gpudb.cluster_response.Builder newBuilder(avro.java.gpudb.cluster_response.Builder other) {
    return new avro.java.gpudb.cluster_response.Builder(other);
  }
  
  /** Creates a new cluster_response RecordBuilder by copying an existing cluster_response instance */
  public static avro.java.gpudb.cluster_response.Builder newBuilder(avro.java.gpudb.cluster_response other) {
    return new avro.java.gpudb.cluster_response.Builder(other);
  }
  
  /**
   * RecordBuilder for cluster_response instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<cluster_response>
    implements org.apache.avro.data.RecordBuilder<cluster_response> {

    private java.util.List<java.lang.CharSequence> attributes;
    private java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> count_map;
    private boolean first_pass;
    private java.util.List<java.lang.Double> list;
    private java.lang.CharSequence set_id;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gpudb.cluster_response.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gpudb.cluster_response.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing cluster_response instance */
    private Builder(avro.java.gpudb.cluster_response other) {
            super(avro.java.gpudb.cluster_response.SCHEMA$);
      if (isValidValue(fields()[0], other.attributes)) {
        this.attributes = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[0].schema(), other.attributes);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.count_map)) {
        this.count_map = (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>>) data().deepCopy(fields()[1].schema(), other.count_map);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.first_pass)) {
        this.first_pass = (java.lang.Boolean) data().deepCopy(fields()[2].schema(), other.first_pass);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.list)) {
        this.list = (java.util.List<java.lang.Double>) data().deepCopy(fields()[3].schema(), other.list);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.set_id)) {
        this.set_id = (java.lang.CharSequence) data().deepCopy(fields()[4].schema(), other.set_id);
        fieldSetFlags()[4] = true;
      }
    }

    /** Gets the value of the 'attributes' field */
    public java.util.List<java.lang.CharSequence> getAttributes() {
      return attributes;
    }
    
    /** Sets the value of the 'attributes' field */
    public avro.java.gpudb.cluster_response.Builder setAttributes(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[0], value);
      this.attributes = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'attributes' field has been set */
    public boolean hasAttributes() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'attributes' field */
    public avro.java.gpudb.cluster_response.Builder clearAttributes() {
      attributes = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'count_map' field */
    public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> getCountMap() {
      return count_map;
    }
    
    /** Sets the value of the 'count_map' field */
    public avro.java.gpudb.cluster_response.Builder setCountMap(java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>> value) {
      validate(fields()[1], value);
      this.count_map = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'count_map' field has been set */
    public boolean hasCountMap() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'count_map' field */
    public avro.java.gpudb.cluster_response.Builder clearCountMap() {
      count_map = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'first_pass' field */
    public java.lang.Boolean getFirstPass() {
      return first_pass;
    }
    
    /** Sets the value of the 'first_pass' field */
    public avro.java.gpudb.cluster_response.Builder setFirstPass(boolean value) {
      validate(fields()[2], value);
      this.first_pass = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'first_pass' field has been set */
    public boolean hasFirstPass() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'first_pass' field */
    public avro.java.gpudb.cluster_response.Builder clearFirstPass() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'list' field */
    public java.util.List<java.lang.Double> getList() {
      return list;
    }
    
    /** Sets the value of the 'list' field */
    public avro.java.gpudb.cluster_response.Builder setList(java.util.List<java.lang.Double> value) {
      validate(fields()[3], value);
      this.list = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'list' field has been set */
    public boolean hasList() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'list' field */
    public avro.java.gpudb.cluster_response.Builder clearList() {
      list = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'set_id' field */
    public java.lang.CharSequence getSetId() {
      return set_id;
    }
    
    /** Sets the value of the 'set_id' field */
    public avro.java.gpudb.cluster_response.Builder setSetId(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.set_id = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'set_id' field has been set */
    public boolean hasSetId() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'set_id' field */
    public avro.java.gpudb.cluster_response.Builder clearSetId() {
      set_id = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    @Override
    public cluster_response build() {
      try {
        cluster_response record = new cluster_response();
        record.attributes = fieldSetFlags()[0] ? this.attributes : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[0]);
        record.count_map = fieldSetFlags()[1] ? this.count_map : (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.CharSequence>>) defaultValue(fields()[1]);
        record.first_pass = fieldSetFlags()[2] ? this.first_pass : (java.lang.Boolean) defaultValue(fields()[2]);
        record.list = fieldSetFlags()[3] ? this.list : (java.util.List<java.lang.Double>) defaultValue(fields()[3]);
        record.set_id = fieldSetFlags()[4] ? this.set_id : (java.lang.CharSequence) defaultValue(fields()[4]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
