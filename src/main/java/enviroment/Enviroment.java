package enviroment;

import java.io.IOException;
import java.util.Properties;

public class Enviroment {
	private static Properties properties;
	static {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			properties = new Properties();
			properties.load(classLoader.getResourceAsStream("resources/config.properties"));
		} catch (Exception e) {
			System.out.println("Error occured when loading resource");
		}
	}

	public static String getProps(String key) throws IOException {
		return (String) properties.getProperty(key);
	}
}
