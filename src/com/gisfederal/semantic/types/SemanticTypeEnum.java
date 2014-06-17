package com.gisfederal.semantic.types;

public enum SemanticTypeEnum {
		TRACK("TRACK"), POLYGON2D("POLYGON2D"),POLYGON3D("POLYGON3D"), LINE("LINE"), TIME("TIME"), 
		GENERICOBJECT("GENERICOBJECT"), POINT("POINT"), SHAPE("SHAPE"), EMPTY("");	
		
		private final String name;
		
		private SemanticTypeEnum(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		public static SemanticTypeEnum valueOfWithEmpty(String name) {
			if(name.equals("")) {
				return EMPTY;
			} else {
				return SemanticTypeEnum.valueOf(name);
			}
		}
		
		public static void main(String[] args) {
			
			System.out.println(" Empty name is " + EMPTY.name());
			System.out.println(" Empty string is " + EMPTY.toString());
			
			System.out.println(" GaiaObject name is " + GENERICOBJECT.name());
			System.out.println(" GaiaObject string is " + GENERICOBJECT.toString());
			
		}
		
}
