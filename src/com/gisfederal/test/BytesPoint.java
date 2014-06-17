/**
 * 
 */
package com.gisfederal.test;

import java.nio.ByteBuffer;

/**
 * @author pjacobs
 *
 */
public class BytesPoint {
	public String msg_id;
	public double x;
	public double y;	
	public int timestamp;
	public String source;
	public String group_id;
	public ByteBuffer byte_data;
	
	public String OBJECT_ID;

	public BytesPoint() {} // reflection (get set)

	public BytesPoint(String obj_id, String msg_id, double x, double y, int timestamp, String source, String group_id, ByteBuffer byte_data) {
		this.msg_id = msg_id;
		this.x = x;
		this.y = y;
		this.timestamp = timestamp;
		this.source = source;
		this.group_id = group_id;
		this.byte_data = byte_data;
		this.OBJECT_ID = obj_id;
	}
	
	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof BytesPoint))return false;
		BytesPoint p = (BytesPoint)other;
		if(p.msg_id.toString().equals(this.msg_id.toString()) && p.x == this.x && p.y == this.y &&
			p.timestamp == this.timestamp && p.source.toString().equals(this.source.toString()) && p.group_id.toString().equals(this.group_id.toString())
			&& p.byte_data.equals(this.byte_data)){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString(){		
		return "object_id"+OBJECT_ID+"msg_id:"+msg_id+" x:"+x+" y:"+y+" timestamp:"+timestamp+" source:"+source+" group_id:"+group_id+" byte_data:"
				+byte_data.toString();
	}
}
