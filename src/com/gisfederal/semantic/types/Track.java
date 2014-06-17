package com.gisfederal.semantic.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gisfederal.SetId;

public class Track extends SemanticType {
	public static final String groupingFieldName = "TRACKID";
	public static final SemanticTypeEnum type = SemanticTypeEnum.TRACK;
	
	List<TrackPoint> trackPoints = new ArrayList<TrackPoint>();
	private SetId setID; // setID where this is stored
	
	public SetId getSetID() {
		return setID;
	}

	public void setSetID(SetId setID) {
		this.setID = setID;
	}

	public void addTrackPoint(TrackPoint tp) {
		trackPoints.add(tp);
	}
	
	public List<TrackPoint> getTrackPoints() {
		return trackPoints;
	}
	
	public static class TrackPoint {
		private Double x;
		private Double y;
		private Double z;
		private Double time;
		private Map<String, String> features = new HashMap<String, String>();
		public Double getX() {
			return x;
		}
		public void setX(Double x) {
			this.x = x;
		}
		public Double getY() {
			return y;
		}
		public void setY(Double y) {
			this.y = y;
		}
		public Double getZ() {
			return z;
		}
		public void setZ(Double z) {
			this.z = z;
		}
		public Double getTime() {
			return time;
		}
		public void setTime(Double time) {
			this.time = time;
		}
		public Map<String, String> getFeatures() {
			return features;
		}
	}
	
	public int size() {
		return trackPoints.size();
	}
}
