package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.HotelController;
import entity.AdditionalService;
import entity.Employee;
import entity.Gender;
import entity.Guest;
import entity.Housekeeper;
import entity.PriceList;
import entity.QualificationLevel;
import entity.Receptionist;
import entity.Reservation;
import entity.ReservationRequest;
import entity.Room;
import entity.RoomStatus;
import entity.RoomType;
import manager.HotelManager;

public class AdministratorFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private HotelManager hotelManager;
    private HotelController hotelController;

    public AdministratorFrame(HotelManager hotelManager, HotelController hotelController) {
        this.hotelManager = hotelManager;
        this.hotelController = hotelController;
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 50, 1500, 1000);
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        this.setTitle("Hotel Management System - Administrator Panel");
        this.setVisible(true);
        this.setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu employeeMenu = new JMenu("Zaposleni");
        menuBar.add(employeeMenu);
        JMenuItem showEmployees = new JMenuItem("Pregledaj sve zaposlene");
        employeeMenu.add(showEmployees);
        JMenuItem addEmployee = new JMenuItem("Dodaj novog zaposlenog");
        employeeMenu.add(addEmployee);
        JMenuItem updateEmplyee = new JMenuItem("Izmeni zaposlenog");
        employeeMenu.add(updateEmplyee);
        JMenuItem deleteEmployee = new JMenuItem("Obriši zaposlenog");
        employeeMenu.add(deleteEmployee);

        JMenu guestMenu = new JMenu("Gosti");
        menuBar.add(guestMenu);
        JMenuItem showGuests = new JMenuItem("Pregledaj sve goste");
        guestMenu.add(showGuests);
        JMenuItem addGuest = new JMenuItem("Dodaj novog gosta");
        guestMenu.add(addGuest);
        JMenuItem updateGuest = new JMenuItem("Izmeni informacije o gostu");
        guestMenu.add(updateGuest);
        JMenuItem deleteGuest = new JMenuItem("Obriši gosta");
        guestMenu.add(deleteGuest);
        
        JMenu roomTypeMenu = new JMenu("Tip sobe");
        menuBar.add(roomTypeMenu);
        JMenuItem showRoomType = new JMenuItem("Pregledaj sve tipove sobe");
        roomTypeMenu.add(showRoomType);
       
        JMenu reservationMenu = new JMenu("Rezervacije");
        menuBar.add(reservationMenu);
        JMenuItem showReservations = new JMenuItem("Pregledaj sve rezervacije");
        reservationMenu.add(showReservations);
        JMenuItem addReservation = new JMenuItem("Kreiraj novu rezervaciju");
        reservationMenu.add(addReservation);
        JMenuItem deleteReservation = new JMenuItem("Brisanje rezervacije");
        reservationMenu.add(deleteReservation);
        JMenuItem updateReservation = new JMenuItem("Izmeni rezervacije");

        JMenu roomMenu = new JMenu("Sobe");
        menuBar.add(roomMenu);
        JMenuItem showRooms = new JMenuItem("Pregledaj sve sobe");
        roomMenu.add(showRooms);
        JMenuItem addRoom = new JMenuItem("Dodaj novu sobu");
        roomMenu.add(addRoom);
        JMenuItem updateRoom = new JMenuItem("Izmeni informacije o sobi");
        roomMenu.add(updateRoom);
        JMenuItem deleteRoom = new JMenuItem("Obriši sobu");
        roomMenu.add(deleteRoom);

        JMenu additionalServiceMenu = new JMenu("Dodatne usluge hotela");
        menuBar.add(additionalServiceMenu);
        JMenuItem showAdditionalServices = new JMenuItem("Pregledaj sve dodatne usluge");
        additionalServiceMenu.add(showAdditionalServices);
        JMenuItem addAdditionalService = new JMenuItem("Dodaj novu dodatnu uslugu");
        additionalServiceMenu.add(addAdditionalService);
        JMenuItem updateAdditionalService = new JMenuItem("Izmeni informacije o dodatnoj usluzi");
        additionalServiceMenu.add(updateAdditionalService);
        JMenuItem deleteAdditionalService = new JMenuItem("Obriši dodatnu uslugu");
        additionalServiceMenu.add(deleteAdditionalService);
       
        JMenu priceListMenu = new JMenu("Cenovnik");
        menuBar.add(priceListMenu);
        JMenuItem showPriceLists = new JMenuItem("Prikaz svih cenovnika");
        priceListMenu.add(showPriceLists);
        JMenuItem addPriceList= new JMenuItem("Dodaj novi cenovnik");
        priceListMenu.add(addPriceList);
        JMenuItem updatePriceList = new JMenuItem("Izmeni informacije o cenovniku");
        priceListMenu.add(updatePriceList);
        JMenuItem deletePriceList = new JMenuItem("Obriši cenovnik");
        priceListMenu.add(deletePriceList);

        JMenu reportMenu = new JMenu("Izveštaji");
        menuBar.add(reportMenu);
        JMenuItem incomeExpenses = new JMenuItem("Prihodi i rashodi hotela");
        reportMenu.add(incomeExpenses);
        JMenuItem occupancyRoom = new JMenuItem("Zauzetost soba");
        reportMenu.add(occupancyRoom);
        JMenuItem analysisOfFinancialReports = new JMenuItem("Analiza finansijskih izveštaja");
        reportMenu.add(analysisOfFinancialReports);
        
        JMenu settingsMenu = new JMenu("Postavke");
        menuBar.add(settingsMenu);
        JMenuItem changePassword = new JMenuItem("Promena lozinke");
        settingsMenu.add(changePassword);
        JMenuItem signOut = new JMenuItem("Odjava iz sistema");
        settingsMenu.add(signOut);

        showEmployees.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAllEmployees();
            }
        }); 
        
        addEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               addNewEmployee();
            }
        });
        updateEmplyee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	updateEmployee();
             }
         });
        
        deleteEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	deleteEmployee();
             }
         });
        
        showGuests.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAllGuests();
            }
        }); 
        
        addGuest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               addNewGuests();
            }
        });
        updateGuest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	updateGuest();
             }
         });
        
        deleteGuest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               deleteGuest();
            }
        });
        
        showRoomType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showRoomType();
             }
         });
        
        showRooms.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showRooms();
             }
         });
        addRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	addRoom();
             }
         });
        updateRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	updateRoom();
             }
         });
        deleteRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	deleteRoom();
             }
         });
        
        showAdditionalServices.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showAdditionalServices();
             }
         });
        
        addAdditionalService.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	addAdditionalService();
             }
         });

        updateAdditionalService.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	updateAdditionalService();
             }
         });
        
        deleteAdditionalService.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	deleteAdditionalService();
             }
         });   
        
        showPriceLists.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showPriceLists();
             }
         });
        
        showPriceLists.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showPriceLists();
             }
         }); 
        addPriceList.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		addPriceList();
             }
        });
        
        updatePriceList.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		updatePriceList();
             }
        });
        
        deletePriceList.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		deletePriceList();
             }
        });
    }
  
  
	private void displayAllEmployees() {
        Map<String, Employee> employeeMap = hotelManager.getEmployees().get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        String[] columnNames = {
            "Ime", "Prezime", "Pol", "Datum rođenja",
            "Telefon", "Adresa", "Korisničko ime", "Iskustvo",
            "Plata", "Nivo kvalifikacije"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        for (Employee employee : employeeMap.values()) {
            String phoneNumber = employee.getPhoneNumber();
            if (phoneNumber.startsWith("\"") && phoneNumber.endsWith("\"")) {
                phoneNumber = phoneNumber.substring(1, phoneNumber.length() - 1);
            }
            Object[] row = {
                employee.getName(),
                employee.getSurname(),
                employee.getGender().toString(),
                employee.getDateOfBirth().format(formatter),
                phoneNumber,
                employee.getAdress(),
                employee.getUsername(),
                employee.getExperience(),
                employee.getSalary(),
                employee.getQualificationLevel().toString()
            };
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        contentPane.removeAll();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    protected void addNewEmployee() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JRadioButton receptionistButton = new JRadioButton("Recepcioner");
        JRadioButton housekeeperButton = new JRadioButton("Sobarica");
        ButtonGroup group = new ButtonGroup();
        group.add(receptionistButton);
        group.add(housekeeperButton);
        panel.add(new JLabel("Izaberite ulogu zaposlenog:"));
        panel.add(receptionistButton);
        panel.add(housekeeperButton);
        
       
        int result = JOptionPane.showConfirmDialog(null, panel, "Dodavanje novog zaposlenog",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String name = JOptionPane.showInputDialog("Unesite ime:");
            String surname = JOptionPane.showInputDialog("Unesite prezime:");
            String genderStr = JOptionPane.showInputDialog("Unesite pol (M/F):");
            Gender gender = genderStr.equalsIgnoreCase("M") ? Gender.MALE : Gender.FEMALE;
            String dateOfBirthStr = JOptionPane.showInputDialog("Unesite datum rođenja (dd.MM.yyyy.):");
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
            String phoneNumber = JOptionPane.showInputDialog("Unesite broj telefona:");
            String address = JOptionPane.showInputDialog("Unesite adresu:");
            String username = JOptionPane.showInputDialog("Unesite korisničko ime:");
            String password = JOptionPane.showInputDialog("Unesite lozinku:");
            String experienceStr = JOptionPane.showInputDialog("Unesite godine iskustva:");
            int experience = Integer.parseInt(experienceStr);
            String salaryStr = JOptionPane.showInputDialog("Unesite platu:");
            double salary = Double.parseDouble(salaryStr);
            String qualificationLevelStr = JOptionPane.showInputDialog("Unesite nivo kvalifikacije:");
            QualificationLevel qualificationLevel = QualificationLevel.valueOf(qualificationLevelStr.toUpperCase());
        
            if (receptionistButton.isSelected()) {
                Receptionist newReceptionist = new Receptionist(name, surname, gender, dateOfBirth, phoneNumber, address, username, password, experience, salary, qualificationLevel);
                hotelManager.addReceptionist(newReceptionist);
                hotelController.addReceptionist(newReceptionist);
            } else if (housekeeperButton.isSelected()) {
                Housekeeper newHousekeeper = new Housekeeper(name, surname, gender, dateOfBirth, phoneNumber, address, username, password, experience, salary, qualificationLevel);
                hotelManager.addHousekeeper(newHousekeeper);
                hotelController.addHousekeeper(newHousekeeper);
            }
            
            displayAllEmployees(); 
        }
    }
    
    protected void updateEmployee() {
        String username = JOptionPane.showInputDialog("Unesite korisničko ime zaposlenog čije informacije želite da ažurirate:");
        Employee employeeToUpdate = hotelManager.getEmployees().FindById(username);
        
        if (employeeToUpdate != null) {
            JOptionPane.showMessageDialog(null, "Trenutne informacije o zaposlenom:\n" + employeeToUpdate.toString());
            
            String newName = JOptionPane.showInputDialog("Unesite novo ime (ostavite prazno ako ne želite da menjate):");
            if (!newName.isEmpty()) {
                employeeToUpdate.setName(newName);
            }
            
            String newSurname = JOptionPane.showInputDialog("Unesite novo prezime (ostavite prazno ako ne želite da menjate):");
            if (!newSurname.isEmpty()) {
                employeeToUpdate.setSurname(newSurname);
            }
            
            String newGender = JOptionPane.showInputDialog("Unesite novi pol (M/F) (ostavite prazno ako ne želite da menjate):");
            if (!newGender.isEmpty()) {
                Gender gender = newGender.equalsIgnoreCase("M") ? Gender.MALE : Gender.FEMALE;
                employeeToUpdate.setGender(gender);
            }
            
            String newDateOfBirthStr = JOptionPane.showInputDialog("Unesite novi datum rođenja (dd.MM.yyyy.) (ostavite prazno ako ne želite da menjate):");
            if (!newDateOfBirthStr.isEmpty()) {
                LocalDate newDateOfBirth = LocalDate.parse(newDateOfBirthStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
                employeeToUpdate.setDateOfBirth(newDateOfBirth);
            }
                       
            String newPhoneNumber = JOptionPane.showInputDialog("Unesite novi broj telefona (ostavite prazno ako ne želite da menjate):");
            if (!newPhoneNumber.isEmpty()) {
                employeeToUpdate.setPhoneNumber(newPhoneNumber);
            }
            
            String newAddress = JOptionPane.showInputDialog("Unesite novu adresu (ostavite prazno ako ne želite da menjate):");
            if (!newAddress.isEmpty()) {
                employeeToUpdate.setAdress(newAddress);
            }
            
            String newPassword = JOptionPane.showInputDialog("Unesite novu lozinku (ostavite prazno ako ne želite da menjate):");
            if (!newPassword.isEmpty()) {
                employeeToUpdate.setPassword(newPassword);
            }
            
            String newExperienceStr = JOptionPane.showInputDialog("Unesite novo iskustvo (ostavite prazno ako ne želite da menjate):");
            if (!newExperienceStr.isEmpty()) {
                int newExperience = Integer.parseInt(newExperienceStr);
                employeeToUpdate.setExperience(newExperience);
            }
            
            String newSalaryStr = JOptionPane.showInputDialog("Unesite novu platu (ostavite prazno ako ne želite da menjate):");
            if (!newSalaryStr.isEmpty()) {
                double newSalary = Double.parseDouble(newSalaryStr);
                employeeToUpdate.setSalary(newSalary);
            }
            
            String newQualificationLevelStr = JOptionPane.showInputDialog("Unesite novi nivo kvalifikacije (ostavite prazno ako ne želite da menjate):");
            if (!newQualificationLevelStr.isEmpty()) {
                QualificationLevel newQualificationLevel = QualificationLevel.valueOf(newQualificationLevelStr.toUpperCase());
                employeeToUpdate.setQualificationLevel(newQualificationLevel);
            }
            
            hotelManager.updateEmployee(employeeToUpdate);
            String userType = hotelManager.getUserType(employeeToUpdate.getUsername());
            if(userType.equals("receptionist")) {
            	hotelController.updateReceptionist((Receptionist) employeeToUpdate);
            }
            else if (userType.equals("housekeeper")) {
            	hotelController.updateHousekeeper((Housekeeper) employeeToUpdate);
            }
            
            JOptionPane.showMessageDialog(null, "Informacije o zaposlenom su uspešno ažurirane.");
        } else {
            JOptionPane.showMessageDialog(null, "Nije pronađen zaposleni sa unetim korisničkim imenom.");
        }
        displayAllEmployees();
    }
    
    protected void deleteEmployee() {
        String username = JOptionPane.showInputDialog("Unesite korisničko ime zaposlenog koga želite da uklonite");
        Employee employeeToDelete = hotelManager.getEmployees().FindById(username);
        if (employeeToDelete != null) {
            String userType = hotelManager.getUserType(employeeToDelete.getUsername());
            if (userType != null) {
                if (userType.equals("receptionist")) {
                	hotelController.deleteReceptionist((Receptionist) employeeToDelete);
                } else if (userType.equals("housekeeper")) {
                	hotelController.deleteHousekeeper((Housekeeper) employeeToDelete);
                }
                JOptionPane.showMessageDialog(null, "Informacije o zaposlenom su uspešno ažurirane.");
            } else {
                JOptionPane.showMessageDialog(null, "Nije moguće odrediti tip korisnika.");
            }
            hotelManager.deleteEmployee(employeeToDelete);
            displayAllEmployees(); 
        } else {
            JOptionPane.showMessageDialog(null, "Nije pronađen zaposleni sa unetim korisničkim imenom.");
        }
    }
    
    protected void displayAllGuests() {
		Map<String, Guest> guestMap = hotelManager.getGuests().get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        String[] columnNames = {
            "Ime", "Prezime", "Pol", "Datum rođenja",
            "Telefon", "Adresa", "Korisničko ime", "Lozinka"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        for (Guest guest : guestMap.values()) {
            String phoneNumber = guest.getPhoneNumber();
            if (phoneNumber.startsWith("\"") && phoneNumber.endsWith("\"")) {
                phoneNumber = phoneNumber.substring(1, phoneNumber.length() - 1);
            }
            Object[] row = {
            	guest.getName(),
            	guest.getSurname(),
            	guest.getGender().toString(),
            	guest.getDateOfBirth().format(formatter),
                phoneNumber,
                guest.getAdress(),
                guest.getUsername(),
                guest.getPassword()
            };
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        contentPane.removeAll();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    protected void addNewGuests() {
		String name = JOptionPane.showInputDialog("Unesite ime:");
        String surname = JOptionPane.showInputDialog("Unesite prezime:");
        String genderStr = JOptionPane.showInputDialog("Unesite pol (M/F):");
        Gender gender = genderStr.equalsIgnoreCase("M") ? Gender.MALE : Gender.FEMALE;
        String dateOfBirthStr = JOptionPane.showInputDialog("Unesite datum rođenja (dd.MM.yyyy.):");
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        String phoneNumber = JOptionPane.showInputDialog("Unesite broj telefona:");
        String address = JOptionPane.showInputDialog("Unesite adresu:");
        String username = JOptionPane.showInputDialog("Unesite korisničko ime:");
        String password = JOptionPane.showInputDialog("Unesite lozinku:");
        Guest newGuest = new Guest(name, surname, gender, dateOfBirth, phoneNumber, address, username, password);
        hotelManager.addGuest(newGuest);
        hotelController.addGuest(newGuest);
        displayAllGuests(); 
	}
    
    protected void updateGuest() {
		String username = JOptionPane.showInputDialog("Unesite korisničko ime gosta čije informacije želite da ažurirate:");
        Guest guestToUpdate = hotelManager.getGuests().FindById(username);
        
        if (guestToUpdate != null) {
            JOptionPane.showMessageDialog(null, "Trenutne informacije o gostu:\n" + guestToUpdate.toString());
            
            String newName = JOptionPane.showInputDialog("Unesite novo ime (ostavite prazno ako ne želite da menjate):");
            if (!newName.isEmpty()) {
                guestToUpdate.setName(newName);
            }
            
            String newSurname = JOptionPane.showInputDialog("Unesite novo prezime (ostavite prazno ako ne želite da menjate):");
            if (!newSurname.isEmpty()) {
                guestToUpdate.setSurname(newSurname);
            }
            
            String newGender = JOptionPane.showInputDialog("Unesite novi pol (M/F) (ostavite prazno ako ne želite da menjate):");
            if (!newGender.isEmpty()) {
                Gender gender = newGender.equalsIgnoreCase("M") ? Gender.MALE : Gender.FEMALE;
                guestToUpdate.setGender(gender);
            }
            
            String newDateOfBirthStr = JOptionPane.showInputDialog("Unesite novi datum rođenja (dd.MM.yyyy.) (ostavite prazno ako ne želite da menjate):");
            if (!newDateOfBirthStr.isEmpty()) {
                LocalDate newDateOfBirth = LocalDate.parse(newDateOfBirthStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
                guestToUpdate.setDateOfBirth(newDateOfBirth);
            }
                       
            String newPhoneNumber = JOptionPane.showInputDialog("Unesite novi broj telefona (ostavite prazno ako ne želite da menjate):");
            if (!newPhoneNumber.isEmpty()) {
                guestToUpdate.setPhoneNumber(newPhoneNumber);
            }
            
            String newAddress = JOptionPane.showInputDialog("Unesite novu adresu (ostavite prazno ako ne želite da menjate):");
            if (!newAddress.isEmpty()) {
                guestToUpdate.setAdress(newAddress);
            }
            
            String newPassword = JOptionPane.showInputDialog("Unesite novu lozinku (ostavite prazno ako ne želite da menjate):");
            if (!newPassword.isEmpty()) {
                guestToUpdate.setPassword(newPassword);
            }
            hotelController.updateGuest(guestToUpdate);
            JOptionPane.showMessageDialog(null, "Informacije o gostu su uspešno ažurirane.");
        } else {
            JOptionPane.showMessageDialog(null, "Nije pronađen gost sa unetim korisničkim imenom.");
        }
        displayAllGuests();
    }
    
    protected void deleteGuest() {
    	String username = JOptionPane.showInputDialog("Unesite korisničko ime gosta koga želite da uklonite");
    	Guest guestToDelete = hotelManager.getGuests().FindById(username);
        if (guestToDelete != null) {
            hotelController.deleteGuest(guestToDelete);
            hotelManager.deleteGuest(guestToDelete);
            JOptionPane.showMessageDialog(null, "Gost je uspešno obrisan");
            displayAllGuests(); 
        } else {
            JOptionPane.showMessageDialog(null, "Nije pronađen gost sa unetim korisničkim imenom.");
        }
    }
    
    protected void showRoomType() {
	    String[] columnNames = {"Tip", "Opis"};
	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
	    
	    for (RoomType roomType : RoomType.values()) {
	        Object[] row = {
	            roomType.name(),
	            roomType.getDescription()
	        };
	        tableModel.addRow(row);
	    }
	    
	    JTable table = new JTable(tableModel);
	    JScrollPane scrollPane = new JScrollPane(table);

	    contentPane.removeAll();
	    contentPane.add(scrollPane, BorderLayout.CENTER);
	    contentPane.revalidate();
	    contentPane.repaint();
	}
    
    protected void showRooms() {
	    Map<String, Room> roomMap = hotelManager.getRooms().get();
	    if (roomMap == null) {
	        return;
	    }
	    String[] columnNames = {
	        "Broj Sobe", "Tip Sobe", "Status Sobe"
	    };
	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    for (Room room : roomMap.values()) {
	        Object[] row = {
	            room.getRoomNumber(),
	            room.getRoomType(),
	            room.getRoomStatus()
	        };
	        tableModel.addRow(row);
	    }

	    JTable table = new JTable(tableModel);
	    JScrollPane scrollPane = new JScrollPane(table);

	    contentPane.removeAll();
	    contentPane.add(scrollPane, BorderLayout.CENTER);
	    contentPane.revalidate();
	    contentPane.repaint();
	}
    
    protected void addRoom() {
	    String roomNumber = JOptionPane.showInputDialog("Unesite broj sobe:");
	    String roomTypeString = JOptionPane.showInputDialog("Unesite tip sobe:");
	    String roomStatusString = JOptionPane.showInputDialog("Unesite status sobe:");
	    RoomType roomType = RoomType.valueOf(roomTypeString.toUpperCase());
	    RoomStatus roomStatus = RoomStatus.valueOf(roomStatusString.toUpperCase());
	    Room newRoom = new Room(roomType, roomNumber, roomStatus);
	    hotelManager.addRoom(newRoom);
	    hotelController.addRoom(newRoom);
	    showRooms();
	}
    
    
    protected void updateRoom() {
		String roomNumber = JOptionPane.showInputDialog("Unesite broj sobe čije informacije želite da ažurirate:");
        Room roomToUpdate = hotelManager.getRooms().FindById(roomNumber);
        
        if (roomToUpdate != null) {
            JOptionPane.showMessageDialog(null, "Trenutne informacije o sobi:\n" + roomToUpdate.toString());
            
            String newRoomTypeString = JOptionPane.showInputDialog("Unesite novo tip sobe (ostavite prazno ako ne želite da menjate):");
            RoomType newRoomType = RoomType.valueOf(newRoomTypeString.toUpperCase());
            if (!newRoomTypeString.isEmpty()) {
            	roomToUpdate.setRoomType(newRoomType);
            }
            hotelController.updateRoom(roomToUpdate);
            JOptionPane.showMessageDialog(null, "Informacije o sobi su uspešno ažurirane.");
        } else {
            JOptionPane.showMessageDialog(null, "Nije pronađena soba sa unetim brojem.");
        }
        showRooms();	
	}
    
    protected void deleteRoom() {
		String number = JOptionPane.showInputDialog("Unesite broj sobe koje želite da uklonite");
    	Room roomToDelete = hotelManager.getRooms().FindById(number);
        if (roomToDelete != null) {
            hotelController.deleteRoom(roomToDelete);
            hotelManager.deleteRoom(roomToDelete);
            JOptionPane.showMessageDialog(null, "Soba je uspešno obrisana");
            showRooms(); 
        } else {
            JOptionPane.showMessageDialog(null, "Nije pronađen soba sa unetim brojem.");
        }
    }
    
    protected void showAdditionalServices() {
		Map<String, AdditionalService> additionalServiceMap = hotelManager.getAdditionalServices().get();
	    if (additionalServiceMap == null) {
	        return;
	    }
	    String[] columnNames = {
	        "Ime", "Cena"
	    };
	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	   for (AdditionalService additionalService : additionalServiceMap.values()) {
	        Object[] row = {
	        	additionalService.getName(),
	        	additionalService.getPrice(),
	        };
	        tableModel.addRow(row);
	    }

	    JTable table = new JTable(tableModel);
	    JScrollPane scrollPane = new JScrollPane(table);

	    contentPane.removeAll();
	    contentPane.add(scrollPane, BorderLayout.CENTER);
	    contentPane.revalidate();
	    contentPane.repaint();
	}
    
    protected void addAdditionalService() {
		String name = JOptionPane.showInputDialog("Unesite ime usluge:");
	    String price = JOptionPane.showInputDialog("Unesite cenu usluge:");
	    AdditionalService newAdditionalService = new AdditionalService(name, Double.parseDouble(price));
	    hotelManager.addAdditionalService(newAdditionalService);
	    hotelController.addAdditionalService(newAdditionalService);
	    showAdditionalServices();
	}
    
    protected void updateAdditionalService() {
		String name = JOptionPane.showInputDialog("Unesite ime usluge čije informacije želite da ažurirate:");
		AdditionalService additionalServicesToUpdate = hotelManager.getAdditionalServices().FindById(name);
        
        if (additionalServicesToUpdate != null) {
            JOptionPane.showMessageDialog(null, "Trenutne informacije o usluzi:\n" + additionalServicesToUpdate.toString());
            String price = JOptionPane.showInputDialog("Unesite cenu (ostavite prazno ako ne želite da menjate):");
            additionalServicesToUpdate.setPrice(Double.parseDouble(price));
            hotelController.updateAdditionalService(additionalServicesToUpdate);
            JOptionPane.showMessageDialog(null, "Informacije o sobi su uspešno ažurirane.");
        } else {
            JOptionPane.showMessageDialog(null, "Nije pronađena soba sa unetim brojem.");
        }
        showAdditionalServices(); 
		
	}
    
    protected void deleteAdditionalService() {
		String name = JOptionPane.showInputDialog("Unesite ime usluge koje želite da uklonite");
		AdditionalService serviceToDelete = hotelManager.getAdditionalServices().FindById(name);
        if (serviceToDelete != null) {
            hotelController.deleteAdditionalService(serviceToDelete);
            hotelManager.deleteAdditionalService(serviceToDelete);
            JOptionPane.showMessageDialog(null, "Usluga je uspešno obrisana");
            showAdditionalServices(); 
        } else {
            JOptionPane.showMessageDialog(null, "Nije pronađen usluga sa unetim imenom.");
        }
    }
    
	protected void showPriceLists() {
		Map<String,PriceList> priceListMap = hotelManager.getPriceLists().get();
	    if (priceListMap == null) {
	        return;
	    }
	    String[] columnNames = {"Datum početka važenja", "Datum kraja važenja",
				"Cene soba", "Cene dodatnih usluga"};
	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	   for (PriceList priceList : priceListMap.values()) {
	        Object[] row = {
	        		priceList.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")),
	        		priceList.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")),
	        		priceList.getFormattedRoomPrices(),
	        		priceList.getFormattedAdditionalServicePrices()
	        };
	        tableModel.addRow(row);
	    }

	    JTable table = new JTable(tableModel);
	    JScrollPane scrollPane = new JScrollPane(table);

	    contentPane.removeAll();
	    contentPane.add(scrollPane, BorderLayout.CENTER);
	    contentPane.revalidate();
	    contentPane.repaint();
	}


	protected void addPriceList() {
		try {
			String startDateStr = JOptionPane.showInputDialog("Unesite datum početka važenja cenovnika (dd.MM.yyyy.):");
	        LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	        String endDateStr = JOptionPane.showInputDialog("Unesite datum kraja važenja cenovnika (dd.MM.yyyy.):");
	        LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	        PriceList priceList = new PriceList(startDate, endDate);

	        String[] roomTypes = {"SINGLE", "DOUBLE", "TWIN", "TRIPLE"};
	        for (String type : roomTypes) {
	        	String roomPriceStr = JOptionPane.showInputDialog("Unesite cenu za tip sobe " + type + ":");
	            double roomPrice = Double.parseDouble(roomPriceStr);
	            RoomType roomType = RoomType.valueOf(type);
	            priceList.addRoomPrice(roomType, roomPrice);
	        }

	        Map<String,AdditionalService> allServices = hotelManager.getAdditionalServices().get();
	        for (AdditionalService service : allServices.values()) {
	        	String servicePriceStr = JOptionPane.showInputDialog("Unesite cenu za uslugu " + service.getName() + ":");
	            double servicePrice = Double.parseDouble(servicePriceStr);
	            priceList.addAdditionalServicePrice(service, servicePrice);
	        }

	        hotelManager.addPriceList(priceList);
	        hotelController.addPriceList(priceList);
	        JOptionPane.showMessageDialog(null, "Cenovnik je uspešno dodat.");
	        } catch (Exception e) {
	          JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom kreiranja cenovnika. Proverite unete podatke i pokušajte ponovo.");
	          e.printStackTrace();
	        }
			showPriceLists();
	    }		

	
	protected void deletePriceList() {
		String startDateStr = JOptionPane.showInputDialog("Unesite datum početka važenja cenovnika (dd.MM.yyyy.):");
		LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		String endDateStr = JOptionPane.showInputDialog("Unesite datum kraja važenja cenovnika (dd.MM.yyyy.):");
		LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String startDateFormatted = startDate.format(formatter);
        String endDateFormatted = endDate.format(formatter);
		String id = startDateFormatted + "_" + endDateFormatted;
		PriceList priceList = hotelManager.getPriceLists().FindById(id);

		if (priceList != null) {
		    hotelController.deletePriceList(priceList);
		    hotelManager.deletePriceList(priceList);
		    JOptionPane.showMessageDialog(null, "Cenovnik je uspešno obrisan");
		    showAdditionalServices(); 
		} else {
		    JOptionPane.showMessageDialog(null, "Nije pronađen cenovnik sa unetim datumima.");
		}
		showPriceLists();
	}

	protected void updatePriceList() {
	    try {
	        String startDateStr = JOptionPane.showInputDialog("Unesite datum početka važenja cenovnika koji želite da promenite (dd.MM.yyyy.):");
	        LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	        String endDateStr = JOptionPane.showInputDialog("Unesite datum kraja važenja cenovnika koji želite da promenite (dd.MM.yyyy.):");
	        LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	        
	        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	        String startDateFormatted = startDate.format(dateFormatter);
	        String endDateFormatted = endDate.format(dateFormatter);
	        String id = startDateFormatted + "_" + endDateFormatted;
	        
	        PriceList priceList = hotelManager.getPriceLists().FindById(id);
	        if (priceList != null) {
	            JOptionPane.showMessageDialog(null, "Trenutne informacije o cenovniku:\n" + priceList.toString());
	            
	            String newStartDateStr = JOptionPane.showInputDialog("Unesite novi datum početka važenja (ostavite prazno ako ne želite da menjate):");
	            if (!newStartDateStr.isEmpty()) {
	                LocalDate newStartDate = LocalDate.parse(newStartDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	                priceList.setStartDate(newStartDate);
	            }

	            String newEndDateStr = JOptionPane.showInputDialog("Unesite novi datum kraja važenja (ostavite prazno ako ne želite da menjate):");
	            if (!newEndDateStr.isEmpty()) {
	                LocalDate newEndDate = LocalDate.parse(newEndDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	                priceList.setEndDate(newEndDate);
	            }

	            String[] roomTypes = {"SINGLE", "DOUBLE", "TWIN", "TRIPLE"};
	            for (String type : roomTypes) {
	                String newPriceStr = JOptionPane.showInputDialog("Unesite novu cenu za tip sobe " + type + " (ostavite prazno ako ne želite da menjate):");
	                if (!newPriceStr.isEmpty()) {
	                    double newPrice = Double.parseDouble(newPriceStr);
	                    RoomType roomType = RoomType.valueOf(type);
	                    priceList.setRoomPrice(roomType, newPrice);
	                }
	            }
	            
	            int updateMoreServices = JOptionPane.YES_OPTION;
	            while (updateMoreServices == JOptionPane.YES_OPTION) {
	                String serviceName = JOptionPane.showInputDialog("Unesite naziv usluge koju želite da ažurirate (ostavite prazno ako ne želite da menjate):");
	                if (!serviceName.isEmpty()) {
	                    String newServicePriceStr = JOptionPane.showInputDialog("Unesite novu cenu usluge " + serviceName + " (ostavite prazno ako ne želite da menjate):");
	                    if (!newServicePriceStr.isEmpty()) {
	                        double newServicePrice = Double.parseDouble(newServicePriceStr);
	                        AdditionalService service = hotelManager.getAdditionalServices().FindById(serviceName);
	                        if (service != null) {
	                            priceList.setAdditionalServicePrice(service, newServicePrice);
	                        } else {
	                            JOptionPane.showMessageDialog(null, "Dodatna usluga sa navedenim nazivom nije pronađena.");
	                        }
	                    }
	                }
	                updateMoreServices = JOptionPane.showConfirmDialog(null, "Želite li da ažurirate još neku dodatnu uslugu?", "Ažuriranje dodatnih usluga", JOptionPane.YES_NO_OPTION);
	            }

	            hotelController.updatePriceList(startDate,endDate,priceList);
	            JOptionPane.showMessageDialog(null, "Cenovnik je uspešno ažuriran.");
	        } else {
	            JOptionPane.showMessageDialog(null, "Nije pronađen cenovnik sa unetim datumima.");
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom ažuriranja cenovnika. Proverite unete podatke i pokušajte ponovo.");
	        e.printStackTrace();
	    }
	    showPriceLists();
	}	
}

    
	/*
	protected void updateReservation() {
		// TODO Auto-generated method stub
		
	}

	protected void deleteReservation() {
		// TODO Auto-generated method stub
		
	}

	protected void addReservation() {
		// TODO Auto-generated method stub
		
	}

	protected void showReservations() {
		Map<String, Reservation> reservationMap = hotelManager.getReservations().get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        String[] columnNames = {
        		"ID", "Tip sobe", "Broj Gostiju", "Datum početka", "Datum kraja",
        		"Status", "Gost", "Dodatne usluge", "Iskustvo",
        		"Plata", "Nivo kvalifikacije", "Cena"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        csvReader.readReservationDataFromCSV("src/data/reservation.csv");
        for (Reservation reservation : reservationMap.values()) {
        	String additionalServicesString = "";
    	    for (AdditionalService service : reservation.additionalServices) {
    	        additionalServicesString += service.getName() + ", ";
    	    }
            Object[] row = {
            	
                reservation.getID(),
                reservation.getRoomType(),
                reservation.getNumberOfGuests(),
                reservation	.getStartDate().format(formatter),
                reservation	.getEndDate().format(formatter),
                reservation.getReservationStatus(),
                reservation.getGuest().getUsername(),
                additionalServicesString,
                reservation.getTotalPrice()
            };
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        contentPane.removeAll();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
}
	
*/