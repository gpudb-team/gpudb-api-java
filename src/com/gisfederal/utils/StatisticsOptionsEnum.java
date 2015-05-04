package com.gisfederal.utils;

public enum StatisticsOptionsEnum {
	
	COUNT("count"),
	MEAN("mean"),
	STDV("stdv"),
	VARIANCE("variance"),
	SKEW("skew"),
	KURTOSIS("kurtosis"),
	SUM("sum"),
	MIN("min"),
	MAX("max"),
	WEIGHTED_AVERAGE("weighted_average"),
	CARDINALITY("cardinality"),
	ESTIMATED_CARDINALITY("estimated_cardinality");
	
	private String value;
	
	private StatisticsOptionsEnum(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
