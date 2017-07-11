/**
 * 
 */
package fr.epita.iam.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.epita.iam.common.ApplicationConfig;
import fr.epita.iam.common.ApplicationConstant;
import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DaoDeleteException;
import fr.epita.iam.exceptions.DaoSaveException;
import fr.epita.iam.exceptions.DaoSearchException;
import fr.epita.iam.exceptions.DaoUpdateException;

/**
 * 
 * This is a class that contains all the database operations for the class
 * Identity
 * 
 * <pre>
 *  JDBCIdentityDAO dao = new JDBCIdentityDAO();
 *  // save an identity
 *  dao.save(new Identity(...));
 *  
 *  //search with an example criteria (qbe)  
 *  dao.search(new Identity(...);
 * </pre>
 * 
 * <b>warning</b> this class is dealing with database connections, so beware to
 * release it through the {@link #releaseResources()}
 * 
 * @author arvind
 *
 */
public class JDBCIdentityDAO implements IdentityDAO {

	Connection connection;

	
	public JDBCIdentityDAO() throws SQLException, IOException {
		ApplicationConfig applicationConfig = ApplicationConfig.getInstance(); 
		String url = applicationConfig.get(ApplicationConstant.DB_URL);
		String user = applicationConfig.get(ApplicationConstant.DB_USER);
		String password = applicationConfig.get(ApplicationConstant.DB_PASSWORD);
		this.connection = DriverManager.getConnection(url,user,password);
	}
	/** Save method saves the identity from myDB.
	 *  This method is called from the launcher. java option.
	 *  @return void
	 *  @param Identity
	 */
	public void save(Identity identity) throws DaoSaveException {
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement("insert into IDENTITIES (IDENTITY_DISPLAYNAME, IDENTITY_EMAIL, IDENTITY_BIRTHDATE) values(?, ?, ?)");
			preparedStatement.setString(1, identity.getDisplayName());
			preparedStatement.setString(2, identity.getEmail());
			preparedStatement.setDate(3,  new java.sql.Date(identity.getDob().getTime()));
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException sqle) {
			DaoSaveException exception = new DaoSaveException();
			exception.initCause(sqle);
			exception.setFaultObject(identity);
			throw exception;
		}
	}

	/** Search method does the search identity from myDB.
	 *  This method is called from the launcher. java option.
	 *  @return void
	 *  @param Identity
	 */
	public List<Identity> search(Identity criteria) throws DaoSearchException {
		List<Identity> returnedList = new ArrayList<Identity>();
		try {
			String sql  = "SELECT * from IDENTITIES where "+getCriteriaQuery(criteria);
			PreparedStatement preparedStatement = this.connection
					.prepareStatement(sql);

			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				String displayName = results.getString("IDENTITY_DISPLAYNAME");
				String email = results.getString("IDENTITY_EMAIL");
				Date dob = results.getDate("IDENTITY_BIRTHDATE");
				String uid = results.getString("IDENTITY_UID");
				returnedList.add(new Identity(displayName, uid, email, dob));

			}
		} catch (SQLException sqle) {
			DaoSearchException daose = new DaoSearchException();
			daose.initCause(sqle);
			throw daose;
		}

		return returnedList;
	}

	/**
	 * this is releasing the database connection, so you should not use this
	 * instance of DAO anymore
	 */
	public void releaseResources() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *  Update method updates the Identity from myDB.
	 *  This method is called from the launcher. java option.
	 *  @return void
	 *  @param identity of the user
	 */
	@Override
	public void update(Identity identity) throws DaoUpdateException{
		// TODO Auto-generated method stub	
		try {
			String sql = "update IDENTITIES set "+getUpdateCriteria(identity);
		PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
		preparedStatement.executeUpdate();
		preparedStatement.close();
	} catch (SQLException sqle) {
		DaoUpdateException exception = new DaoUpdateException();
		exception.initCause(sqle);
		//exception.(identity);
		throw exception;
	}

	}

	/** Delete method deletes the identity from myDB.
	 *  This method is called from the launcher. java option.
	 *  
	 *  
	 */
	@Override
	public void delete(Identity identity) throws DaoDeleteException{
		try {
			String sql = "delete from IDENTITIES where "+getCriteriaQuery(identity);
		PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
		preparedStatement.execute();
		preparedStatement.close();
	} catch (SQLException sqle) {
		DaoDeleteException exception = new DaoDeleteException();
		exception.initCause(sqle);
		//exception.(identity);
		throw exception;

	}

	}
	
	public static String getCriteriaQuery(Identity identity) {
		StringBuilder query = new StringBuilder();
		boolean foundACriteria=false; 	
		if(identity.getDisplayName()!=null && !identity.getDisplayName().equals("")) {
			foundACriteria=true;
			query.append("IDENTITY_DISPLAYNAME = '"+identity.getDisplayName()+"' ");
		}
		if(identity.getDob()!=null){
			java.util.Date dt = identity.getDob();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //Or whatever format fits best your needs.
			String dateStr = sdf.format(dt);
			if(foundACriteria)
				query.append("AND IDENTITY_BIRTHDATE = '"+dateStr+"' ");
			else
				query.append("IDENTITY_BIRTHDATE = '"+dateStr+"' ");
				
			foundACriteria=true;
			
		}
		if(identity.getEmail()!=null && !identity.getEmail().equals("")){
			if(foundACriteria)
				query.append("AND IDENTITY_EMAIL = '"+identity.getEmail()+"' ");
			else
				query.append("IDENTITY_EMAIL = '"+identity.getEmail()+"' ");
				
			//foundACriteria=true;
			
		}
		return query.toString();
	}

	public static String getUpdateCriteria(Identity identity) {
		StringBuffer query = new StringBuffer();
		boolean foundACriteria=false; 	
		if(identity.getDisplayName()!=null && !identity.getDisplayName().equals("")) {
			foundACriteria=true;
			query.append("IDENTITY_DISPLAYNAME = '"+identity.getDisplayName()+"' ");
		}
		if(identity.getDob()!=null){
			java.util.Date dt = identity.getDob();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //Or whatever format fits best your needs.
			String dateStr = sdf.format(dt);
			if(foundACriteria)
				query.append(", IDENTITY_BIRTHDATE = '"+dateStr+"' ");
			else
				query.append("IDENTITY_BIRTHDATE = '"+dateStr+"' ");
				
			foundACriteria=true;
			
		}
		if(identity.getEmail()!=null && !identity.getEmail().equals("")){
			if(foundACriteria)
				query.append(", IDENTITY_EMAIL = '"+identity.getEmail()+"' ");
			else
				query.append("IDENTITY_EMAIL = '"+identity.getEmail()+"' ");
				
			//foundACriteria=true;
			
		}
		if(identity.getUid()!=null && !identity.getUid().equals("")){
			query.append(" where IDENTITY_UID = "+identity.getUid()+" ");
		}
	
		return query.toString();
	}
	
}
