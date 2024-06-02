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
import entity.CleaningStatus;
import entity.Guest;
import entity.Housekeeper;
import entity.Reservation;
import entity.ReservationRequest;
import entity.ReservationStatus;
import entity.Room;
import entity.RoomCleaningRecord;
import entity.RoomType;
import manager.HotelManager;

public class RoomCleaningRecordController {
	private HotelManager hotelManager;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private String filename = "src/data/cleaningRecords.csv";
	    
	public RoomCleaningRecordController(HotelManager hotelManager) {
		this.hotelManager = hotelManager;
	}
	public void readRoomCleaningRecordsFromCSV() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate today = LocalDate.now();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	        	 String[] data = line.split(",");
	        	 LocalDate date = LocalDate.parse(data[0].trim(), formatter);
	        	 String username = data[1].trim();
	        	 Housekeeper housekeeper = hotelManager.getHousekeepers().FindById(username);
	        	 String roomNumber = data[2].trim();
	        	 CleaningStatus status = CleaningStatus.valueOf(data[3].trim().toUpperCase());
	        	 Room room = hotelManager.getRooms().FindById(roomNumber);
	        	 if(housekeeper == null || room == null) {
	        		 continue;
	        	 }
	        	 RoomCleaningRecord roomCleaningRecord = new RoomCleaningRecord(date,housekeeper,room,status);
	        	 hotelManager.addRoomCleaningRecord(roomCleaningRecord);
	        	 if (date.equals(today)) {
	                    housekeeper.assignRoom(room);
	                }
	        }
		} catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public boolean writeRoomCleaningRecordToCSV(RoomCleaningRecord roomCleaningRecords) {
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
            writer.println(roomCleaningRecords.toCSVString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	public boolean updateRoomCleaningRecordFromCSV(RoomCleaningRecord updatedRecord) {
	    List<String> lines = new ArrayList<>();
	    boolean isUpdated = false;
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(",");
	            LocalDate csvDate = LocalDate.parse(data[0], formatter);
	            String csvHouseKeeperUsername = data[1].trim();
	            String csvRoomNumber = data[2].trim();
	            if (csvDate.equals(updatedRecord.getDate()) &&
	                csvHouseKeeperUsername.equals(updatedRecord.getHousekeeper().getUsername()) &&
	                csvRoomNumber.equals(updatedRecord.getRoom().getRoomNumber())) {
	                String updatedLine = updatedRecord.toCSVString();
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
}
