package reader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import constants.Strategy;
import model.ListData;
import model.RepresentObject;
import test.DBTable;
import utils.DBUtils;

public class DBReader implements Reader {

	@Override
	public ListData readData(Readable readable) throws Exception {
		DBTable table = (DBTable) readable;
		ListData data = new ListData(table.getDataContentType());
		Connection connection = null;
		try {
			connection = table.getConnection();
			String[] columns = table.getColumns();
			addStudentToList(data, connection, columns);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnectionQuietly(connection);
		}
		return data;
	}

	private static void addStudentToList(ListData data, Connection connection, String... columns) {
		String procedureName = "get" + data.getDataContentType() + "s";
		StringBuilder builder = new StringBuilder("CREATE PROCEDURE " + procedureName + "() BEGIN " + "SELECT ");
		for (String column : columns) {
			builder.append(column + ", ");
		}
		builder.deleteCharAt(builder.lastIndexOf(","));
		builder.append("FROM " + data.getDataContentType() + "; END");
		String sqlDropOldProcedure = "DROP PROCEDURE IF EXISTS " + procedureName;
		String sqlCreateProcedureSelect = builder.toString();
		String sqlGetAllStudents = "CALL " + procedureName + "()";
		String[] queries = new String[] { sqlDropOldProcedure, sqlCreateProcedureSelect, sqlGetAllStudents };
		try {
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			for (String sql : queries) {
				statement.execute(sql);
			}
			connection.commit();
			ResultSet rs = statement.getResultSet();
			data.setNumOfColumn(rs.getMetaData().getColumnCount());
			RepresentObject object = null;
			while (rs.next()) {
				object = new RepresentObject();
				for (int i = 0; i < data.getNumOfColumn(); i++) {
					object.addValue(String.valueOf(rs.getObject(i + 1)));
				}
				data.add(object);
			}

		} catch (Exception e) {
			DBUtils.rollbackQuietly(connection);
			e.printStackTrace();
		} finally {
			DBUtils.closeConnectionQuietly(connection);
		}
	}

	public static void main(String[] args) throws Exception {
		Reader reader = ReaderFactory.getReader("db");
		Readable readable = new DBTable(Strategy.URL_STAGING, "student");
		ListData data = reader.readData(readable);
		for (RepresentObject object : data) {
			System.out.println(data.getDataContentType() + object.attributes);
		}

//		String[] columns = new String[] { "id", "firstname", "lastname", "dob", "class_id", "email", "home_town" };
//
//		String procedureName = "get" + "student" + "s";
//		StringBuilder builder = new StringBuilder("CREATE PROCEDURE " + procedureName + "() BEGIN " + "SELECT ");
//		for (String column : columns) {
//			builder.append(column + ", ");
//		}
//		builder.deleteCharAt(builder.lastIndexOf(","));
//		builder.append("FROM student; END");
//		System.out.println(builder.toString());
	}
}
