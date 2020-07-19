package log;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import constants.Action;
import constants.ConstantQuery;
import constants.Status;
import constants.Strategy;
import model.Log;
import utils.DBConnectionUtils;

public class Logger {
	public static Log readLog(int id_log) {
		Connection connection = null;
		Log log = null;
		try {
			connection = DBConnectionUtils.getConnection(Strategy.URL_CONTROL);
			PreparedStatement statement = connection.prepareStatement(ConstantQuery.GET_LOG_BY_ID);
			statement.setInt(1, id_log);
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
			DBConnectionUtils.closeConnectionQuietly(connection);
		}
		return log;
	}

	public static List<Log> readAllLogs() {
		Connection connection = null;
		ArrayList<Log> logs = new ArrayList<Log>();
		try {
			connection = DBConnectionUtils.getConnection(Strategy.URL_CONTROL);
			PreparedStatement statement = connection.prepareStatement(ConstantQuery.GET_ALL_LOGS);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Log log = new Log();
				log = new Log();
				log.setId_config(rs.getInt("id_config"));
				log.setCurrent_action(rs.getString("current_action"));
				log.setId_log(rs.getInt("id_log"));
				log.setSource_dir(rs.getString("source_dir"));
				log.setSource_name(rs.getString("source_name"));
				log.setStatus(rs.getString("status"));
				log.setTime_insert(rs.getTimestamp("time_insert"));
				logs.add(log);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtils.closeConnectionQuietly(connection);
		}
		return logs;
	}

	public static Log readLog(int id_config, Action current_action, Status status) {
		Connection connection = null;
		Log log = null;
		try {
			connection = DBConnectionUtils.getConnection(Strategy.URL_CONTROL);
			PreparedStatement statement = connection.prepareStatement(ConstantQuery.GET_LOG_BY_IDCONFIG_ACTION_STATUS);
			statement.setInt(1, id_config);
			statement.setString(2, current_action.name());
			statement.setString(3, status.name());
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
			DBConnectionUtils.closeConnectionQuietly(connection);
		}
		return log;
	}

	public static void updateLog(int id_log, Action current_action, Status status) {
		Connection connection = null;
		try {
			connection = DBConnectionUtils.getConnection(Strategy.URL_CONTROL);
			PreparedStatement statement = connection.prepareStatement(ConstantQuery.UPDATE_LOG_ACTION_STATUS_LOG_BY_ID);
			statement.setString(1, current_action.name());
			statement.setString(2, status.name());
			statement.setInt(3, id_log);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtils.closeConnectionQuietly(connection);
		}
	}

	public static void writeLog(Log log) {
		Connection connection = null;
		try {
			connection = DBConnectionUtils.getConnection(Strategy.URL_CONTROL);
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS TOTAL FROM LOG");
			int index = 0;
			if (rs.next()) {
				index = rs.getInt("TOTAL") + 1;
			}
			PreparedStatement preparedStatement = connection.prepareStatement(ConstantQuery.INSERT_LOG);
			preparedStatement.setInt(1, index);
			preparedStatement.setInt(2, log.getId_config());
			preparedStatement.setString(3, log.getSource_dir());
			preparedStatement.setString(4, log.getSource_name());
			preparedStatement.setTimestamp(6, log.getTime_insert());
			preparedStatement.setString(7, log.getCurrent_action());
			preparedStatement.setString(8, log.getStatus());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("ERROR WHEN WRITTING LOG");
		} finally {
			DBConnectionUtils.closeConnectionQuietly(connection);
		}

	}

	public static List<File> checkNotLogFiles(String directory) {
		List<String> loggedFiles = loggedFile();
		List<File> notLogFiles = new ArrayList<File>();
		File dir = new File(directory);
		for (File file : dir.listFiles()) {
			if (!loggedFiles.contains(file.getName())) {
				notLogFiles.add(file);
			}
		}
		return notLogFiles;
	}

	private static List<String> loggedFile() {
		List<String> files = new ArrayList<String>();
		Connection connection = null;
		try {
			connection = DBConnectionUtils.getConnection(Strategy.URL_CONTROL);
			String sql = "SELECT source_name FROM LOG";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				files.add(rs.getString("source_name"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			DBConnectionUtils.closeConnectionQuietly(connection);
		}
		return files;
	}
}
