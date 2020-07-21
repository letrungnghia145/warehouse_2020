package model;

import java.util.ArrayList;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ListData extends ArrayList<RepresentObject> {
	private static final long serialVersionUID = 1L;
	private String dataContentType;
//	private int numOfColumns;
	private MetaData metaData;

	public ListData(String dataContentType) {
		this.dataContentType = dataContentType.toLowerCase();
	}

	public String getDataContentType() {
		return dataContentType;
	}

	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(RepresentObject object) {
		this.metaData = new MetaData(object);
	}

//	public void setMetaData(MetaData metaData) {
//		this.metaData = metaData;
//	}
	public static void main(String[] args) {
	}
}
