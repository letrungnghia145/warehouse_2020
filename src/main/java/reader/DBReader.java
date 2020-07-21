package reader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import model.DBTable;
import model.ListData;
import model.RepresentObject;
import utils.DBConnectionUtils;

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
			DBConnectionUtils.closeConnectionQuietly(connection);
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
//			data.setNumOfColumn(rs.getMetaData().getColumnCount());
			ResultSetMetaData metaData = rs.getMetaData();
			RepresentObject object = new RepresentObject();
			for (int i = 0; i < metaData.getColumnCount(); i++) {
				object.addAttribute(String.valueOf(metaData.getColumnName(i + 1)));
			}
			data.setMetaData(object);
			while (rs.next()) {
				object = new RepresentObject();
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					object.addAttribute(String.valueOf(rs.getObject(i + 1)));
				}
				data.add(object);
			}

		} catch (Exception e) {
			DBConnectionUtils.rollbackQuietly(connection);
			e.printStackTrace();
		} finally {
			DBConnectionUtils.closeConnectionQuietly(connection);
		}
	}
}
