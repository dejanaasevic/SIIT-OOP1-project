package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entity.Room;
import entity.RoomStatus;
import entity.RoomType;
import manager.HotelManager;

public class RoomController {
	
	private HotelManager hotelManager;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private String filename = "src/data/rooms.csv";
	    
	public RoomController(HotelManager hotelManager) {
		this.hotelManager = hotelManager;
	}
	
	public void readRoomDataFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String roomNumber = values[0];
                RoomType roomType = RoomType.valueOf(values[1]);
                RoomStatus roomStatus = RoomStatus.valueOf(values[2]);
                Room room = new Room(roomType, roomNumber, roomStatus);
                hotelManager.addRoom(room); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public boolean writeRoomToCSV(Room newRoom) {
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("src/data/rooms.csv", true)))) {
            writer.println(newRoom.toCSVString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	public boolean updateRoomInCSV(Room roomToUpdate) {
        List<String> lines = new ArrayList<>();
        String targetNumber = roomToUpdate.getRoomNumber();
        boolean isUpdated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0]; 
                if (username.equals(targetNumber)) {
                    
                    String updatedLine = roomToUpdate.toCSVString();
                    lines.add(updatedLine);
                    isUpdated = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        } else {
           return false;
        }
    }
	
	public boolean deleteRoomFromCSV(Room room) {
	        List<String> lines = new ArrayList<>();
	        boolean isDeleted = false;

	        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] data = line.split(",");
	                String lineRoomNumber = data[0].trim();
	                if (!lineRoomNumber.equals(room.getRoomNumber())) {
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
}
