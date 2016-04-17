package com.krisbox.ihub.rest.controllers;

import java.util.Arrays;

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
		logger.info("=============================All Values=============================");
		logger.info("iHub URL		  == " + csp.getURL());
		logger.info("AuthID           == " + csp.getAuthId());
		logger.info("Burst Column     == " + csp.getBurstColumn());
		logger.info("Burst Values     == " + Arrays.toString(csp.getBurstvalues()));
		logger.info("iHub Users       == " + Arrays.toString(csp.getIhubuser()));
		logger.info("iHub Groups      == " + Arrays.toString(csp.getIhubgroup()));
		logger.info("Report Design    == " + csp.getRptdesign());
		logger.info("Parameter Names  == " + Arrays.toString(csp.getParameterNames()));
		logger.info("Parameter Values == " + Arrays.toString(csp.getParameterValues()));
		logger.info("Output Type      == " + csp.getOutputtype());
		logger.info("E-Mail           == " + Arrays.toString(csp.getEmail()));
		logger.info("Attachment       == " + Arrays.toString(csp.getAttachment()));
		logger.info("=============================End Values=============================");
		
		CustomSchedulerResponse csr = new CustomSchedulerResponse(id, csp.getBurstvalues().length);
		System.out.println("************" + csp.getBurstvalues()[2]);
		
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
