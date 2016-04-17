package com.krisbox.ihub.rest.controllers;

public class CustomSchedulerResponse {
	private long id;
	private String[] burstvalue;
	private String[] response;
	
	public CustomSchedulerResponse(long id, int totalreports) {
		this.id = id;
		burstvalue = new String[totalreports];
		response   = new String[totalreports];
	}
	
	public void setBurstvalue(int reportCounter, String burstvalue) {
		this.burstvalue[reportCounter] = burstvalue;
	}
	
	public void setResponse(int reportCounter, String response) {
		this.response[reportCounter] = response;
	}
	
	public long getId() {
		return id;
	}
	
	public String[] getResponse() {
		return response;
	}
	
	public String[] getBurstvalue() {
		return burstvalue;
	}
}
