package test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import constants.Strategy;
import model.RepresentObject;
import utils.DBUtils;

public class Testz {
	public static void test(String nameObject) {
		ArrayList<RepresentObject> data = new ArrayList<>();
		String[] columns = new String[] { "id", "lastname", "firstname", "dob", "class_id", "email", "home_town" };
		Connection connection = null;
		try {
			connection = DBUtils.getConnection(Strategy.URL_STAGING);
			CallableStatement callableStatement = connection.prepareCall("CALL getAll"+nameObject+"s()");
			ResultSet rs = callableStatement.executeQuery();
			RepresentObject object = null;
			while (rs.next()) {
				object = new RepresentObject();
				for (String column : columns) {
					object.addValue(rs.getString(column));
				}
				data.add(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnectionQuietly(connection);
		}

		for (RepresentObject representObject : data) {
			System.out.println(representObject.attributes);
		}
	}
	public static void main(String[] args) {
		test("Student");
	}
}
