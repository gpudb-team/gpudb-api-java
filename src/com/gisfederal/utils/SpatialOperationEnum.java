package com.gisfederal.utils;

public enum SpatialOperationEnum {
	WITHIN("within"),OVERLAPS("overlaps"),CONTAINS("contains"),INTERSECTS("intersects"),EQUALS("equals"),CROSSES("crosses"),DISJOINT("disjoint");
	private final String name;
	
	private SpatialOperationEnum(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
