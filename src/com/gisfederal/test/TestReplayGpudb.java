package com.gisfederal.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gisfederal.GPUdb;
import com.gisfederal.request.Request;
import com.gisfederal.request.RequestData;

public class TestReplayGpudb {
	private static GPUdb gPUdb;

	@BeforeClass
	public static void setUpClass() throws Exception {
		// Code executed before the first test method
		System.out.println("Build gpudb...");
		String gpudbURL = System
				.getProperty("GPUDB_URL", "http://127.0.0.1:9191");
		gPUdb = new GPUdb(gpudbURL, "", "admin", "changeme");
		System.out.println("Built gpudb");
	}

	@Test
	public void testReplayFile() {

		gPUdb.setCollectForReplay(false);
		readExecuteReplayFile();

	}

	private void readExecuteReplayFile() {

		StringBuffer contents = new StringBuffer();

		try {

			BufferedReader input = new BufferedReader(new FileReader(
					"gpudb_replay.txt"));
			try {
				String timeLine;
				String fileLine = "";
				byte[] dataLine;
				
				String line = null;

				int lineCnt = 0;
				while ((line = input.readLine()) != null) {					
					//System.out.println("XXX:"+line);
					lineCnt++;
					if( lineCnt == 1 ) {
						//System.out.println(" Time is " + line);
					} else if( lineCnt == 2 ) {
						//System.out.println(" File is " + line);
						fileLine = line;
					} else if( lineCnt == 3 ) {
						//System.out.println(" Data is " + line);
						lineCnt = 0;
						dataLine = Base64.decodeBase64(line);
						Request myReq = new ReplayRequest(gPUdb, fileLine, dataLine);
						long startTime = System.currentTimeMillis();
						myReq.post_to_gpudb();
						long endTime = System.currentTimeMillis();
						System.out.println(fileLine + " endpoint took " + (endTime-startTime) + " milliseconds." );
					}
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	class ReplayRequest extends Request {
		public ReplayRequest(GPUdb gPUdb, String file, byte[] data){
			this.gPUdb = gPUdb;
			this.file = file;
			this.log = Logger.getLogger(ReplayRequest.class);

			this.requestData = new RequestData(data);
		}
	}
}