package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Reservation {
	private RoomType roomType;
	private int numberOfGuests;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDate creationDate;
	private ReservationStatus reservationStatus;
	private Room room;
	private Guest guest;
	private List<AdditionalService> additionalServices;
	private List<String> roomAttributes;
	private String id;
	private double totalPrice;
	
	public Reservation (RoomType roomType, int numberOfGuests, LocalDate startDate,
						LocalDate endDate,Room room,
						Guest guest, LocalDate creationDate) {
		this.roomType = roomType;
		this.numberOfGuests = numberOfGuests;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reservationStatus = ReservationStatus.PENDING;
		this.room = room;
		this.guest = guest;
		this.id = generateID();
		this.totalPrice = 0;
		this.additionalServices = new ArrayList<>();
		this.roomAttributes = new ArrayList<>();
		this.creationDate = creationDate;
	}
	
	public Reservation(ReservationRequest reservationRequest, Room room) {
		this.roomType = reservationRequest.getRoomType();
		this.numberOfGuests = reservationRequest.getNumberOfGuests();
		this.startDate = reservationRequest.getStartDate();
		this.endDate = reservationRequest.getEndDate();
		this.reservationStatus = ReservationStatus.CONFIRMED;
		this.guest = reservationRequest.getGuest();
		this.room =room;
		this.id = generateID();
		this.totalPrice = calculateTotalPrice();
		this.additionalServices = reservationRequest.getAdditionalServices();
		this.roomAttributes = reservationRequest.getRoomAttributes();
	}
	

	private String generateID() {
		return this.startDate.toString() + "_" + endDate.toString() + "_" + this.room.getRoomNumber();
	}

	public double calculateTotalPrice() {
		return 0;
	}
	
	public void addAdditionalService(AdditionalService service) {
		additionalServices.add(service);
	}
	
	public void removeAdditionalService(AdditionalService service) {
		additionalServices.remove(service);
	}
	
	public List<AdditionalService> getAdditionalService() {
		return additionalServices;
	}
	
	public List<String> getRoomAttributes() {
        return roomAttributes;
    }
	
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public void setPrice(double price) {
		this.totalPrice = price;
	}
	
	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}
	
	public void setStartDate(LocalDate startDate){
		this.startDate = startDate;
	}
	
	public void setEndDate(LocalDate endDate){
		this.endDate = endDate;
	}
	
	public void setCreationDate(LocalDate creationDate){
		this.creationDate = creationDate;
	}
	
	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	
	public void setRoomAttributes(List<String> roomAttributes) {
        this.roomAttributes = roomAttributes;
    }
	
	public double getPrice() {
		return this.totalPrice;
	}
	
	public RoomType getRoomType() {
		return this.roomType;
	}
	
	public int getNumberOfGuests() {
		return this.numberOfGuests;
	}
	
	public LocalDate getStartDate(){
		return this.startDate;
	}
	
	public LocalDate getEndDate(){
		return this.endDate;
	}
	
	public LocalDate getCreationDate() {
		return this.creationDate;
	}
	
	public ReservationStatus getReservationStatus() {
		return this.reservationStatus;
	}
	
	public Room getRoom() {
		return this.room;
	}
	
	public Guest getGuest() {
		return this.guest;
	}
	public String getID() {
		return this.id;
	}
	
	public double getTotalPrice() {
		return this.totalPrice;
	}
	
	public void addRoomAttribute(String roomAttribute) {
        roomAttributes.add(roomAttribute);
    }

    public void removeRoomAttribute(String roomAttribute) {
        roomAttributes.remove(roomAttribute);
    }
	
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
	            "ID rezervacije: " + id + "\n" +
	            "ukupna cena: " + totalPrice + "\n" +
	            "Datum kreiranja:" + creationDate.format(formatter) ;
	}

	public String toCSVString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDateStr = startDate.format(formatter);
        String endDateStr = endDate.format(formatter);
        String creationDateStr = creationDate.format(formatter);

        StringBuilder servicesString = new StringBuilder();
        for (int i = 0; i < additionalServices.size(); i++) {
            servicesString.append(additionalServices.get(i).getName());
            if (i < additionalServices.size() - 1) {
                servicesString.append(";");
            }
        }

        StringBuilder attributesString = new StringBuilder();
        for (int i = 0; i < roomAttributes.size(); i++) {
            attributesString.append(roomAttributes.get(i));
            if (i < roomAttributes.size() - 1) {
                attributesString.append(";");
            }
        }

        return String.join(",", 
            roomType.toString(), 
            String.valueOf(numberOfGuests), 
            startDateStr, 
            endDateStr, 
            reservationStatus.toString(), 
            guest.getUsername(), 
            servicesString.toString(), 
            attributesString.toString(), 
            room.getRoomNumber(),
            String.valueOf(totalPrice),
            creationDateStr
        );
    }
	
	public void updateAdditionalServicePrice(AdditionalService service, double newServicePrice) {
	    if (service != null) {
	        for (AdditionalService additionalService : additionalServices) {
	            if (additionalService.getName().equals(service.getName())) {
	                additionalService.setPrice(newServicePrice); 
	                System.out.println("Cena dodatne usluge " + service.getName() + " je uspešno ažurirana.");
	                return; 
	            }
	        }
	        System.out.println("Dodatna usluga " + service.getName() + " nije pronađena.");
	    } else {
	        System.out.println("Dodatna usluga nije validna.");
	    }
	}
}