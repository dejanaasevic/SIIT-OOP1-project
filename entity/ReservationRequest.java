package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationRequest{
	private RoomType roomType;
	private int numberOfGuests;
	private LocalDate startDate;
	private LocalDate endDate;
	private ReservationStatus reservationStatus;
	private Guest guest;
	private List<AdditionalService> additionalServices;
	
	public ReservationRequest(RoomType roomType, int numberOfGuests, LocalDate startDate,
			LocalDate endDate, Guest guest) {
		this.roomType = roomType;
		this.numberOfGuests = numberOfGuests;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reservationStatus = ReservationStatus.PENDING;
		this.guest = guest;
		this.additionalServices = new ArrayList<>();
	}
	
	public RoomType getRoomType() {
        return roomType;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public Guest getGuest() {
        return guest;
    }

    public List<AdditionalService> getAdditionalServices() {
        return additionalServices;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setAdditionalServices(List<AdditionalService> additionalServices) {
        this.additionalServices = additionalServices;
    }
    
    public void addAdditionalService(AdditionalService service) {
		additionalServices.add(service);
	}
	
	public void removeAdditionalService(AdditionalService service) {
		additionalServices.remove(service);
	}
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        String additionalServicesString = "";
        for (AdditionalService service : additionalServices) {
            additionalServicesString += service.getName() + ", ";
        }
        
        return "tip sobe: " + roomType.getDescription() + "\n" +
                "broj gostiju: " + numberOfGuests + "\n" +
                "datum početka: " + startDate.format(formatter) + "\n" +
                "datum kraja: " + endDate.format(formatter) + "\n" +
                "status rezervacije: " + reservationStatus.getDescription() + "\n" +
                "gost: " + guest.getUsername() + "\n" +
                "dodatne usluge: " + additionalServicesString + "\n";
    }

    public String toCSVString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		StringBuilder csvString = new StringBuilder();

		csvString.append(roomType.name()).append(",");
		csvString.append(numberOfGuests).append(",");
		csvString.append(startDate.format(formatter)).append(",");
		csvString.append(endDate.format(formatter)).append(",");
		csvString.append(reservationStatus.name()).append(",");
		csvString.append(guest.getUsername()).append(",");

		for (AdditionalService service : additionalServices) {
			csvString.append(service.getName()).append(";");
		}

		if (!additionalServices.isEmpty()) {
			csvString.deleteCharAt(csvString.length() - 1);
		}

		return csvString.toString();
	}
}