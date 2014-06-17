/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gaia;  
@SuppressWarnings("all")
public class register_type_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"register_type_request\",\"namespace\":\"avro.java.gaia\",\"fields\":[{\"name\":\"type_definition\",\"type\":\"string\"},{\"name\":\"annotation\",\"type\":\"string\"},{\"name\":\"label\",\"type\":\"string\"},{\"name\":\"semantic_type\",\"type\":\"string\"}]}");
  @Deprecated public java.lang.CharSequence type_definition;
  @Deprecated public java.lang.CharSequence annotation;
  @Deprecated public java.lang.CharSequence label;
  @Deprecated public java.lang.CharSequence semantic_type;

  /**
   * Default constructor.
   */
  public register_type_request() {}

  /**
   * All-args constructor.
   */
  public register_type_request(java.lang.CharSequence type_definition, java.lang.CharSequence annotation, java.lang.CharSequence label, java.lang.CharSequence semantic_type) {
    this.type_definition = type_definition;
    this.annotation = annotation;
    this.label = label;
    this.semantic_type = semantic_type;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return type_definition;
    case 1: return annotation;
    case 2: return label;
    case 3: return semantic_type;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: type_definition = (java.lang.CharSequence)value$; break;
    case 1: annotation = (java.lang.CharSequence)value$; break;
    case 2: label = (java.lang.CharSequence)value$; break;
    case 3: semantic_type = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'type_definition' field.
   */
  public java.lang.CharSequence getTypeDefinition() {
    return type_definition;
  }

  /**
   * Sets the value of the 'type_definition' field.
   * @param value the value to set.
   */
  public void setTypeDefinition(java.lang.CharSequence value) {
    this.type_definition = value;
  }

  /**
   * Gets the value of the 'annotation' field.
   */
  public java.lang.CharSequence getAnnotation() {
    return annotation;
  }

  /**
   * Sets the value of the 'annotation' field.
   * @param value the value to set.
   */
  public void setAnnotation(java.lang.CharSequence value) {
    this.annotation = value;
  }

  /**
   * Gets the value of the 'label' field.
   */
  public java.lang.CharSequence getLabel() {
    return label;
  }

  /**
   * Sets the value of the 'label' field.
   * @param value the value to set.
   */
  public void setLabel(java.lang.CharSequence value) {
    this.label = value;
  }

  /**
   * Gets the value of the 'semantic_type' field.
   */
  public java.lang.CharSequence getSemanticType() {
    return semantic_type;
  }

  /**
   * Sets the value of the 'semantic_type' field.
   * @param value the value to set.
   */
  public void setSemanticType(java.lang.CharSequence value) {
    this.semantic_type = value;
  }

  /** Creates a new register_type_request RecordBuilder */
  public static avro.java.gaia.register_type_request.Builder newBuilder() {
    return new avro.java.gaia.register_type_request.Builder();
  }
  
  /** Creates a new register_type_request RecordBuilder by copying an existing Builder */
  public static avro.java.gaia.register_type_request.Builder newBuilder(avro.java.gaia.register_type_request.Builder other) {
    return new avro.java.gaia.register_type_request.Builder(other);
  }
  
  /** Creates a new register_type_request RecordBuilder by copying an existing register_type_request instance */
  public static avro.java.gaia.register_type_request.Builder newBuilder(avro.java.gaia.register_type_request other) {
    return new avro.java.gaia.register_type_request.Builder(other);
  }
  
  /**
   * RecordBuilder for register_type_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<register_type_request>
    implements org.apache.avro.data.RecordBuilder<register_type_request> {

    private java.lang.CharSequence type_definition;
    private java.lang.CharSequence annotation;
    private java.lang.CharSequence label;
    private java.lang.CharSequence semantic_type;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gaia.register_type_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gaia.register_type_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing register_type_request instance */
    private Builder(avro.java.gaia.register_type_request other) {
            super(avro.java.gaia.register_type_request.SCHEMA$);
      if (isValidValue(fields()[0], other.type_definition)) {
        this.type_definition = (java.lang.CharSequence) data().deepCopy(fields()[0].schema(), other.type_definition);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.annotation)) {
        this.annotation = (java.lang.CharSequence) data().deepCopy(fields()[1].schema(), other.annotation);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.label)) {
        this.label = (java.lang.CharSequence) data().deepCopy(fields()[2].schema(), other.label);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.semantic_type)) {
        this.semantic_type = (java.lang.CharSequence) data().deepCopy(fields()[3].schema(), other.semantic_type);
        fieldSetFlags()[3] = true;
      }
    }

    /** Gets the value of the 'type_definition' field */
    public java.lang.CharSequence getTypeDefinition() {
      return type_definition;
    }
    
    /** Sets the value of the 'type_definition' field */
    public avro.java.gaia.register_type_request.Builder setTypeDefinition(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.type_definition = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'type_definition' field has been set */
    public boolean hasTypeDefinition() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'type_definition' field */
    public avro.java.gaia.register_type_request.Builder clearTypeDefinition() {
      type_definition = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'annotation' field */
    public java.lang.CharSequence getAnnotation() {
      return annotation;
    }
    
    /** Sets the value of the 'annotation' field */
    public avro.java.gaia.register_type_request.Builder setAnnotation(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.annotation = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'annotation' field has been set */
    public boolean hasAnnotation() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'annotation' field */
    public avro.java.gaia.register_type_request.Builder clearAnnotation() {
      annotation = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'label' field */
    public java.lang.CharSequence getLabel() {
      return label;
    }
    
    /** Sets the value of the 'label' field */
    public avro.java.gaia.register_type_request.Builder setLabel(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.label = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'label' field has been set */
    public boolean hasLabel() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'label' field */
    public avro.java.gaia.register_type_request.Builder clearLabel() {
      label = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'semantic_type' field */
    public java.lang.CharSequence getSemanticType() {
      return semantic_type;
    }
    
    /** Sets the value of the 'semantic_type' field */
    public avro.java.gaia.register_type_request.Builder setSemanticType(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.semantic_type = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'semantic_type' field has been set */
    public boolean hasSemanticType() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'semantic_type' field */
    public avro.java.gaia.register_type_request.Builder clearSemanticType() {
      semantic_type = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    public register_type_request build() {
      try {
        register_type_request record = new register_type_request();
        record.type_definition = fieldSetFlags()[0] ? this.type_definition : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.annotation = fieldSetFlags()[1] ? this.annotation : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.label = fieldSetFlags()[2] ? this.label : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.semantic_type = fieldSetFlags()[3] ? this.semantic_type : (java.lang.CharSequence) defaultValue(fields()[3]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
