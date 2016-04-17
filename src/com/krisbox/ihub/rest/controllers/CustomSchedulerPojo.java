package com.krisbox.ihub.rest.controllers;

import org.krisbox.ihub.utils.Connection;

public class CustomSchedulerPojo {
	private final long         id;
	private final String       authId;
	private final String	   username;
	private final String	   password;
	private final String	   volume;
	private final String	   hostname;
	private final String       port;
	private final boolean      ssl;
	private final String[]     ihubuser;
	private final String[]     ihubgroup;
	private final String       burstcolumn;
	private final String[]     burstvalues;
	private final String       rptdesign;
	private final String[]     parameterNames;
	private final String[]     parameterValues;
	private final OutputType   outputtype;
	private final boolean[]    email;
	private final boolean[]    attachment;
	
	public CustomSchedulerPojo(long id,
						   String authId,
						   String username,
						   String password,
						   String volume,
						   String hostname,
						   String port,
						   boolean ssl,
						   String     burstcolumn,
						   String[]   burstvalues,
						   String[]   ihubuser,
						   String[]   ihubgroup,
						   String     rptdesign,
						   String[]   parameterNames,
						   String[]   parameterValues,
						   String     outputtype,
						   boolean[]  email,
						   boolean[]  attachment) {
		this.id              = id;
		this.authId          = authId;
		this.username  		 = username;
		this.password   	 = password;
		this.volume			 = volume;
		//this.authId			 = new Connection().getAuthID(); // LEFT IN FOR LOCAL TESTING
		this.hostname		 = hostname;
		this.port			 = port;
		this.ssl			 = ssl;
		this.ihubuser        = ihubuser;
		this.ihubgroup       = ihubgroup;
		this.burstcolumn     = burstcolumn;
		this.burstvalues     = burstvalues;
		this.rptdesign       = rptdesign;
		this.parameterNames  = parameterNames;
		this.parameterValues = parameterValues;
		this.outputtype = OutputType.valueOf(outputtype);
		this.email      = email;
		this.attachment = attachment;
	}
	
	public long getId() {
		return id;
	}
	
	public String getAuthId() {
		return authId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getVolume() {
		return volume;
	}
	
	public String getURL() {
		if(ssl) {
			return "https://" + hostname + ":" + port;
		}
		
		return "http://" + hostname + ":" + port;
	}
	
	public String getBurstColumn() {
		return burstcolumn;
	}
	
	public String[] getBurstvalues() {
		return burstvalues;
	}
	
	public String[] getIhubuser() {
		return ihubuser;
	}
	
	public String[] getIhubgroup() {
		return ihubgroup;
	}
	
	public String getRptdesign() {
		return rptdesign;
	}
	
	public String[] getParameterNames() {
		return parameterNames;
	}
	
	public String[] getParameterValues() {
		return parameterValues;
	}
	
	public OutputType getOutputtype() {
		return outputtype;
	}
	
	public boolean[] getEmail() {
		return email;
	}
	
	public boolean[] getAttachment() {
		return attachment;
	}
}
