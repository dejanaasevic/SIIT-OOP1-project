package hotel;

public enum RoomStatus {
	OCCUPIED("Soba je zauzeta"),
	VACANT("Soba je slobodna"),
	CLEANING("Soba se čisti");
	
	private final String description;
	
	RoomStatus(String description){
		this. description = description;
	}
	
	public String getDescription() {
		return description;
	}
}