package test;

import java.sql.Date;
import java.util.Calendar;

import model.ListData;
import model.RepresentObject;
import model.WrapFile;
import reader.Reader;
import reader.ReaderFactory;
import utils.DateFormatUtils;

public class NewTest {
	public static void main(String[] args) throws Exception {
		long timeInMilis = DateFormatUtils.getTimeInMilis("14-05-1999");
		Date date = new Date(timeInMilis);
		System.out.println(date);
	}
}
