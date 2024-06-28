package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationRequest {
    private RoomType roomType;
    private int numberOfGuests;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate creationDate;
    private ReservationStatus reservationStatus;
    private Guest guest;
    private double price;
    private List<AdditionalService> additionalServices;
    private List<String> roomAttributes;

    public ReservationRequest(RoomType roomType, int numberOfGuests, LocalDate startDate,
                              LocalDate endDate, Guest guest, LocalDate creationDate) {
        this.roomType = roomType;
        this.numberOfGuests = numberOfGuests;
        this.startDate = startDate;
        this.endDate = endDate;
        this.creationDate = creationDate;
        this.reservationStatus = ReservationStatus.PENDING;
        this.guest = guest;
        this.price = 0;
        this.additionalServices = new ArrayList<>();
        this.roomAttributes = new ArrayList<>();
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
    
    public void setCreationDate(LocalDate creationDate){
		this.creationDate = creationDate;
	}
    
    public LocalDate getCreationDate() {
		return this.creationDate;
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

    public List<String> getRoomAttributes() {
        return roomAttributes;
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

    public void setRoomAttributes(List<String> roomAttributes) {
        this.roomAttributes = roomAttributes;
    }

    public void addAdditionalService(AdditionalService service) {
        additionalServices.add(service);
    }

    public void removeAdditionalService(AdditionalService service) {
        additionalServices.remove(service);
    }

    public void addRoomAttribute(String roomAttribute) {
        roomAttributes.add(roomAttribute);
    }

    public void removeRoomAttribute(String roomAttribute) {
        roomAttributes.remove(roomAttribute);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        String additionalServicesString = "";
        for (AdditionalService service : additionalServices) {
            additionalServicesString += service.getName() + ", ";
        }
        
        String roomAttributesString = String.join(", ", roomAttributes);

        return "tip sobe: " + roomType.getDescription() + "\n" +
                "broj gostiju: " + numberOfGuests + "\n" +
                "datum početka: " + startDate.format(formatter) + "\n" +
                "datum kraja: " + endDate.format(formatter) + "\n" +
                "status rezervacije: " + reservationStatus.getDescription() + "\n" +
                "gost: " + guest.getUsername() + "\n" +
                "dodatne usluge: " + additionalServicesString + "\n" +
                "atributi sobe: " + roomAttributesString + "\n" +
                "Datum kreiranja:" + creationDate.format(formatter);
    }

    public String toCSVString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        StringBuilder csvString = new StringBuilder();
        String creationDateStr = creationDate.format(formatter);

        csvString.append(roomType.name()).append(",");
        csvString.append(numberOfGuests).append(",");
        csvString.append(startDate.format(formatter)).append(",");
        csvString.append(endDate.format(formatter)).append(",");
        csvString.append(reservationStatus.name()).append(",");
        csvString.append(guest.getUsername()).append(",");

        for (AdditionalService service : additionalServices) {
            csvString.append(service.getName()).append(";");
        }
        csvString.append(",");

        csvString.append(price).append(",");

        for (String attribute : roomAttributes) {
            csvString.append(attribute).append(";");
        }
        
        csvString.append(",").append(creationDateStr);

        return csvString.toString();
    }

    public ReservationRequest copy() {
        ReservationRequest copy = new ReservationRequest(this.roomType, this.numberOfGuests, this.startDate, this.endDate, this.guest, this.creationDate);
        copy.setReservationStatus(this.reservationStatus);
        copy.setPrice(this.price);

        List<AdditionalService> additionalServicesCopy = new ArrayList<>();
        for (AdditionalService additionalService : this.additionalServices) {
            AdditionalService newService = new AdditionalService(additionalService.getName(), additionalService.getPrice());
            additionalServicesCopy.add(newService);
        }
        copy.setAdditionalServices(additionalServicesCopy);

        List<String> roomAttributesCopy = new ArrayList<>(this.roomAttributes);
        copy.setRoomAttributes(roomAttributesCopy);

        return copy;
    }
}
