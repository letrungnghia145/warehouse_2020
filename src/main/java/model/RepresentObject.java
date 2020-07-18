package model;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RepresentObject {
	public List<String> attributes = new ArrayList<>();
	public int attributeLength = 0;

	public RepresentObject(String... attributes) {
		for (String value : attributes) {
			if (value != null) {
				this.attributes.add(value);
			}
		}
	}

	public void addValue(String value) {
		attributes.add(value);
		attributeLength++;
	}
}
