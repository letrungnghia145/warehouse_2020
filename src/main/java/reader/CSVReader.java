package reader;

import java.io.File;
import java.util.List;

public class CSVReader<T> implements Reader<T> {
	private Class<T> instanceClass;

	public CSVReader(Class<T> instanceClass) {
		this.instanceClass = instanceClass;
	}

	public List<T> readData(File file) {
		// TODO Auto-generated method stub
		return null;
	}

}
