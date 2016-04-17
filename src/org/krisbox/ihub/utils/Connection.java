package org.krisbox.ihub.utils;

import java.net.MalformedURLException;

import javax.xml.rpc.ServiceException;

import com.actuate.aces.idapi.Authenticator;
import com.actuate.aces.idapi.control.ActuateException;

public class Connection {
	private final String USERNAME = "administrator";
	private final String PASSWORD = "";
	private final String VOLUME   = "Default Volume";
	private final String HOSTNAME = "http://192.168.1.143:8000";
	private String authid;
	
	public Connection() {
		try {
			Authenticator auth = new Authenticator(HOSTNAME, USERNAME, PASSWORD, VOLUME);
			authid = new String(auth.getAuthenticationId());
		} catch (MalformedURLException | ServiceException | ActuateException e) {
			e.printStackTrace();
		}
	}
	
	public String getAuthID() {
		return authid;
	}
}
