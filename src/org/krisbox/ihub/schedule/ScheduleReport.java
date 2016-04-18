package org.krisbox.ihub.schedule;

import java.util.HashMap;

import com.actuate.aces.idapi.Authenticator;
import com.actuate.aces.idapi.JobScheduler;
import com.actuate.schemas.ArrayOfParameterValue;
import com.actuate.schemas.ArrayOfString;
import com.actuate.schemas.SubmitJob;
import com.actuate.schemas.SubmitJobOperation;
import com.actuate.schemas.SubmitJobResponse;
import com.krisbox.ihub.rest.controllers.OutputType;
import com.actuate.schemas.NewFile;
import com.actuate.schemas.ParameterValue;

public class ScheduleReport {
	
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
						  String  outputfolder,
						  boolean email,
						  boolean attachment) throws Exception {
		
		executeReport(count, authId, username, password, volume, url, ihubuser, ihubgroup, burstcolumn,
				   burstvalue, rptdesign, parameterNames, parameterValues,
				   outputtype, outputfolder, email, attachment);
	}
	
	private void executeReport(long count,
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
			  String  outputfolder,
			  boolean email,
			  boolean attachment) throws Exception {
		//Authenticator auth = new Authenticator(url, java.net.URLDecoder.decode(authId));
		Authenticator auth = new Authenticator(url, 
											  java.net.URLDecoder.decode(username, "UTF-8"),
											  java.net.URLDecoder.decode(password, "UTF-8"),
											  java.net.URLDecoder.decode(volume, "UTF-8"));
		String outputName;
		
		if(outputfolder.substring(outputfolder.length() - 1) != "/") {
			outputName = outputfolder + new java.io.File(rptdesign.replace(".rptdesign", "-" + burstvalue + "." + outputtype.toString().toLowerCase())).getName();
		}else{
			outputName = outputfolder + "/" + new java.io.File(rptdesign.replace(".rptdesign", "-" + burstvalue + "." + outputtype.toString().toLowerCase())).getName();
		}
		
		
		ArrayOfParameterValue t = new ArrayOfParameterValue();
		
		HashMap<String, String> parameters;
		
		ParameterValue value = new ParameterValue();
		value.setName(burstcolumn);
		value.setValue(burstvalue);
		t.setParameterValue(new ParameterValue[]{value});
		parameters = new HashMap<String, String>();
		
		parameters.put(java.net.URLDecoder.decode(burstcolumn, "UTF-8"),
				   java.net.URLDecoder.decode(burstvalue, "UTF-8"));
		
		ParameterValue[] values = new ParameterValue[parameterNames.length+1];
		ParameterValue b1 = new ParameterValue();
		b1.setName(burstcolumn);
		b1.setValue(burstvalue);
		values[0] = b1;
		
		for(int i=0; i<parameterNames.length; i++) {
			ParameterValue v1 = new ParameterValue();
			v1.setName(java.net.URLDecoder.decode(parameterNames[i], "UTF-8"));
			v1.setValue(java.net.URLDecoder.decode(parameterValues[i], "UTF-8"));
			values[i+1] = v1;
		}
		
		ArrayOfString users  = new ArrayOfString();
		ArrayOfString groups = new ArrayOfString();
		
		if(!ihubuser.isEmpty())
			users.setString(new String[]{ihubuser});
		
		if(!ihubgroup.isEmpty())
			groups.setString(new String[]{ihubgroup});
		
		SubmitJob sj = new SubmitJob();
		
        sj.setPriority(1000);
        //sj.setHeadline("2611");
        sj.setParameterValues(new ArrayOfParameterValue(values));
        sj.setKeepOutputFile(true);
        sj.setOverrideRecipientPref(true);
        sj.setSendEmailForSuccess(email);
        sj.setAttachReportInEmail(attachment);
        sj.setSendSuccessNotice(false);
        sj.setSendFailureNotice(false);
        
        sj.setNotifyUsersByName(users);
        
        sj.setInputFileName(rptdesign);
        
        if(outputfolder.substring(outputfolder.length() - 1) != "/") {
			outputName = outputfolder + new java.io.File(rptdesign.replace(".rptdesign", "-" + burstvalue + "." + outputtype.toString().toLowerCase())).getName();
			NewFile nf = new NewFile();
			JobScheduler js = new JobScheduler(auth);
			js.addPermission(ihubuser, null, "VR");
			nf.setACL(js.getPermissions());
			nf.setName(outputName);
			sj.setRequestedOutputFile(nf);
		}else{
			outputName = outputfolder + "/" + new java.io.File(rptdesign.replace(".rptdesign", "-" + burstvalue + "." + outputtype.toString().toLowerCase())).getName();
			NewFile nf = new NewFile();
			JobScheduler js = new JobScheduler(auth);
			js.addPermission(ihubuser, null, "VR");
			nf.setACL(js.getPermissions());
			nf.setName(outputName);
			sj.setRequestedOutputFile(nf);
		}
        
        sj.setOperation(SubmitJobOperation.RunReport);

        SubmitJobResponse submitJobResponse = null;
        submitJobResponse = auth.getAcxControl().proxy.submitJob(sj); //actuateControl.proxy.sj(sj);
        System.out.println(submitJobResponse.getJobId());
	}
}
