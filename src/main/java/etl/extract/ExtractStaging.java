package etl.extract;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import constants.Action;
import constants.DatabaseName;
import constants.Status;
import constants.Strategy;
import etl.load.LoadClass;
import log.Logger;
import model.Config;
import model.ListData;
import model.Log;
import model.WrapFile;
import reader.Readable;
import reader.Reader;
import reader.ReaderFactory;
import utils.DBConnectionUtils;
import utils.DBManageTableUtils;

public class ExtractStaging {
	public static boolean extractToStaging(Log log) {
		Connection connection = null;
		Strategy url_connection = Strategy.URL_STAGING;
		try {
			connection = DBConnectionUtils.getConnection(url_connection);
			Config config = Config.loadConfig(log.getId_config());
			Readable readable = new WrapFile(log.getSource_dir() + File.separator + log.getSource_name());
//			String tableName = readable.getDataContentType();
			String[] columns = config.getColumn_list().split(",");
			boolean isCreated = DBManageTableUtils.createTable(url_connection, config.getSource_type(), columns);
			if (isCreated) {
				Reader reader = ReaderFactory.getReader(readable.getFileType());
				ListData data = reader.readData(readable);
				boolean isloaded = LoadClass.loadDataToDB(url_connection, data);
				Logger.updateLog(log.getId_log(), Action.LOAD_STAGING, Status.SUCCESS);
				return isloaded;
			}
		} catch (Exception e) {
			Logger.updateLog(log.getId_log(), Action.LOAD_STAGING, Status.FAIL);
		} finally {
			DBConnectionUtils.closeConnectionQuietly(connection);
		}
		return false;
	}

	public static boolean isStagingHaveTable(DatabaseName dbName, String tableName) {
		Connection connection = null;
		try {
			connection = DBConnectionUtils.getConnection(Strategy.URL_STAGING);
			String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, dbName.name().toLowerCase());
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				if (tableName.equals(rs.getString("table_name"))) {
					return true;
				}
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtils.closeConnectionQuietly(connection);
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(isStagingHaveTable(DatabaseName.staging, "registration"));
	}
}
