package model;

import java.util.ArrayList;

public class WrapArrayList extends ArrayList<RepresentObject> {
	private static final long serialVersionUID = 1L;
	private String dataContentType;
	private int numOfColumn;

	public WrapArrayList(String dataContentType) {
		this.dataContentType = dataContentType;
	}

	public String getDataContentType() {
		return dataContentType;
	}

	public int getNumOfColumn() {
		return numOfColumn;
	}

	public void setNumOfColumn(int numOfColumn) {
		this.numOfColumn = numOfColumn;
	}
}
