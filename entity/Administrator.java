package entity;

import java.time.LocalDate;

public class Administrator extends User {
	
	public Administrator(String name, String surname, Gender gender, LocalDate dateOfBirth, String phoneNumber,
			String adress, String username, String password) {
		
		super(name, surname, gender, dateOfBirth, phoneNumber, adress, username, password);
	}	
}