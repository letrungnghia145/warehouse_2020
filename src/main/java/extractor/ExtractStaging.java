package extractor;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import reader.XLSXReader;
import utils.DBUtils;
import utils.TableGenerator;

public class ExtractStaging {
	public static boolean loadStaging(Log log) {
		try {
			boolean processedStaging = isProcessedStaging();
			if (processedStaging) {
				Config config = Config.loadConfig(log.getId_config());
				Reader<Student> reader = new XLSXReader<Student>(Student.class);
				File file = new File(log.getSource_dir() + File.separator + log.getSource_name());
				boolean isGenerated = TableGenerator.generate(Strategy.URL_STAGING, log.getSource_name(),
						config.getColumn_list().split(","));
				if (isGenerated) {
					List<Student> data = reader.readData(file);
					for (Student student : data) {
						insertToStaging(student, log.getSource_name());
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

	private static void insertToStaging(Student student, String tableName) {
		Connection connection = null;
		try {
			connection = DBUtils.getConnection(Strategy.URL_STAGING);
			String sql = "INSERT INTO `" + tableName + "` VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, Integer.parseInt(student.getNum()));
			statement.setString(2, student.getId());
			statement.setString(3, student.getLastname());
			statement.setString(4, student.getFirstname());
			statement.setString(5, student.getDob());
			statement.setString(6, student.getClass_id());
			statement.setString(7, student.getClass_name());
			statement.setString(8, student.getPhone());
			statement.setString(9, student.getEmail());
			statement.setString(10, student.getHome_town());
			statement.setString(11, student.getNote());
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR");
		} finally {
			DBUtils.closeConnectionQuietly(connection);
		}
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
//		File file = new File("data/drive.ecepvn.org/sinhvien_chieu_nhom11.xlsx");
		List<Config> configs = Config.loadAllConfigs(Status.IN_PROGRESS);
		for (Config config : configs) {
			Log log = Logger.readLog(config.getId_config(), Action.DOWNLOAD, Status.SUCCESS);
			boolean isLoadStaging = ExtractStaging.loadStaging(log);
			System.out.println(isLoadStaging);
		}
//		Reader<StudentForExcel> reader = new XLSXReader<StudentForExcel>(StudentForExcel.class);
//		List<StudentForExcel> data = reader.readData(file);
//		for (StudentForExcel studentForExcel : data) {
//			System.out.println(studentForExcel);
//		boolean processedStaging = isProcessedStaging();
//		System.out.println(processedStaging);
//		}
	}
}
