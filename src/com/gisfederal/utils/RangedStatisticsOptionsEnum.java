package com.gisfederal.utils;

public enum RangedStatisticsOptionsEnum {
	
	COUNT("count"),
	MEAN("mean"),
	STDV("stdv"),
	VARIANCE("variance"),
	SKEW("skew"),
	KURTOSIS("kurtosis"),
	SUM("sum"),
	MIN("min"),
	MAX("max"),
	FIRST("first"),
	LAST("last"),
	WEIGHTED_AVERAGE("weighted_average");
	
	private String value;
	
	private RangedStatisticsOptionsEnum(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
}
