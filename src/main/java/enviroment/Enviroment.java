package enviroment;

import java.util.Properties;

public class Enviroment {
	private static Properties properties;
	static {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			properties = new Properties();
			properties.load(classLoader.getResourceAsStream("config.properties"));
		} catch (Exception e) {
			System.out.println("Error occured when loading resource");
		}
	}

	public static String getProps(String key) {
		return (String) properties.getProperty(key);
	}
}
