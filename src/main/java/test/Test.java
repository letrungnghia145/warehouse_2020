package test;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;

import constants.Strategy;
import log.Logger;
import model.Config;
import model.Log;
import model.RepresentObject;
import model.ListData;
import model.WrapFile;
import reader.Reader;
import reader.ReaderFactory;
import utils.DBManagementUtils;
import utils.DBUtils;

public class Test {
	public static boolean loadStaging(Log log) {
		Connection connection = null;
		try {
			Config config = Config.loadConfig(log.getId_config());
			WrapFile file = new WrapFile(log.getSource_dir() + File.separator + log.getSource_name());
			String[] columns = config.getColumn_list().split(",");
			String tableName = file.getDataContentType();
			boolean isTableCreated = DBManagementUtils.createTable(Strategy.URL_STAGING, tableName, columns);
			if (isTableCreated) {
				Reader reader = ReaderFactory.getReader(file.getFileType());
				ListData data = reader.readData(file);
				return insertData(Strategy.URL_STAGING, data);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnectionQuietly(connection);
		}
		return false;
	}

	private static boolean insertData(Strategy url_connection, ListData data) {
		StringBuilder sqlCallProcedure = new StringBuilder("CALL insert" + data.getDataContentType() + '(');
		int count = 0;
		while (count < data.getNumOfColumn()) {
			sqlCallProcedure.append("?,");
			count++;
		}
		sqlCallProcedure.append(')');
		sqlCallProcedure.deleteCharAt(sqlCallProcedure.lastIndexOf(","));
		Connection connection = null;
		try {
			connection = DBUtils.getConnection(url_connection);
			CallableStatement statement = connection.prepareCall(sqlCallProcedure.toString());
			connection.setAutoCommit(false);
			for (RepresentObject object : data) {
				List<String> attributes = object.attributes;
				for (int i = 0; i < attributes.size(); i++) {
					if (i == 0) {
						statement.setInt(i + 1, Integer.parseInt(attributes.get(i)));
						continue;
					}
					statement.setString(i + 1, attributes.get(i));
				}
				statement.addBatch();
			}
			statement.executeBatch();
			connection.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnectionQuietly(connection);
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
//		Reader reader = ReaderFactory.getReader("XLSX");
//		WrapFile file = new WrapFile("data/drive.ecepvn.org/sinhvien_chieu_nhom11.xlsx");
//		WrapArrayList<RepresentObject> data = reader.readData(file);

		Log log = Logger.readLog(1);
		boolean isLoaded = loadStaging(log);
		System.out.println(isLoaded);
	}
}
