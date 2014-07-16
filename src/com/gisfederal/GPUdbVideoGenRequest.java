package com.gisfederal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GPUdbVideoGenRequest {

	public enum IntervalType {DISJOINT, GROWTH, OVERLAP};
	
	private double min_x;
	private double max_x;
	private double min_y;
	private double max_y;
	private String x_attr_name;
	private String y_attr_name;
	private int width;
	private int height;
	private String projection;
	private long bg_color;
	private List<String> set_ids;
	private List<String> world_set_ids;
	private List<List<CharSequence>> track_ids;
	private List<Boolean> do_points;
	private List<Boolean> do_shapes;
	private List<Boolean> do_tracks;
	private List<Long> pointcolors;
	private List<Integer> pointsizes;
	private List<String> pointshapes;
	private List<Integer> shapelinewidths;
	private List<Long> shapelinecolors;
	private List<Long> shapefillcolors;
	private List<Integer> tracklinewidths;
	private List<Long> tracklinecolors;
	private List<Integer> trackmarkersizes;
	private List<Long> trackmarkercolors;
	private List<String> trackmarkershapes;
	private List<Long> trackheadcolors;
	private List<Integer> trackheadsizes;
	private List<String> trackheadshapes;
	private List<List<Double>> time_intervals;
	private String video_style;
	private String session_key;

	public double getMin_x() {
		return min_x;
	}

	public double getMax_x() {
		return max_x;
	}

	public double getMin_y() {
		return min_y;
	}

	public double getMax_y() {
		return max_y;
	}

	public String getX_attr_name() {
		return x_attr_name;
	}

	public String getY_attr_name() {
		return y_attr_name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getProjection() {
		return projection;
	}

	public long getBg_color() {
		return bg_color;
	}

	public List<String> getSet_ids() {
		return set_ids;
	}

	public List<String> getWorld_set_ids() {
		return world_set_ids;
	}

	public List<List<CharSequence>> getTrack_ids() {
		return track_ids;
	}

	public List<Boolean> getDo_points() {
		return do_points;
	}

	public List<Boolean> getDo_shapes() {
		return do_shapes;
	}

	public List<Boolean> getDo_tracks() {
		return do_tracks;
	}

	public List<Long> getPointcolors() {
		return pointcolors;
	}

	public List<Integer> getPointsizes() {
		return pointsizes;
	}

	public List<String> getPointshapes() {
		return pointshapes;
	}

	public List<Integer> getShapelinewidths() {
		return shapelinewidths;
	}

	public List<Long> getShapelinecolors() {
		return shapelinecolors;
	}

	public List<Long> getShapefillcolors() {
		return shapefillcolors;
	}

	public List<Integer> getTracklinewidths() {
		return tracklinewidths;
	}

	public List<Long> getTracklinecolors() {
		return tracklinecolors;
	}

	public List<Integer> getTrackmarkersizes() {
		return trackmarkersizes;
	}

	public List<Long> getTrackmarkercolors() {
		return trackmarkercolors;
	}

	public List<String> getTrackmarkershapes() {
		return trackmarkershapes;
	}

	public List<Long> getTrackheadcolors() {
		return trackheadcolors;
	}

	public List<Integer> getTrackheadsizes() {
		return trackheadsizes;
	}

	public List<String> getTrackheadshapes() {
		return trackheadshapes;
	}

	public List<List<Double>> getTime_intervals() {
		return time_intervals;
	}

	public String getVideo_style() {
		return video_style;
	}

	public String getSession_key() {
		return session_key;
	}

	public static class Builder {

		// Manadtory params
		private final double min_x;
		private final double max_x;
		private final double min_y;
		private final double max_y;
		private final String x_attr_name;
		private final String y_attr_name;
		private final List<String> set_ids = new ArrayList<String>();
		private final List<String> world_set_ids = new ArrayList<String>();
		private final List<List<Double>> time_intervals = new ArrayList<List<Double>>();

		// Optional params
		private int width = 1024;
		private int height = 1024;
		private String projection = "plate_carree";
		private long bg_color = 0x00;

		private List<List<CharSequence>> track_ids = new ArrayList<List<CharSequence>>();
		
		
		private List<Boolean> do_points = new ArrayList<Boolean>(Arrays
				.asList(new Boolean[] { Boolean.TRUE }));
		private List<Boolean> do_shapes = new ArrayList<Boolean>(Arrays
				.asList(new Boolean[] { Boolean.TRUE }));
		private List<Boolean> do_tracks = new ArrayList<Boolean>(Arrays
				.asList(new Boolean[] { Boolean.TRUE }));

		private List<Long> pointcolors = new ArrayList<Long>(Arrays
				.asList(new Long[] { 0xFFFF0000L }));
		private List<Integer> pointsizes = new ArrayList<Integer>(Arrays.asList(new Integer[] { 3 }));
		private List<String> pointshapes = new ArrayList<String>(Arrays
				.asList(new String[] { "square" }));

		private List<Integer> shapelinewidths = new ArrayList<Integer>(Arrays
				.asList(new Integer[] { 4 }));
		private List<Long> shapelinecolors = new ArrayList<Long>(Arrays
				.asList(new Long[] { 0xFF00FFFFL }));
		private List<Long> shapefillcolors = new ArrayList<Long>(Arrays.asList(new Long[] { -1L }));

		private List<Integer> tracklinewidths = new ArrayList<Integer>(Arrays
				.asList(new Integer[] { 2 }));
		private List<Long> tracklinecolors = new ArrayList<Long>(Arrays
				.asList(new Long[] { 0xFF0000FFL }));
		private List<Integer> trackmarkersizes = new ArrayList<Integer>(Arrays
				.asList(new Integer[] { 4 }));
		private List<Long> trackmarkercolors = new ArrayList<Long>(Arrays
				.asList(new Long[] { 0xFFFF00FFL }));
		private List<String> trackmarkershapes = new ArrayList<String>(Arrays
				.asList(new String[] { "none" }));
		private List<Long> trackheadcolors = new ArrayList<Long>(Arrays
				.asList(new Long[] { 0xFF00FFFFL }));
		private List<Integer> trackheadsizes = new ArrayList<Integer>(Arrays
				.asList(new Integer[] { 10 }));
		private List<String> trackheadshapes = new ArrayList<String>(Arrays
				.asList(new String[] { "hollowdiamond" }));

		private String video_style = "normal";
		private String session_key = "SESSION_" + UUID.randomUUID();

		public Builder(double min_x, double max_x, double min_y, double max_y,
				String x_attr_name, String y_attr_name, List<String> set_ids,
				List<String> world_set_ids, List<List<Double>> time_intervals) {
			this.min_x = min_x;
			this.max_x = max_x;
			this.min_y = min_y;
			this.max_y = max_y;
			this.x_attr_name = x_attr_name;
			this.y_attr_name = y_attr_name;
			this.set_ids.addAll(set_ids);
			this.world_set_ids.addAll(world_set_ids);
			this.time_intervals.addAll(time_intervals);
			for(int i = 0; i < set_ids.size(); i++){
				this.track_ids.add(Arrays.asList(new CharSequence[]{}));
			}
		}

		public Builder(double min_x, double max_x, double min_y, double max_y,
				String x_attr_name, String y_attr_name, String set_id,
				String world_set_id, Double startTime, Double endTime,
				int intervals, IntervalType itype) {
			this.min_x = min_x;
			this.max_x = max_x;
			this.min_y = min_y;
			this.max_y = max_y;
			this.x_attr_name = x_attr_name;
			this.y_attr_name = y_attr_name;
			this.set_ids.add(set_id);
			this.world_set_ids.add(world_set_id);
			this.time_intervals.addAll(getTimeIntervals(startTime, endTime,
					intervals, itype));
			for(int i = 0; i < set_ids.size(); i++){
				this.track_ids.add(Arrays.asList(new CharSequence[]{}));
			}
		}

		private List<List<Double>> getTimeIntervals(Double startTime,
				Double endTime, int intervals, IntervalType itype) {

			List<List<Double>> result = new ArrayList<List<Double>>();
			
			if( itype == IntervalType.OVERLAP ) {
				double halfInterval = (endTime - startTime) / (intervals + 1);
				double tempStart = startTime;
				double tempEnd = startTime + halfInterval * 2;
				for (int ii = 0; ii < intervals; ii++) {
					List<Double> val = new ArrayList<Double>();
					val.add(tempStart);
					val.add(tempEnd);
					result.add(val);
					tempStart = tempStart + halfInterval;
					tempEnd = tempEnd + halfInterval;
				}
			} else {
				double length = (endTime - startTime) / intervals;
				double tempStart = startTime;
				double tempEnd = startTime + length;
				for (int ii = 0; ii < intervals; ii++) {
					List<Double> val = new ArrayList<Double>();
					val.add(tempStart);
					val.add(tempEnd);
					result.add(val);
					if( itype == IntervalType.DISJOINT )
						tempStart = tempEnd;
					tempEnd = tempEnd + length;
				}
			}
			return result;
		}

		public Builder width(int value) {
			width = value;
			return this;
		}

		public Builder height(int value) {
			height = value;
			return this;
		}

		public Builder projection(String value) {
			projection = value;
			return this;
		}

		public Builder bg_color(long value) {
			bg_color = value;
			return this;
		}
		
		public Builder tracklinewidths(String linewidths) {
			tracklinewidths.clear();
			for (String s : linewidths.split(","))
				tracklinewidths.add(Integer.parseInt(s));
			return this;
		}
		
		public Builder tracklinecolors(String linecolors) {
			tracklinecolors.clear();
			for (String s : linecolors.split(","))
				tracklinecolors.add(Long.decode(s));
			return this;
		}
		
		public Builder trackmarkersizes(String markersizes) {
			trackmarkersizes.clear();
			for (String s : markersizes.split(","))
				trackmarkersizes.add(Integer.parseInt(s));
			return this;
		}

		public Builder trackmarkercolors(String markercolors) {
			trackmarkercolors.clear();
			for (String s : markercolors.split(","))
				trackmarkercolors.add(Long.decode(s));
			return this;
		}

		public Builder trackmarkershapes(String markershapes) {
			trackmarkershapes.clear();
			for (String s : markershapes.split(","))
				trackmarkershapes.add(s);
			return this;
		}

		public Builder trackheadcolors(String headcolors) {
			trackheadcolors.clear();
			for (String s : headcolors.split(","))
				trackheadcolors.add(Long.decode(s));
			return this;
		}

		public Builder trackheadsizes(String headsizes) {
			trackheadsizes.clear();
			for (String s : headsizes.split(","))
				trackheadsizes.add(Integer.parseInt(s));
			return this;
		}

		public Builder trackheadshapes(String headshapes) {
			trackheadshapes.clear();
			for (String s : headshapes.split(","))
				trackheadshapes.add(s);
			return this;
		}
		
		public Builder do_points(String dopoints) {
			do_points.clear();
			for (String s : dopoints.split(","))
				do_points.add(Boolean.parseBoolean(s));
			return this;
		}
		
		public Builder do_shapes(String doshapes) {
			do_shapes.clear();
			for (String s : doshapes.split(","))
				do_shapes.add(Boolean.parseBoolean(s));
			return this;
		}
				
		public Builder do_tracks(String dotracks) {
			do_tracks.clear();
			for (String s : dotracks.split(","))
				do_tracks.add(Boolean.parseBoolean(s));
			return this;
		}

		public Builder pointcolors(String pointcolorsString) {
			pointcolors.clear();
			for (String s : pointcolorsString.split(","))
				pointcolors.add(Long.decode(s));
			return this;
		}
		
		public Builder pointshapes(String pointshapesString) {
			pointshapes.clear();
			for (String s : pointshapesString.split(","))
				pointshapes.add(s);
			return this;
		}

		public Builder pointsizes(String pointsizesString) {
			pointsizes.clear();
			for (String s : pointsizesString.split(","))
				pointsizes.add(Integer.parseInt(s));
			return this;
		}
		
		public Builder shapelinewidths(String shapelinewidthsString) {
			shapelinewidths.clear();
			for (String s : shapelinewidthsString.split(","))
				shapelinewidths.add(Integer.parseInt(s));
			return this;
		}
		
		public Builder shapelinecolors(String shapelinecolorsString) {
			shapelinecolors.clear();
			for (String s : shapelinecolorsString.split(","))
				shapelinecolors.add(Long.decode(s));
			return this;
		}
				
		public Builder shapefillcolors(String shapefillcolorsString) {
			shapefillcolors.clear();
			for (String s : shapefillcolorsString.split(","))
				shapefillcolors.add(Long.decode(s));
			return this;
		}
		
		public GPUdbVideoGenRequest build() {
			return new GPUdbVideoGenRequest(this);
		}

	}

	private GPUdbVideoGenRequest(Builder builder) {
		min_x = builder.min_x;
		max_x = builder.max_x;
		min_y = builder.min_y;
		max_y = builder.max_y;
		x_attr_name = builder.x_attr_name;
		y_attr_name = builder.y_attr_name;
		width = builder.width;
		height = builder.height;
		projection = builder.projection;
		bg_color = builder.bg_color;
		set_ids = builder.set_ids;
		world_set_ids = builder.world_set_ids;
		track_ids = builder.track_ids;
		do_points = builder.do_points;
		do_shapes = builder.do_shapes;
		do_tracks = builder.do_tracks;
		pointcolors = builder.pointcolors;
		pointsizes = builder.pointsizes;
		pointshapes = builder.pointshapes;
		shapelinewidths = builder.shapelinewidths;
		shapelinecolors = builder.shapelinecolors;
		shapefillcolors = builder.shapefillcolors;
		tracklinewidths = builder.tracklinewidths;
		tracklinecolors = builder.tracklinecolors;
		trackmarkersizes = builder.trackmarkersizes;
		trackmarkercolors = builder.trackmarkercolors;
		trackmarkershapes = builder.trackmarkershapes;
		trackheadcolors = builder.trackheadcolors;
		trackheadsizes = builder.trackheadsizes;
		trackheadshapes = builder.trackheadshapes;
		time_intervals = builder.time_intervals;
		video_style = builder.video_style;
		session_key = builder.session_key;
	}

	@Override
	public String toString() {
		return "GpudbVideoGenRequest [min_x=" + min_x + ", max_x=" + max_x
				+ ", min_y=" + min_y + ", max_y=" + max_y + ", x_attr_name="
				+ x_attr_name + ", y_attr_name=" + y_attr_name + ", width="
				+ width + ", height=" + height + ", projection=" + projection
				+ ", bg_color=" + bg_color + ", set_ids=" + set_ids
				+ ", world_set_ids=" + world_set_ids + ", track_ids="
				+ track_ids + ", do_points=" + do_points + ", do_shapes="
				+ do_shapes + ", do_tracks=" + do_tracks + ", pointcolors="
				+ pointcolors + ", pointsizes=" + pointsizes + ", pointshapes="
				+ pointshapes + ", shapelinewidths=" + shapelinewidths
				+ ", shapelinecolors=" + shapelinecolors + ", shapefillcolors="
				+ shapefillcolors + ", tracklinewidths=" + tracklinewidths
				+ ", tracklinecolors=" + tracklinecolors
				+ ", trackmarkersizes=" + trackmarkersizes
				+ ", trackmarkercolors=" + trackmarkercolors
				+ ", trackmarkershapes=" + trackmarkershapes
				+ ", trackheadcolors=" + trackheadcolors + ", trackheadsizes="
				+ trackheadsizes + ", trackheadshapes=" + trackheadshapes
				+ ", time_intervals=" + time_intervals + ", video_style="
				+ video_style + ", session_key=" + session_key + "]";
	}

	public static void main(String args[]) {

		GPUdbVideoGenRequest gvgr = new GPUdbVideoGenRequest.Builder(-180.0,
				180.0, -90.0, 90.0, "X", "Y", "S1", "WS1", 0.0, 50.0, 4, GPUdbVideoGenRequest.IntervalType.DISJOINT)
				.tracklinewidths("1,2,3")
				.build();

		System.out.println(" Request is " + gvgr);

		gvgr = new GPUdbVideoGenRequest.Builder(-180.0,
				180.0, -90.0, 90.0, "X", "Y", "S1", "WS1", 0.0, 50.0, 4, GPUdbVideoGenRequest.IntervalType.GROWTH)
				.build();

		System.out.println(" Request is " + gvgr);
		
		gvgr = new GPUdbVideoGenRequest.Builder(-180.0,
				180.0, -90.0, 90.0, "X", "Y", "S1", "WS1", 0.0, 50.0, 4, GPUdbVideoGenRequest.IntervalType.OVERLAP)
				.build();

		System.out.println(" Request is " + gvgr);


	}
}
