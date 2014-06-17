/**
 * 
 */
package com.gisfederal.request;

import java.net.MalformedURLException;
import java.net.URL;

import com.gisfederal.GaiaException;


public class RequestConnection {
	// either use "ip:port" :
	protected String ip;
	protected int port;	
	// or (reverse proxy)  "host/baseFile":
	protected String host;
	protected String baseFile;
	// tells us which (this is for the first)
	protected boolean usingIP;
	private String protocol; 
	
	/**
	 * Build the request connection with just the IP and port. Specify the protocol.
	 * @param protocol The protocol (HTTP or HTTPS)
	 * @param ip The IP address (ex: 127.0.0.1)
	 * @param port The port (ex: 9191)	 
	 * @throws GaiaException
	 */
	public RequestConnection(String protocol, String ip, int port) throws GaiaException{
		this.ip = ip;
		this.port = port;
		this.protocol = protocol;
		usingIP = true;
		
		// test that the URL is OK
		try {
			buildURL("test");		
		} catch (MalformedURLException e) {
			throw new GaiaException("Malformed url; "+e.toString());
		}		
	}

	public RequestConnection(String protocol, String host, String baseFile) {
		this.host = host;
		this.baseFile = baseFile;
		usingIP = false;
		this.protocol = protocol;
	}
	
	public RequestConnection(String protocol, String ip) throws GaiaException{
		// We dont have a port. If HTTPS - try 443, otherwise try 80
		this(protocol, ip, protocol.equalsIgnoreCase("https") ? 443 : 80);
	}
	
	/**
	 * Construct a URL object.  Used by Request.
	 * @throws MalformedURLException 
	 */
	public URL buildURL(String file) throws MalformedURLException {		
		if(usingIP) {	
			return new URL(protocol, ip, port, file);
		} else {
			// NOTE: "file" already has a "/" infront
			return new URL(protocol, host, baseFile+file);
		}
	}
	
	public String toString() {
		if(usingIP){
			return String.format("(ip, port) = (%s, %d)\n", ip, port);
		} else {
			return String.format("(host, baseFile) = (%s, %s)\n", host, baseFile);
		}
	}
	
	/**
	 * The objects are equal if the URL connections are equal.
	 * @param other The object we are comparing against.
	 * @return boolean whether they are equal.
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof RequestConnection)) return false;
		RequestConnection rc = (RequestConnection)other;
		try {
			if(rc.buildURL("test").equals(this.buildURL("test"))) return true;
			else return false;
		}catch (MalformedURLException e) {
			return false;
		}		
	}
}
