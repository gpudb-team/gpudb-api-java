package com.gisfederal;

/**
 * Encapsulates the source information. The type and sub type. 
 */
public class SourceType {
	public String type;
	public String subType;
	
	/**
	 * Build a source type object from the type and subtype
	 * @param type Source type.
	 * @param subType Source subtype.
	 */
	public SourceType(String type, String subType) {
		this.type = type;
		this.subType = subType;
	}
	
	/**
	 * Build the source type object from the colon separated type and subtype.
	 * @param colonSeparatedType Encoded subtype and type separated by a colon.
	 */
	public SourceType(String colonSeparatedType) {
		// split it up; expecting "source_type:source_sub_type". We will tolerate multiple colon.		
		int sep = colonSeparatedType.lastIndexOf(":");
		if( sep != -1 ) {
			this.type = colonSeparatedType.substring(0, sep);
			this.subType = colonSeparatedType.substring(sep+1, colonSeparatedType.length());
		} else {
			this.type = colonSeparatedType;
			this.subType = "UNKNOWN";
		}
	}
	
	@Override
	public String toString(){
		return this.type+":"+this.subType;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof SourceType)) return false;
		SourceType st = (SourceType)other;
		if(st.type.equals(this.type) && st.subType.equals(this.subType)) return true;
		else return false;
	}

	/**
	 * Return the object's hashcode.
	 * @return The hashcode.
	 */
	@Override
	public int hashCode() {
		return this.subType.hashCode()+this.type.hashCode();
	}

	public static void main(String[] args) {
		
		SourceType st = new SourceType("xxxxxxx");
		System.out.println(" Type " + st.type);
		System.out.println(" SubType " + st.subType);
	}
		
}