package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class PriceList {
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<RoomType, Double> roomPrices;
    private Map<String, Double> additionalServicePrices;
    private String id;

    public PriceList(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        roomPrices = new HashMap<>();
        additionalServicePrices = new HashMap<>();
        id = generateID();
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public String getId() {
        return this.id;
    }

    public boolean addRoomPrice(RoomType roomType, double price) {
        if (roomPrices.containsKey(roomType)) {
            return false;
        } else {
            roomPrices.put(roomType, price);
            return true;
        }
    }

    public boolean addAdditionalServicePrice(AdditionalService service) {
        if (additionalServicePrices.containsKey(service.getName())) {
            return false;
        } else {
            additionalServicePrices.put(service.getName(), service.getPrice());
            return true;
        }
    }

    public boolean addAdditionalServicePrice(AdditionalService service, double price) {
        if (additionalServicePrices.containsKey(service.getName())) {
            return false;
        } else {
            additionalServicePrices.put(service.getName(), price);
            return true;
        }
    }

    public Double findRoomPrice(RoomType roomType) {
        return roomPrices.get(roomType);
    }

    public Double findAdditionalServicePrice(String serviceName) {
        return additionalServicePrices.get(serviceName);
    }

    public boolean updateRoomPrice(RoomType roomType, double price) {
        if (roomPrices.containsKey(roomType)) {
            roomPrices.put(roomType, price);
            return true;
        }
        return false;
    }

    public boolean updateAdditionalServicePrice(AdditionalService service, double price) {
        if (additionalServicePrices.containsKey(service.getName())) {
            additionalServicePrices.put(service.getName(), service.getPrice());
            return true;
        }
        return false;
    }

    public boolean deleteRoomPrice(RoomType roomType) {
        if (roomPrices.containsKey(roomType)) {
            roomPrices.remove(roomType);
            return true;
        }
        return false;
    }

    public boolean deleteAdditionalServicePrice(String serviceName) {
        if (additionalServicePrices.containsKey(serviceName)) {
            additionalServicePrices.remove(serviceName);
            return true;
        }
        return false;
    }

    public void setRoomPrice(RoomType roomType, double newPrice) {
        if (roomPrices.containsKey(roomType)) {
            roomPrices.put(roomType, newPrice);
        }
    }

    public void setAdditionalServicePrice(AdditionalService service, double newServicePrice) {
        if (additionalServicePrices.containsKey(service.getName())) {
            additionalServicePrices.put(service.getName(), newServicePrice);
        }
    }

    public String generateID() {
        String formattedStartDate = this.startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String formattedEndDate = this.endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return formattedStartDate + "_" + formattedEndDate;
    }

    public String getFormattedRoomPrices() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<RoomType, Double> entry : roomPrices.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(" ");
        }
        return sb.toString().trim();
    }

    public String getFormattedAdditionalServicePrices() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Double> entry : additionalServicePrices.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(" ");
        }
        return sb.toString().trim();
    }
    
    @Override
    public String toString() {
        return "Start date: " + startDate + "\n" +
               "End date: " + endDate + "\n" +
               "Room prices: " + getFormattedRoomPrices() + "\n" +
               "Additional service prices: " + getFormattedAdditionalServicePrices();
    }

    
    public String toCSVString() {
        return startDate + ", " + endDate + ", " + getFormattedRoomPrices() + ", " + getFormattedAdditionalServicePrices();
    }
}