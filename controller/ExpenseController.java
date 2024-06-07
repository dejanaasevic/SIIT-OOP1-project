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

import entity.Expense;
import entity.Guest;
import entity.RoomType;
import manager.HotelManager;

public class ExpenseController {
    private HotelManager hotelManager;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String filename = "src/data/expenses.csv";
    
    public ExpenseController(HotelManager hotelManager) {
        this.hotelManager = hotelManager;
    }

    public void readExpensesFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Guest guest = hotelManager.getGuests().FindById(data[0]);
                RoomType roomType = RoomType.valueOf(data[1]);
                LocalDate date = LocalDate.parse(data[2], dateFormatter);
                double amount = Double.parseDouble(data[3]);
                
                Expense expense = new Expense(guest, roomType, date, amount);
                hotelManager.addExpense(expense);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addExpenseToCSV(Expense expense) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
            writer.println(expense.toCSVString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteExpenseFromCSV(Expense expenseToDelete) {
        List<String> lines = new ArrayList<>();
        boolean isDeleted = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
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