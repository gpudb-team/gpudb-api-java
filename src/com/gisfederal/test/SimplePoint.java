package com.gisfederal.test;

public class SimplePoint {
	public double x;
	public double y;
	
	public String OBJECT_ID;
	
	public SimplePoint() {}
	
	public SimplePoint(String objectid, double x, double y) {
		this.x = x;
		this.y = y;
		this.OBJECT_ID = objectid;
	}
}
