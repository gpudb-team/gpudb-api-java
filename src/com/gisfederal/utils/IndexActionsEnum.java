package com.gisfederal.utils;

public enum IndexActionsEnum {
	CREATE("create"),DELETE("delete"),LIST("list");
	private final String name;

	private IndexActionsEnum(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
