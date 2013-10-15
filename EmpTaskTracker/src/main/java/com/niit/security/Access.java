/*
 * Class: Access
 * 
 * Created Version: 2.0
 * Current Version: 2.4
 * 
 * Copyright NIIT Technologies Pvt Ltd.
 */

package com.niit.security;


import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Sandeep Srivastava - EMP ID:53694
 */
public class Access {

	private static final Log log = LogFactory.getLog(Access.class);

	public enum Page {

		USER, ADMIN

	}

	public enum Permission {
		READ_ONLY, READ, WRITE
	}

	private String userId;
	private String displayName;
	private boolean admin = false;

	/**
	 * Results Per Page, for the lists of results pages, so the app remembers
	 * how many results you like on a page.
	 */
	private int rpp = 15;
	private Map<String, String> userSec;
	private Map<String, String> sysAdminSec;

	private SingletonAccess singletonAccess;

	private List<String> soaEnvs;

	/**
	 * This empty constructor is required for the way Session-Stateful beans
	 * work. DO NOT USE IT (or any other constructor for this object) IN YOUR
	 * CODE.
	 */
	public Access() {
	}

	public void setup(String userId, String[] userRoles) throws Exception {
		if (this.userId == null) {
			this.userId = userId;
			this.displayName = singletonAccess.getDisplayName(this.userId);
			// this.dbAccess = singletonAccess.getDbAccess(this.userId,
			// userRoles);
		}
	}

	/**
	 * To be on the safe side, we can't just null the whole access Object, so
	 * use this for (for example) a user log out.
	 */
	public void tearDown() {
		userId = null;
		displayName = null;
		sysAdminSec = null;
		admin = false;
	}

	public void setSingletonAccess(SingletonAccess singletonAccess) {
		this.singletonAccess = singletonAccess;
	}

	public String getUserId() {
		return userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setRpp(int rpp) {
		this.rpp = rpp;
	}

	public int getRpp() {
		return rpp;
	}

	private boolean genericReadOnlyCheck(Map<String, String> accesses,
			boolean associated) {

		for (String key : accesses.keySet()) {
			String permission = accesses.get(key);

			if ("READ".equals(permission)) {
				return true;
			}

			if ("WRITE".equals(permission) && !associated) {
				return true;
			}
		}
		return false;
	}

	private boolean genericReadCheck(Map<String, String> accesses) {

		for (String key : accesses.keySet()) {
			String permission = accesses.get(key);

			if ("READ".equals(permission)) {
				return true;
			}

			if ("WRITE".equals(permission)) {
				return true;
			}

			if ("SUPER_WRITE".equals(permission)) {
				return true;
			}
		}
		return false;
	}

	private boolean genericFieldReadOnlyCheck(Map<String, String> accesses,
			boolean associated, String[] fields) {

		for (String field : fields) {
			if (accesses.containsKey(field)) {
				String permission = accesses.get(field);

				if ("READ".equals(permission)) {
					return true;
				}

				if ("WRITE".equals(permission) && !associated) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean genericFieldReadCheck(Map<String, String> accesses,
			String[] fields) {

		for (String field : fields) {
			if (accesses.containsKey(field)) {
				String permission = accesses.get(field);

				if ("READ".equals(permission)) {
					return true;
				}

				if ("WRITE".equals(permission)) {
					return true;
				}

				if ("SUPER_WRITE".equals(permission)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean genericWriteCheck(Map<String, String> accesses,
			boolean associated) {

		for (String key : accesses.keySet()) {
			String permission = accesses.get(key);

			if ("WRITE".equals(permission) && associated) {
				return true;
			}

			if ("SUPER_WRITE".equals(permission)) {
				return true;
			}
		}
		return false;
	}

	private boolean genericFieldWriteCheck(Map<String, String> accesses,
			boolean associated, String[] fields) {

		for (String field : fields) {
			if (accesses.containsKey(field)) {
				String permission = accesses.get(field);

				if ("WRITE".equals(permission) && associated) {
					return true;
				}

				if ("SUPER_WRITE".equals(permission)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasAccess(Page page, Permission perm) {
		return hasAccess(page, perm, false);
	}

	public boolean hasAccess(Page page, Permission perm, boolean associated) {
		Map<String, String> accesses;
		switch (page) {
		case ADMIN:
			accesses = getAdminSec();
			break;
		case USER:
			accesses = getUserSec();
			break;
		default:
			return false; // Impossible
		}

		switch (perm) {
		case READ_ONLY:
			return genericReadOnlyCheck(accesses, associated);
		case READ:
			return genericReadCheck(accesses);
		case WRITE:
			return genericWriteCheck(accesses, associated);
		}

		return false;
	}

	public boolean hasAccess(Page page, Permission perm, String field) {
		return hasAccess(page, perm, false, field);
	}

	public boolean hasAccess(Page page, Permission perm, boolean associated,
			String... fields) {

		Map<String, String> accesses;
		switch (page) {
		case ADMIN:
			accesses = getAdminSec();
			break;
		case USER:
			accesses = getUserSec();
			break;
		default:
			return false; // Impossible
		}

		switch (perm) {
		case READ_ONLY:
			return genericFieldReadOnlyCheck(accesses, associated, fields);
		case READ:
			return genericFieldReadCheck(accesses, fields);
		case WRITE:
			return genericFieldWriteCheck(accesses, associated, fields);
		}

		return false;
	}

	public Map<String, String> getAdminSec() {

		Map<String, String> adminSec = null;

		return adminSec;
	}

	public Map<String, String> getUserSec() {

		Map<String, String> userSec = null;

		return userSec;
	}

}