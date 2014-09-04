package com.gisfederal.semantic.types;

public enum AnnotationAttributeEnum {
	
	STORE_ONLY("store_only"),
	TEXT_SEARCH("text_search");
	
	private final String attribute;
	
	private AnnotationAttributeEnum(String attribute) {
		this.attribute = attribute;
	}
	
	public String attribute() {
		return attribute;
	}
	
	public static void main(String[] args) {
		System.out.println("STORE_ONLY = " + AnnotationAttributeEnum.STORE_ONLY.attribute());
		System.out.println("TEXT_SEARCH = " + AnnotationAttributeEnum.TEXT_SEARCH.attribute());
	}

}
