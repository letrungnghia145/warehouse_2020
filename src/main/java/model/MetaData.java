package model;

import java.util.List;

public class MetaData {
	private List<Object> columnList;

	public MetaData(RepresentObject object) {
		columnList = object.attributes;
	}

	public List<Object> getColumnList() {
		return columnList;
	}

	public int getNumOfColumns() {
		return columnList.size();
	}
}
