package hotel;

public class Room {
	private RoomType roomType;
	private String roomNumber;
	private RoomStatus roomStatus;
	
	public Room (RoomType roomType, String roomNumber, RoomStatus roomStatus) {
		this.roomType = roomType;
		this.roomNumber = roomNumber;
		this.roomStatus = roomStatus;
	}
	
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public void roomStatus(RoomStatus roomStatus) {
		this.roomStatus = roomStatus;
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
}