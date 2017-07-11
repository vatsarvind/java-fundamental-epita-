/**
 * 
 */
package fr.epita.iam.datamodel;

import java.util.Date;
import java.util.UUID;

/**class for the identity
 * @author arvind
 *
 */
public class Identity {
	
	private String displayName;
	private String uid;
	private String email;
	private Date dob;
	
	
	
	/**constructor for identity
	 * @param displayName
	 * @param uid
	 * @param email
	 */
	public Identity(String displayName, String uid, String email, Date dob) {
		this.displayName = displayName;
		if(uid==null || uid.equals(""))
			this.uid = UUID.randomUUID().toString();
		else{
			this.uid = uid;
		}
		this.email = email;
		this.dob = dob;
	}
	
	
	/** method to displayName
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**method for uid
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	/**method for getting email
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	

	public Date getDob() {
		return dob;
	}


	public void setDob(Date dob) {
		this.dob = dob;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Identity [displayName=" + displayName + ", uid=" + uid + ", email=" + email + ", dob=" + dob +"]";
	}
	

	
	

}
