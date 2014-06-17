package com.gisfederal.semantic.types;

import java.util.Map;

public class GenericSemanticType extends SemanticType {
	public static final String groupingFieldName = "";
	public static final SemanticTypeEnum type = SemanticTypeEnum.GENERICOBJECT;
	
	public Map<String,String> features;
	public GenericSemanticType() {}
	
	public GenericSemanticType(Map<String, String> features) {
		this.features = features;
	}	
}
