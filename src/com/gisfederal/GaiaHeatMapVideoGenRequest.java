package com.gisfederal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GaiaHeatMapVideoGenRequest {

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

	private String colormap;
	private int blur_radius;
	private long gradient_start_color;
	private long gradient_end_color;
	
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

	public List<List<Double>> getTime_intervals() {
		return time_intervals;
	}

	public String getVideo_style() {
		return video_style;
	}

	public String getSession_key() {
		return session_key;
	}

	public String getColormap() {
		return colormap;
	}

	public int getBlur_radius() {
		return blur_radius;
	}

	public long getGradient_start_color() {
		return gradient_start_color;
	}

	public long getGradient_end_color() {
		return gradient_end_color;
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
		private final List<List<Double>> time_intervals = new ArrayList<List<Double>>();

		// Optional params
		private int width = 1024;
		private int height = 1024;
		private String projection = "plate_carree";
		private long bg_color = 0x00;

		private String colormap = "jet";
		private int blur_radius = 20;
		private long gradient_start_color = 0xFFFFFFFF;
		private long gradient_end_color = 0xFF0000FF;
		
		
		private String video_style = "normal";
		private String session_key = "SESSION_" + UUID.randomUUID();

		public Builder(double min_x, double max_x, double min_y, double max_y,
				String x_attr_name, String y_attr_name, List<String> set_ids,
				List<List<Double>> time_intervals) {
			this.min_x = min_x;
			this.max_x = max_x;
			this.min_y = min_y;
			this.max_y = max_y;
			this.x_attr_name = x_attr_name;
			this.y_attr_name = y_attr_name;
			this.set_ids.addAll(set_ids);
			this.time_intervals.addAll(time_intervals);
		}

		public Builder(double min_x, double max_x, double min_y, double max_y,
				String x_attr_name, String y_attr_name, String set_id,
				Double startTime, Double endTime,
				int intervals, IntervalType itype) {
			this.min_x = min_x;
			this.max_x = max_x;
			this.min_y = min_y;
			this.max_y = max_y;
			this.x_attr_name = x_attr_name;
			this.y_attr_name = y_attr_name;
			this.set_ids.add(set_id);
			this.time_intervals.addAll(getTimeIntervals(startTime, endTime,
					intervals, itype));
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
		
		public Builder colormap(String value) {
			colormap = value;
			return this;
		}

		public Builder blur_radius(int value) {
			blur_radius = value;
			return this;
		}

		public Builder gradient_start_color(long value) {
			gradient_start_color = value;
			return this;
		}

		public Builder gradient_end_color(long value) {
			gradient_end_color = value;
			return this;
		}


		public GaiaHeatMapVideoGenRequest build() {
			return new GaiaHeatMapVideoGenRequest(this);
		}

	}

	private GaiaHeatMapVideoGenRequest(Builder builder) {
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
		
		colormap = builder.colormap;
		blur_radius = builder.blur_radius;
		gradient_start_color = builder.gradient_start_color;
		gradient_end_color = builder.gradient_end_color;

		time_intervals = builder.time_intervals;
		video_style = builder.video_style;
		session_key = builder.session_key;
	}


	@Override
	public String toString() {
		return "GaiaHeatMapGenRequest [min_x=" + min_x + ", max_x=" + max_x
				+ ", min_y=" + min_y + ", max_y=" + max_y + ", x_attr_name="
				+ x_attr_name + ", y_attr_name=" + y_attr_name + ", width="
				+ width + ", height=" + height + ", projection=" + projection
				+ ", bg_color=" + bg_color + ", set_ids=" + set_ids
				+ ", colormap=" + colormap + ", blur_radius=" + blur_radius
				+ ", gradient_start_color=" + gradient_start_color
				+ ", gradient_end_color=" + gradient_end_color
				+ ", time_intervals=" + time_intervals + ", video_style="
				+ video_style + ", session_key=" + session_key + "]";
	}

	public static void main(String args[]) {

		GaiaHeatMapVideoGenRequest gvgr = new GaiaHeatMapVideoGenRequest.Builder(-180.0,
				180.0, -90.0, 90.0, "X", "Y", "S1", 0.0, 50.0, 4, GaiaHeatMapVideoGenRequest.IntervalType.DISJOINT)
				.colormap("whatever").blur_radius(3).gradient_start_color(1).gradient_end_color(2)
				.build();

		System.out.println(" Request is " + gvgr);

		gvgr = new GaiaHeatMapVideoGenRequest.Builder(-180.0,
				180.0, -90.0, 90.0, "X", "Y", "S1", 0.0, 50.0, 4, GaiaHeatMapVideoGenRequest.IntervalType.GROWTH)
				.build();

		System.out.println(" Request is " + gvgr);
		
		gvgr = new GaiaHeatMapVideoGenRequest.Builder(-180.0,
				180.0, -90.0, 90.0, "X", "Y", "S1", 0.0, 50.0, 4, GaiaHeatMapVideoGenRequest.IntervalType.OVERLAP)
				.build();

		System.out.println(" Request is " + gvgr);
	}
}
