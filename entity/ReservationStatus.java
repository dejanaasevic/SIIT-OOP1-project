package entity;

public enum ReservationStatus {
	PENDING("Rezervacija je na čekanju"),
	CONFIRMED("Rezervacija je potvrđena"),
	REJECTED("Rezervacija je odbijena"),
	CANCELLED("Rezervacija je otkazana"),
	ACTIVE("Rezervacija je aktivna"),
	COMPLETED("Rezervacija je gotova");
	
	private final String description;
	
	ReservationStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

}
