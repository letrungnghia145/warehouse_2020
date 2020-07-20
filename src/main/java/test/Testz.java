package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.UnsupportedDataTypeException;

import model.ListData;
import utils.DateFormatUtils;

public class Testz {
	private Properties properties = new Properties();

	@SuppressWarnings("unchecked")
	public ListData transform(ListData draw_data) {
		ListData transformed_data = new ListData();
		Map<Integer, Class<?>> indexColumn_castType = (Map<Integer, Class<?>>) properties.get("columns.cast.class");
		try {
//			if (indexColumn_castType==null)
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@SuppressWarnings("unchecked")
	public <T> T treatingDataType(Class<T> classType, String value) throws UnsupportedDataTypeException {
		String name_class = classType.getName();
		switch (name_class) {
		case "java.lang.Integer":
			return (T) new Integer(value);
		case "java.lang.String":
			return (T) value;
		case "java.util.Date":
			try {
				if (Long.parseLong(value) > 0)
					return (T) new java.util.Date(DateFormatUtils.getTimeInMilis(Long.parseLong(value)));
			} catch (Exception e) {
				return (T) new java.util.Date(DateFormatUtils.getTimeInMilis(value));
			}
		case "java.sql.Date":
			try {
				if (Long.parseLong(value) > 0)
					return (T) new java.sql.Date(DateFormatUtils.getTimeInMilis(Long.parseLong(value)));
			} catch (Exception e) {
				return (T) new java.sql.Date(DateFormatUtils.getTimeInMilis(value));
			}
		case "java.sql.Timestamp":
			try {
				if (Long.parseLong(value) > 0)
					return (T) new java.sql.Timestamp(DateFormatUtils.getTimeInMilis(Long.parseLong(value)));
			} catch (Exception e) {
				return (T) new java.sql.Timestamp(DateFormatUtils.getTimeInMilis(value));
			}
		default:
			throw new UnsupportedDataTypeException(classType.getName() + " does not supported to cast value");
		}
	}

	public static void main(String[] args) throws Exception {
//		Properties properties = new Properties();
//		properties.put("map", new HashMap<>());
//		properties.put(new RepresentObject(), 7);
//		System.out.println(properties.get("map"));
//		Integer newInstance = Integer.class.newInstance();

//		Constructor<?>[] constructors = Integer.class.getConstructors();
//		for (Constructor<?> constructor : constructors) {
//			System.out.println(constructor.getParameterTypes().getClass().newInstance());
//		}
//		Constructor<?>[] declaredConstructors = Integer.class.getDeclaredConstructors();

		// List properties
		// +TimeFormater
		// Map string name with class<?> to format

//		Date treatingDataType = treatingDataType(java.sql.Date.class, "14/05/1999");
//		System.out.println(treatingDataType);

		Map<Integer, Class<?>> indexColumn_castType = new HashMap<Integer, Class<?>>();
		Properties properties = new Properties();
		properties.put("columns.cast.class", indexColumn_castType);
	}
}
