package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Housekeeper extends Employee{
	private List<Room> assignedRooms;
	public Housekeeper(String name, String surname,Gender gender, LocalDate dateOfBirth, String phoneNumber, String adress,
			String username, String password, int experience, double salary, QualificationLevel  qualificationLevel) {
		
		super(name, surname, gender, dateOfBirth, phoneNumber, adress, username, password, experience, salary,
				qualificationLevel);
		 this.assignedRooms = new ArrayList<>();
	}
	
	public List<Room> getAssignedRooms(){
		return assignedRooms;
	}
	
	public int getAssignedRoomsCount() {
		return assignedRooms.size();
	}
	
	public void assignRoom(Room room) {
        assignedRooms.add(room);
    }

    public void removeRoom(Room room) {
        assignedRooms.remove(room);
    }

	public String toCSVString() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    StringBuilder sb = new StringBuilder();
	    sb.append("Housekeeper").append(",");
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