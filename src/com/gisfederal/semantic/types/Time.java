package com.gisfederal.semantic.types;

import java.util.Map;

public class Time extends SemanticType{
	public static final String groupingFieldName = "";
	public static final SemanticTypeEnum type = SemanticTypeEnum.TIME;
	
	public Double time;
	public Map<String,String> features;
	
	public Time(){}
	
	public Time(Double time, Map<String,String> features){
		this.time = time;
		this.features = features;
	}	
}
