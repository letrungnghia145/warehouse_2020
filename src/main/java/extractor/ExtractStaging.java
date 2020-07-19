package extractor;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;

import constants.Action;
import constants.Status;
import constants.Strategy;
import log.Logger;
import model.Config;
import model.Log;
import model.RepresentObject;
import model.WrapFile;
import reader.Reader;
import reader.ReaderFactory;
import utils.DBManageTableUtils;
import utils.DBConnectionUtils;

public class ExtractStaging {

	private static boolean insertStaging(WrapFile file, int attributeLength) throws Exception {
		StringBuilder sqlCallProcedure = new StringBuilder("CALL insert" + file.getDataContentType() + "(");
		for (int i = 0; i < attributeLength; i++) {
			sqlCallProcedure.append("?,");
		}
		sqlCallProcedure.deleteCharAt(sqlCallProcedure.lastIndexOf(","));
		sqlCallProcedure.append(")");
		Connection connection = null;
		try {
			Reader reader = ReaderFactory.getReader(file.getFileType());
			List<RepresentObject> data = reader.readData(file);
			connection = DBConnectionUtils.getConnection(Strategy.URL_STAGING);
			connection.setAutoCommit(false);
			CallableStatement callableStatement = connection.prepareCall(sqlCallProcedure.toString());

			for (RepresentObject object : data) {
				List<String> attributes = object.attributes;
				for (int i = 0; i < attributes.size(); i++) {
					if (i == 0)
						callableStatement.setInt(i + 1, Integer.parseInt(attributes.get(i)));
					callableStatement.setString(i + 1, attributes.get(i));
				}
				callableStatement.addBatch();
			}
			callableStatement.executeBatch();
			connection.commit();
			return true;

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			DBConnectionUtils.closeConnectionQuietly(connection);
		}
	}

	public static boolean loadStaging(Log log) {
		Connection connection = null;
		Strategy url_connection = Strategy.URL_STAGING;
		try {
			connection = DBConnectionUtils.getConnection(url_connection);
			Config config = Config.loadConfig(log.getId_config());
			WrapFile file = new WrapFile(log.getSource_dir() + File.separator + log.getSource_name());
			String tableName = file.getDataContentType();
			String[] columns = config.getColumn_list().split(",");
			boolean isCreated = DBManageTableUtils.createTable(url_connection, tableName, columns);
			if (isCreated) {
				boolean isInserted = insertStaging(file, columns.length);
				Logger.updateLog(log.getId_log(), Action.LOAD_STAGING, Status.SUCCESS);
				return isInserted;
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
