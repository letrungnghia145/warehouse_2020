package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import constants.Action;
import constants.ConstantQuery;
import constants.Status;
import constants.Strategy;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.DBConnectionUtils;

@Data
@NoArgsConstructor
public class Config {
	private int id_config;
	private String hostname;
	private int port;
	private String username;
	private String password;
	private String remote_dir;
	private String local_dir;
	private String column_list;
	private String status;

	public static Config loadConfig(int id_config) {
		Connection connection = null;
		Config config = null;
		try {
			connection = DBConnectionUtils.getConnection(Strategy.URL_CONTROL);
			PreparedStatement statement = connection.prepareStatement(ConstantQuery.LOAD_CONFIG_BY_ID);
			statement.setInt(1, id_config);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				config = new Config();
				config.setColumn_list(rs.getString("column_list"));
				config.setHostname(rs.getString("hostname"));
				config.setId_config(rs.getInt("id_config"));
				config.setLocal_dir(rs.getString("local_dir"));
				config.setPassword(rs.getString("password"));
				config.setPort(rs.getInt("port"));
				config.setRemote_dir(rs.getString("remote_dir"));
				config.setStatus(rs.getString("status"));
				config.setUsername(rs.getString("username"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtils.closeConnectionQuietly(connection);
		}
		return config;
	}

	public static List<Config> loadAllConfigs() {
		Connection connection = null;
		ArrayList<Config> configs = new ArrayList<Config>();
		try {
			connection = DBConnectionUtils.getConnection(Strategy.URL_CONTROL);
			PreparedStatement statement = connection.prepareStatement(ConstantQuery.LOAD_ALL_CONFIGS);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Config config = new Config();
				config = new Config();
				config.setColumn_list(rs.getString("column_list"));
				config.setHostname(rs.getString("hostname"));
				config.setId_config(rs.getInt("id_config"));
				config.setLocal_dir(rs.getString("local_dir"));
				config.setPassword(rs.getString("password"));
				config.setPort(rs.getInt("port"));
				config.setRemote_dir(rs.getString("remote_dir"));
				config.setStatus(rs.getString("status"));
				config.setUsername(rs.getString("username"));
				configs.add(config);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtils.closeConnectionQuietly(connection);
		}
		return configs;
	}

	public static List<Config> loadAllConfigs(Status status) {
		Connection connection = null;
		ArrayList<Config> configs = new ArrayList<Config>();
		try {
			connection = DBConnectionUtils.getConnection(Strategy.URL_CONTROL);
			PreparedStatement statement = connection.prepareStatement(ConstantQuery.LOAD_CONFIG_BY_STATUS);
			statement.setString(1, status.name());
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Config config = new Config();
				config = new Config();
				config.setColumn_list(rs.getString("column_list"));
				config.setHostname(rs.getString("hostname"));
				config.setId_config(rs.getInt("id_config"));
				config.setLocal_dir(rs.getString("local_dir"));
				config.setPassword(rs.getString("password"));
				config.setPort(rs.getInt("port"));
				config.setRemote_dir(rs.getString("remote_dir"));
				config.setStatus(rs.getString("status"));
				config.setUsername(rs.getString("username"));
				configs.add(config);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtils.closeConnectionQuietly(connection);
		}
		return configs;
	}

	public void updateStatus(Status status) {
		Connection connection = null;
		try {
			connection = DBConnectionUtils.getConnection(Strategy.URL_CONTROL);
			PreparedStatement statement = connection.prepareStatement(ConstantQuery.UPDATE_CONFIG_STATUS_BY_CONFIG_ID);
			statement.setString(1, status.name());
			statement.setInt(2, this.getId_config());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtils.closeConnectionQuietly(connection);
		}
	}

	public static void main(String[] args) {
//		List<Config> configs = Config.loadAllConfigs(Status.IN_PROGRESS);
//		for (Config config : configs) {
//			System.out.println(config);
//		}
		Config config = Config.loadConfig(1);
		config.updateStatus(Status.IN_PROGRESS);
	}
}
