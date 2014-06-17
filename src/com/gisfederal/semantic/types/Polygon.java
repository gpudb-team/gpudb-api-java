package com.gisfederal.semantic.types;

import java.util.Map;

public class Polygon extends SemanticType {
	public static final String groupingFieldName = "POLYGON_ID";
	public static final SemanticTypeEnum type = SemanticTypeEnum.POLYGON2D; // NOTE: polygon3d??
	
	public String wkt;
	public Map<String,String> features;
	
	public Polygon(){}
	
	public Polygon(String wkt, String groupingField, Map<String, String> features) {
		this.wkt = wkt;
		this.features = features;
		this.groupingField = groupingField;
	}
	
	public String toString() {
		return "wkt:"+this.wkt+" "+groupingFieldName+": "+this.groupingField+" features.size():"+features.size();
	}
}
