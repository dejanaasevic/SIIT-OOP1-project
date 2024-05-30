package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Guest extends User {

	public Guest(String name, String surname, Gender gender, LocalDate dateOfBirth, String phoneNumber, String adress,
			String username, String password) {
		
		super(name, surname, gender, dateOfBirth, phoneNumber, adress, username, password);
	}

	public String toCSVString() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    StringBuilder sb = new StringBuilder();
	    sb.append("Guest").append(",");
	    sb.append(getName()).append(",");
	    sb.append(getSurname()).append(",");
	    sb.append(getGender()).append(",");
	    sb.append(getDateOfBirth().format(dateFormatter)).append(",");
	    sb.append("\"").append(getPhoneNumber()).append("\",");
	    sb.append(getAdress()).append(",");
	    sb.append(getUsername()).append(",");
	    sb.append(getPassword()).append(",");
	    return sb.toString();
	}
}
