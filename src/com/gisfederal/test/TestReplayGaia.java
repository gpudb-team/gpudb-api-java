package com.gisfederal.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gisfederal.Gaia;
import com.gisfederal.request.Request;
import com.gisfederal.request.RequestData;

public class TestReplayGaia {
	private static Gaia gaia;

	@BeforeClass
	public static void setUpClass() throws Exception {
		// Code executed before the first test method
		System.out.println("Build gaia...");
		String gaiaURL = System
				.getProperty("GAIA_URL", "http://127.0.0.1:9191");
		gaia = new Gaia(gaiaURL);
		System.out.println("Built gaia");
	}

	@Test
	public void testReplayFile() {

		gaia.setCollectForReplay(false);
		readExecuteReplayFile();

	}

	private void readExecuteReplayFile() {

		StringBuffer contents = new StringBuffer();

		try {

			BufferedReader input = new BufferedReader(new FileReader(
					"gaia_replay.txt"));
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
						Request myReq = new ReplayRequest(gaia, fileLine, dataLine);
						long startTime = System.currentTimeMillis();
						myReq.post_to_gaia();
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
		public ReplayRequest(Gaia gaia, String file, byte[] data){
			this.gaia = gaia;
			this.file = file;
			this.log = Logger.getLogger(ReplayRequest.class);

			this.requestData = new RequestData(data);
		}
	}
}