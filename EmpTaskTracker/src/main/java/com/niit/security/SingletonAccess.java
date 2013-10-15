/*
 * Class: SingletonAccess.java
 * Version: 0.1
 * 
 * Copyright NIIT Technologies Pvt Ltd.
 */

package com.niit.security;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Sandeep Srivastava , EMP ID:53694
 */
public final class SingletonAccess {

	private String displayName;
	private static Log log = LogFactory.getLog(SingletonAccess.class);
	
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getDisplayName(String userID)throws Exception {
		if(this.displayName==null){
			throw new Exception("Staff number " + userID + " does not exist in the People table!");
		}
		
		return this.displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public static Log getLog() {
		return log;
	}
	public static void setLog(Log log) {
		SingletonAccess.log = log;
	}

	
	
	
}