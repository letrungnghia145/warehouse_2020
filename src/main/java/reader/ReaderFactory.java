package reader;

public class ReaderFactory {
	public static Reader getReader(String ext) {
		String extension = ext.toLowerCase();
		if (extension.startsWith("."))
			extension = extension.substring(1);
		Reader reader = null;
		switch (extension) {
		case "xlsx":
			reader = new XLSXReader();
			break;
		case "txt":
			reader = new TXTReader();
			break;
		case "csv":
			reader = new CSVReader();
			break;
		case "database":
			reader = new DBReader();
			break;
		default:
			break;
		}
		return reader;
	}
}
