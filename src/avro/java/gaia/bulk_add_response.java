/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gaia;  
@SuppressWarnings("all")
public class bulk_add_response extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"bulk_add_response\",\"namespace\":\"avro.java.gaia\",\"fields\":[{\"name\":\"OBJECT_IDs\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"set_id\",\"type\":\"string\"},{\"name\":\"list\",\"type\":{\"type\":\"array\",\"items\":\"bytes\"}},{\"name\":\"list_str\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"list_encoding\",\"type\":\"string\"},{\"name\":\"matching_triggers\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":\"string\"}}},{\"name\":\"matching_qualifiers\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":\"double\"}}},{\"name\":\"nonmatching_triggers\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":\"string\"}}},{\"name\":\"nonmatching_qualifiers\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":\"double\"}}}]}");
  @Deprecated public java.util.List<java.lang.CharSequence> OBJECT_IDs;
  @Deprecated public java.lang.CharSequence set_id;
  @Deprecated public java.util.List<java.nio.ByteBuffer> list;
  @Deprecated public java.util.List<java.lang.CharSequence> list_str;
  @Deprecated public java.lang.CharSequence list_encoding;
  @Deprecated public java.util.List<java.util.List<java.lang.CharSequence>> matching_triggers;
  @Deprecated public java.util.List<java.util.List<java.lang.Double>> matching_qualifiers;
  @Deprecated public java.util.List<java.util.List<java.lang.CharSequence>> nonmatching_triggers;
  @Deprecated public java.util.List<java.util.List<java.lang.Double>> nonmatching_qualifiers;

  /**
   * Default constructor.
   */
  public bulk_add_response() {}

  /**
   * All-args constructor.
   */
  public bulk_add_response(java.util.List<java.lang.CharSequence> OBJECT_IDs, java.lang.CharSequence set_id, java.util.List<java.nio.ByteBuffer> list, java.util.List<java.lang.CharSequence> list_str, java.lang.CharSequence list_encoding, java.util.List<java.util.List<java.lang.CharSequence>> matching_triggers, java.util.List<java.util.List<java.lang.Double>> matching_qualifiers, java.util.List<java.util.List<java.lang.CharSequence>> nonmatching_triggers, java.util.List<java.util.List<java.lang.Double>> nonmatching_qualifiers) {
    this.OBJECT_IDs = OBJECT_IDs;
    this.set_id = set_id;
    this.list = list;
    this.list_str = list_str;
    this.list_encoding = list_encoding;
    this.matching_triggers = matching_triggers;
    this.matching_qualifiers = matching_qualifiers;
    this.nonmatching_triggers = nonmatching_triggers;
    this.nonmatching_qualifiers = nonmatching_qualifiers;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return OBJECT_IDs;
    case 1: return set_id;
    case 2: return list;
    case 3: return list_str;
    case 4: return list_encoding;
    case 5: return matching_triggers;
    case 6: return matching_qualifiers;
    case 7: return nonmatching_triggers;
    case 8: return nonmatching_qualifiers;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: OBJECT_IDs = (java.util.List<java.lang.CharSequence>)value$; break;
    case 1: set_id = (java.lang.CharSequence)value$; break;
    case 2: list = (java.util.List<java.nio.ByteBuffer>)value$; break;
    case 3: list_str = (java.util.List<java.lang.CharSequence>)value$; break;
    case 4: list_encoding = (java.lang.CharSequence)value$; break;
    case 5: matching_triggers = (java.util.List<java.util.List<java.lang.CharSequence>>)value$; break;
    case 6: matching_qualifiers = (java.util.List<java.util.List<java.lang.Double>>)value$; break;
    case 7: nonmatching_triggers = (java.util.List<java.util.List<java.lang.CharSequence>>)value$; break;
    case 8: nonmatching_qualifiers = (java.util.List<java.util.List<java.lang.Double>>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'OBJECT_IDs' field.
   */
  public java.util.List<java.lang.CharSequence> getOBJECTIDs() {
    return OBJECT_IDs;
  }

  /**
   * Sets the value of the 'OBJECT_IDs' field.
   * @param value the value to set.
   */
  public void setOBJECTIDs(java.util.List<java.lang.CharSequence> value) {
    this.OBJECT_IDs = value;
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

  /**
   * Gets the value of the 'list_str' field.
   */
  public java.util.List<java.lang.CharSequence> getListStr() {
    return list_str;
  }

  /**
   * Sets the value of the 'list_str' field.
   * @param value the value to set.
   */
  public void setListStr(java.util.List<java.lang.CharSequence> value) {
    this.list_str = value;
  }

  /**
   * Gets the value of the 'list_encoding' field.
   */
  public java.lang.CharSequence getListEncoding() {
    return list_encoding;
  }

  /**
   * Sets the value of the 'list_encoding' field.
   * @param value the value to set.
   */
  public void setListEncoding(java.lang.CharSequence value) {
    this.list_encoding = value;
  }

  /**
   * Gets the value of the 'matching_triggers' field.
   */
  public java.util.List<java.util.List<java.lang.CharSequence>> getMatchingTriggers() {
    return matching_triggers;
  }

  /**
   * Sets the value of the 'matching_triggers' field.
   * @param value the value to set.
   */
  public void setMatchingTriggers(java.util.List<java.util.List<java.lang.CharSequence>> value) {
    this.matching_triggers = value;
  }

  /**
   * Gets the value of the 'matching_qualifiers' field.
   */
  public java.util.List<java.util.List<java.lang.Double>> getMatchingQualifiers() {
    return matching_qualifiers;
  }

  /**
   * Sets the value of the 'matching_qualifiers' field.
   * @param value the value to set.
   */
  public void setMatchingQualifiers(java.util.List<java.util.List<java.lang.Double>> value) {
    this.matching_qualifiers = value;
  }

  /**
   * Gets the value of the 'nonmatching_triggers' field.
   */
  public java.util.List<java.util.List<java.lang.CharSequence>> getNonmatchingTriggers() {
    return nonmatching_triggers;
  }

  /**
   * Sets the value of the 'nonmatching_triggers' field.
   * @param value the value to set.
   */
  public void setNonmatchingTriggers(java.util.List<java.util.List<java.lang.CharSequence>> value) {
    this.nonmatching_triggers = value;
  }

  /**
   * Gets the value of the 'nonmatching_qualifiers' field.
   */
  public java.util.List<java.util.List<java.lang.Double>> getNonmatchingQualifiers() {
    return nonmatching_qualifiers;
  }

  /**
   * Sets the value of the 'nonmatching_qualifiers' field.
   * @param value the value to set.
   */
  public void setNonmatchingQualifiers(java.util.List<java.util.List<java.lang.Double>> value) {
    this.nonmatching_qualifiers = value;
  }

  /** Creates a new bulk_add_response RecordBuilder */
  public static avro.java.gaia.bulk_add_response.Builder newBuilder() {
    return new avro.java.gaia.bulk_add_response.Builder();
  }
  
  /** Creates a new bulk_add_response RecordBuilder by copying an existing Builder */
  public static avro.java.gaia.bulk_add_response.Builder newBuilder(avro.java.gaia.bulk_add_response.Builder other) {
    return new avro.java.gaia.bulk_add_response.Builder(other);
  }
  
  /** Creates a new bulk_add_response RecordBuilder by copying an existing bulk_add_response instance */
  public static avro.java.gaia.bulk_add_response.Builder newBuilder(avro.java.gaia.bulk_add_response other) {
    return new avro.java.gaia.bulk_add_response.Builder(other);
  }
  
  /**
   * RecordBuilder for bulk_add_response instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<bulk_add_response>
    implements org.apache.avro.data.RecordBuilder<bulk_add_response> {

    private java.util.List<java.lang.CharSequence> OBJECT_IDs;
    private java.lang.CharSequence set_id;
    private java.util.List<java.nio.ByteBuffer> list;
    private java.util.List<java.lang.CharSequence> list_str;
    private java.lang.CharSequence list_encoding;
    private java.util.List<java.util.List<java.lang.CharSequence>> matching_triggers;
    private java.util.List<java.util.List<java.lang.Double>> matching_qualifiers;
    private java.util.List<java.util.List<java.lang.CharSequence>> nonmatching_triggers;
    private java.util.List<java.util.List<java.lang.Double>> nonmatching_qualifiers;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gaia.bulk_add_response.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gaia.bulk_add_response.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing bulk_add_response instance */
    private Builder(avro.java.gaia.bulk_add_response other) {
            super(avro.java.gaia.bulk_add_response.SCHEMA$);
      if (isValidValue(fields()[0], other.OBJECT_IDs)) {
        this.OBJECT_IDs = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[0].schema(), other.OBJECT_IDs);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.set_id)) {
        this.set_id = (java.lang.CharSequence) data().deepCopy(fields()[1].schema(), other.set_id);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.list)) {
        this.list = (java.util.List<java.nio.ByteBuffer>) data().deepCopy(fields()[2].schema(), other.list);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.list_str)) {
        this.list_str = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[3].schema(), other.list_str);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.list_encoding)) {
        this.list_encoding = (java.lang.CharSequence) data().deepCopy(fields()[4].schema(), other.list_encoding);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.matching_triggers)) {
        this.matching_triggers = (java.util.List<java.util.List<java.lang.CharSequence>>) data().deepCopy(fields()[5].schema(), other.matching_triggers);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.matching_qualifiers)) {
        this.matching_qualifiers = (java.util.List<java.util.List<java.lang.Double>>) data().deepCopy(fields()[6].schema(), other.matching_qualifiers);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.nonmatching_triggers)) {
        this.nonmatching_triggers = (java.util.List<java.util.List<java.lang.CharSequence>>) data().deepCopy(fields()[7].schema(), other.nonmatching_triggers);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.nonmatching_qualifiers)) {
        this.nonmatching_qualifiers = (java.util.List<java.util.List<java.lang.Double>>) data().deepCopy(fields()[8].schema(), other.nonmatching_qualifiers);
        fieldSetFlags()[8] = true;
      }
    }

    /** Gets the value of the 'OBJECT_IDs' field */
    public java.util.List<java.lang.CharSequence> getOBJECTIDs() {
      return OBJECT_IDs;
    }
    
    /** Sets the value of the 'OBJECT_IDs' field */
    public avro.java.gaia.bulk_add_response.Builder setOBJECTIDs(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[0], value);
      this.OBJECT_IDs = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'OBJECT_IDs' field has been set */
    public boolean hasOBJECTIDs() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'OBJECT_IDs' field */
    public avro.java.gaia.bulk_add_response.Builder clearOBJECTIDs() {
      OBJECT_IDs = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'set_id' field */
    public java.lang.CharSequence getSetId() {
      return set_id;
    }
    
    /** Sets the value of the 'set_id' field */
    public avro.java.gaia.bulk_add_response.Builder setSetId(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.set_id = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'set_id' field has been set */
    public boolean hasSetId() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'set_id' field */
    public avro.java.gaia.bulk_add_response.Builder clearSetId() {
      set_id = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'list' field */
    public java.util.List<java.nio.ByteBuffer> getList() {
      return list;
    }
    
    /** Sets the value of the 'list' field */
    public avro.java.gaia.bulk_add_response.Builder setList(java.util.List<java.nio.ByteBuffer> value) {
      validate(fields()[2], value);
      this.list = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'list' field has been set */
    public boolean hasList() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'list' field */
    public avro.java.gaia.bulk_add_response.Builder clearList() {
      list = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'list_str' field */
    public java.util.List<java.lang.CharSequence> getListStr() {
      return list_str;
    }
    
    /** Sets the value of the 'list_str' field */
    public avro.java.gaia.bulk_add_response.Builder setListStr(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[3], value);
      this.list_str = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'list_str' field has been set */
    public boolean hasListStr() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'list_str' field */
    public avro.java.gaia.bulk_add_response.Builder clearListStr() {
      list_str = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'list_encoding' field */
    public java.lang.CharSequence getListEncoding() {
      return list_encoding;
    }
    
    /** Sets the value of the 'list_encoding' field */
    public avro.java.gaia.bulk_add_response.Builder setListEncoding(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.list_encoding = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'list_encoding' field has been set */
    public boolean hasListEncoding() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'list_encoding' field */
    public avro.java.gaia.bulk_add_response.Builder clearListEncoding() {
      list_encoding = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'matching_triggers' field */
    public java.util.List<java.util.List<java.lang.CharSequence>> getMatchingTriggers() {
      return matching_triggers;
    }
    
    /** Sets the value of the 'matching_triggers' field */
    public avro.java.gaia.bulk_add_response.Builder setMatchingTriggers(java.util.List<java.util.List<java.lang.CharSequence>> value) {
      validate(fields()[5], value);
      this.matching_triggers = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'matching_triggers' field has been set */
    public boolean hasMatchingTriggers() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'matching_triggers' field */
    public avro.java.gaia.bulk_add_response.Builder clearMatchingTriggers() {
      matching_triggers = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'matching_qualifiers' field */
    public java.util.List<java.util.List<java.lang.Double>> getMatchingQualifiers() {
      return matching_qualifiers;
    }
    
    /** Sets the value of the 'matching_qualifiers' field */
    public avro.java.gaia.bulk_add_response.Builder setMatchingQualifiers(java.util.List<java.util.List<java.lang.Double>> value) {
      validate(fields()[6], value);
      this.matching_qualifiers = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'matching_qualifiers' field has been set */
    public boolean hasMatchingQualifiers() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'matching_qualifiers' field */
    public avro.java.gaia.bulk_add_response.Builder clearMatchingQualifiers() {
      matching_qualifiers = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /** Gets the value of the 'nonmatching_triggers' field */
    public java.util.List<java.util.List<java.lang.CharSequence>> getNonmatchingTriggers() {
      return nonmatching_triggers;
    }
    
    /** Sets the value of the 'nonmatching_triggers' field */
    public avro.java.gaia.bulk_add_response.Builder setNonmatchingTriggers(java.util.List<java.util.List<java.lang.CharSequence>> value) {
      validate(fields()[7], value);
      this.nonmatching_triggers = value;
      fieldSetFlags()[7] = true;
      return this; 
    }
    
    /** Checks whether the 'nonmatching_triggers' field has been set */
    public boolean hasNonmatchingTriggers() {
      return fieldSetFlags()[7];
    }
    
    /** Clears the value of the 'nonmatching_triggers' field */
    public avro.java.gaia.bulk_add_response.Builder clearNonmatchingTriggers() {
      nonmatching_triggers = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /** Gets the value of the 'nonmatching_qualifiers' field */
    public java.util.List<java.util.List<java.lang.Double>> getNonmatchingQualifiers() {
      return nonmatching_qualifiers;
    }
    
    /** Sets the value of the 'nonmatching_qualifiers' field */
    public avro.java.gaia.bulk_add_response.Builder setNonmatchingQualifiers(java.util.List<java.util.List<java.lang.Double>> value) {
      validate(fields()[8], value);
      this.nonmatching_qualifiers = value;
      fieldSetFlags()[8] = true;
      return this; 
    }
    
    /** Checks whether the 'nonmatching_qualifiers' field has been set */
    public boolean hasNonmatchingQualifiers() {
      return fieldSetFlags()[8];
    }
    
    /** Clears the value of the 'nonmatching_qualifiers' field */
    public avro.java.gaia.bulk_add_response.Builder clearNonmatchingQualifiers() {
      nonmatching_qualifiers = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    @Override
    public bulk_add_response build() {
      try {
        bulk_add_response record = new bulk_add_response();
        record.OBJECT_IDs = fieldSetFlags()[0] ? this.OBJECT_IDs : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[0]);
        record.set_id = fieldSetFlags()[1] ? this.set_id : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.list = fieldSetFlags()[2] ? this.list : (java.util.List<java.nio.ByteBuffer>) defaultValue(fields()[2]);
        record.list_str = fieldSetFlags()[3] ? this.list_str : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[3]);
        record.list_encoding = fieldSetFlags()[4] ? this.list_encoding : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.matching_triggers = fieldSetFlags()[5] ? this.matching_triggers : (java.util.List<java.util.List<java.lang.CharSequence>>) defaultValue(fields()[5]);
        record.matching_qualifiers = fieldSetFlags()[6] ? this.matching_qualifiers : (java.util.List<java.util.List<java.lang.Double>>) defaultValue(fields()[6]);
        record.nonmatching_triggers = fieldSetFlags()[7] ? this.nonmatching_triggers : (java.util.List<java.util.List<java.lang.CharSequence>>) defaultValue(fields()[7]);
        record.nonmatching_qualifiers = fieldSetFlags()[8] ? this.nonmatching_qualifiers : (java.util.List<java.util.List<java.lang.Double>>) defaultValue(fields()[8]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}