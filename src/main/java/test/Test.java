package test;

import java.io.File;
import java.io.FileOutputStream;

import constants.Strategy;
import utils.DBManagementUtils;

public class Test {

	public static void main(String[] args) throws Exception {
		FileOutputStream fos = new FileOutputStream(new File("classes/name.java"), true);
		String content = "import java.io.File;\r\n" + 
				"import java.io.FileOutputStream;\r\n" + 
				"import java.util.Date;\r\n" + 
				"\r\n" + 
				"import constants.Strategy;\r\n" + 
				"import utils.DBManagementUtils;\r\n" + 
				"\r\n" + 
				"public class Test {\r\n" + 
				"	private int id;\r\n" + 
				"	private String name;\r\n" + 
				"	private Date dob;\r\n" + 
				"//	public static void main(String[] args) throws Exception {\r\n" + 
				"//		FileOutputStream fos = new FileOutputStream(\"classes/name.java\");\r\n" + 
				"//		String \r\n" + 
				"//	}\r\n" + 
				"}\r\n" + 
				"";
		byte[] bytes = content.getBytes();
		fos.write(bytes);
		fos.close();
	}
}
