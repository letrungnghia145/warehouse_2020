package utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import constants.Strategy;
import enviroment.Enviroment;

public class DBConnectionUtils {
	public static Connection getConnection(Strategy strategy) throws ClassNotFoundException, SQLException, IOException {
		Class.forName(Enviroment.getProps("database.mysql.driver-class"));
		String url = Enviroment.getProps("database." + strategy.name().toLowerCase());
		String user = Enviroment.getProps("database.username");
		String password = Enviroment.getProps("database.password");
		Connection connection = DriverManager.getConnection(url, user, password);
		return connection;
	}

	public static void closeConnectionQuietly(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {

				}
			}
		}
	}

	public static void rollbackQuietly(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
