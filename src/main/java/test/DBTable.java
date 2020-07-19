package test;

import java.sql.Connection;

import constants.Strategy;
import reader.Readable;
import utils.DBUtils;

public class DBTable implements Readable {
	private Connection connection;
	private String dataContentType;
	private String fileType;
	private String[] columns;

	public DBTable(Strategy url_connection, String tableName, String... columnsToGet) {
		try {
			connection = DBUtils.getConnection(url_connection);
			dataContentType = tableName;
			this.columns = columnsToGet;
			fileType = "db";
			if (columnsToGet.length == 0)
				columns = new String[] { "*" };
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDataContentType() {
		return dataContentType;
	}

	public String getFileType() {
		return fileType;
	}

	public Connection getConnection() {
		return connection;
	}

	public String[] getColumns() {
		return columns;
	}
}
