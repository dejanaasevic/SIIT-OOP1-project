package entity;

import java.util.ArrayList;
import java.util.List;

public class Room {
	private RoomType roomType;
	private String roomNumber;
	private RoomStatus roomStatus;
	private List<String> roomAttributes;
	
	public Room (RoomType roomType, String roomNumber, RoomStatus roomStatus) {
		this.roomType = roomType;
		this.roomNumber = roomNumber;
		this.roomStatus = roomStatus;
		this.roomAttributes = new ArrayList<>();
	}
	
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	
	public void setRoomAttributes(List<String> roomAttributes) {
		this.roomAttributes = roomAttributes;
	}
	
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public void setRoomStatus(RoomStatus roomStatus) {
		this.roomStatus = roomStatus;
	}
	
	public List<String> getRoomAttributes(){
		return roomAttributes;
	}
	
	public RoomType getRoomType() {
		return this.roomType;
	}
	
	public String getRoomNumber() {
		return this.roomNumber;
	}
	
	public RoomStatus getRoomStatus() {
		return this.roomStatus;
	}
	
	public boolean hasRoomAttribute(String attribute) {
		return roomAttributes.contains(attribute);
	}
	 
	public boolean addRoomAttribute(String attribute) {
		if(hasRoomAttribute(attribute)) {
			return false;
		}
		roomAttributes.add(attribute);
		return true;
	}
	
	public boolean removeRoomAttribute(String attribute) {
		if(hasRoomAttribute(attribute)) {
			roomAttributes.remove(attribute);
			return true;
		}
		return false;
	}

	public String toString() {
	    String attributesString = String.join(", ", roomAttributes);
	    return "Broj sobe: " + roomNumber + ", Tip sobe: " + roomType + ", Status sobe: " + roomStatus + ", Osobine: " + attributesString;
	}

	public String toCSVString() {
	    String attributesString = String.join("; ", roomAttributes);
	    return roomNumber + "," + roomType + "," + roomStatus + "," + attributesString;
	}
}