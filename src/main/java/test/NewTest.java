package test;

import model.ListData;
import model.RepresentObject;
import model.WrapFile;
import reader.Reader;
import reader.ReaderFactory;

public class NewTest {
	public static void main(String[] args) throws Exception {
//		WrapFile file = new WrapFile("data/drive.ecepvn.org/sinhvien_chieu_nhom4.txt");
		WrapFile file = new WrapFile("data/drive.ecepvn.org/sinhvien_chieu_nhom7.csv");

		Reader reader = ReaderFactory.getReader(file.getFileType());
		ListData data = reader.readData(file);
		for (RepresentObject representObject : data) {
			System.out.println(representObject.attributes);
		}
//		String d = "\t";
//		System.out.println(d.charAt(0));
	}
}
