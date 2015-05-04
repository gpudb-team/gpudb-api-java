/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gpudb;  
@SuppressWarnings("all")
public class get_set_sorted_response extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"get_set_sorted_response\",\"namespace\":\"avro.java.gpudb\",\"fields\":[{\"name\":\"attribute\",\"type\":\"string\"},{\"name\":\"data_map\",\"type\":{\"type\":\"map\",\"values\":{\"type\":\"array\",\"items\":\"double\"}}},{\"name\":\"set_id\",\"type\":\"string\"},{\"name\":\"start\",\"type\":\"int\"},{\"name\":\"end\",\"type\":\"int\"},{\"name\":\"list\",\"type\":{\"type\":\"array\",\"items\":\"bytes\"}}]}");
  @Deprecated public java.lang.CharSequence attribute;
  @Deprecated public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> data_map;
  @Deprecated public java.lang.CharSequence set_id;
  @Deprecated public int start;
  @Deprecated public int end;
  @Deprecated public java.util.List<java.nio.ByteBuffer> list;

  /**
   * Default constructor.
   */
  public get_set_sorted_response() {}

  /**
   * All-args constructor.
   */
  public get_set_sorted_response(java.lang.CharSequence attribute, java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> data_map, java.lang.CharSequence set_id, java.lang.Integer start, java.lang.Integer end, java.util.List<java.nio.ByteBuffer> list) {
    this.attribute = attribute;
    this.data_map = data_map;
    this.set_id = set_id;
    this.start = start;
    this.end = end;
    this.list = list;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return attribute;
    case 1: return data_map;
    case 2: return set_id;
    case 3: return start;
    case 4: return end;
    case 5: return list;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: attribute = (java.lang.CharSequence)value$; break;
    case 1: data_map = (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>>)value$; break;
    case 2: set_id = (java.lang.CharSequence)value$; break;
    case 3: start = (java.lang.Integer)value$; break;
    case 4: end = (java.lang.Integer)value$; break;
    case 5: list = (java.util.List<java.nio.ByteBuffer>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'attribute' field.
   */
  public java.lang.CharSequence getAttribute() {
    return attribute;
  }

  /**
   * Sets the value of the 'attribute' field.
   * @param value the value to set.
   */
  public void setAttribute(java.lang.CharSequence value) {
    this.attribute = value;
  }

  /**
   * Gets the value of the 'data_map' field.
   */
  public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> getDataMap() {
    return data_map;
  }

  /**
   * Sets the value of the 'data_map' field.
   * @param value the value to set.
   */
  public void setDataMap(java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> value) {
    this.data_map = value;
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
   * Gets the value of the 'list' field.
   */
  public java.util.List<java.nio.ByteBuffer> getList() {
    return list;
  }

  /**
   * Sets the value of the 'list' field.
   * @param value the value to set.
   */
  public void setList(java.util.List<java.nio.ByteBuffer> value) {
    this.list = value;
  }

  /** Creates a new get_set_sorted_response RecordBuilder */
  public static avro.java.gpudb.get_set_sorted_response.Builder newBuilder() {
    return new avro.java.gpudb.get_set_sorted_response.Builder();
  }
  
  /** Creates a new get_set_sorted_response RecordBuilder by copying an existing Builder */
  public static avro.java.gpudb.get_set_sorted_response.Builder newBuilder(avro.java.gpudb.get_set_sorted_response.Builder other) {
    return new avro.java.gpudb.get_set_sorted_response.Builder(other);
  }
  
  /** Creates a new get_set_sorted_response RecordBuilder by copying an existing get_set_sorted_response instance */
  public static avro.java.gpudb.get_set_sorted_response.Builder newBuilder(avro.java.gpudb.get_set_sorted_response other) {
    return new avro.java.gpudb.get_set_sorted_response.Builder(other);
  }
  
  /**
   * RecordBuilder for get_set_sorted_response instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<get_set_sorted_response>
    implements org.apache.avro.data.RecordBuilder<get_set_sorted_response> {

    private java.lang.CharSequence attribute;
    private java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> data_map;
    private java.lang.CharSequence set_id;
    private int start;
    private int end;
    private java.util.List<java.nio.ByteBuffer> list;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gpudb.get_set_sorted_response.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gpudb.get_set_sorted_response.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing get_set_sorted_response instance */
    private Builder(avro.java.gpudb.get_set_sorted_response other) {
            super(avro.java.gpudb.get_set_sorted_response.SCHEMA$);
      if (isValidValue(fields()[0], other.attribute)) {
        this.attribute = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.attribute);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.data_map)) {
        this.data_map = (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>>) data().deepCopy(fields()[1].schema(), other.data_map);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.set_id)) {
        this.set_id = (java.lang.CharSequence) data().deepCopy(fields()[2].schema(), other.set_id);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.start)) {
        this.start = (java.lang.Integer) data().deepCopy(fields()[3].schema(), other.start);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.end)) {
        this.end = (java.lang.Integer) data().deepCopy(fields()[4].schema(), other.end);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.list)) {
        this.list = (java.util.List<java.nio.ByteBuffer>) data().deepCopy(fields()[5].schema(), other.list);
        fieldSetFlags()[5] = true;
      }
    }

    /** Gets the value of the 'attribute' field */
    public java.lang.CharSequence getAttribute() {
      return attribute;
    }
    
    /** Sets the value of the 'attribute' field */
    public avro.java.gpudb.get_set_sorted_response.Builder setAttribute(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.attribute = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'attribute' field has been set */
    public boolean hasAttribute() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'attribute' field */
    public avro.java.gpudb.get_set_sorted_response.Builder clearAttribute() {
      attribute = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'data_map' field */
    public java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> getDataMap() {
      return data_map;
    }
    
    /** Sets the value of the 'data_map' field */
    public avro.java.gpudb.get_set_sorted_response.Builder setDataMap(java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>> value) {
      validate(fields()[1], value);
      this.data_map = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'data_map' field has been set */
    public boolean hasDataMap() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'data_map' field */
    public avro.java.gpudb.get_set_sorted_response.Builder clearDataMap() {
      data_map = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'set_id' field */
    public java.lang.CharSequence getSetId() {
      return set_id;
    }
    
    /** Sets the value of the 'set_id' field */
    public avro.java.gpudb.get_set_sorted_response.Builder setSetId(java.lang.CharSequence value) {
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
    public avro.java.gpudb.get_set_sorted_response.Builder clearSetId() {
      set_id = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'start' field */
    public java.lang.Integer getStart() {
      return start;
    }
    
    /** Sets the value of the 'start' field */
    public avro.java.gpudb.get_set_sorted_response.Builder setStart(int value) {
      validate(fields()[3], value);
      this.start = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'start' field has been set */
    public boolean hasStart() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'start' field */
    public avro.java.gpudb.get_set_sorted_response.Builder clearStart() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'end' field */
    public java.lang.Integer getEnd() {
      return end;
    }
    
    /** Sets the value of the 'end' field */
    public avro.java.gpudb.get_set_sorted_response.Builder setEnd(int value) {
      validate(fields()[4], value);
      this.end = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'end' field has been set */
    public boolean hasEnd() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'end' field */
    public avro.java.gpudb.get_set_sorted_response.Builder clearEnd() {
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'list' field */
    public java.util.List<java.nio.ByteBuffer> getList() {
      return list;
    }
    
    /** Sets the value of the 'list' field */
    public avro.java.gpudb.get_set_sorted_response.Builder setList(java.util.List<java.nio.ByteBuffer> value) {
      validate(fields()[5], value);
      this.list = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'list' field has been set */
    public boolean hasList() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'list' field */
    public avro.java.gpudb.get_set_sorted_response.Builder clearList() {
      list = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    @Override
    public get_set_sorted_response build() {
      try {
        get_set_sorted_response record = new get_set_sorted_response();
        record.attribute = fieldSetFlags()[0] ? this.attribute : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.data_map = fieldSetFlags()[1] ? this.data_map : (java.util.Map<java.lang.CharSequence,java.util.List<java.lang.Double>>) defaultValue(fields()[1]);
        record.set_id = fieldSetFlags()[2] ? this.set_id : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.start = fieldSetFlags()[3] ? this.start : (java.lang.Integer) defaultValue(fields()[3]);
        record.end = fieldSetFlags()[4] ? this.end : (java.lang.Integer) defaultValue(fields()[4]);
        record.list = fieldSetFlags()[5] ? this.list : (java.util.List<java.nio.ByteBuffer>) defaultValue(fields()[5]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
