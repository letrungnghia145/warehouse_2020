package utils;

import java.sql.CallableStatement;
import java.sql.Connection;

import constants.Strategy;

public class DBManagementUtils {

	public static boolean createTable(Strategy url_connection, String tableName, String... columns) {
		StringBuilder sqlCreateTable = new StringBuilder();
		String primaryKey = "";
		sqlCreateTable.append("CREATE TABLE IF NOT EXISTS `" + tableName + "` (");
		for (int i = 0; i < columns.length; i++) {
			String[] contrains = columns[i].split(":");
			if (i == 0)
				primaryKey = contrains[0];
			for (String contrain : contrains) {
				sqlCreateTable.append(" " + contrain);
			}
			sqlCreateTable.append(",");
		}
		sqlCreateTable.append("PRIMARY KEY (`" + primaryKey
				+ "`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
		Connection connection = null;
		try {
			connection = DBUtils.getConnection(url_connection);
			connection.setAutoCommit(false);
			CallableStatement prepareCall = connection.prepareCall("USE STAGING");
			prepareCall.addBatch(sqlCreateTable.toString());
			prepareCall.addBatch("DROP PROCEDURE IF EXISTS insert" + tableName + ";");
			prepareCall.addBatch(getQueryCreateInsertProcedure(tableName, columns));
			prepareCall.executeBatch();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			DBUtils.closeConnectionQuietly(connection);
		}
		return false;
	}

	private static String getQueryCreateInsertProcedure(String tableName, String... columns) {
		StringBuilder sqlCreateProcedure = new StringBuilder();
		sqlCreateProcedure.append("CREATE PROCEDURE insert" + tableName + "s(");
		for (int i = 0; i < columns.length; i++) {
			String[] split = columns[i].split(":");
			sqlCreateProcedure.append("IN " + split[0] + " " + split[1] + ",");
		}
		sqlCreateProcedure.deleteCharAt(sqlCreateProcedure.lastIndexOf(","));
		sqlCreateProcedure.append(")BEGIN");
		sqlCreateProcedure.append(" INSERT INTO `" + tableName + "` values(");
		for (int i = 0; i < columns.length; i++) {
			String[] split = columns[i].split(":");
			sqlCreateProcedure.append(split[0] + ",");
		}
		sqlCreateProcedure.deleteCharAt(sqlCreateProcedure.lastIndexOf(","));
		sqlCreateProcedure.append(");END");
		return sqlCreateProcedure.toString();
	}
//
//	public static void main(String[] args) {
//		long start = System.currentTimeMillis();
//		String tableName = "Student";
//		String[] columns = "num:int(45):not null,id:varchar(255):null,lastname:varchar(255):null,firstname:varchar(255):null,dob:varchar(255):null,class_id:varchar(255):null,class_name:varchar(255):null,phone:varchar(255):null,email:varchar(255):null,home_town:varchar(255):null,note:varchar(255):null"
//				.split(",");
//		boolean isCreated = DBManagementUtils.createTable(Strategy.URL_STAGING, tableName, columns);
//		System.out.println(isCreated);
//		long end = System.currentTimeMillis();
//		System.out.println(end - start + "ms");
//	}
}
