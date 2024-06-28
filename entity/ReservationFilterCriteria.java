package entity;

import java.util.List;

public class ReservationFilterCriteria {
	private String roomType;
	private String roomNumber;
	private Double minPrice;
	private Double maxPrice;
	private boolean airConditioning;
	private boolean tv;
	private boolean balcony;
	private boolean nonSmokingRoom;
	private boolean smokingRoom;
	private boolean safe;
	private boolean miniBar;
	
	 public ReservationFilterCriteria(String roomType, String roomNumber, Double minPrice, Double maxPrice,
             boolean airConditioning, boolean tv, boolean balcony,
             boolean nonSmokingRoom, boolean smokingRoom, boolean safe, boolean miniBar) {
		 	this.roomType = roomType;
		 	this.roomNumber = roomNumber;
		 	this.minPrice = minPrice;
		 	this.maxPrice = maxPrice;
		 	this.airConditioning = airConditioning;
		 	this.tv = tv;
		 	this.balcony = balcony;
		 	this.nonSmokingRoom = nonSmokingRoom;
		 	this.smokingRoom = smokingRoom;
		 	this.safe = safe;
		 	this.miniBar = miniBar;
	 }
	 
	   public String getRoomType() {
	        return roomType;
	    }

	    public String getRoomNumber() {
	        return roomNumber;
	    }

	    public Double getMinPrice() {
	        return minPrice;
	    }

	    public Double getMaxPrice() {
	        return maxPrice;
	    }

	    public boolean hasAirConditioning() {
	        return airConditioning;
	    }

	    public boolean hasTv() {
	        return tv;
	    }

	    public boolean hasBalcony() {
	        return balcony;
	    }

	    public boolean isNonSmokingRoom() {
	        return nonSmokingRoom;
	    }

	    public boolean isSmoking() {
	        return smokingRoom;
	    }

	    public boolean hasSafe() {
	        return safe;
	    }

	    public boolean hasMiniBar() {
	        return miniBar;
	    }
	    
	    public boolean matches(Reservation reservation) {
	        if (roomType != null && !roomType.isEmpty() && !reservation.getRoomType().equals(roomType)) {
	            return false;
	        }

	        if (roomNumber != null && !roomNumber.isEmpty() && !reservation.getRoom().getRoomNumber().equals(roomNumber)) {
	            return false;
	        }

	        double totalPrice = reservation.getTotalPrice();
	        if (minPrice != null && totalPrice < minPrice) {
	            return false;
	        }
	        if (maxPrice != null && totalPrice > maxPrice) {
	            return false;
	        }
	        List<String> roomAttributes = reservation.getRoomAttributes();
	        if (airConditioning && !roomAttributes.contains("Klima uređaj")) {
	        	return false;
	        }
	        if (tv && !roomAttributes.contains("TV")) {
	        	return false;
	        }
	        if (balcony && !roomAttributes.contains("Balkon")) {
	        	return false;
	        }
	        if (nonSmokingRoom && !roomAttributes.contains("Ne-pušačka")) {
	        	return false;
	        }
	        if (smokingRoom && !roomAttributes.contains("Pušačka")) {
	        	return false;
	        }
	        if (safe && !roomAttributes.contains("Sef")) {
	        	return false;
	        }
	        if (miniBar && !roomAttributes.contains("Mini-bar")) {
	        	return false;
	        }
	        return true;
	    }
	    
	    public boolean matches(ReservationRequest reservationRequest) {
	        if (roomType != null && !roomType.isEmpty() && !reservationRequest.getRoomType().equals(roomType)) {
	            return false;
	        }

	        double totalPrice = reservationRequest.getPrice();
	        if (minPrice != null && totalPrice < minPrice) {
	            return false;
	        }
	        if (maxPrice != null && totalPrice > maxPrice) {
	            return false;
	        }
	        List<String> roomAttributes = reservationRequest.getRoomAttributes();
	        if (airConditioning && !roomAttributes.contains("Klima uređaj")) {
	        	return false;
	        }
	        if (tv && !roomAttributes.contains("TV")) {
	        	return false;
	        }
	        if (balcony && !roomAttributes.contains("Balkon")) {
	        	return false;
	        }
	        if (nonSmokingRoom && !roomAttributes.contains("Ne-pušačka")) {
	        	return false;
	        }
	        if (smokingRoom && !roomAttributes.contains("Pušačka")) {
	        	return false;
	        }
	        if (safe && !roomAttributes.contains("Sef")) {
	        	return false;
	        }
	        if (miniBar && !roomAttributes.contains("Mini-bar")) {
	        	return false;
	        }
	        return true;
	    }
}
