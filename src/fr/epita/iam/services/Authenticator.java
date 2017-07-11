/**
 * 
 */
package fr.epita.iam.services;

/**
 *
 * @author arvind
 *
 */
public class Authenticator {
	
	/**
	 * This method is checking authentication
	 * @param userName A user name is given
	 * @param password a password need to get in
	 * @return
	 */
	public static boolean authenticate(String userName, String password){
		
		return "arvind".equals(userName) && "arvind".equals(password);
		
	}

}
