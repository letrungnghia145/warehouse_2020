package test;

import constants.Strategy;
import utils.DBManageTableUtils;

public class NewTest {
	public static void main(String[] args) {
		Strategy url_connection = Strategy.URL_STAGING;
		String name = "student";
		String[] columns = "num:int(45):not null,id:varchar(255):null,lastname:varchar(255):null,firstname:varchar(255):null,dob:varchar(255):null,class_id:varchar(255):null,class_name:varchar(255):null,phone:varchar(255):null,email:varchar(255):null,home_town:varchar(255):null,note:varchar(255):null"
				.split(",");
		boolean isSuccess = DBManageTableUtils.createTable(url_connection, name, columns);
		System.out.println(isSuccess);
	}
}
