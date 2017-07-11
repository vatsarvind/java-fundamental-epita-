/**
 * 
 */
package fr.epita.iam.tests.datamodel;

import fr.epita.iam.datamodel.Identity;

/**
 * @author arvind
 *
 */
public class TestIdentity {

	/**
	 * 
	 */
	public static void main(String[] args) {
		Identity identity = new Identity("Thomas Broussard", "001", "tbr@tbr.com",null);
		
		System.out.println(identity);

	}

}
