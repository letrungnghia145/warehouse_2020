package test;

import constants.Action;
import constants.Status;
import constants.Strategy;
import extractor.ExtractStaging;
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
	public static void readDataMethodOfDBReaderClass() throws Exception {
		String[] columns = new String[] { "id", "lastname", "firstname", "dob", "class_id", "email", "home_town" };
		Readable readable = new DBTable(Strategy.URL_STAGING, "student", columns);
		Reader reader = ReaderFactory.getReader(readable.getFileType());
		ListData data = reader.readData(readable);
		for (RepresentObject object : data) {
			System.out.println(data.getDataContentType() + object.attributes);
		}
	}

	public static void readDataMethodOfXLSXReaderClass() throws Exception {
		Readable readable = new WrapFile("data/drive.ecepvn.org/sinhvien_chieu_nhom11.xlsx");
		Reader reader = ReaderFactory.getReader(readable.getFileType());
		ListData data = reader.readData(readable);
		for (RepresentObject object : data) {
			System.out.println(data.getDataContentType() + object.attributes);
		}
	}

	public static void loadStagingMethodOfExtractStagingClass() {
		Log log = Logger.readLog(1, Action.DOWNLOAD, Status.SUCCESS);
		boolean isLoaded = ExtractStaging.loadStaging(log);
		System.out.println(isLoaded);
	}

	public static void run() throws Exception {
//		/* 1 */ readDataMethodOfDBReaderClass();
//		/* 2 */ readDataMethodOfXLSXReaderClass();
//		/* 3 */ loadStagingMethodOfExtractStagingClass();
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		run();
		long end = System.currentTimeMillis();
		System.out.println(end - start + "ms");
	}
}
