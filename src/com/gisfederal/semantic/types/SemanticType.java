package com.gisfederal.semantic.types;



/**
 * All semantic types inherit from this class.
 */
public abstract class SemanticType {
	/**
	 * This defines the field that we use to determine what gpudb types combine together to form one semantic type. Empty if it's one-to-one.
	 */
	public static String groupingFieldName;
	
	/**
	 * The unique value of the grouping field which defines this semantic type.
	 */
	public String groupingField;

	/**
	 * The different semantic types.  Each of these needs to match up with a java class.
	 */	
	public static SemanticTypeEnum type;
	
	// NOTE: put features map here?
}
