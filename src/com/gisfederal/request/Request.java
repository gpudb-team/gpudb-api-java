package com.gisfederal.request;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.log4j.Logger;

import com.gisfederal.Gaia;
import com.gisfederal.GaiaException;

public abstract class Request {
	
	private static final String logParam = "log"; 
	
	protected RequestData requestData;
	protected Gaia gaia;
	protected String file; // ex: /calculate
	protected Logger log;
	
	protected String auditMessage;

	public String getAuditMessage() {
		return auditMessage;
	}

	public void setAuditMessage(String auditMessage) {
		this.auditMessage = auditMessage;
	}
	
	protected void getAuditPart(StringBuffer msg) {
		msg.append("[user=");
		msg.append(this.gaia.getUser_name());
		msg.append("]");
		
		msg.append("[auths=");
		msg.append(this.gaia.getUserAuth());
		msg.append("]");
		
		msg.append("[endpoint=");
		msg.append(this.getClass().getSimpleName());
		msg.append("]");
	}

	public byte[] post_to_gaia() throws GaiaException {
		String temp, ret = "";

		ByteArrayBuffer baf = new ByteArrayBuffer(200);

		// byte[] ba = null;

		log.debug("sending out [" + requestData + "]");
		log.debug("BEFORE TRY:" + gaia.toString());

		// get the connection object
		RequestConnection rc = gaia.getRequestConnection();
		try {
			log.debug(String.format("file:%s and rc:%s\n", file, rc.toString()));

			URL gaiaUrl = rc.buildURL(file);
			log.debug(gaiaUrl.toString());

			long startTime = System.currentTimeMillis();
			HttpURLConnection connection = (HttpURLConnection) gaiaUrl
					.openConnection();
			
			connection.setRequestMethod("POST");
			connection.setDoOutput(true); // means we are writing to the URL
			// key, value (headers)
			connection.setRequestProperty("Content-type",
					"application/octet-stream");
			connection.setRequestProperty("Accept", "application/octet-stream");
			
			//System.out.println(" AUDITMESSAGE : " + auditMessage);
			
			connection.setRequestProperty(logParam, auditMessage);
			ByteArrayOutputStream baos = (ByteArrayOutputStream) connection
					.getOutputStream();

			baos.write(requestData.getData());
			baos.close();
			log.debug("before reader");
			// /
			BufferedInputStream bis = new BufferedInputStream(
					connection.getInputStream());
			int read = 0;
			int bufSize = 8192;
			byte[] buffer = new byte[bufSize];
			while (true) {
				read = bis.read(buffer);
				if (read == -1) {
					break;
				}
				baf.append(buffer, 0, read);
			}

			long endTime = System.currentTimeMillis();

			connection.disconnect();

			// Lets create and write to a replay file if needed
			if (gaia.isCollectForReplay()) {

				writeReplayFile(file, requestData.getData(),
						(endTime - startTime) / 1000);
			}
			log.debug("ret ba length [" + baf.length() + "]");
		} catch (java.net.ConnectException e) {
			log.error(e.toString());
			throw new GaiaException(
					"Java net connection when posting and reading from gaia; connection:"
							+ rc.toString() + " error:" + e.toString());
		} catch (Exception e) {
			log.error(e.toString());
			throw new GaiaException(
					"Exception when posting and reading from gaia; connection:"
							+ rc.toString() + " error:" + e.toString());
		}

		return baf.buffer();
	}

	private void writeReplayFile(String file, byte[] data, long elapsedSecs) {

		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter("gaia_replay.txt", true)));
			out.println("ET:" + elapsedSecs);
			out.println(file);
			String encodedData = Base64.encodeBase64URLSafeString(data);
			// System.out.println(" Len " + encodedData.length());
			out.println(encodedData);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}