package test;

import java.sql.Connection;

import constants.Strategy;
import reader.Readable;
import utils.DBConnectionUtils;

public class DBTable implements Readable {
	private Connection connection;
	private String dataContentType;
	private String[] columns;

	public DBTable(Strategy url_connection, String tableName, String... columnsToGet) {
		try {
			connection = DBConnectionUtils.getConnection(url_connection);
			dataContentType = tableName;
			this.columns = columnsToGet;
			if (columnsToGet.length == 0)
				columns = new String[] { "*" };
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getDataContentType() {
		return dataContentType;
	}

	public Connection getConnection() {
		return connection;
	}

	public String[] getColumns() {
		return columns;
	}

	@Override
	public String getFileType() {
		return "database";
	}
}
