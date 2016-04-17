package com.krisbox.ihub.rest.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
  
@RestController 
public class CustomSchedulerController {  
	private final AtomicLong counter = new AtomicLong();
	
    @RequestMapping("/customscheduler")
    public CustomSchedulerResponse customscheduler(@RequestParam(value="authId") String authId,
    											   @RequestParam(value="username") String username,
    											   @RequestParam(value="password") String password,
    											   @RequestParam(value="volume")   String volume,
    											   @RequestParam(value="hostname") String hostname,
    											   @RequestParam(value="port") String port,
    											   @RequestParam(value="ssl") boolean ssl,
    											   @RequestParam(value="burstcolumn") String burstcolumn,
    											   @RequestParam(value="burstvalues") String[] burstvalues,
    											   @RequestParam(value="ihubuser") String[] ihubuser,
    											   @RequestParam(value="ihubgroup") String[] ihubgroup,
    											   @RequestParam(value="rptdesign") String rptdesign,
    											   @RequestParam(value="parameterNames") String[] parameterNames,
    											   @RequestParam(value="parameterValues") String[] parameterValues,
    											   @RequestParam(value="outputtype") String outputtype,
    											   @RequestParam(value="email") boolean[] email,
    											   @RequestParam(value="attachment") boolean[] attachment) {
    	
    	long currentCounter = counter.incrementAndGet();
    	
    	CustomSchedulerPojo cs = new CustomSchedulerPojo(currentCounter,
    													  authId,
    													  username,
    													  password,
    													  volume,
    													  hostname,
    													  port,
    													  ssl,
    													  burstcolumn,
    													  burstvalues,
    													  ihubuser,
    													  ihubgroup,
    													  rptdesign,
    													  parameterNames,
    													  parameterValues,
    													  outputtype,
    													  email,
    													  attachment);
    	
    	return new OTContoller(currentCounter, cs).executeNow();
    }
}