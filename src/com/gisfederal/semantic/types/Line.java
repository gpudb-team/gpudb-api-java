package com.gisfederal.semantic.types;

import java.util.Map;

public class Line extends SemanticType {
	public static String groupingFieldName = "LINE_ID";
	public static final SemanticTypeEnum type = SemanticTypeEnum.LINE;
	
	public String wkt;
	public Map<String,String> features;
	
	public Line(){}
	
	public Line(String wkt, String groupingField, Map<String, String> features) {
		this.wkt = wkt;
		this.features = features;
		this.groupingField = groupingField;
	}
	
	public String toString() {
		return "wkt:"+this.wkt+" "+groupingFieldName+": "+this.groupingField+" features.size():"+features.size();
	}
}
