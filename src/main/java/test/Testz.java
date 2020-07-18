package test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import constants.Strategy;
import utils.DBUtils;

public class Testz {
	public static void main(String[] args) {
		Connection connection = null;
		try {
			connection = DBUtils.getConnection(Strategy.URL_STAGING);
			CallableStatement callableStatement = connection.prepareCall("CALL getAllStudents()");
			ResultSet rs = callableStatement.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtils.closeConnectionQuietly(connection);
		}
	}
}
