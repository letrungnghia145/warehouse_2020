package utils;

import java.sql.Connection;
import java.sql.Statement;

import constants.Strategy;

public class DBManageTableUtils {

	public static boolean createTable(Strategy url_connection, String name, String... columns) {
		String sqlCreateTable = getSqlCreateTable(name, columns);
		String sqlDropOldProcedure = "DROP PROCEDURE IF EXISTS insert" + name;
		String sqlCreateInsertProcedure = getSqlCreateInsertProcedure(name, columns);
		String[] queries = new String[] { sqlCreateTable, sqlDropOldProcedure, sqlCreateInsertProcedure };
		Connection connection = null;
		try {
			connection = DBConnectionUtils.getConnection(url_connection);
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			for (String query : queries) {
				statement.addBatch(query);
			}
			statement.executeBatch();
			connection.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtils.closeConnectionQuietly(connection);
		}
		return false;
	}

	private static String getSqlCreateInsertProcedure(String name, String... columns) {
		StringBuilder sqlCreateProcedure = new StringBuilder();
		sqlCreateProcedure.append("CREATE PROCEDURE insert" + name + "(");
		for (int i = 0; i < columns.length; i++) {
			String[] split = columns[i].split(":");
			sqlCreateProcedure.append("IN " + split[0] + " " + split[1] + ",");
		}
		sqlCreateProcedure.deleteCharAt(sqlCreateProcedure.lastIndexOf(","));
		sqlCreateProcedure.append(") BEGIN");
		sqlCreateProcedure.append(" INSERT INTO `" + name + "` values(");
		for (int i = 0; i < columns.length; i++) {
			String[] split = columns[i].split(":");
			sqlCreateProcedure.append(split[0] + ",");
		}
		sqlCreateProcedure.deleteCharAt(sqlCreateProcedure.lastIndexOf(","));
		sqlCreateProcedure.append("); END");
		return sqlCreateProcedure.toString();
	}

	private static String getSqlCreateTable(String name, String... columns) {
		StringBuilder sqlCreateTable = new StringBuilder();
		String primaryKey = "";
		sqlCreateTable.append("CREATE TABLE IF NOT EXISTS `" + name + "` (");
		for (int i = 0; i < columns.length; i++) {
			String[] constraints = columns[i].split(":");
			if (i == 0)
				primaryKey = constraints[0];
			for (String constraint : constraints) {
				sqlCreateTable.append(" " + constraint);
			}
			sqlCreateTable.append(",");
		}
		sqlCreateTable.append("PRIMARY KEY (`" + primaryKey
				+ "`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
		return sqlCreateTable.toString();
	}
}
