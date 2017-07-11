/**
 * 
 */
package fr.epita.iam.tests.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author arvind
 *
 */
public class TestDatabases {

	/**for testing database connection
	 * @param args for user and password
	 * @throws SQLException 
	 * here a connection is made to the local database
	 */
	public static void main(String[] args) throws SQLException {
		String connectionString = "jdbc:derby://localhost:1527/myDB";
		String user = "arvind";
		String password = "arvind";
		
		Connection connection = DriverManager.getConnection(connectionString, user, password);
		
		PreparedStatement preparedStatement = connection.prepareStatement("insert into IDENTITIES (IDENTITY_DISPLAYNAME, IDENTITY_EMAIL, IDENTITY_BIRTHDATE) values(?, ?, ?)");
		
		preparedStatement.setString(1, "arvind");
		preparedStatement.setString(2, "arvind@gmail.com");
		preparedStatement.setDate(3,  new Date(new java.util.Date().getTime()));
		preparedStatement.execute();
		preparedStatement.close();
		
		PreparedStatement selectAllStatement = connection.prepareStatement("select * from IDENTITIES");
		
		ResultSet results = selectAllStatement.executeQuery();
		
		
		while (results.next()){
			String displayName = results.getString("IDENTITY_DISPLAYNAME");
			String email = results.getString("IDENTITY_EMAIL");
			Date birthDate = results.getDate("IDENTITY_BIRTHDATE");
			
			System.out.println(displayName + " - " + email + " - " + birthDate );
			
		}
		
		selectAllStatement.close();
		results.close();
		connection.close();
		
	}

}
