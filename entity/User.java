package entity;

import java.time.LocalDate;

public class User {
	
	private String name;
	private String surname;
	private Gender gender;
	private LocalDate dateOfBirth;
	private String phoneNumber;
	private String adress;
	private String username;
	private String password;
	
	public User(String name, String surname, Gender gender, LocalDate dateOfBirth,
			    String phoneNumber, String adress, String username, String password) {
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.adress = adress;
		this.username = username;
		this.password = password;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	public void setUsername(String username ) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public Gender getGender() {
		return this.gender;
	}
	
	public LocalDate getDateOfBirth() {
		return this.dateOfBirth;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public String getAdress() {
		return this.adress;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public String toString() {
	    return "Name: " + this.name + "\n" +
	           "Surname: " + this.surname + "\n" +
	           "Gender: " + this.gender + "\n" +
	           "Date of Birth: " + this.dateOfBirth + "\n" +
	           "Phone Number: " + this.phoneNumber + "\n" +
	           "Address: " + this.adress + "\n" +
	           "Username: " + this.username + "\n" +
	           "Password: " + this.password;
	}
	
}