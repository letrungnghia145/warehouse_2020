package model;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class RepresentObject {
	public List<Object> attributes = new ArrayList<>();
//	public static int numOfColumn;

	public RepresentObject(Object... attributes) {
//		numOfColumn = attributes.length;
		for (Object value : attributes) {
			if (value != null) {
				this.attributes.add(value);
			}
		}
	}

	public void addAttribute(Object attribute) {
		attributes.add(attribute);
//		numOfColumn++;
	}

//	public int getNumOfColumn() {
//		return numOfColumn;
//	}
	
	public static void main(String[] args) {
		RepresentObject object = new RepresentObject();
		object.addAttribute("abc");
		object.addAttribute("cell 2");
		System.out.println(object);
	}
}
