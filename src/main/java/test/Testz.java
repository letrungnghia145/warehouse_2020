package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import constants.Action;
import constants.ConstantQuery;
import constants.Status;
import constants.Strategy;
import log.Logger;
import model.Log;
import utils.DBUtils;

public class Testz {
	private static List<String> getTableName(String schemaName) {
		List<String> tables = new ArrayList<String>();
		Connection connection = null;
		try {
			connection = DBUtils.getConnection(Strategy.URL_STAGING);
			PreparedStatement statement = connection.prepareStatement(ConstantQuery.GET_NAME_TABLE_FROM_DB);
			statement.setString(1, schemaName);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				tables.add(rs.getString("TABLE_NAME"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			DBUtils.closeConnectionQuietly(connection);
		}
		return tables;
	}

	private static Log getLog(String tableName) {
		Connection connection = null;
		Log log = null;
		try {
			connection = DBUtils.getConnection(Strategy.URL_CONTROL);
			String sql = "select * from log where source_name = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, tableName);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				log = new Log();
				log.setId_config(rs.getInt("id_config"));
				log.setCurrent_action(rs.getString("current_action"));
				log.setId_log(rs.getInt("id_log"));
				log.setSource_dir(rs.getString("source_dir"));
				log.setSource_name(rs.getString("source_name"));
				log.setStatus(rs.getString("status"));
				log.setTime_insert(rs.getTimestamp("time_insert"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnectionQuietly(connection);
		}
		return log;
	}

	public static void main(String[] args) throws Exception {
//		Connection connection = DBUtils.getConnection(Strategy.URL_CONTROL);
//		Log log = Logger.readLog(Action.DOWNLOAD, Status.SUCCESS);
//		System.out.println(log);
//		System.out.println(log.getSource_name());
		List<String> list = getTableName("staging");
		for (String table : list) {
			Log log = getLog(table);
			System.out.println(log);
		}
	}
}
