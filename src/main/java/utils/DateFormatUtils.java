package utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateFormatUtils {
	private static String[] delimeters = new String[] { "-", "/" };

	private static long changeTimeline() {
		Calendar calender1900 = Calendar.getInstance();
		Calendar calender1970 = Calendar.getInstance();

//		Excel time
		Date excelTime = Date.valueOf("1900-01-01");
//		Java time
		Date javaTime = Date.valueOf("1970-01-01");

		calender1900.setTime(excelTime);
		calender1970.setTime(javaTime);
		long milis = (calender1970.getTimeInMillis() - calender1900.getTimeInMillis());
		return milis;
	}

	public static String getDate(int dayCount) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long timeLineInMilis = changeTimeline();
		long offsetTimeMilis = 2 * (24 * 3600 * 1000);
		long dayToMilis = (long) dayCount * (24 * 3600 * 1000);
		return simpleDateFormat.format(new java.util.Date(dayToMilis - timeLineInMilis - offsetTimeMilis));
	}

	public static long getTimeInMilis(int dayCount) {
		long timeLineInMilis = changeTimeline();
		long offsetTimeMilis = 2 * (24 * 3600 * 1000);
		long dayToMilis = (long) dayCount * (24 * 3600 * 1000);
		return dayToMilis - timeLineInMilis - offsetTimeMilis;
	}

	public static long getTimeInMilis(String dateAsString) {
		String[] values = null;
		for (String delimiter : delimeters) {
			if (dateAsString.contains(delimiter))
				values = dateAsString.split(delimiter);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = Date.valueOf(values[2] + "-" + values[1] + "-" + values[0]);
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}
}
