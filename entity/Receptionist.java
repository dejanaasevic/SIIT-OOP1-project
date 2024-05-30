package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Receptionist extends Employee {

	public Receptionist(String name, String surname, Gender gender, LocalDate dateOfBirth, String phoneNumber, String adress,
			String username, String password, int experience, double salary, QualificationLevel  qualificationLevel) {
		
		super(name, surname, gender, dateOfBirth, phoneNumber, adress, username, password, experience, salary,
				qualificationLevel);
	}

	public String toCSVString() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    StringBuilder sb = new StringBuilder();
	    sb.append("Receptionist").append(",");
	    sb.append(getName()).append(",");
	    sb.append(getSurname()).append(",");
	    sb.append(getGender()).append(",");
	    sb.append(getDateOfBirth().format(dateFormatter)).append(",");
	    sb.append("\"").append(getPhoneNumber()).append("\",");
	    sb.append(getAdress()).append(",");
	    sb.append(getUsername()).append(",");
	    sb.append(getPassword()).append(",");
	    sb.append(getExperience()).append(",");
	    sb.append(getSalary()).append(",");
	    sb.append(getQualificationLevel());
	    return sb.toString();
	}
}