/**
 * 
 */
package com.gisfederal.request;

/**
 * @author pjacobs
 * Wrap around byte[] (or String) the thing we are sending across the HTTP connection
 */
public class RequestData {
	protected byte[] data;
	/**
	 * 
	 */
	public RequestData(byte[] data) {
		this.data = data;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public String toString() {
		return "byte[].length:"+data.length;
	}
}
