package test;

import etl.extract.ExtractStaging;
import log.Logger;
import model.ListData;
import model.Log;
import model.RepresentObject;
import model.WrapFile;
import reader.Readable;
import reader.Reader;
import reader.ReaderFactory;

public class NewTest {
	public static void main(String[] args) throws Exception {
//		Strategy url_connection = Strategy.URL_STAGING;
//		String name = "student";
//		String[] columns = "num:int(45):not null,id:varchar(255):null,lastname:varchar(255):null,firstname:varchar(255):null,dob:varchar(255):null,class_id:varchar(255):null,class_name:varchar(255):null,phone:varchar(255):null,email:varchar(255):null,home_town:varchar(255):null,note:varchar(255):null"
//				.split(",");
//		boolean isSuccess = DBManageTableUtils.createTable(url_connection, name, columns);
//		System.out.println(isSuccess);

//		Log log = Logger.readLog(49);
//		boolean extractToStaging = ExtractStaging.extractToStaging(log);
//		System.out.println(extractToStaging);
		
		
		Readable readable = new WrapFile("D://data/drive.ecepvn.org/student/sinhvien_chieu_nhom12.csv");
		Reader reader = ReaderFactory.getReader(readable.getFileType());
		ListData data = reader.readData(readable);
		for (RepresentObject representObject : data) {
			System.out.println(representObject.attributes);
		}

//		Config config = Config.loadConfig(4);
//		System.out.println(config);
		
		
	}
}
