/**
 * 
 */
package fr.epita.iam.launcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import fr.epita.iam.common.ApplicationConfig;
import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DaoDeleteException;
import fr.epita.iam.exceptions.DaoSaveException;
import fr.epita.iam.exceptions.DaoSearchException;
import fr.epita.iam.exceptions.DaoUpdateException;
import fr.epita.iam.services.Authenticator;
import fr.epita.iam.services.IdentityDAO;
import fr.epita.iam.services.JDBCIdentityDAO;
import fr.epita.logging.LogConfiguration;
import fr.epita.logging.Logger;

/**launcher class for launching the main application
 
 * @author HAPPY
 *
 */
public class Launcher {

	
	public static void main(String[] args) throws FileNotFoundException, SQLException, ParseException, IOException {

		// IdentityDAO dao = new FileIdentityDAO();
		IdentityDAO dao = new JDBCIdentityDAO();

		ApplicationConfig applicationconfig = ApplicationConfig.getInstance();
		LogConfiguration conf = new LogConfiguration("c:/java/application.log");
		Logger logger = new Logger(conf);

		logger.log("beginning of the program");
		Scanner scanner = new Scanner(System.in);

		System.out.println("User name :");
		String userName = scanner.nextLine();
		System.out.println("Password :");
		String password = scanner.nextLine();

		if (!Authenticator.authenticate(userName, password)) {
			logger.log("unable to authenticate " + userName);
			return;
		} else {
			System.out.println("Successfully authenticated");
			// We are authenticated
			String answer = "";
			List<Identity> listIdentity = null;

			while (!"5".equals(answer)) {
				Date dob = null;
				String displayName = null;
				String email = null;
				String sDate1 = null;
				System.out.println("1. Create Identity");
				System.out.println("2. Update Identity");
				System.out.println("3. Delete Identity");
				System.out.println("4. Search Identity");
				System.out.println("5. Quit");
				System.out.println("your choice : ");

				logger.log("User chose the " + answer + " choice");

				answer = scanner.nextLine();

				switch (answer) {
				case "1":
					System.out.println("Identity Creation");
					logger.log("selected the identity creation");
					System.out.println("please input the identity display name :");
					displayName = scanner.nextLine();
					System.out.println("identity email :");
					email = scanner.nextLine();
					System.out.println("identity date of Birth :dd/MM/yyyy");
					sDate1 = scanner.nextLine();
					dob = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);

					Identity identity = new Identity(displayName, null, email, dob);
					try {
						dao.save(identity);
						System.out.println("the save operation completed successfully");
					} catch (DaoSaveException e) {
						System.out.println("the save operation is not able to complete, details :" + e.getMessage());
					}

					break;
				case "2":

					// Update Identity
					System.out.println("Identity Update");

					logger.log("select the identity to Update");
					System.out.println("please input the identity display name :");
					displayName = scanner.nextLine();
					System.out.println("identity email :");
					email = scanner.nextLine();
					System.out.println("identity date of Birth :dd/MM/yyyy");
					sDate1 = scanner.nextLine();
					if (sDate1 != null && !sDate1.equals(""))
						dob = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
					try {
						for (Identity idx : listIdentity) {
							identity = new Identity(displayName, idx.getUid(), email, dob);
							dao.update(identity);
						}
						System.out.println("the update operation completed successfully");
					} catch (DaoUpdateException e) {
						System.out.println("the update operation is not able to complete, details :" + e.getMessage());
					}

					break;
				case "3":

					// Delete Identity
					System.out.println("Identity Deletion");
					logger.log("selected the identity Deletion");
					System.out.println("please input the identity display name :");
					displayName = scanner.nextLine();
					System.out.println("identity email :");
					email = scanner.nextLine();
					System.out.println("identity date of Birth :dd/MM/yyyy");
					sDate1 = scanner.nextLine();
					if (sDate1 != null && !sDate1.equals(""))
						dob = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
					identity = new Identity(displayName, null, email, dob);
					try {
						dao.delete(identity);
						System.out.println("the delete operation completed successfully");
					} catch (DaoDeleteException e) {
						System.out.println("the delete operation is not able to complete, details :" + e.getMessage());
					}

					break;

				case "4":

					System.out.println("Identity Search");

					logger.log("selected the identity search");
					System.out.println("please input the identity display name :");
					displayName = scanner.nextLine();
					System.out.println("identity email :");
					email = scanner.nextLine();
					System.out.println("identity date of Birth :dd/MM/yyyy");
					sDate1 = scanner.nextLine();
					if (sDate1 != null && !sDate1.equals(""))
						dob = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
					identity = new Identity(displayName, null, email, dob);
					try {
						listIdentity = dao.search(identity);
						if (listIdentity != null && !listIdentity.isEmpty())
							for (Identity idx : listIdentity) {
								System.out.println(idx.toString());
							}
						System.out.println("the search operation completed successfully");
					} catch (DaoSearchException e) {
						System.out.println("the search operation is not able to complete, details :" + e.getMessage());
					}

					break;
				case "5":

					System.out.println("you decided to quit, bye!");
					break;
				default:

					System.out.println("unrecognized option : type 1,2,3,4 or 5 to quit");
					break;
				}

			}

		}

	}

}
