package reader;

import java.io.File;
import java.util.List;

import model.RepresentObject;

public interface Reader {
	public List<RepresentObject> readData(File file) throws Exception;
}
