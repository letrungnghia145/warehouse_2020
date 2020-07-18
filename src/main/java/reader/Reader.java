package reader;

import model.WrapArrayList;

public interface Reader {
	public WrapArrayList readData(Readable file) throws Exception;
}
