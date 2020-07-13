package utils;

import java.sql.Connection;
import java.sql.Statement;

import constants.Action;
import constants.Status;
import constants.Strategy;
import log.Logger;
import model.Config;
import model.Log;

public class TableGenerator {
	public static boolean generate(Strategy database, String tablename, String... columnName) {
		StringBuilder builder = new StringBuilder();
		Connection connection = null;
		builder.append("CREATE TABLE `" + tablename + "` (\n");
//		builder.append(b)
		for (String column : columnName) {
			if (column.equals(columnName[0])) {
				builder.append("`" + column + "` INT(45) NOT NULL,\n");
				continue;
			}
			builder.append("`" + column + "` VARCHAR(255) NULL,\n");
		}
		builder.append("PRIMARY KEY (`" + columnName[0]
				+ "`)\n) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
		try {
			connection = DBUtils.getConnection(database);
			Statement statement = connection.createStatement();
			int executeUpdate = statement.executeUpdate(builder.toString());
			if (executeUpdate != 0)
				return false;
			return true;
		} catch (Exception e) {
		} finally {
			DBUtils.closeConnectionQuietly(connection);
		}
		return false;
	}

	public static void main(String[] args) {
		Config config = Config.loadConfig(1);
		Log log = Logger.readLog(config.getId_config(), Action.DOWNLOAD, Status.SUCCESS);
		TableGenerator.generate(Strategy.URL_STAGING, log.getSource_name(), config.getColumn_list().split(","));
	}
}
