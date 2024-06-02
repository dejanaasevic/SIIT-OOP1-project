package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RoomCleaningRecord {
	 private LocalDate date;
	 private Housekeeper housekeeper;
	 private Room room;
	 private CleaningStatus status;
	 private String id;
	 public RoomCleaningRecord(LocalDate date, Housekeeper housekeeper, Room room, CleaningStatus status) {
		 this.date = date;
		 this.housekeeper = housekeeper;
		 this.room = room;
		 this.status = status;
		 this.id = generateId();
	 }
	 
	 private String generateId() {
		return date.toString() + "_" + room.getRoomNumber();
 	}

	public Housekeeper getHousekeeper() {
		 return this.housekeeper;
	 }
	 
	 public LocalDate getDate() {
		 return this.date;
	 }
	 public Room getRoom() {
		 return this.room;
	 }
	 
	 public CleaningStatus getStatus() {
		 return this.status;
	 }
	 public String getID() {
		 return this.id;
	 }
	 
	 public void setHousekeeper(Housekeeper housekeeper) {
		  this.housekeeper = housekeeper;
	 }
	 
	 public void setDate(LocalDate date) {
		 this.date = date;
	 }
	 
	 public void setRoomRoom(Room room) {
		 this.room = room;
	 }
	 
	 public void setStatus(CleaningStatus status) {
		 this.status = status;
	 }
	 
	 @Override
	    public String toString() {
	        return "date:" + date +
	                ",housekeeper:" + housekeeper +
	                ", room:" + room +
	                ", status:" + status;
	    }

	 public String toCSVString() {
		    StringBuilder csvString = new StringBuilder();
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    csvString.append(date.format(formatter)).append(",");
		    csvString.append(housekeeper.getUsername()).append(",");
		    csvString.append(room.getRoomNumber()).append(",");
		    csvString.append(status.name());
		    return csvString.toString();
		}
}