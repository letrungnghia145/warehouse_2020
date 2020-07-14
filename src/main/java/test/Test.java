package test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import model.Student;

public class Test {
	public static String getSQL(Object object) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Field[] fields = object.getClass().getDeclaredFields();
		StringBuilder s = new StringBuilder();
		s.append("INSERT INTO staging values(");
		for (Field field : fields) {
			Method method = object.getClass()
					.getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
			s.append("'"+method.invoke(object) + "', ");
		}
		s.append(")");
		s.deleteCharAt(s.lastIndexOf(", "));
		return s.toString();
	}

	public static void main(String[] args) throws Exception {
		Student student = new Student("1", "17130132", "Lê Trung", "Nghĩa", "14/05/1999", "DH17DTC",
				"Công Nghệ Thông Tin", "0868880758", "17130132@st.hcmuaf.edu.vn", "Tây Ninh", "blank");
		System.out.println(getSQL(student));
	}
}
