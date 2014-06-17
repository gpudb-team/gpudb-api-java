package com.gisfederal;

import java.util.ArrayList;
import java.util.List;

//// NOTE: probably depreciate!
public class GetTracksResponse {
	public int totalNumTracks;
	public List<String> trackIDs;
	public List<Integer> counts;
	public List<String> resultSets;
	
	public GetTracksResponse() {
		// defaults
		this.totalNumTracks = 0;
		this.trackIDs = new ArrayList<String>();
		this.counts = new ArrayList<Integer>();
		this.resultSets = new ArrayList<String>();
	}
	
	public GetTracksResponse(int totalNumTracks, List<String> trackIDs, List<Integer> counts, List<String> resultSets) {
		this.totalNumTracks = totalNumTracks;
		this.trackIDs = trackIDs;
		this.counts = counts;
		this.resultSets = resultSets;
	}
}
