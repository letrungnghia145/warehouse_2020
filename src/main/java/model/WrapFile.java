package model;

import java.io.File;

import reader.Readable;

public class WrapFile extends File implements Readable {
	private static final long serialVersionUID = 1L;
	private String dataContentType;
	private String fileType;

	public WrapFile(String pathname) {
		super(pathname);
		dataContentType = getDataContentType(this.getName());
		fileType = getFileType(this.getName());
	}

	private String getDataContentType(String name) {
		String lowerNameCase = name.toLowerCase();
		if (lowerNameCase.startsWith("sinhvien"))
			return "Student";
		else if (lowerNameCase.startsWith("monhoc"))
			return "Subject";
		else if (lowerNameCase.startsWith("lophoc"))
			return "Class";
		else if (lowerNameCase.startsWith("dangky"))
			return "Registration";
		return lowerNameCase;

	}

	private String getFileType(String name) {
		String fileType = name.substring(name.lastIndexOf(46));
		return fileType;
	}

	public String getDataContentType() {
		return dataContentType;
	}

	public String getFileType() {
		return fileType;
	}
}
