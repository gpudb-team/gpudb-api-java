package com.gisfederal.utils;

public enum StatisticsOptionsEnum {
	
	MEAN("mean"),
	STDV("stdv"),
	ESTIMATED_CARDINALITY("estimated_cardinality");
	
	private String value;
	
	private StatisticsOptionsEnum(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
