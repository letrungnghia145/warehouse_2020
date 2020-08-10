package etl.extract;

import java.io.File;
import java.sql.Connection;

import constants.Action;
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
			String tableName = readable.getDataContentType();
			String[] columns = config.getColumn_list().split(",");
			boolean isCreated = DBManageTableUtils.createTable(url_connection, tableName, columns);
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

//	private static boolean isProcessedStaging() throws IllegalStagingStateException {
//		Connection connection = null;
//		try {
//			connection = DBUtils.getConnection(Strategy.URL_STAGING);
//			PreparedStatement statement = connection.prepareStatement(ConstantQuery.GET_NAME_TABLE_FROM_DB);
//			statement.setString(1, "staging");
//			ResultSet rs = statement.executeQuery();
//			if (!rs.next()) {
//				return true;
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			DBUtils.closeConnectionQuietly(connection);
//		}
//		throw new IllegalStagingStateException("Staging is processing");
//	}
}
