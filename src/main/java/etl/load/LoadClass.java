package etl.load;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;

import constants.Strategy;
import model.ListData;
import model.RepresentObject;
import utils.DBConnectionUtils;

public class LoadClass {
	public static boolean loadDataToDB(Strategy url_connection, ListData data) throws Exception {
		String sqlCallProcedure = getSqlCallProcedure(data.getDataContentType(), data.getMetaData().getNumOfColumns());
		Connection connection = null;
		try {
			connection = DBConnectionUtils.getConnection(url_connection);
			connection.setAutoCommit(false);
			CallableStatement callableStatement = connection.prepareCall(sqlCallProcedure);
			for (RepresentObject object : data) {
				List<Object> attributes = object.attributes;
				try {
					for (int i = 0; i < attributes.size(); i++) {
						if (i == 0)
							callableStatement.setInt(i + 1, Integer.parseInt(String.valueOf(attributes.get(i))));
						callableStatement.setString(i + 1, String.valueOf(attributes.get(i)));
					}
					callableStatement.addBatch();
				} catch (Exception e) {
					continue;
				}
			}
			callableStatement.executeBatch();
			connection.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			DBConnectionUtils.closeConnectionQuietly(connection);
		}
	}

	private static String getSqlCallProcedure(String name, int numOfColumns) {
		StringBuilder sqlCallProcedure = new StringBuilder("CALL insert" + name + "(");
		for (int i = 0; i < numOfColumns; i++) {
			sqlCallProcedure.append("?,");
		}
		sqlCallProcedure.deleteCharAt(sqlCallProcedure.lastIndexOf(","));
		sqlCallProcedure.append(")");
		return sqlCallProcedure.toString();
	}
}
