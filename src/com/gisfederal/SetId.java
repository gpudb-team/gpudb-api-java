package com.gisfederal;
import java.util.UUID;

/**
 * The SetId encapsulates the ID (a String/UUID) that is associated with one NamedSet. 
 * @author pjacobs
 */
public class SetId{
	
	private String id;

	/**
	 * Construct a SetId object with a randomly generated ID
	 */
	public SetId() {
		this.id = UUID.randomUUID().toString();
	}
	/**
	 * Construct a SetId object using the given ID
	 * @param id The ID of this SetId
	 */
	public SetId(String id) {
		this.id = id;
	}
	/**
	 * Return the ID for this SetId.
	 * @return The ID.
	 */
	public String get_id() {
		return id;
	}

	// NOTE: these methods are because SetId is a key in the singleton hashtable storage
	/**
	 * Equals method for comparing to SetId objects
	 * @param other The other object.
	 * @return Whether they are equal.
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof SetId)) return false;
		SetId si = (SetId)other;
		if(si.get_id().equals(this.id)) return true;
		else return false;
	}

	/**
	 * Return the object's hashcode.
	 * @return The hashcode.
	 */
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	
	@Override
	public String toString() {
		return this.id;
	}
}