/**
 * 
 */
package com.gisfederal.test;

/**
 * @author pjacobs
 *
 */
public class KeyValueType {
	public String key;
	public String value;
	
	public String OBJECT_ID;
	
	/**
	 * 
	 */
	public KeyValueType() {
		// TODO Auto-generated constructor stub
	}
	
	public KeyValueType(String objectId, String key, String value){
		this.OBJECT_ID = objectId;
		this.key = key;
		this.value = value;
	}
}
