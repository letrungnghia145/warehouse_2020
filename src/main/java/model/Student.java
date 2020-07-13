package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	private String num;
	private String id;
	private String lastname;
	private String firstname;
	private String dob;
	private String class_id;
	private String class_name;
	private String phone;
	private String email;
	private String home_town;
	private String note;
}
