package fr.epita.iam.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {
public Properties applicationProperties = null;
public static ApplicationConfig applicationConfig= null;
/**method for the configuration of application
 * @param 
 * @throws IOException
 */
private ApplicationConfig() throws IOException{
applicationProperties = new Properties();
 InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("fr/epita/iam/common/application.properties");
 applicationProperties.load(inputStream);
}

public static ApplicationConfig getInstance() throws IOException{
	if(applicationConfig==null)
		applicationConfig=new  ApplicationConfig();
	return applicationConfig;
}
// 
public String  get(String key) {
	return applicationProperties.getProperty(key);
}

}
