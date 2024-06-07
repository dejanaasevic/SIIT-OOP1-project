package entity;

import java.time.LocalDate;

public class Expense {
    private Guest guest;
    private RoomType roomType;
    private LocalDate date;
    private double amount;

    public Expense(Guest guest, RoomType roomType, LocalDate date, double amount) {
        this.guest = guest;
        this.roomType = roomType;
        this.date = date;
        this.amount = amount;
    }

     public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "guest=" + guest +
                ", roomType=" + roomType +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }

    public String toCSVString() {
        return String.join(",", guest.getUsername(), roomType.name(), date.toString(), Double.toString(amount));
    }
}