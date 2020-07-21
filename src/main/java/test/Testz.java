package test;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.activation.UnsupportedDataTypeException;

import model.ListData;
import model.RepresentObject;
import utils.DateFormatUtils;

public class Testz {
	private Properties properties = new Properties();

	@SuppressWarnings("unchecked")
	public ListData transform(ListData draw_data) {
		Map<Integer, Class<?>> indexColumn_castType = (Map<Integer, Class<?>>) properties.get("columns.cast.class");
		try {
			for (int indexOfObject = 0; indexOfObject < draw_data.size(); indexOfObject++) {
				List<Object> attributes = draw_data.get(indexOfObject).attributes;
				Set<Entry<Integer, Class<?>>> entries = indexColumn_castType.entrySet();
				RepresentObject newObject = new RepresentObject();
				for (Entry<Integer, Class<?>> entry : entries) {
					int indexOfAttribute = entry.getKey() - 1;
					Object draw_format_attribute = attributes.get(indexOfAttribute);
					Class<?> typeClassFormat = entry.getValue();
					Object transformedAttribute = getAttributeFormated(typeClassFormat, draw_format_attribute);
					attributes.set(indexOfAttribute, transformedAttribute);
					newObject.attributes = attributes;
				}
				draw_data.set(indexOfObject, newObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return draw_data;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttributeFormated(Class<T> typeToFormat, Object valueAsObject) throws UnsupportedDataTypeException {
		String value = String.valueOf(valueAsObject);
		String name_class = typeToFormat.getName();
		switch (name_class) {
		case "java.lang.Integer":
			return (T) new Integer(value);
		case "java.lang.String":
			return (T) value;
		case "java.util.Date":
			try {
				if (Integer.parseInt(value) > 0)
					return (T) new java.util.Date(DateFormatUtils.getTimeInMilis(Integer.parseInt(value)));
			} catch (Exception e) {
				return (T) new java.util.Date(DateFormatUtils.getTimeInMilis(value));
			}
		case "java.sql.Date":
			try {
				if (Integer.parseInt(value) > 0)
					return (T) new java.sql.Date(DateFormatUtils.getTimeInMilis(Integer.parseInt(value)));
			} catch (Exception e) {
				return (T) new java.sql.Date(DateFormatUtils.getTimeInMilis(value));
			}
		case "java.sql.Timestamp":
			try {
				if (Integer.parseInt(value) > 0)
					return (T) new java.sql.Timestamp(DateFormatUtils.getTimeInMilis(Integer.parseInt(value)));
			} catch (Exception e) {
				return (T) new java.sql.Timestamp(DateFormatUtils.getTimeInMilis(value));
			}
		default:
			throw new UnsupportedDataTypeException(typeToFormat.getName() + " does not supported to cast value");
		}
	}

	public static void main(String[] args) throws Exception {

		// List properties
		// +TimeFormater
		// Map string name with class<?> to format

//		java.sql.Date treatingDataType = test.treatingDataType(java.sql.Date.class, "14/05/1999");
//		System.out.println(treatingDataType);
//
//		Map<Integer, Class<?>> indexColumn_castType = new HashMap<Integer, Class<?>>();
//		indexColumn_castType.put(0, Integer.class);
//		Properties properties = new Properties();
//		properties.put("columns.cast.class", indexColumn_castType);
//
//		Reader reader = ReaderFactory.getReader("XLSX");
//		WrapFile file = new WrapFile("data/drive.ecepvn.org/sinhvien_chieu_nhom11.xlsx");
//		ListData data = reader.readData(file);
//		ListData transform = test.transform(data);
//		test.setProperties(properties);
//		for (RepresentObject representObject : transform) {
//			List<Object> attributes = representObject.attributes;
//			System.out.println(attributes.get(0));
//		}

		// JOB schedule: crontab -e
		Map<String, Class<?>> map = new HashMap<>();
		map.put("id", Integer.class);
		map.put("lastname", String.class);
		map.put("firstname", String.class);
		map.put("dob", Date.class);
		map.put("class_id", String.class);
		map.put("email", String.class);
		map.put("home_town", String.class);
	}
}
