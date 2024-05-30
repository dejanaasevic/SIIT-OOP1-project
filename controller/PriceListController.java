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
import entity.PriceList;
import entity.RoomType;
import manager.HotelManager;

public class PriceListController {
	
	private HotelManager hotelManager;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private String filename = "src/data/priceList.csv";
	    
	public PriceListController(HotelManager hotelManager) {
		this.hotelManager = hotelManager;
	}
	
	public void readPriceListDataFromCSV() {
	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(",");
	            LocalDate startDate = LocalDate.parse(data[0].trim(), dateFormatter);
	            LocalDate endDate = LocalDate.parse(data[1].trim(), dateFormatter);
	            
	            PriceList priceList = new PriceList(startDate, endDate);
	            
	            String[] roomData = data[2].trim().split(" ");
	            for (String item : roomData) {
	                String[] roomItem = item.split(":");
	                RoomType roomType = RoomType.valueOf(roomItem[0].toUpperCase());
	                Double price = Double.parseDouble(roomItem[1]);
	                priceList.addRoomPrice(roomType, price);
	            }
	            
	            String[] serviceData = data[3].trim().split(" ");
	            for (String item : serviceData) {
	                String[] serviceItem = item.split(":");
	                String serviceName = serviceItem[0];
	                Double price = Double.parseDouble(serviceItem[1]);
	                AdditionalService additionalService = new AdditionalService(serviceName, price);
	                priceList.addAdditionalServicePrice(additionalService);
	            }
	            System.out.print(priceList.generateID());
	            hotelManager.addPriceList(priceList);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public boolean writePiceListToCSV(PriceList priceList) {
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
            writer.println(priceList.toCSVString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}

	public boolean deletePriceListFromCSV(PriceList priceList) {
	    List<String> lines = new ArrayList<>();
	    boolean isDeleted = false;

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String priceListStartDate = priceList.getStartDate().format(formatter);
	    String priceListEndDate = priceList.getEndDate().format(formatter);

	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(",");
	            String startDate = data[0].trim();
	            String endDate = data[1].trim();
	            if (!startDate.equals(priceListStartDate) || !endDate.equals(priceListEndDate)) {
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

	public boolean updatePriceListFromCSV(LocalDate startDate, LocalDate endDate, PriceList priceList) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    List<String> lines = new ArrayList<>();
	    String startDateString = startDate.format(formatter);
	    String endDateString = endDate.format(formatter);
	    boolean isUpdated = false;

	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(",");
	            String existingStartDateString = data[0].trim();
	            String existingEndDateString = data[1].trim();
	            if (existingStartDateString.equals(startDateString) && existingEndDateString.equals(endDateString)) {
	                String updatedLine = priceList.toCSVString();
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
}