package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entity.AdditionalService;
import entity.Guest;
import entity.ReservationRequest;
import entity.ReservationStatus;
import entity.RoomType;
import manager.HotelManager;

public class ReservationRequestController {	
	private HotelManager hotelManager;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private String filename = "src/data/reservationRequest.csv";
		    
	public ReservationRequestController(HotelManager hotelManager) {
		this.hotelManager = hotelManager;
	}

	public void readReservationRequestsDataFromCSV() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	    	String line;
	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(",");
	            RoomType roomType = RoomType.valueOf(data[0]);
	            int numberOfGuests = Integer.parseInt(data[1]);
	            LocalDate startDate = LocalDate.parse(data[2], formatter);
	            LocalDate endDate = LocalDate.parse(data[3], formatter);
	            ReservationStatus reservationStatus = ReservationStatus.valueOf(data[4]);
	            String guestUsername = data[5];
	            Guest guest = hotelManager.getGuests().FindById(guestUsername);
	            if (guest == null) {
	                continue;
	            }
	            
	            ReservationRequest reservationRequest = new ReservationRequest(roomType, numberOfGuests, startDate, endDate, guest);
	            reservationRequest.setReservationStatus(reservationStatus);
	            
	            if (data.length > 6) {
	            	
	                String[] additionalServices = data[6].split(";");
	                for (String serviceName : additionalServices) {                	
	                    AdditionalService service = hotelManager.getAdditionalServices().FindById(serviceName.trim());	                    
	                    reservationRequest.addAdditionalService(service);
	                    
	                }
	            }
	            
	            hotelManager.addReservationRequest(reservationRequest);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public boolean writeReservationRequestToCSV(ReservationRequest reservationRequest) {
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
            writer.println(reservationRequest.toCSVString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

	public boolean updateReservationRequestFromCSV(ReservationRequest updatedRequest, ReservationRequest requestOriginal) {
		List<String> lines = new ArrayList<>();
	    boolean isUpdated = false;
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(",");
	            String csvRoomType = data[0];
	            String csvStartDate = data[2];
	            String csvEndDate = data[3];
	            String csvStatus = data[4];
	            String csvUsername = data[5];

	            String reqRoomType = requestOriginal.getRoomType().name();
	            String reqStartDate = requestOriginal.getStartDate().format(formatter);
	            String reqEndDate = requestOriginal.getEndDate().format(formatter);
	            String reqStatus = requestOriginal.getReservationStatus().name();
	            String reqUsername = requestOriginal.getGuest().getUsername();

	            if (csvRoomType.equals(reqRoomType) && csvStartDate.equals(reqStartDate) &&
	                csvEndDate.equals(reqEndDate) && csvStatus.equals(reqStatus) && 
	                csvUsername.equals(reqUsername)) {
	                String updatedLine = updatedRequest.toCSVString();
	                lines.add(updatedLine);
	                isUpdated = true;
	            } else {
	                lines.add(line);
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	    
	    if (isUpdated) {
	        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
	            for (String updatedLine : lines) {
	                writer.println(updatedLine);
	            }
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    return false;
	}

	public boolean deleteReservationRequestFromCSV(ReservationRequest reservationRequest, ReservationRequest requestOriginal) {
	    List<String> lines = new ArrayList<>();
	    boolean isDeleted = false;
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	    
	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(",");
	            String csvRoomType = data[0];
	            String csvStartDate = data[2];
	            String csvEndDate = data[3];
	            String csvStatus = data[4];
	            String csvUsername = data[5];

	            String reqRoomType = requestOriginal.getRoomType().name();
	            String reqStartDate = requestOriginal.getStartDate().format(formatter);
	            String reqEndDate = requestOriginal.getEndDate().format(formatter);
	            String reqStatus = requestOriginal.getReservationStatus().name();
	            String reqUsername = requestOriginal.getGuest().getUsername();
	            if (!(csvRoomType.equals(reqRoomType) && 
	                  csvStartDate.equals(reqStartDate) &&
	                  csvEndDate.equals(reqEndDate) && 
	                  csvStatus.equals(reqStatus) && 
	                  csvUsername.equals(reqUsername))) {
	                lines.add(line);
	            } else {
	                isDeleted = true;
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }

	    if (isDeleted) {
	        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
	            for (String updatedLine : lines) {
	                writer.println(updatedLine);
	            }
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	    } else {
	        return false;
	    }
	}

	public void checkAndRejectExpiredReservationRequests() {
		LocalDate today = LocalDate.now();
		List<ReservationRequest> reservationRequests = hotelManager.getReservationRequests().getReservationRequests();
		for(ReservationRequest reservationRequest: reservationRequests) {
			if(reservationRequest.getStartDate().isBefore(today)
					&& reservationRequest.getReservationStatus().equals(ReservationStatus.PENDING)) {
				ReservationRequest reservationRequestCopy = reservationRequest.copy();
				reservationRequest.setReservationStatus(ReservationStatus.REJECTED);
				updateReservationRequestFromCSV(reservationRequest,reservationRequestCopy);
			}
		}
		
	}
}