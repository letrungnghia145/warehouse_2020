package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import model.ListData;
import model.RepresentObject;
import model.WrapFile;

public class CSVReader implements Reader {

	private String[] delimiters = new String[] { ",", "|", ":", ";", "\t" };

	public ListData readData(Readable readable) throws Exception {
		WrapFile file = (WrapFile) readable;
		ListData data = new ListData(file.getDataContentType());
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String readLine;
		RepresentObject object = null;
		int index = 0;
		String delimiter = getDelimiter(file);
		while ((readLine = reader.readLine()) != null) {
			object = new RepresentObject();
			if (index == 0) {
				String[] attributes = readLine.split(delimiter);
				for (String attribute : attributes) {
					object.addAttribute(attribute);
				}
				data.setMetaData(object);
				index++;
				continue;
			}
			String standalized = standalize(readLine, delimiter);
			for (String attribute : standalized.split(delimiter)) {
				object.addAttribute(attribute);
			}
			index++;
			data.add(object);
		}
		reader.close();
		return data;
	}

	private String standalize(String standalizable, String d) {
		String replace = standalizable.replace(d + d, d + "blank" + d);
		StringBuilder builder = new StringBuilder(replace);
		Character c = null;
		if (d.equals("\\|")) {
			c = Character.valueOf(d.charAt(1));
		} else {
			c = Character.valueOf(d.charAt(0));
		}
		if (builder.charAt(builder.length() - 1) == c)
			builder.append("blank");
		return builder.toString();
	}

	private String getDelimiter(File file) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String readLine = reader.readLine();
			for (String delimiter : delimiters) {
				if (readLine.contains(delimiter)) {
					if (delimiter.equals("|")) {
						return "\\|";
					}
					return delimiter;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
		return "";
	}

}
