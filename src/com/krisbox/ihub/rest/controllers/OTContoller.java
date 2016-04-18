package com.krisbox.ihub.rest.controllers;

import org.apache.log4j.Logger;
import org.krisbox.ihub.schedule.ScheduleReport;

public class OTContoller {
	private final long id;
	private final CustomSchedulerPojo csp;
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	public OTContoller(long id, CustomSchedulerPojo csp) {
		this.id  = id;
		this.csp = csp;
	}
	
	public CustomSchedulerResponse executeNow() {
		CustomSchedulerResponse csr = new CustomSchedulerResponse(id, csp.getBurstvalues().length);
		
		boolean tempEmail = false;
		boolean tempAttachment = false;
		
		for(int i=0; i<csp.getBurstvalues().length; i++) {
			try {
				// Pre-process optional parameters
				if(i >= csp.getEmail().length) {
					tempEmail = false;
				}else{
					tempEmail = csp.getEmail()[i];
				}
				
				
				if(i >= csp.getAttachment().length) {
					tempAttachment = false;
				}else{
					tempAttachment = csp.getAttachment()[i];
				}
				
				new ScheduleReport(i,
						  csp.getAuthId(),
						  csp.getUsername(),
						  csp.getPassword(),
						  csp.getVolume(),
						  csp.getURL(),
						  csp.getIhubuser()[i],
						  csp.getIhubgroup()[i],
						  csp.getBurstColumn(),
						  csp.getBurstvalues()[i],
						  csp.getRptdesign(),
						  csp.getParameterNames(),
						  csp.getParameterValues(),
						  csp.getOutputtype(),
						  csp.getOutputfolder(),
						  tempEmail,
						  tempAttachment);
				
				csr.setBurstvalue(i, csp.getBurstvalues()[i]);
				csr.setResponse(i, "Success");
			}catch(Exception ex){
				csr.setResponse(i, ex.getMessage());
				logger.error(ex.getMessage());
			}
		}
		
		return csr;
	}
	
	public long getId() {
		return id;
	}
	
	public CustomSchedulerPojo getCsp() {
		return csp;
	}
}
