package reader;

public class ReaderFactory {
	public static Reader<?> getReader(String ext, Class<?> tClass) {
		Reader<?> reader = null;
		switch (ext) {
		case ".xlsx":
			reader = new XLSXReader<>(tClass);
			break;
		case ".txt":
			reader = new TXTReader<>(tClass);
			break;
		case ".csv":
			reader = new CSVReader<>(tClass);
			break;
		default:
			break;
		}
		return reader;
	}
}
