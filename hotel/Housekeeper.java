package hotel;

import java.time.LocalDate;

public class Housekeeper extends Employee{

	public Housekeeper(String name, String surname,Gender gender, LocalDate dateOfBirth, String phoneNumber, String adress,
			String username, String password, int experience, double salary, QualificationLevel  qualificationLevel) {
		
		super(name, surname, gender, dateOfBirth, phoneNumber, adress, username, password, experience, salary,
				qualificationLevel);		
	}
}