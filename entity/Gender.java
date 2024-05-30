package entity;

public enum Gender {
	MALE("muško"),
	FEMALE("žensko");
	
	private final String description;
	
	Gender(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
}
