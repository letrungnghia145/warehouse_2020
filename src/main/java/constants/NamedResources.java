package constants;

import java.lang.reflect.Field;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NamedResources {
	@SuppressWarnings("unused")
	private static final String student = "sinhvien";
	@SuppressWarnings("unused")
	private static final String clazz = "lophop";
	@SuppressWarnings("unused")
	private static final String registration = "dangky";
	@SuppressWarnings("unused")
	private static final String subject = "monhoc";

	public static String getNamedResource(String name) {
		String named = new String("object");
		try {
			if (name.equals("class"))
				name = "clazz";
			Field[] declaredFields = NamedResources.class.getDeclaredFields();
			for (Field field : declaredFields) {
				if (field.getName().equals(name)) {
					named = (String) field.get(field.getType().newInstance());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return named;
	}
}
