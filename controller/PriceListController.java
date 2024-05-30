package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
	            hotelManager.addPriceList(priceList);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


}
