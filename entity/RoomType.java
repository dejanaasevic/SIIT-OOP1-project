package entity;

public enum RoomType {
	SINGLE("Jednokrevetna soba (1)"),
	DOUBLE("Dvokrevetna soba sa jednim ležajem (2)"),
	TWIN("Dvokrevetna soba sa dva ležaja (1+1)"),
	TRIPLE("Trokrevetna soba (2+1)");

	private final String description;

	RoomType(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
