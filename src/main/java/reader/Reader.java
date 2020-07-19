package reader;

import model.ListData;

public interface Reader {
	public ListData readData(Readable readable) throws Exception;
}
