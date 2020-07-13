package reader;

import java.io.File;
import java.util.List;

public interface Reader<T> {
	public List<T> readData(File file) throws Exception;
}
