package test;

import constants.Strategy;
import model.ListData;
import model.RepresentObject;
import reader.Readable;
import reader.Reader;
import reader.ReaderFactory;

public class TestCompleteMethodClass {
	public static void readDataMethodOfDBReaderClass() throws Exception {
		String[] columns = new String[] {"id","lastname", "firstname", "dob", "class_id", "email", "home_town"};
		Readable readable = new DBTable(Strategy.URL_STAGING, "student", columns);
		Reader reader = ReaderFactory.getReader(readable.getFileType());
		ListData data = reader.readData(readable);
		for (RepresentObject object : data) {
			System.out.println(data.getDataContentType() + object.attributes);
		}
	}
	public static void run() throws Exception {
		readDataMethodOfDBReaderClass();
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		run();
		long end = System.currentTimeMillis();
		System.out.println(end-start+"ms");
	}
}
