package hotel;

import java.time.LocalDate;

public class Guest extends User {

	public Guest(String name, String surname, Gender gender, LocalDate dateOfBirth, String phoneNumber, String adress,
			String username, String password) {
		
		super(name, surname, gender, dateOfBirth, phoneNumber, adress, username, password);
	}
}