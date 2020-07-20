package model;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RepresentObject {
	public List<Object> attributes = new ArrayList<>();
//	public static int numOfColumn;

	public RepresentObject(String... attributes) {
//		numOfColumn = attributes.length;
		for (String value : attributes) {
			if (value != null) {
				this.attributes.add(value);
			}
		}
	}

	public void addValue(String value) {
		attributes.add(value);
//		numOfColumn++;
	}

//	public int getNumOfColumn() {
//		return numOfColumn;
//	}
}
