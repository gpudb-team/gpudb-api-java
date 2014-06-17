package com.gisfederal.semantic.types;

import java.util.Map;

public class Shape extends SemanticType {
	public static final String groupingFieldName = "";
	public static final SemanticTypeEnum type = SemanticTypeEnum.SHAPE; 
	
	public String wkt;
	public Map<String,String> features;
	
	public Shape(){}
	
	public Shape(String wkt, String groupingField, Map<String, String> features) {
		this.wkt = wkt;
		this.features = features;
		this.groupingField = groupingField;
	}
	
	public String toString() {
		return "wkt:"+this.wkt+" "+groupingFieldName+": "+this.groupingField+" features.size():"+features.size();
	}
}
