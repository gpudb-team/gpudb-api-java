package com.gisfederal.request;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.log4j.Logger;

import com.gisfederal.GPUdb;
import com.gisfederal.GPUdbException;

public abstract class Request {

	private static final String logParam = "log";
	private static final String setIdParam = "setid";
	private static final String mutableParam = "mutable";

	protected RequestData requestData;
	protected GPUdb gPUdb;
	protected String file; // ex: /calculate
	protected Logger log;

	protected String auditMessage;
	
	protected String setId;

	public String getAuditMessage() {
		return auditMessage;
	}

	public void setAuditMessage(String auditMessage) {
		this.auditMessage = auditMessage;
	}

	protected void getAuditPart(StringBuffer msg) {
		msg.append("[user=");
		msg.append(this.gPUdb.getUser_name());
		msg.append("]");

		msg.append("[auths=");
		msg.append(this.gPUdb.getUserAuth());
		msg.append("]");

		msg.append("[endpoint=");
		msg.append(this.getClass().getSimpleName());
		msg.append("]");
	}

	public byte[] post_to_gpudb() throws GPUdbException {
		return post_to_gpudb(false);
	}

	public byte[] post_to_gpudb(boolean sendExtraParam) throws GPUdbException {
		String temp, ret = "";

		ByteArrayBuffer baf = new ByteArrayBuffer(200);

		// byte[] ba = null;

		log.debug("sending out [" + requestData + "]");
		log.debug("BEFORE TRY:" + gPUdb.toString());

		// get the connection object
		RequestConnection rc = gPUdb.getRequestConnection();
		try {
			log.debug(String.format("file:%s and rc:%s\n", file, rc.toString()));
			
			URL gpudbUrl = rc.buildURL(file);
			log.debug(gpudbUrl.toString());

			long startTime = System.currentTimeMillis();
			HttpURLConnection connection = (HttpURLConnection) gpudbUrl
					.openConnection();

			connection.setRequestMethod("POST");
			connection.setDoOutput(true); // means we are writing to the URL
			// key, value (headers)
			
			setContentType(connection);
			
			connection.setRequestProperty("Accept", "application/octet-stream");

			String userAuthString = gPUdb.getUser_name()+":"+gPUdb.getUser_pwd();
			String basicAuth = "Basic " + new String(Base64.encodeBase64String(userAuthString.getBytes()));
			basicAuth = basicAuth.substring(0, basicAuth.length()-2);
			connection.setRequestProperty ("Authorization", basicAuth); 
			
			//System.out.println(" AUDITMESSAGE : " + auditMessage);

			connection.setRequestProperty(logParam, auditMessage);
			
			if( StringUtils.isNotEmpty(setId)) {
				connection.setRequestProperty(setIdParam, setId);
			}
			
			ByteArrayOutputStream baos = (ByteArrayOutputStream) connection
					.getOutputStream();

			baos.write(requestData.getData());
			baos.close();
			log.debug("before reader");

			BufferedInputStream bis = null;
			try {
				bis = new BufferedInputStream(connection.getInputStream());
			} catch (IOException exception) {
				bis = new BufferedInputStream(connection.getErrorStream());
			}
			
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
			if (gPUdb.isCollectForReplay()) {

				writeReplayFile(file, requestData.getData(),
						(endTime - startTime) / 1000);
			}
			log.debug("ret ba length [" + baf.length() + "]");
		} catch (java.net.ConnectException e) {
			log.error(e.toString());
			throw new GPUdbException(
					"Java net connection when posting and reading from gpudb; connection:"
							+ rc.toString() + " error:" + e.toString());
		} catch (Exception e) {
			log.error(e.toString());
			throw new GPUdbException(
					"Exception when posting and reading from gpudb; connection:"
							+ rc.toString() + " error:" + e.toString());
		}

		return baf.buffer();
	}

	protected void setContentType(HttpURLConnection connection) {
		connection.setRequestProperty("Content-type",
				"application/octet-stream");
	}

	private void writeReplayFile(String file, byte[] data, long elapsedSecs) {

		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter("gpudb_replay.txt", true)));
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