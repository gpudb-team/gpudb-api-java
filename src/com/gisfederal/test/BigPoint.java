package com.gisfederal.test;

/**
 * 
 */

/**
 * @author pjacobs
 *
 */
public class BigPoint {
	// private or public it will get picked up by the getDeclaredFields
	public String msg_id;
	public double x;
	public double y;	
	public int timestamp;
	public String source;
	public String group_id;
	
	public String OBJECT_ID;

	public BigPoint() {} // needed for reflection (in get set)
	
	public BigPoint(String OBJECT_ID, String msg_id, double x, double y, int timestamp, String source, String group_id) {
		this.msg_id = msg_id;
		this.x = x;
		this.y = y;
		this.timestamp = timestamp;
		this.source = source;
		this.group_id = group_id;
		this.OBJECT_ID = OBJECT_ID;
	}

	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof BigPoint))return false;
		BigPoint p = (BigPoint)other;
		if(p.msg_id.equals(this.msg_id) && p.x == this.x && p.y == this.y &&
				p.timestamp == this.timestamp && p.source.equals(this.source) && p.group_id.equals(this.group_id)) return true;
		else return false;
	}

	@Override
	public String toString(){
		return "object_id"+OBJECT_ID+"msg_id:"+msg_id+" x:"+x+" y:"+y+" timestamp:"+timestamp+" source:"+source+" group_id:"+group_id;
	}
}