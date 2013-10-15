/*
 * Class: SecurityInterceptor.java
 * Copyright NIIT Technologies Pvt Ltd.
 */

package com.niit.security;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <p>Ensures all users have an "Access" object.</p>
 *
 * @author Sandeep Srivastava, EMP ID:543694
 */
@Component
public final class SecurityInterceptor extends HandlerInterceptorAdapter {

	private static Log log = LogFactory.getLog(SecurityInterceptor.class);
	
	private Access access;

	public void setAccess(Access access) {
		this.access = access;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		if(access.getUserId() == null) {
			
			String userRoleString = request.getHeader("ba-sso-authfor");
			String staffNo        = request.getHeader("ba-sso-uid");
			
			if(staffNo == null || userRoleString == null) {
				log.warn("THIS SHOULD NEVER SHOW ON LIVE!"
						+ " Hardcoding User in Absence of Headers!");
				// User
				// Support
				// ManagerRead
				// TeamLeader
				// ManagerReadWrite
				// SysAdmin
				// ServiceLevelManager aka Catherine Sim level    - Can update SL
				// ITAuditor           aka Margaret Zimunhu level - Can update Audit In Scope
				// userRoleString = "AppsKB-" + "SysAdmin" + ":";
				// staffNo = "N433115";
			}
			
			staffNo = staffNo.toUpperCase();
			String[] userRoles = userRoleString.split(":");

			try {
				log.debug(staffNo + " being looked up.");
				access.setup(staffNo, userRoles);
			} catch (Exception e) {
				log.warn(staffNo + " lookup threw an Exception - " + e);
				response.sendError(400);
				return false;
			}
		} else {
			log.debug(access.getUserId() + " already identified.");
		}
		return true;
	}
}