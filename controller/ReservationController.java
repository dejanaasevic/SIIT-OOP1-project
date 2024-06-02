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
import entity.Reservation;
import entity.ReservationRequest;
import entity.ReservationStatus;
import entity.Room;
import entity.RoomType;
import manager.HotelManager;

public class ReservationController {
	private HotelManager hotelManager;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private String filename = "src/data/reservation.csv";
	
	public ReservationController(HotelManager hotelManager) {
		this.hotelManager = hotelManager;
	}
	
	public void readReservationDataFromCSV() {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(",");
	            RoomType roomType = RoomType.valueOf(data[0].trim());
	            int numberOfGuests = Integer.parseInt(data[1].trim());
	            LocalDate startDate = LocalDate.parse(data[2].trim(), formatter);
	            LocalDate endDate = LocalDate.parse(data[3].trim(), formatter);
	            ReservationStatus reservationStatus = ReservationStatus.valueOf(data[4].trim());
	            String guestUsername = data[5].trim();
	            String roomNumber = data[7].trim();
	            double price = Double.parseDouble(data[8].trim());
	            
	            Room room = hotelManager.getRooms().FindById(roomNumber);
	            Guest guest = hotelManager.getGuests().FindById(guestUsername);
	            if (guest == null || room == null) {
	                continue;
	            }
	            
	            Reservation reservation = new Reservation(roomType, numberOfGuests, startDate, endDate, room, guest);
	            reservation.setReservationStatus(reservationStatus);
	            reservation.setPrice(price);
	            
	            if (data.length > 6 && !data[6].trim().isEmpty()) {
	                String[] additionalServices = data[6].split(";");
	                for (String serviceName : additionalServices) {
	                    AdditionalService service = hotelManager.getAdditionalServices().FindById(serviceName.trim());
	                    if (service != null) {
	                        reservation.addAdditionalService(service);
	                    }
	                }
	            }
	            
	            hotelManager.addReservation(reservation);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public boolean deleteReservationFromCSV(Reservation reservation) {
		List<String> lines = new ArrayList<>();
        boolean isDeleted = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String startDateString = data[2].trim();
                String endDateString = data[3].trim();
                String roomNumber = data[7].trim();
                double price = Double.parseDouble(data[8].trim());
                if (!startDateString.equals(reservation.getStartDate().toString()) &&
                    !endDateString.equals(reservation.getEndDate().toString()) &&
                    !roomNumber.equals(reservation.getRoom().getRoomNumber()) &&
                    price != reservation.getPrice()){
                    lines.add(line);
                } else {
                    isDeleted = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

	public boolean writeReservationToCSV(Reservation reservation) {
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
            writer.println(reservation.toCSVString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public boolean updateReservationInCSV(LocalDate startDate, LocalDate endDate, String roomStr, Reservation reservation) {
	    List<String> lines = new ArrayList<>();
	    boolean isUpdated = false;
	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(",");
	            String csvStartDate = data[2];
	            String csvEndDate = data[3];
	            String csvRoomStr = data[7];
	            double price = Double.parseDouble(data[8].trim());
	            if (csvStartDate.equals(startDate.toString())
	            		&& csvEndDate.equals(endDate.toString()) && csvRoomStr.equals(roomStr) && price == reservation.getPrice()) {
	                lines.add(reservation.toCSVString());
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

	public void checkAndRejectExpiredReservations() {
		LocalDate today = LocalDate.now();
		for (Reservation reservation : hotelManager.getReservations().get().values()) {
            if (reservation.getReservationStatus() == ReservationStatus.CONFIRMED &&
                reservation.getEndDate().isBefore(today)) {
                reservation.setReservationStatus(ReservationStatus.COMPLETED);
                updateReservationInCSV(reservation.getStartDate(), reservation.getEndDate(),reservation.getRoom().getRoomNumber(),reservation);
            }
        }
		
	}
}
