package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
}