package controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entity.Administrator;
import entity.Gender;
import entity.Guest;
import entity.Housekeeper;
import entity.QualificationLevel;
import entity.Receptionist;
import manager.HotelManager;

public class UserController {
    private HotelManager hotelManager;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String filename = "src/data/users.csv";
    
    public UserController(HotelManager hotelManager) {
        this.hotelManager = hotelManager;
    }

    public void readUsersFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String userType = data[0];
                LocalDate date = LocalDate.parse(data[4], dateFormatter);
                switch (userType) {
                    case "Administrator":
                        hotelManager.addAdministrator(new Administrator(
                            data[1], data[2], Gender.valueOf(data[3]),
                            date, data[5], data[6], data[7], data[8]
                        ));
                        break;
                    case "Receptionist":
                        hotelManager.addReceptionist(new Receptionist(
                            data[1], data[2], Gender.valueOf(data[3]),
                            date, data[5], data[6], data[7], data[8],
                            Integer.parseInt(data[9]), Double.parseDouble(data[10]), QualificationLevel.valueOf(data[11])
                        ));
                        break;
                    case "Housekeeper":
                        hotelManager.addHousekeeper(new Housekeeper(
                            data[1], data[2], Gender.valueOf(data[3]),
                            date, data[5], data[6], data[7], data[8],
                            Integer.parseInt(data[9]), Double.parseDouble(data[10]), QualificationLevel.valueOf(data[11])
                        ));
                        break;
                    case "Guest":
                        hotelManager.addGuest(new Guest(
                            data[1], data[2], Gender.valueOf(data[3]),
                            date, data[5], data[6], data[7], data[8]
                        ));
                        break;
                    default:
                        System.out.println("Nepoznat tip korisnika: " + userType);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean writeEmployeeToCSV(Housekeeper housekeeper) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
            writer.println(housekeeper.toCSVString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean writeEmployeeToCSV(Receptionist receptionist) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
            writer.println(receptionist.toCSVString());
            return true;
        } catch (IOException e) {
        	e.printStackTrace();
        	return false;
        }
    }
    
	public boolean writeGuestToCSV(Guest newGuest) {
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
            writer.println(newGuest.toCSVString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateEmployeeInCSV(Housekeeper housekeeper) {
        List<String> lines = new ArrayList<>();
        String targetUsername = housekeeper.getUsername();
        boolean isUpdated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[7]; 
                if (username.equals(targetUsername)) {
                    
                    String updatedLine = housekeeper.toCSVString();
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
    
    public boolean updateEmployeeInCSV(Receptionist receptionist) {
        List<String> lines = new ArrayList<>();
        String targetUsername = receptionist.getUsername();
        boolean isUpdated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[7]; 
                if (username.equals(targetUsername)) {
                    
                    String updatedLine = receptionist.toCSVString();
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
            	return true;
            }
        } else {
            return false;
        }
    }
    
    public boolean updateGuestInCSV(Guest guest) {
        List<String> lines = new ArrayList<>();
        String targetUsername = guest.getUsername();
        boolean isUpdated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String username = data[7]; 
                if (username.equals(targetUsername)) {
                    
                    String updatedLine = guest.toCSVString();
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
    
    public boolean deleteEmployeeFromCSV(Receptionist receptionist) {
        List<String> lines = new ArrayList<>();
        boolean isDeleted = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String lineUsername = data[7].trim();
                if (!lineUsername.equals(receptionist.getUsername())) {
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
    
    public boolean deleteEmployeeFromCSV(Housekeeper housekeeper) {
        List<String> lines = new ArrayList<>();
        boolean isDeleted = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String lineUsername = data[7].trim();
                if (!lineUsername.equals(housekeeper.getUsername())) {
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
    
    
    public boolean deleteGuestFromCSV(Guest guestToDelete) {
        List<String> lines = new ArrayList<>();
        boolean isDeleted = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String lineUsername = data[7].trim();
                if (!lineUsername.equals(guestToDelete.getUsername())) {
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