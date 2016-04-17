package org.krisbox.ihub.schedule;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.krisbox.ihub.utils.Connection;

import com.actuate.aces.idapi.Authenticator;
import com.actuate.aces.idapi.JobScheduler;
import com.actuate.aces.idapi.ReportExecuter;
import com.actuate.aces.idapi.ReportViewer;
import com.krisbox.ihub.rest.controllers.OutputType;

public class ScheduleReport {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	public ScheduleReport(long count,
						  String authId,
						  String username,
						  String password,
						  String volume,
						  String url,
						  String ihubuser,
						  String ihubgroup,
						  String burstcolumn,
						  String burstvalue,
						  String rptdesign,
						  String[] parameterNames,
						  String[] parameterValues,
						  OutputType outputtype,
						  boolean email,
						  boolean attachment) throws Exception {
		logger.info("=============================Report " + count + " Values=============================");
		logger.info("AuthID           == " + authId);
		logger.info("iHub URL         == " + url);
		logger.info("Burst Column     == " + burstcolumn);
		logger.info("Burst Value      == " + burstvalue);
		logger.info("iHub Users       == " + ihubuser);
		logger.info("iHub Groups      == " + ihubgroup);
		logger.info("Report Design    == " + rptdesign);
		logger.info("Parameter Names");
		logger.info("===============");
		readStringArray(parameterNames);
		logger.info("Parameter Values");
		logger.info("================");
		readStringArray(parameterValues);
		logger.info("Output Type      == " + outputtype);
		logger.info("E-Mail           == " + email);
		logger.info("Attachment       == " + attachment);
		logger.info("================================================================================");
		
		sendToiHub(count, authId, username, password, volume, url, ihubuser, ihubgroup, burstcolumn,
				   burstvalue, rptdesign, parameterNames, parameterValues,
				   outputtype, email, attachment);
	}
	
	private void sendToiHub(long count,
			  String authId,
			  String username,
			  String password,
			  String volume,
			  String url,
			  String ihubuser,
			  String ihubgroup,
			  String burstcolumn,
			  String burstvalue,
			  String rptdesign,
			  String[] parameterNames,
			  String[] parameterValues,
			  OutputType outputtype,
			  boolean email,
			  boolean attachment) throws Exception {
		//Authenticator auth = new Authenticator(url, java.net.URLDecoder.decode(authId));
		Authenticator auth = new Authenticator(url, 
											  java.net.URLDecoder.decode(username),
											  java.net.URLDecoder.decode(password),
											  java.net.URLDecoder.decode(volume));
		
		String outputName = rptdesign.replace(".rptdesign", "-" + burstvalue + "." + outputtype.toString().toLowerCase());
		logger.info("**OUTPUT NAME** " + outputName);
		
		HashMap<String, String> parameters;
		
		if(parameterNames == null) {
			parameters = null;
			parameters = new HashMap<String, String>();
			
			parameters.put(java.net.URLDecoder.decode(burstcolumn),
						   java.net.URLDecoder.decode(burstvalue));
		}else{
			parameters = new HashMap<String, String>();
			
			parameters.put(java.net.URLDecoder.decode(burstcolumn),
					   java.net.URLDecoder.decode(burstvalue));
			
			for(int i=0; i<parameterNames.length; i++) {
				parameters.put(java.net.URLDecoder.decode(parameterNames[i], "UTF-8") ,
							   java.net.URLDecoder.decode(parameterValues[i], "UTF-8"));
			}
		}
		
		JobScheduler jobScheduler = new JobScheduler(auth);
		String jobId = jobScheduler.scheduleJob("TEST JOB", rptdesign, outputName, null, parameters);
		System.out.println("jobId = " + jobId);
	}
	
	private void readStringArray(String[] arr) {
		for(int i=0; i<arr.length; i++) {
			logger.info(arr[i]);
		}
	}
}
