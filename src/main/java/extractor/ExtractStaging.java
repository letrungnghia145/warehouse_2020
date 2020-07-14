package extractor;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import Exception.IllegalStagingStateException;
import constants.Action;
import constants.ConstantQuery;
import constants.Status;
import constants.Strategy;
import log.Logger;
import model.Config;
import model.Log;
import model.Student;
import reader.Reader;
import reader.ReaderFactory;
import utils.DBUtils;
import utils.TableGenerator;

public class ExtractStaging<T> {
	private Class<T> tClass;

	public ExtractStaging(Class<T> tClass) {
		this.tClass = tClass;
	}

	public boolean loadStaging(Log log) {
		try {
			String source_name = log.getSource_name();
			String extension = source_name.substring(source_name.lastIndexOf(46));
			boolean processedStaging = isProcessedStaging();
			if (processedStaging) {
				Config config = Config.loadConfig(log.getId_config());
				Reader<?> reader = ReaderFactory.getReader(extension, tClass);
				File file = new File(log.getSource_dir() + File.separator + log.getSource_name());
				boolean isGenerated = TableGenerator.generate(Strategy.URL_STAGING, log.getSource_name(),
						config.getColumn_list().split(","));
				if (isGenerated) {
					List<?> data = reader.readData(file);
					for (Object object : data) {
						insertToStaging(object, log.getSource_name());
					}
					Logger.updateLog(log.getId_log(), Action.LOAD_STAGING, Status.SUCCESS);
					return true;
				}
			}
		} catch (IllegalStagingStateException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private void insertToStaging(Object object, String tableName) {
		Connection connection = null;
		try {
			connection = DBUtils.getConnection(Strategy.URL_STAGING);
			Statement statement = connection.createStatement();
			String sql = createInsertQuery(object, tableName);
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR");
		} finally {
			DBUtils.closeConnectionQuietly(connection);
		}
	}

	private String createInsertQuery(Object object, String tableName) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Field[] fields = object.getClass().getDeclaredFields();
		StringBuilder s = new StringBuilder();
		s.append("INSERT INTO `" + tableName + "` values(");
		for (Field field : fields) {
			String field_name = field.getName();
			Method method = object.getClass()
					.getMethod("get" + field_name.substring(0, 1).toUpperCase() + field_name.substring(1));
			s.append("'" + method.invoke(object) + "', ");
		}
		s.append(")");
		s.deleteCharAt(s.lastIndexOf(", "));
		return s.toString();
	}

	private static boolean isProcessedStaging() throws IllegalStagingStateException {
		Connection connection = null;
		try {
			connection = DBUtils.getConnection(Strategy.URL_STAGING);
			PreparedStatement statement = connection.prepareStatement(ConstantQuery.GET_NAME_TABLE_FROM_DB);
			statement.setString(1, "staging");
			ResultSet rs = statement.executeQuery();
			if (!rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnectionQuietly(connection);
		}
		throw new IllegalStagingStateException("Staging is processing");
	}

	public static void main(String[] args) throws Exception {
		List<Config> configs = Config.loadAllConfigs(Status.IN_PROGRESS);
		for (Config config : configs) {
			Log log = Logger.readLog(config.getId_config(), Action.DOWNLOAD, Status.SUCCESS);
			if (log == null)
				break;
			ExtractStaging<Student> extractor = new ExtractStaging<>(Student.class);
			System.out.println(extractor.loadStaging(log));
		}
	}
}
