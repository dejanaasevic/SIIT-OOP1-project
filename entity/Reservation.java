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
	private ReservationStatus reservationStatus;
	private Room room;
	private Guest guest;
	private List<AdditionalService> additionalServices;
	private String id;
	private double totalPrice;
	
	public Reservation (RoomType roomType, int numberOfGuests, LocalDate startDate,
						LocalDate endDate,Room room,
						Guest guest) {
		this.roomType = roomType;
		this.numberOfGuests = numberOfGuests;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reservationStatus = ReservationStatus.PENDING;
		this.room = room;
		this.guest = guest;
		this.id = generateID();
		this.totalPrice = calculateTotalPrice();
		this.additionalServices = new ArrayList<>();
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
	
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
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
	
	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	
	
	public void getTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
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
	            "dodatne usluge: " + additionalServicesString + "\n" +
	            "ID rezervacije: " + id + "\n" +
	            "ukupna cena: " + totalPrice + "\n";
	}	
}