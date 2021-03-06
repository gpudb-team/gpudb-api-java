/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package avro.java.gpudb;  
@SuppressWarnings("all")
public class plot2d_heatmap_cb_request extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"plot2d_heatmap_cb_request\",\"namespace\":\"avro.java.gpudb\",\"fields\":[{\"name\":\"min_x\",\"type\":\"double\"},{\"name\":\"max_x\",\"type\":\"double\"},{\"name\":\"min_y\",\"type\":\"double\"},{\"name\":\"max_y\",\"type\":\"double\"},{\"name\":\"x_attr_name\",\"type\":\"string\"},{\"name\":\"y_attr_name\",\"type\":\"string\"},{\"name\":\"width\",\"type\":\"int\"},{\"name\":\"height\",\"type\":\"int\"},{\"name\":\"projection\",\"type\":\"string\"},{\"name\":\"set_ids\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"cb_attr\",\"type\":\"string\"},{\"name\":\"cb_vals\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"cb_ranges\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"colormaps\",\"type\":{\"type\":\"array\",\"items\":\"string\"}},{\"name\":\"blur_radii\",\"type\":{\"type\":\"array\",\"items\":\"int\"}},{\"name\":\"bg_color\",\"type\":\"long\"},{\"name\":\"gradient_start_colors\",\"type\":{\"type\":\"array\",\"items\":\"long\"}},{\"name\":\"gradient_end_colors\",\"type\":{\"type\":\"array\",\"items\":\"long\"}},{\"name\":\"user_auth_string\",\"type\":\"string\"}]}");
  @Deprecated public double min_x;
  @Deprecated public double max_x;
  @Deprecated public double min_y;
  @Deprecated public double max_y;
  @Deprecated public java.lang.CharSequence x_attr_name;
  @Deprecated public java.lang.CharSequence y_attr_name;
  @Deprecated public int width;
  @Deprecated public int height;
  @Deprecated public java.lang.CharSequence projection;
  @Deprecated public java.util.List<java.lang.CharSequence> set_ids;
  @Deprecated public java.lang.CharSequence cb_attr;
  @Deprecated public java.util.List<java.lang.CharSequence> cb_vals;
  @Deprecated public java.util.List<java.lang.CharSequence> cb_ranges;
  @Deprecated public java.util.List<java.lang.CharSequence> colormaps;
  @Deprecated public java.util.List<java.lang.Integer> blur_radii;
  @Deprecated public long bg_color;
  @Deprecated public java.util.List<java.lang.Long> gradient_start_colors;
  @Deprecated public java.util.List<java.lang.Long> gradient_end_colors;
  @Deprecated public java.lang.CharSequence user_auth_string;

  /**
   * Default constructor.
   */
  public plot2d_heatmap_cb_request() {}

  /**
   * All-args constructor.
   */
  public plot2d_heatmap_cb_request(java.lang.Double min_x, java.lang.Double max_x, java.lang.Double min_y, java.lang.Double max_y, java.lang.CharSequence x_attr_name, java.lang.CharSequence y_attr_name, java.lang.Integer width, java.lang.Integer height, java.lang.CharSequence projection, java.util.List<java.lang.CharSequence> set_ids, java.lang.CharSequence cb_attr, java.util.List<java.lang.CharSequence> cb_vals, java.util.List<java.lang.CharSequence> cb_ranges, java.util.List<java.lang.CharSequence> colormaps, java.util.List<java.lang.Integer> blur_radii, java.lang.Long bg_color, java.util.List<java.lang.Long> gradient_start_colors, java.util.List<java.lang.Long> gradient_end_colors, java.lang.CharSequence user_auth_string) {
    this.min_x = min_x;
    this.max_x = max_x;
    this.min_y = min_y;
    this.max_y = max_y;
    this.x_attr_name = x_attr_name;
    this.y_attr_name = y_attr_name;
    this.width = width;
    this.height = height;
    this.projection = projection;
    this.set_ids = set_ids;
    this.cb_attr = cb_attr;
    this.cb_vals = cb_vals;
    this.cb_ranges = cb_ranges;
    this.colormaps = colormaps;
    this.blur_radii = blur_radii;
    this.bg_color = bg_color;
    this.gradient_start_colors = gradient_start_colors;
    this.gradient_end_colors = gradient_end_colors;
    this.user_auth_string = user_auth_string;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return min_x;
    case 1: return max_x;
    case 2: return min_y;
    case 3: return max_y;
    case 4: return x_attr_name;
    case 5: return y_attr_name;
    case 6: return width;
    case 7: return height;
    case 8: return projection;
    case 9: return set_ids;
    case 10: return cb_attr;
    case 11: return cb_vals;
    case 12: return cb_ranges;
    case 13: return colormaps;
    case 14: return blur_radii;
    case 15: return bg_color;
    case 16: return gradient_start_colors;
    case 17: return gradient_end_colors;
    case 18: return user_auth_string;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: min_x = (java.lang.Double)value$; break;
    case 1: max_x = (java.lang.Double)value$; break;
    case 2: min_y = (java.lang.Double)value$; break;
    case 3: max_y = (java.lang.Double)value$; break;
    case 4: x_attr_name = (java.lang.CharSequence)value$; break;
    case 5: y_attr_name = (java.lang.CharSequence)value$; break;
    case 6: width = (java.lang.Integer)value$; break;
    case 7: height = (java.lang.Integer)value$; break;
    case 8: projection = (java.lang.CharSequence)value$; break;
    case 9: set_ids = (java.util.List<java.lang.CharSequence>)value$; break;
    case 10: cb_attr = (java.lang.CharSequence)value$; break;
    case 11: cb_vals = (java.util.List<java.lang.CharSequence>)value$; break;
    case 12: cb_ranges = (java.util.List<java.lang.CharSequence>)value$; break;
    case 13: colormaps = (java.util.List<java.lang.CharSequence>)value$; break;
    case 14: blur_radii = (java.util.List<java.lang.Integer>)value$; break;
    case 15: bg_color = (java.lang.Long)value$; break;
    case 16: gradient_start_colors = (java.util.List<java.lang.Long>)value$; break;
    case 17: gradient_end_colors = (java.util.List<java.lang.Long>)value$; break;
    case 18: user_auth_string = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'min_x' field.
   */
  public java.lang.Double getMinX() {
    return min_x;
  }

  /**
   * Sets the value of the 'min_x' field.
   * @param value the value to set.
   */
  public void setMinX(java.lang.Double value) {
    this.min_x = value;
  }

  /**
   * Gets the value of the 'max_x' field.
   */
  public java.lang.Double getMaxX() {
    return max_x;
  }

  /**
   * Sets the value of the 'max_x' field.
   * @param value the value to set.
   */
  public void setMaxX(java.lang.Double value) {
    this.max_x = value;
  }

  /**
   * Gets the value of the 'min_y' field.
   */
  public java.lang.Double getMinY() {
    return min_y;
  }

  /**
   * Sets the value of the 'min_y' field.
   * @param value the value to set.
   */
  public void setMinY(java.lang.Double value) {
    this.min_y = value;
  }

  /**
   * Gets the value of the 'max_y' field.
   */
  public java.lang.Double getMaxY() {
    return max_y;
  }

  /**
   * Sets the value of the 'max_y' field.
   * @param value the value to set.
   */
  public void setMaxY(java.lang.Double value) {
    this.max_y = value;
  }

  /**
   * Gets the value of the 'x_attr_name' field.
   */
  public java.lang.CharSequence getXAttrName() {
    return x_attr_name;
  }

  /**
   * Sets the value of the 'x_attr_name' field.
   * @param value the value to set.
   */
  public void setXAttrName(java.lang.CharSequence value) {
    this.x_attr_name = value;
  }

  /**
   * Gets the value of the 'y_attr_name' field.
   */
  public java.lang.CharSequence getYAttrName() {
    return y_attr_name;
  }

  /**
   * Sets the value of the 'y_attr_name' field.
   * @param value the value to set.
   */
  public void setYAttrName(java.lang.CharSequence value) {
    this.y_attr_name = value;
  }

  /**
   * Gets the value of the 'width' field.
   */
  public java.lang.Integer getWidth() {
    return width;
  }

  /**
   * Sets the value of the 'width' field.
   * @param value the value to set.
   */
  public void setWidth(java.lang.Integer value) {
    this.width = value;
  }

  /**
   * Gets the value of the 'height' field.
   */
  public java.lang.Integer getHeight() {
    return height;
  }

  /**
   * Sets the value of the 'height' field.
   * @param value the value to set.
   */
  public void setHeight(java.lang.Integer value) {
    this.height = value;
  }

  /**
   * Gets the value of the 'projection' field.
   */
  public java.lang.CharSequence getProjection() {
    return projection;
  }

  /**
   * Sets the value of the 'projection' field.
   * @param value the value to set.
   */
  public void setProjection(java.lang.CharSequence value) {
    this.projection = value;
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
   * Gets the value of the 'cb_attr' field.
   */
  public java.lang.CharSequence getCbAttr() {
    return cb_attr;
  }

  /**
   * Sets the value of the 'cb_attr' field.
   * @param value the value to set.
   */
  public void setCbAttr(java.lang.CharSequence value) {
    this.cb_attr = value;
  }

  /**
   * Gets the value of the 'cb_vals' field.
   */
  public java.util.List<java.lang.CharSequence> getCbVals() {
    return cb_vals;
  }

  /**
   * Sets the value of the 'cb_vals' field.
   * @param value the value to set.
   */
  public void setCbVals(java.util.List<java.lang.CharSequence> value) {
    this.cb_vals = value;
  }

  /**
   * Gets the value of the 'cb_ranges' field.
   */
  public java.util.List<java.lang.CharSequence> getCbRanges() {
    return cb_ranges;
  }

  /**
   * Sets the value of the 'cb_ranges' field.
   * @param value the value to set.
   */
  public void setCbRanges(java.util.List<java.lang.CharSequence> value) {
    this.cb_ranges = value;
  }

  /**
   * Gets the value of the 'colormaps' field.
   */
  public java.util.List<java.lang.CharSequence> getColormaps() {
    return colormaps;
  }

  /**
   * Sets the value of the 'colormaps' field.
   * @param value the value to set.
   */
  public void setColormaps(java.util.List<java.lang.CharSequence> value) {
    this.colormaps = value;
  }

  /**
   * Gets the value of the 'blur_radii' field.
   */
  public java.util.List<java.lang.Integer> getBlurRadii() {
    return blur_radii;
  }

  /**
   * Sets the value of the 'blur_radii' field.
   * @param value the value to set.
   */
  public void setBlurRadii(java.util.List<java.lang.Integer> value) {
    this.blur_radii = value;
  }

  /**
   * Gets the value of the 'bg_color' field.
   */
  public java.lang.Long getBgColor() {
    return bg_color;
  }

  /**
   * Sets the value of the 'bg_color' field.
   * @param value the value to set.
   */
  public void setBgColor(java.lang.Long value) {
    this.bg_color = value;
  }

  /**
   * Gets the value of the 'gradient_start_colors' field.
   */
  public java.util.List<java.lang.Long> getGradientStartColors() {
    return gradient_start_colors;
  }

  /**
   * Sets the value of the 'gradient_start_colors' field.
   * @param value the value to set.
   */
  public void setGradientStartColors(java.util.List<java.lang.Long> value) {
    this.gradient_start_colors = value;
  }

  /**
   * Gets the value of the 'gradient_end_colors' field.
   */
  public java.util.List<java.lang.Long> getGradientEndColors() {
    return gradient_end_colors;
  }

  /**
   * Sets the value of the 'gradient_end_colors' field.
   * @param value the value to set.
   */
  public void setGradientEndColors(java.util.List<java.lang.Long> value) {
    this.gradient_end_colors = value;
  }

  /**
   * Gets the value of the 'user_auth_string' field.
   */
  public java.lang.CharSequence getUserAuthString() {
    return user_auth_string;
  }

  /**
   * Sets the value of the 'user_auth_string' field.
   * @param value the value to set.
   */
  public void setUserAuthString(java.lang.CharSequence value) {
    this.user_auth_string = value;
  }

  /** Creates a new plot2d_heatmap_cb_request RecordBuilder */
  public static avro.java.gpudb.plot2d_heatmap_cb_request.Builder newBuilder() {
    return new avro.java.gpudb.plot2d_heatmap_cb_request.Builder();
  }
  
  /** Creates a new plot2d_heatmap_cb_request RecordBuilder by copying an existing Builder */
  public static avro.java.gpudb.plot2d_heatmap_cb_request.Builder newBuilder(avro.java.gpudb.plot2d_heatmap_cb_request.Builder other) {
    return new avro.java.gpudb.plot2d_heatmap_cb_request.Builder(other);
  }
  
  /** Creates a new plot2d_heatmap_cb_request RecordBuilder by copying an existing plot2d_heatmap_cb_request instance */
  public static avro.java.gpudb.plot2d_heatmap_cb_request.Builder newBuilder(avro.java.gpudb.plot2d_heatmap_cb_request other) {
    return new avro.java.gpudb.plot2d_heatmap_cb_request.Builder(other);
  }
  
  /**
   * RecordBuilder for plot2d_heatmap_cb_request instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<plot2d_heatmap_cb_request>
    implements org.apache.avro.data.RecordBuilder<plot2d_heatmap_cb_request> {

    private double min_x;
    private double max_x;
    private double min_y;
    private double max_y;
    private java.lang.CharSequence x_attr_name;
    private java.lang.CharSequence y_attr_name;
    private int width;
    private int height;
    private java.lang.CharSequence projection;
    private java.util.List<java.lang.CharSequence> set_ids;
    private java.lang.CharSequence cb_attr;
    private java.util.List<java.lang.CharSequence> cb_vals;
    private java.util.List<java.lang.CharSequence> cb_ranges;
    private java.util.List<java.lang.CharSequence> colormaps;
    private java.util.List<java.lang.Integer> blur_radii;
    private long bg_color;
    private java.util.List<java.lang.Long> gradient_start_colors;
    private java.util.List<java.lang.Long> gradient_end_colors;
    private java.lang.CharSequence user_auth_string;

    /** Creates a new Builder */
    private Builder() {
      super(avro.java.gpudb.plot2d_heatmap_cb_request.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(avro.java.gpudb.plot2d_heatmap_cb_request.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing plot2d_heatmap_cb_request instance */
    private Builder(avro.java.gpudb.plot2d_heatmap_cb_request other) {
            super(avro.java.gpudb.plot2d_heatmap_cb_request.SCHEMA$);
      if (isValidValue(fields()[0], other.min_x)) {
        this.min_x = (java.lang.Double) data().deepCopy(fields()[0].schema(), other.min_x);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.max_x)) {
        this.max_x = (java.lang.Double) data().deepCopy(fields()[1].schema(), other.max_x);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.min_y)) {
        this.min_y = (java.lang.Double) data().deepCopy(fields()[2].schema(), other.min_y);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.max_y)) {
        this.max_y = (java.lang.Double) data().deepCopy(fields()[3].schema(), other.max_y);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.x_attr_name)) {
        this.x_attr_name = (java.lang.CharSequence) data().deepCopy(fields()[4].schema(), other.x_attr_name);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.y_attr_name)) {
        this.y_attr_name = (java.lang.CharSequence) data().deepCopy(fields()[5].schema(), other.y_attr_name);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.width)) {
        this.width = (java.lang.Integer) data().deepCopy(fields()[6].schema(), other.width);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.height)) {
        this.height = (java.lang.Integer) data().deepCopy(fields()[7].schema(), other.height);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.projection)) {
        this.projection = (java.lang.CharSequence) data().deepCopy(fields()[8].schema(), other.projection);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.set_ids)) {
        this.set_ids = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[9].schema(), other.set_ids);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.cb_attr)) {
        this.cb_attr = (java.lang.CharSequence) data().deepCopy(fields()[10].schema(), other.cb_attr);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.cb_vals)) {
        this.cb_vals = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[11].schema(), other.cb_vals);
        fieldSetFlags()[11] = true;
      }
      if (isValidValue(fields()[12], other.cb_ranges)) {
        this.cb_ranges = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[12].schema(), other.cb_ranges);
        fieldSetFlags()[12] = true;
      }
      if (isValidValue(fields()[13], other.colormaps)) {
        this.colormaps = (java.util.List<java.lang.CharSequence>) data().deepCopy(fields()[13].schema(), other.colormaps);
        fieldSetFlags()[13] = true;
      }
      if (isValidValue(fields()[14], other.blur_radii)) {
        this.blur_radii = (java.util.List<java.lang.Integer>) data().deepCopy(fields()[14].schema(), other.blur_radii);
        fieldSetFlags()[14] = true;
      }
      if (isValidValue(fields()[15], other.bg_color)) {
        this.bg_color = (java.lang.Long) data().deepCopy(fields()[15].schema(), other.bg_color);
        fieldSetFlags()[15] = true;
      }
      if (isValidValue(fields()[16], other.gradient_start_colors)) {
        this.gradient_start_colors = (java.util.List<java.lang.Long>) data().deepCopy(fields()[16].schema(), other.gradient_start_colors);
        fieldSetFlags()[16] = true;
      }
      if (isValidValue(fields()[17], other.gradient_end_colors)) {
        this.gradient_end_colors = (java.util.List<java.lang.Long>) data().deepCopy(fields()[17].schema(), other.gradient_end_colors);
        fieldSetFlags()[17] = true;
      }
      if (isValidValue(fields()[18], other.user_auth_string)) {
        this.user_auth_string = (java.lang.CharSequence) data().deepCopy(fields()[18].schema(), other.user_auth_string);
        fieldSetFlags()[18] = true;
      }
    }

    /** Gets the value of the 'min_x' field */
    public java.lang.Double getMinX() {
      return min_x;
    }
    
    /** Sets the value of the 'min_x' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setMinX(double value) {
      validate(fields()[0], value);
      this.min_x = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'min_x' field has been set */
    public boolean hasMinX() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'min_x' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearMinX() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'max_x' field */
    public java.lang.Double getMaxX() {
      return max_x;
    }
    
    /** Sets the value of the 'max_x' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setMaxX(double value) {
      validate(fields()[1], value);
      this.max_x = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'max_x' field has been set */
    public boolean hasMaxX() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'max_x' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearMaxX() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'min_y' field */
    public java.lang.Double getMinY() {
      return min_y;
    }
    
    /** Sets the value of the 'min_y' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setMinY(double value) {
      validate(fields()[2], value);
      this.min_y = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'min_y' field has been set */
    public boolean hasMinY() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'min_y' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearMinY() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'max_y' field */
    public java.lang.Double getMaxY() {
      return max_y;
    }
    
    /** Sets the value of the 'max_y' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setMaxY(double value) {
      validate(fields()[3], value);
      this.max_y = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'max_y' field has been set */
    public boolean hasMaxY() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'max_y' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearMaxY() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'x_attr_name' field */
    public java.lang.CharSequence getXAttrName() {
      return x_attr_name;
    }
    
    /** Sets the value of the 'x_attr_name' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setXAttrName(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.x_attr_name = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'x_attr_name' field has been set */
    public boolean hasXAttrName() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'x_attr_name' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearXAttrName() {
      x_attr_name = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'y_attr_name' field */
    public java.lang.CharSequence getYAttrName() {
      return y_attr_name;
    }
    
    /** Sets the value of the 'y_attr_name' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setYAttrName(java.lang.CharSequence value) {
      validate(fields()[5], value);
      this.y_attr_name = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'y_attr_name' field has been set */
    public boolean hasYAttrName() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'y_attr_name' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearYAttrName() {
      y_attr_name = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'width' field */
    public java.lang.Integer getWidth() {
      return width;
    }
    
    /** Sets the value of the 'width' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setWidth(int value) {
      validate(fields()[6], value);
      this.width = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'width' field has been set */
    public boolean hasWidth() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'width' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearWidth() {
      fieldSetFlags()[6] = false;
      return this;
    }

    /** Gets the value of the 'height' field */
    public java.lang.Integer getHeight() {
      return height;
    }
    
    /** Sets the value of the 'height' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setHeight(int value) {
      validate(fields()[7], value);
      this.height = value;
      fieldSetFlags()[7] = true;
      return this; 
    }
    
    /** Checks whether the 'height' field has been set */
    public boolean hasHeight() {
      return fieldSetFlags()[7];
    }
    
    /** Clears the value of the 'height' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearHeight() {
      fieldSetFlags()[7] = false;
      return this;
    }

    /** Gets the value of the 'projection' field */
    public java.lang.CharSequence getProjection() {
      return projection;
    }
    
    /** Sets the value of the 'projection' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setProjection(java.lang.CharSequence value) {
      validate(fields()[8], value);
      this.projection = value;
      fieldSetFlags()[8] = true;
      return this; 
    }
    
    /** Checks whether the 'projection' field has been set */
    public boolean hasProjection() {
      return fieldSetFlags()[8];
    }
    
    /** Clears the value of the 'projection' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearProjection() {
      projection = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    /** Gets the value of the 'set_ids' field */
    public java.util.List<java.lang.CharSequence> getSetIds() {
      return set_ids;
    }
    
    /** Sets the value of the 'set_ids' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setSetIds(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[9], value);
      this.set_ids = value;
      fieldSetFlags()[9] = true;
      return this; 
    }
    
    /** Checks whether the 'set_ids' field has been set */
    public boolean hasSetIds() {
      return fieldSetFlags()[9];
    }
    
    /** Clears the value of the 'set_ids' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearSetIds() {
      set_ids = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    /** Gets the value of the 'cb_attr' field */
    public java.lang.CharSequence getCbAttr() {
      return cb_attr;
    }
    
    /** Sets the value of the 'cb_attr' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setCbAttr(java.lang.CharSequence value) {
      validate(fields()[10], value);
      this.cb_attr = value;
      fieldSetFlags()[10] = true;
      return this; 
    }
    
    /** Checks whether the 'cb_attr' field has been set */
    public boolean hasCbAttr() {
      return fieldSetFlags()[10];
    }
    
    /** Clears the value of the 'cb_attr' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearCbAttr() {
      cb_attr = null;
      fieldSetFlags()[10] = false;
      return this;
    }

    /** Gets the value of the 'cb_vals' field */
    public java.util.List<java.lang.CharSequence> getCbVals() {
      return cb_vals;
    }
    
    /** Sets the value of the 'cb_vals' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setCbVals(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[11], value);
      this.cb_vals = value;
      fieldSetFlags()[11] = true;
      return this; 
    }
    
    /** Checks whether the 'cb_vals' field has been set */
    public boolean hasCbVals() {
      return fieldSetFlags()[11];
    }
    
    /** Clears the value of the 'cb_vals' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearCbVals() {
      cb_vals = null;
      fieldSetFlags()[11] = false;
      return this;
    }

    /** Gets the value of the 'cb_ranges' field */
    public java.util.List<java.lang.CharSequence> getCbRanges() {
      return cb_ranges;
    }
    
    /** Sets the value of the 'cb_ranges' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setCbRanges(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[12], value);
      this.cb_ranges = value;
      fieldSetFlags()[12] = true;
      return this; 
    }
    
    /** Checks whether the 'cb_ranges' field has been set */
    public boolean hasCbRanges() {
      return fieldSetFlags()[12];
    }
    
    /** Clears the value of the 'cb_ranges' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearCbRanges() {
      cb_ranges = null;
      fieldSetFlags()[12] = false;
      return this;
    }

    /** Gets the value of the 'colormaps' field */
    public java.util.List<java.lang.CharSequence> getColormaps() {
      return colormaps;
    }
    
    /** Sets the value of the 'colormaps' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setColormaps(java.util.List<java.lang.CharSequence> value) {
      validate(fields()[13], value);
      this.colormaps = value;
      fieldSetFlags()[13] = true;
      return this; 
    }
    
    /** Checks whether the 'colormaps' field has been set */
    public boolean hasColormaps() {
      return fieldSetFlags()[13];
    }
    
    /** Clears the value of the 'colormaps' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearColormaps() {
      colormaps = null;
      fieldSetFlags()[13] = false;
      return this;
    }

    /** Gets the value of the 'blur_radii' field */
    public java.util.List<java.lang.Integer> getBlurRadii() {
      return blur_radii;
    }
    
    /** Sets the value of the 'blur_radii' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setBlurRadii(java.util.List<java.lang.Integer> value) {
      validate(fields()[14], value);
      this.blur_radii = value;
      fieldSetFlags()[14] = true;
      return this; 
    }
    
    /** Checks whether the 'blur_radii' field has been set */
    public boolean hasBlurRadii() {
      return fieldSetFlags()[14];
    }
    
    /** Clears the value of the 'blur_radii' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearBlurRadii() {
      blur_radii = null;
      fieldSetFlags()[14] = false;
      return this;
    }

    /** Gets the value of the 'bg_color' field */
    public java.lang.Long getBgColor() {
      return bg_color;
    }
    
    /** Sets the value of the 'bg_color' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setBgColor(long value) {
      validate(fields()[15], value);
      this.bg_color = value;
      fieldSetFlags()[15] = true;
      return this; 
    }
    
    /** Checks whether the 'bg_color' field has been set */
    public boolean hasBgColor() {
      return fieldSetFlags()[15];
    }
    
    /** Clears the value of the 'bg_color' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearBgColor() {
      fieldSetFlags()[15] = false;
      return this;
    }

    /** Gets the value of the 'gradient_start_colors' field */
    public java.util.List<java.lang.Long> getGradientStartColors() {
      return gradient_start_colors;
    }
    
    /** Sets the value of the 'gradient_start_colors' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setGradientStartColors(java.util.List<java.lang.Long> value) {
      validate(fields()[16], value);
      this.gradient_start_colors = value;
      fieldSetFlags()[16] = true;
      return this; 
    }
    
    /** Checks whether the 'gradient_start_colors' field has been set */
    public boolean hasGradientStartColors() {
      return fieldSetFlags()[16];
    }
    
    /** Clears the value of the 'gradient_start_colors' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearGradientStartColors() {
      gradient_start_colors = null;
      fieldSetFlags()[16] = false;
      return this;
    }

    /** Gets the value of the 'gradient_end_colors' field */
    public java.util.List<java.lang.Long> getGradientEndColors() {
      return gradient_end_colors;
    }
    
    /** Sets the value of the 'gradient_end_colors' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setGradientEndColors(java.util.List<java.lang.Long> value) {
      validate(fields()[17], value);
      this.gradient_end_colors = value;
      fieldSetFlags()[17] = true;
      return this; 
    }
    
    /** Checks whether the 'gradient_end_colors' field has been set */
    public boolean hasGradientEndColors() {
      return fieldSetFlags()[17];
    }
    
    /** Clears the value of the 'gradient_end_colors' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearGradientEndColors() {
      gradient_end_colors = null;
      fieldSetFlags()[17] = false;
      return this;
    }

    /** Gets the value of the 'user_auth_string' field */
    public java.lang.CharSequence getUserAuthString() {
      return user_auth_string;
    }
    
    /** Sets the value of the 'user_auth_string' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder setUserAuthString(java.lang.CharSequence value) {
      validate(fields()[18], value);
      this.user_auth_string = value;
      fieldSetFlags()[18] = true;
      return this; 
    }
    
    /** Checks whether the 'user_auth_string' field has been set */
    public boolean hasUserAuthString() {
      return fieldSetFlags()[18];
    }
    
    /** Clears the value of the 'user_auth_string' field */
    public avro.java.gpudb.plot2d_heatmap_cb_request.Builder clearUserAuthString() {
      user_auth_string = null;
      fieldSetFlags()[18] = false;
      return this;
    }

    @Override
    public plot2d_heatmap_cb_request build() {
      try {
        plot2d_heatmap_cb_request record = new plot2d_heatmap_cb_request();
        record.min_x = fieldSetFlags()[0] ? this.min_x : (java.lang.Double) defaultValue(fields()[0]);
        record.max_x = fieldSetFlags()[1] ? this.max_x : (java.lang.Double) defaultValue(fields()[1]);
        record.min_y = fieldSetFlags()[2] ? this.min_y : (java.lang.Double) defaultValue(fields()[2]);
        record.max_y = fieldSetFlags()[3] ? this.max_y : (java.lang.Double) defaultValue(fields()[3]);
        record.x_attr_name = fieldSetFlags()[4] ? this.x_attr_name : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.y_attr_name = fieldSetFlags()[5] ? this.y_attr_name : (java.lang.CharSequence) defaultValue(fields()[5]);
        record.width = fieldSetFlags()[6] ? this.width : (java.lang.Integer) defaultValue(fields()[6]);
        record.height = fieldSetFlags()[7] ? this.height : (java.lang.Integer) defaultValue(fields()[7]);
        record.projection = fieldSetFlags()[8] ? this.projection : (java.lang.CharSequence) defaultValue(fields()[8]);
        record.set_ids = fieldSetFlags()[9] ? this.set_ids : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[9]);
        record.cb_attr = fieldSetFlags()[10] ? this.cb_attr : (java.lang.CharSequence) defaultValue(fields()[10]);
        record.cb_vals = fieldSetFlags()[11] ? this.cb_vals : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[11]);
        record.cb_ranges = fieldSetFlags()[12] ? this.cb_ranges : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[12]);
        record.colormaps = fieldSetFlags()[13] ? this.colormaps : (java.util.List<java.lang.CharSequence>) defaultValue(fields()[13]);
        record.blur_radii = fieldSetFlags()[14] ? this.blur_radii : (java.util.List<java.lang.Integer>) defaultValue(fields()[14]);
        record.bg_color = fieldSetFlags()[15] ? this.bg_color : (java.lang.Long) defaultValue(fields()[15]);
        record.gradient_start_colors = fieldSetFlags()[16] ? this.gradient_start_colors : (java.util.List<java.lang.Long>) defaultValue(fields()[16]);
        record.gradient_end_colors = fieldSetFlags()[17] ? this.gradient_end_colors : (java.util.List<java.lang.Long>) defaultValue(fields()[17]);
        record.user_auth_string = fieldSetFlags()[18] ? this.user_auth_string : (java.lang.CharSequence) defaultValue(fields()[18]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
