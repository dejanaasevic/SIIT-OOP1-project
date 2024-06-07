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
import entity.Expense;
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
	            String price = data[7];
	            reservationRequest.setPrice(Double.parseDouble(price));
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
	            if (data.length < 6) {
	                System.err.println("Invalid line format: " + line);
	                lines.add(line);
	                continue;
	            }

	            String csvRoomType = data[0].trim();
	            String csvStartDate = data[2].trim();
	            String csvEndDate = data[3].trim();
	            String csvStatus = data[4].trim();
	            String csvUsername = data[5].trim();

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
	            String csvRoomType = data[0].trim();
	            int csvNumberOfGuests = Integer.parseInt(data[1].trim());
	            String csvStartDate = data[2].trim();
	            String csvEndDate = data[3].trim();
	            String csvStatus = data[4].trim();
	            String csvUsername = data[5].trim();

	            String reqRoomType = requestOriginal.getRoomType().name();
	            int reqNumberOfGuests = requestOriginal.getNumberOfGuests();
	            String reqStartDate = requestOriginal.getStartDate().format(formatter);
	            String reqEndDate = requestOriginal.getEndDate().format(formatter);
	            String reqStatus = requestOriginal.getReservationStatus().name();
	            String reqUsername = requestOriginal.getGuest().getUsername();

	            System.out.println("CSV RoomType: " + csvRoomType + ", Request RoomType: " + reqRoomType);
	            System.out.println("CSV NumberOfGuests: " + csvNumberOfGuests + ", Request NumberOfGuests: " + reqNumberOfGuests);
	            System.out.println("CSV StartDate: " + csvStartDate + ", Request StartDate: " + reqStartDate);
	            System.out.println("CSV EndDate: " + csvEndDate + ", Request EndDate: " + reqEndDate);
	            System.out.println("CSV Status: " + csvStatus + ", Request Status: " + reqStatus);
	            System.out.println("CSV Username: " + csvUsername + ", Request Username: " + reqUsername);

	            // Check if all fields match
	            if (csvRoomType.equals(reqRoomType) && 
	                csvNumberOfGuests == reqNumberOfGuests &&
	                csvStartDate.equals(reqStartDate) &&
	                csvEndDate.equals(reqEndDate) &&
	                csvStatus.equals(reqStatus) &&
	                csvUsername.equals(reqUsername)) {
	                isDeleted = true;
	            } else {
	                lines.add(line);
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
	            Expense expence = new Expense(reservationRequest.getGuest(), reservationRequest.getRoomType(),today, reservationRequest.getPrice());
	            hotelManager.addExpense(expence);
	            deleteExpenseFromCSV(expence);
			}
		}
		
	}
	
	 public boolean deleteExpenseFromCSV(Expense expenseToDelete) {
	        List<String> lines = new ArrayList<>();
	        boolean isDeleted = false;

	        try (BufferedReader br = new BufferedReader(new FileReader("src/data/expenses.csv"))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] data = line.split(",");
	                Guest guest = hotelManager.getGuests().FindById(data[0]);
	                RoomType roomType = RoomType.valueOf(data[1]);
	                LocalDate date = LocalDate.parse(data[2], dateFormatter);
	                double amount = Double.parseDouble(data[3]);
	                
	                Expense expense = new Expense(guest, roomType, date, amount);

	                if (!expense.equals(expenseToDelete)) {
	                    lines.add(line);
	                } else {
	                    isDeleted = true;
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        if (isDeleted) {
	            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("src/data/expenses.csv")))) {
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
}