package test;

import java.util.Properties;

import enviroment.Enviroment;

public class Testt {
	public static void main(String[] args) throws Exception {
		String props = Enviroment.getProps("database.url_staging");
		System.out.println(props);
	}
}
