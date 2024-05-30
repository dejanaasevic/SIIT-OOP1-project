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

import entity.AdditionalService;
import manager.HotelManager;

public class AdditionalServiceController {
	
	private HotelManager hotelManager;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private String filename = "src/data/additionalService.csv";
	    
	public AdditionalServiceController(HotelManager hotelManager) {
		this.hotelManager = hotelManager;
	}
	
	public void readAdditionalServiceDataFromCSV() {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String name = values[0];
                Double price = Double.parseDouble(values[1]);
                AdditionalService additionalServiceData = new AdditionalService(name, price);
                hotelManager.addAdditionalService(additionalServiceData); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public boolean writeAdditionalServiceToCSV(AdditionalService additionalService) {
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
            writer.println(additionalService.toCSVString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}

	public boolean updateAdditionalServiceInCSV(AdditionalService additionalService) {
		List<String> lines = new ArrayList<>();
        String name = additionalService.getName();
        boolean isUpdated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[0]; 
                if (username.equals(name)) {
                    
                    String updatedLine = additionalService.toCSVString();
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

	public boolean deleteAdditionalServiceFromCSV(AdditionalService additionalService) {
		 List<String> lines = new ArrayList<>();
	        boolean isDeleted = false;

	        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] data = line.split(",");
	                String serviceName = data[0].trim();
	                if (!serviceName.equals(additionalService.getName())) {
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