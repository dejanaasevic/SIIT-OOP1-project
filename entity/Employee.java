package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Employee extends User {

	private int experience;
	private double salary;
	private QualificationLevel  qualificationLevel;
	
	public Employee(String name, String surname, Gender gender, LocalDate dateOfBirth,
			        String phoneNumber, String adress, String username,
			        String password, int experience, double salary, QualificationLevel qualificationLevel) {
		
		super(name, surname, gender, dateOfBirth, phoneNumber, adress, username, password);
		this.experience = experience;
		this.salary = salary;
		this.qualificationLevel = qualificationLevel;
	}
	
	public void setExperience(int experience) {
		this.experience = experience;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public void setQualificationLevel(QualificationLevel  qualificationLevel) {
		this.qualificationLevel = qualificationLevel;
	}
	
	public int getExperience() {
		return this.experience;
	}
	
	public double getSalary() {
		return this.salary;
	}
	
	public QualificationLevel  getQualificationLevel() {
		return this.qualificationLevel;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	    return "ime: " + this.getName() +'\n' +
	            "prezime: " + this.getSurname() + '\n' +
	            "pol: " + this.getGender() + '\n' +
	            "datum rođenja: " + this.getDateOfBirth().format(formatter) + '\n' +
	            "broj telefona: " + this.getPhoneNumber() + '\n' +
	            "adresa: " + this.getAdress() + '\n' +
	            "korisničko ime: " + this.getUsername() + '\n' +
	            "iskustvo: " + this.experience + '\n' +
	            "plata: " + this.salary + '\n' +
	            "nivo kvalifikacije: " + this.qualificationLevel.getDescription() + '\n'+'\n';
	}

	public Employee copy() {
        return new Employee(
            this.getName(),
            this.getSurname(),
            this.getGender(),
            this.getDateOfBirth(),
            this.getPhoneNumber(),
            this.getAdress(),
            this.getUsername(),
            this.getPassword(),
            this.experience,
            this.salary,
            this.qualificationLevel
        );
    }
}