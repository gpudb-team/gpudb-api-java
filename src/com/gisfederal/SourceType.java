package com.gisfederal;

/**
 * Encapsulates the source information. The type and sub type. 
 */
public class SourceType {
	public String label;
	
	public SourceType(String label) {
		this.label = label;
	}
	
	@Override
	public String toString(){
		return this.label;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof SourceType)) return false;
		SourceType st = (SourceType)other;
		if(st.label.equals(this.label) ) return true;
		else return false;
	}

	/**
	 * Return the object's hashcode.
	 * @return The hashcode.
	 */
	@Override
	public int hashCode() {
		return this.label.hashCode();
	}

	public static void main(String[] args) {
		
		SourceType st = new SourceType("xxxxxxx");
		System.out.println(" Type " + st.label);
	}
		
}