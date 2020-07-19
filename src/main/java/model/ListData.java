package model;

import java.util.ArrayList;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ListData extends ArrayList<RepresentObject> {
	private static final long serialVersionUID = 1L;
	private String dataContentType;
	private int numOfColumn;

	public ListData(String dataContentType) {
		this.dataContentType = dataContentType.toLowerCase();
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
