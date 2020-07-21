package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import constants.Action;
import constants.Status;
import constants.Strategy;
import etl.extract.ExtractStaging;
import etl.transform.TransformClass;
import log.Logger;
import model.DBTable;
import model.ListData;
import model.Log;
import model.RepresentObject;
import model.WrapFile;
import reader.Readable;
import reader.Reader;
import reader.ReaderFactory;

public class TestCompleteMethodClass {
	public static int testReadDataMethodOfDBReaderClass() throws Exception {
		String[] columns = new String[] { "id", "lastname", "firstname", "dob", "class_id", "email", "home_town" };
		Readable readable = new DBTable(Strategy.URL_STAGING, "student", columns);
		Reader reader = ReaderFactory.getReader(readable.getFileType());
		ListData data = reader.readData(readable);
		for (RepresentObject object : data) {
			System.out.println(data.getDataContentType() + object.attributes);
		}
		return data.getNumOfColumn();
	}

	public static int testReadDataMethodOfXLSXReaderClass() throws Exception {
		Readable readable = new WrapFile("data/drive.ecepvn.org/sinhvien_chieu_nhom11.xlsx");
		Reader reader = ReaderFactory.getReader(readable.getFileType());
		ListData data = reader.readData(readable);
		for (RepresentObject object : data) {
			System.out.println(data.getDataContentType() + object.attributes);
		}
		return data.getNumOfColumn();
	}

	public static void testLoadStagingMethodOfExtractStagingClass() {
		Log log = Logger.readLog(1, Action.DOWNLOAD, Status.SUCCESS);
		boolean isLoaded = ExtractStaging.extractToStaging(log);
		System.out.println(isLoaded);
	}

	public static int testReadDataMethodOfTXTReaderClass() throws Exception {
		WrapFile file = new WrapFile("data/drive.ecepvn.org/sinhvien_sang_nhom8.txt");

		Reader reader = ReaderFactory.getReader(file.getFileType());
		ListData data = reader.readData(file);
		for (RepresentObject representObject : data) {
			System.out.println(representObject.attributes);
		}
		return data.getNumOfColumn();
	}

	public static void testTransformMethodOfTransformClass() throws Exception {
		TransformClass transformClass = new TransformClass();
		Properties properties = new Properties();
		Map<Integer, Class<?>> map = new HashMap<>();
		map.put(1, Integer.class);
		map.put(2, String.class);
		map.put(3, String.class);
		map.put(4, java.sql.Date.class);
		map.put(5, String.class);
		map.put(6, String.class);
		map.put(7, String.class);
		properties.put("columns.cast.class", map);
		transformClass.setProperties(properties);

		String[] columns = new String[] { "id", "lastname", "firstname", "dob", "class_id", "email", "home_town" };
		Readable readable = new DBTable(Strategy.URL_STAGING, "student", columns);
		Reader reader = ReaderFactory.getReader(readable.getFileType());
		ListData draw_data = reader.readData(readable);
		ListData transform = transformClass.transform(draw_data);
		for (RepresentObject representObject : transform) {
			List<Object> attributes = representObject.attributes;
			System.out.println(attributes);
		}
	}

	public static void run() throws Exception {
		int rs = 0;
//		/* 1 */ rs = testReadDataMethodOfDBReaderClass();
//		/* 2 */ rs = testReadDataMethodOfXLSXReaderClass();
//		/* 3 */ testLoadStagingMethodOfExtractStagingClass();
//		/* 4 */ testTransformMethodOfTransformClass();
//		/* 5 */ rs = testReadDataMethodOfTXTReaderClass();
		System.out.println(rs);
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		run();
		long end = System.currentTimeMillis();
		System.out.println(end - start + "ms");
	}
}
