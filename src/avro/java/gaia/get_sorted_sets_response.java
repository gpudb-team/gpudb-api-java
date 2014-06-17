/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gaia;  
@SuppressWarnings("all")
public class get_sorted_sets_response extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"get_sorted_sets_response\",\"namespace\":\"avro.java.gaia\",\"fields\":[{\"name\":\"lists\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":\"bytes\"}}},{\"name\":\"list_strs\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":\"string\"}}},{\"name\":\"set_ids\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"attr_vals\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":\"double\"}}}]}");
  @Deprecated public java.util.List<java.util.List<java.nio.ByteBuffer>> lists;
  @Deprecated public java.util.List<java.util.List<java.lang.CharSequence>> list_strs;
  @Deprecated public java.util.List<java.lang.CharSequence> set_ids;
  @Deprecated public java.util.List<java.util.List<java.lang.Double>> attr_vals;

  /**
   * Default constructor.
   */
  public get_sorted_sets_response() {}

  /**
   * All-args constructor.
   */
  public get_sorted_sets_response(java.util.List<java.util.List<java.nio.ByteBuffer>> lists, java.util.List<java.util.List<java.lang.CharSequence>> list_strs, java.util.List<java.lang.CharSequence> set_ids, java.util.List<java.util.List<java.lang.Double>> attr_vals) {
    this.lists = lists;
    this.list_strs = list_strs;
    this.set_ids = set_ids;
    this.attr_vals = attr_vals;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return lists;
    case 1: return list_strs;
    case 2: return set_ids;
    case 3: return attr_vals;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: lists = (java.util.List<java.util.List<java.nio.ByteBuffer>>)value$; break;
    case 1: list_strs = (java.util.List<java.util.List<java.lang.CharSequence>>)value$; break;
    case 2: set_ids = (java.util.List<java.lang.CharSequence>)value$; break;
    case 3: attr_vals = (java.util.List<java.util.List<java.lang.Double>>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'lists' field.
   */
  public java.util.List<java.util.List<java.nio.ByteBuffer>> getLists() {
    return lists;
  }

  /**
   * Sets the value of the 'lists' field.
   * @param value the value to set.
   */
  public void setLists(java.util.List<java.util.List<java.nio.ByteBuffer>> value) {
    this.lists = value;
  }

  /**
   * Gets the value of the 'list_strs' field.
   */
  public java.util.List<java.util.List<java.lang.CharSequence>> getListStrs() {
    return list_strs;
  }

  /**
   * Sets the value of the 'list_strs' field.
   * @param value the value to set.
   */
  public void setListStrs(java.util.List<java.util.List<java.lang.CharSequence>> value) {
    this.list_strs = value;
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
   * Gets the value of the 'attr_vals' field.
   */
  public java.util.List<java.util.List<java.lang.Double>> getAttrVals() {
    return attr_vals;
  }

  /**
   * Sets the value of the 'attr_vals' field.
   * @param value the value to set.
   */
  public void setAttrVals(java.util.List<java.util.List<java.lang.Double>> value) {
    this.attr_vals = value;
  }

  /** Creates a new get_sorted_sets_response RecordBuilder */
  public static avro.java.gaia.get_sorted_sets_response.Builder newBuilder() {
    return new avro.java.gaia.get_sorted_sets_response.Builder();
  }
  
  /** Creates a new get_sorted_sets_response RecordBuilder by copying an existing Builder */
  public static avro.java.gaia.get_sorted_sets_response.Builder newBuilder(avro.java.gaia.get_sorted_sets_response.Builder other) {
    return new avro.java.gaia.get_sorted_sets_response.Builder(other);
  }
  
  /** Creates a new get_sorted_sets_response RecordBuilder by copying an existing get_sorted_sets_response instance */
  public static avro.java.gaia.get_sorted_sets_response.Builder newBuilder(avro.java.gaia.get_sorted_sets_response other) {
    return new avro.java.gaia.get_sorted_sets_response.Builder(other);
  }
  
  /**
   * RecordBuilder for get_sorted_sets_response instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<get_sorted_sets_response>
    implements org.apache.avro.data.RecordBuilder<get_sorted_sets_response> {

    private java.util.List<java.util.List<java.nio.ByteBuffer>> lists;
    private java.util.List<java.util.List<java.lang.CharSequence>> list_strs;
    private java.util.List<java.lang.CharSequence> set_ids;
    private java.util.List<java.util.List<java.lang.Double>> attr_vals;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gaia.get_sorted_sets_response.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gaia.get_sorted_sets_response.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing get_sorted_sets_response instance */
    private Builder(avro.java.gaia.get_sorted_sets_response other) {
            super(avro.java.gaia.get_sorted_sets_response.SCHEMA$);
      if (isValidValue(fields()[0], other.lists)) {
        this.lists = (java.util.List<java.util.List<java.nio.ByteBuffer>>) data().deepCopy(fields()[0].schema(), other.lists);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.list_strs)) {
        this.list_strs = (java.util.List<java.util.List<java.lang.CharSequence>>) data().deepCopy(fields()[1].schema(), other.list_strs);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.set_ids)) {
        this.set_ids = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[2].schema(), other.set_ids);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.attr_vals)) {
        this.attr_vals = (java.util.List<java.util.List<java.lang.Double>>) data().deepCopy(fields()[3].schema(), other.attr_vals);
        fieldSetFlags()[3] = true;
      }
    }

    /** Gets the value of the 'lists' field */
    public java.util.List<java.util.List<java.nio.ByteBuffer>> getLists() {
      return lists;
    }
    
    /** Sets the value of the 'lists' field */
    public avro.java.gaia.get_sorted_sets_response.Builder setLists(java.util.List<java.util.List<java.nio.ByteBuffer>> value) {
      validate(fields()[0], value);
      this.lists = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'lists' field has been set */
    public boolean hasLists() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'lists' field */
    public avro.java.gaia.get_sorted_sets_response.Builder clearLists() {
      lists = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'list_strs' field */
    public java.util.List<java.util.List<java.lang.CharSequence>> getListStrs() {
      return list_strs;
    }
    
    /** Sets the value of the 'list_strs' field */
    public avro.java.gaia.get_sorted_sets_response.Builder setListStrs(java.util.List<java.util.List<java.lang.CharSequence>> value) {
      validate(fields()[1], value);
      this.list_strs = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'list_strs' field has been set */
    public boolean hasListStrs() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'list_strs' field */
    public avro.java.gaia.get_sorted_sets_response.Builder clearListStrs() {
      list_strs = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'set_ids' field */
    public java.util.List<java.lang.CharSequence> getSetIds() {
      return set_ids;
    }
    
    /** Sets the value of the 'set_ids' field */
    public avro.java.gaia.get_sorted_sets_response.Builder setSetIds(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[2], value);
      this.set_ids = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'set_ids' field has been set */
    public boolean hasSetIds() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'set_ids' field */
    public avro.java.gaia.get_sorted_sets_response.Builder clearSetIds() {
      set_ids = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'attr_vals' field */
    public java.util.List<java.util.List<java.lang.Double>> getAttrVals() {
      return attr_vals;
    }
    
    /** Sets the value of the 'attr_vals' field */
    public avro.java.gaia.get_sorted_sets_response.Builder setAttrVals(java.util.List<java.util.List<java.lang.Double>> value) {
      validate(fields()[3], value);
      this.attr_vals = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'attr_vals' field has been set */
    public boolean hasAttrVals() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'attr_vals' field */
    public avro.java.gaia.get_sorted_sets_response.Builder clearAttrVals() {
      attr_vals = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    public get_sorted_sets_response build() {
      try {
        get_sorted_sets_response record = new get_sorted_sets_response();
        record.lists = fieldSetFlags()[0] ? this.lists : (java.util.List<java.util.List<java.nio.ByteBuffer>>) defaultValue(fields()[0]);
        record.list_strs = fieldSetFlags()[1] ? this.list_strs : (java.util.List<java.util.List<java.lang.CharSequence>>) defaultValue(fields()[1]);
        record.set_ids = fieldSetFlags()[2] ? this.set_ids : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[2]);
        record.attr_vals = fieldSetFlags()[3] ? this.attr_vals : (java.util.List<java.util.List<java.lang.Double>>) defaultValue(fields()[3]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
