package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import model.ListData;
import model.RepresentObject;

public class Testz {
//	private Map<String, Object> contrains = new HashMap<String, Object>();
	Properties properties = new Properties();

	public static ListData transform(ListData draw_data) {
		ListData data;
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put("map", new HashMap<>());
		properties.put(new RepresentObject(), 7);
		System.out.println(properties.get("map"));
	}
}
