package com.gisfederal.semantic.types;

import java.util.HashMap;
import java.util.Map;

public class Point2D extends SemanticType {
	public static String groupingFieldName = "";
	public static final SemanticTypeEnum type = SemanticTypeEnum.POINT;
	
	public double x;
	public double y;
	public Map<String,String> features;
	
	public Point2D() {}
	
	public Point2D(double x, double y, Map<String, String> features) {
		this.x = x;
		this.y = y;
		this.features = new HashMap<String, String>();
	}
}
