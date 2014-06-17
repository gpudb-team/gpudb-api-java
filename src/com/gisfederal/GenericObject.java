/**
 *  A generic object.  Used for returning objects back from a join but can be used if you just don't have a java class for the namedset.
 */
package com.gisfederal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pjacobs
 *
 */
public class GenericObject {
	public Map<String,String> dataMap;
	
	/**
	 * 
	 */
	public GenericObject() {
		dataMap = new HashMap<String,String>();		
	}
	
	public void addField(String fieldName, String value){
		dataMap.put(fieldName, value);
	}
	
	public String getField(String fieldName) {
		return dataMap.get(fieldName);
	}
	
	public String getObjectId() {
		return dataMap.get(Type.PROP_OBJECT_ID);
	}
			
	@Override
	public String toString() {
		String str = "";
		for(String key : dataMap.keySet()) {
			str += key+":"+dataMap.get(key)+" ";
		}
		return str;
	}
}
