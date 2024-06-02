package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.HotelController;
import entity.AdditionalService;
import entity.CleaningStatus;
import entity.Gender;
import entity.Guest;
import entity.Housekeeper;
import entity.PriceList;
import entity.Reservation;
import entity.ReservationRequest;
import entity.ReservationStatus;
import entity.Room;
import entity.RoomCleaningRecord;
import entity.RoomStatus;
import entity.RoomType;
import manager.HotelManager;

public class ReceptionistFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HotelManager hotelManager;
	private HotelController hotelController;
	private String username;
	
	public ReceptionistFrame(HotelManager hotelManager, HotelController hotelController, String username) {
		this.hotelManager = hotelManager;
		this.hotelController = hotelController;
		this.username = username;
		initializeUI();
	}

	private void initializeUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 1500, 1000);
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setTitle("Hotel Management System - Receptionist Panel");
		this.setVisible(true);
		this.setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu guestsMenu = new JMenu("Gosti");
        menuBar.add(guestsMenu);
        JMenuItem showAllGuests = new JMenuItem("Prikaz svih gostiju");
        guestsMenu.add(showAllGuests);
        JMenuItem addNewGuest = new JMenuItem("Dodaj novog gosta");
        guestsMenu.add(addNewGuest);
        JMenuItem checkInGuest = new JMenuItem("Check-in gosta");
        guestsMenu.add(checkInGuest);
        JMenuItem checkOutGuest = new JMenuItem("Check-out gosta");
        guestsMenu.add(checkOutGuest);
        
        JMenu reservationRequestMenu = new JMenu("Rezervacije");
        menuBar.add(reservationRequestMenu);
        JMenuItem showreservationRequests = new JMenuItem("Pregledaj sve zahteve rezervacija"); 
        reservationRequestMenu.add(showreservationRequests);
        JMenuItem showReservations = new JMenuItem("Pregledaj sve rezervacije");
        reservationRequestMenu.add(showReservations);
        JMenuItem processReservationRequest  = new JMenuItem("Potvrdi/Odbij zahtev rezervacije");
        
        reservationRequestMenu.add(processReservationRequest);
        
        JMenu roomMenu = new JMenu("Sobe");
        menuBar.add(roomMenu);
        JMenuItem showVacantRoom = new JMenuItem("Prikaz slobodnih soba");
        roomMenu.add(showVacantRoom);
        JMenuItem showOccupiedRoom = new JMenuItem("Prikaz zauzetih sobа"); 
        roomMenu.add(showOccupiedRoom);
        JMenuItem showCleaningRoom = new JMenuItem("Prikaz sobа na spremanju");
        roomMenu.add(showCleaningRoom);
        
        
        JMenu reportMenu = new JMenu("Izveštaji");
        menuBar.add(reportMenu);
        JMenuItem DailyArrivals = new JMenuItem("Izveštaj dnevnih dolazaka");
        reportMenu.add(DailyArrivals);
        JMenuItem dailyDepartures = new JMenuItem("Izveštaj dnevnih odlazaka"); 
        reportMenu.add(dailyDepartures);
        JMenuItem dailyRoomOccupancy  = new JMenuItem("Izveštaj zauzetosti soba");
        reportMenu.add(dailyRoomOccupancy );
        
        processReservationRequest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	processReservationRequest();
            }
        });
        
        showreservationRequests.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showUserReservationRequests();
            }
        });
        
        showReservations.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showReservations();
            }
        });
        
        addNewGuest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	addNewGuest();
            }
        });
        
        showAllGuests.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showAllGuests();
            }
        });
        
        checkInGuest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	checkInGuest();
            }
        });
        
        checkOutGuest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	checkOutGuest();
            }
        });
        
        showVacantRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showVacantRoom();
            }
        });
        
        showOccupiedRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showOccupiedRoom();
            }
        });
        
        showCleaningRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showCleaningRoom();
            }
        });
        
        DailyArrivals.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dailyArrivals();
            }
        });
        
        dailyDepartures.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dailyDepartures();
            }
        });
        dailyRoomOccupancy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dailyRoomOccupancy();
            }
        });
	}


	protected void showReservations() {
		Map<String, Reservation> reservationMap = hotelManager.getReservations().get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        String[] columnNames = {
        		"Datum početka", "Datum kraja", "Gost", "Broj sobe", "Tip sobe", "Broj Gostiju", 
        		"Status","Dodatne usluge", "Cena"
        };
        
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        
        for (Reservation reservation : reservationMap.values()) {
        	List<AdditionalService> additionalService = reservation.getAdditionalService();
        	StringBuilder servicesString = new StringBuilder();
        	for (int i = 0; i < additionalService.size(); i++) {
        	    servicesString.append(additionalService.get(i).getName());
        	    if (i < additionalService.size()) {
        	        servicesString.append(";");
        	    }
        	}
        	
        	Object[] row = {
        		reservation.getStartDate().format(formatter),
                reservation.getEndDate().format(formatter),
                reservation.getGuest().getUsername(),
                reservation.getRoom().getRoomNumber(),
        		reservation.getRoomType(),
                reservation.getNumberOfGuests(),
                reservation.getReservationStatus(),
                servicesString,
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
		
	protected void showAllGuests() {
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
	
	protected void addNewGuest() {
	    String name = JOptionPane.showInputDialog("Unesite ime:");
	    String surname = JOptionPane.showInputDialog("Unesite prezime:");
	    String genderStr = JOptionPane.showInputDialog("Unesite pol (M/F):");
	    Gender gender = genderStr.equalsIgnoreCase("M") ? Gender.MALE : Gender.FEMALE;
	    String dateOfBirthStr = JOptionPane.showInputDialog("Unesite datum rođenja (dd.MM.yyyy.):");
	    LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	    String phoneNumber = JOptionPane.showInputDialog("Unesite broj telefona:");
	    String address = JOptionPane.showInputDialog("Unesite adresu:");
	    String username = JOptionPane.showInputDialog("Unesite email adresu:");
	    String password = JOptionPane.showInputDialog("Unesite broj pasoša:");
	    
	    Guest newGuest = new Guest(name, surname, gender, dateOfBirth, phoneNumber, address, username, password);
	    
	    if (hotelManager.getGuests().FindById(username) != null) {
	        JOptionPane.showMessageDialog(null, "Korisničko ime već postoji. Molimo pokušajte ponovo.", "Greška", JOptionPane.ERROR_MESSAGE);
	    } else {
	        hotelManager.addGuest(newGuest);
	        hotelController.addGuest(newGuest);
	        JOptionPane.showMessageDialog(null, "Gost uspešno dodat!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
	    }
	    
	    showAllGuests(); 
	}
	
	protected void showUserReservationRequests() {
	    List<ReservationRequest> reservationRequests = hotelManager.getReservationRequests().getReservationRequests();
	    if (reservationRequests == null || reservationRequests.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Nema dostupnih rezervacija za prikaz.");
	        return;
	    }

	    String[] columnNames = {
	    	"Korisnik",
	        "Tip Sobe", 
	        "Broj Gostiju", 
	        "Datum Početка", 
	        "Datum Kraja", 
	        "Status Rezervacije", 
	        "Dodatne Uslуге"
	    };

	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	    
	    boolean hasUserReservations = false;
	    
	    for (ReservationRequest reservationRequest : reservationRequests) {
	        hasUserReservations = true;
	        StringBuilder additionalServices = new StringBuilder();
	        for (AdditionalService service : reservationRequest.getAdditionalServices()) {
	            additionalServices.append(service.getName()).append("; ");
	        }
	        if (additionalServices.length() > 0) {
	            additionalServices.setLength(additionalServices.length() - 2);
	        }
	        Object[] row = {
	        		reservationRequest.getGuest().getUsername(),
	        		reservationRequest.getRoomType().name(),
		            reservationRequest.getNumberOfGuests(),
		            reservationRequest.getStartDate().format(formatter),
		            reservationRequest.getEndDate().format(formatter),
		            reservationRequest.getReservationStatus().name(),
		            additionalServices.toString()
	        };
	        tableModel.addRow(row);
	    }

	    if (!hasUserReservations) {
	        JOptionPane.showMessageDialog(null, "Nema rezervacija za prikaz za korisnika: " + username);
	        return;
	    }
	    
	    JTable table = new JTable(tableModel);
	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setBounds(30, 30, 1400, 900); 

	    contentPane.removeAll(); 
	    contentPane.add(scrollPane, BorderLayout.CENTER);
	    contentPane.revalidate();
	    contentPane.repaint();
	}
	
	protected void processReservationRequest() {
	    List<ReservationRequest> reservationRequests = hotelManager.getReservationRequests().getReservationRequests();
	    if (reservationRequests == null || reservationRequests.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Nema dostupnih rezervacija za prikaz.");
	        return;
	    }

	    String[] columnNames = {
	        "Korisnik", "Tip Sobe", "Broj Gostiju", "Datum Početka", "Datum Kraja", "Status Rezervacije", "Dodatne Usluge"
	    };

	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	    for (ReservationRequest reservationRequest : reservationRequests) {
	        StringBuilder additionalServices = new StringBuilder();
	        for (AdditionalService service : reservationRequest.getAdditionalServices()) {
	            additionalServices.append(service.getName()).append("; ");
	        }
	        if (additionalServices.length() > 0) {
	            additionalServices.setLength(additionalServices.length() - 2);
	        }
	        Object[] row = {
	            reservationRequest.getGuest().getUsername(),
	            reservationRequest.getRoomType().name(),
	            reservationRequest.getNumberOfGuests(),
	            reservationRequest.getStartDate().format(formatter),
	            reservationRequest.getEndDate().format(formatter),
	            reservationRequest.getReservationStatus().name(),
	            additionalServices.toString()
	        };
	        tableModel.addRow(row);
	    }

	    JTable table = new JTable(tableModel);
	    JScrollPane scrollPane = new JScrollPane(table);
	    contentPane.setLayout(new BorderLayout());
	    contentPane.removeAll();
	    contentPane.add(scrollPane, BorderLayout.CENTER);

	    JButton processButton = new JButton("Obradi izabranu rezervaciju");
	    processButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int selectedRow = table.getSelectedRow();
	            if (selectedRow != -1) {
	                ReservationRequest selectedRequest = reservationRequests.get(selectedRow);
	                processSelectedReservationRequest(selectedRequest);
	            } else {
	                JOptionPane.showMessageDialog(null, "Molimo izaberite rezervaciju za obradu.", "Greška", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.add(processButton);
	    contentPane.add(buttonPanel, BorderLayout.SOUTH);
	    contentPane.revalidate();
	    contentPane.repaint();
	}

	protected void processSelectedReservationRequest(ReservationRequest selectedRequest) {
	    ReservationRequest selectedRequestCopy = selectedRequest.copy();
	    int result = JOptionPane.showConfirmDialog(
	            null, 
	            "Da li želite da potvrdite ovaj zahtev rezervacije?", 
	            "Potvrda rezervacije", 
	            JOptionPane.YES_NO_OPTION
	    );

	    if (result == JOptionPane.YES_OPTION) {
	        List<Room> availableRooms = hotelManager.getAvailableRooms(
	                selectedRequest.getRoomType(), 
	                selectedRequest.getStartDate(), 
	                selectedRequest.getEndDate()
	        );

	        if (availableRooms.isEmpty()) {
	            JOptionPane.showMessageDialog(
	                    null,
	                    "Nema dostupnih soba koje odgovaraju zahtevu.",
	                    "Greška",
	                    JOptionPane.ERROR_MESSAGE
	            );
	            selectedRequest.setReservationStatus(ReservationStatus.REJECTED);
	            hotelController.updateReservationRequest(selectedRequest, selectedRequestCopy);
	        } else {
	            String[] roomOptions = new String[availableRooms.size()];
	            for (int i = 0; i < availableRooms.size(); i++) {
	                roomOptions[i] = "Soba " + availableRooms.get(i).getRoomNumber();
	            }

	            String selectedRoomStr = (String) JOptionPane.showInputDialog(
	                    null,
	                    "Izaberite sobu za rezervaciju:",
	                    "Izbor sobe",
	                    JOptionPane.QUESTION_MESSAGE,
	                    null,
	                    roomOptions,
	                    roomOptions[0]
	            );

	            if (selectedRoomStr != null) {
	                String[] roomDetails = selectedRoomStr.split(" ");
	                String roomNumber = roomDetails[1];
	                Room selectedRoom = hotelManager.getRooms().FindById(roomNumber);

	                if (selectedRoom != null) {
	                    Reservation reservation = new Reservation(selectedRequest, selectedRoom);
	                    
	                    double price = calculateReservationPrice(reservation);
	                    reservation.setPrice(price);
	                    
	                    selectedRequest.setReservationStatus(ReservationStatus.CONFIRMED);
	                    hotelController.updateReservationRequest(selectedRequest, selectedRequestCopy);
	                    hotelController.deleteReservationRequest(selectedRequest, selectedRequestCopy);
	                    hotelManager.deleteReservationRequest(selectedRequest);
	                    
	                    hotelController.addReservation(reservation);
	                    hotelManager.addReservation(reservation);

	                    JOptionPane.showMessageDialog(
	                            null,
	                            "Rezervacija je uspešno potvrđena.",
	                            "Uspeh",
	                            JOptionPane.INFORMATION_MESSAGE
	                    );
	                } else {
	                    JOptionPane.showMessageDialog(
	                            null,
	                            "Dogodila se greška prilikom izbora sobe.",
	                            "Greška",
	                            JOptionPane.ERROR_MESSAGE
	                    );
	                }
	            }
	        }
	    } else {
	        selectedRequest.setReservationStatus(ReservationStatus.REJECTED);
	        hotelController.updateReservationRequest(selectedRequest, selectedRequestCopy);
	        JOptionPane.showMessageDialog(null, "Rezervacija je odbijena.");
	    }

	    showUserReservationRequests();
	}

	
	private double calculateReservationPrice(Reservation reservation) {
	    double totalPrice = 0;
	    LocalDate date = reservation.getStartDate();

	    while (!date.isAfter(reservation.getEndDate())) {
	        PriceList priceListForDate = hotelManager.getPriceListByDate(date);
	        if (priceListForDate != null) {
	            Double roomPrice = priceListForDate.findRoomPrice(reservation.getRoomType());
	            if (roomPrice != null) {
	                totalPrice += roomPrice;
	            }

	            List<AdditionalService> additionalServices = reservation.getAdditionalService();
	            for (AdditionalService service : additionalServices) {
	                Double servicePrice = priceListForDate.findAdditionalServicePrice(service.getName());
	                if (servicePrice != null) {
	                    totalPrice += servicePrice;
	                }
	            }
	        }
	        date = date.plusDays(1);
	    }
	    return totalPrice;
	}

	private void createNewReservation(Guest guest) {
	    try {
	        String startDateStr = JOptionPane.showInputDialog("Unesite datum početka rezervacije (dd.MM.yyyy.):");
	        LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	        String endDateStr = JOptionPane.showInputDialog("Unesite datum kraja rezervacije (dd.MM.yyyy.):");
	        LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	        
	        String roomTypeStr = JOptionPane.showInputDialog("Unesite tip sobe (SINGLE, DOUBLE, TWIN, TRIPLE):");
	        RoomType roomType = RoomType.valueOf(roomTypeStr.trim().toUpperCase());
	        
	        List<Room> availableRooms = hotelManager.getAvailableRooms(roomType, startDate, endDate);
	        if (availableRooms.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Nema dostupnih soba za navedeni tip i period.", "Greška", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        String[] roomOptions = new String[availableRooms.size()];
	        for (int i = 0; i < availableRooms.size(); i++) {
	            roomOptions[i] = "Soba " + availableRooms.get(i).getRoomNumber();
	        }

	        String selectedRoomStr = (String) JOptionPane.showInputDialog(
	                null,
	                "Izaberite sobu za rezervaciju:",
	                "Izbor sobe",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                roomOptions,
	                roomOptions[0]
	        );

	        if (selectedRoomStr != null) {
	            String[] roomDetails = selectedRoomStr.split(" ");
	            String roomNumber = roomDetails[1];
	            Room selectedRoom = hotelManager.getRooms().FindById(roomNumber);
	            
	            if (selectedRoom != null && availableRooms.contains(selectedRoom)) {
	                String numberOfGuestsStr = JOptionPane.showInputDialog("Unesite broj gostiju:");
	                int numberOfGuests = Integer.parseInt(numberOfGuestsStr);

	                Reservation reservation = new Reservation(roomType, numberOfGuests, startDate, endDate, selectedRoom, guest);
	                int addMoreServices = JOptionPane.YES_OPTION;
	                while (addMoreServices == JOptionPane.YES_OPTION) {
	                    String serviceName = JOptionPane.showInputDialog("Unesite naziv dodatne usluge (ostavite prazno ako ne želite da dodate):");
	                    if (serviceName != null && !serviceName.trim().isEmpty()) {
	                        AdditionalService service = hotelManager.getAdditionalServices().FindById(serviceName.trim());
	                        if (service != null) {
	                            reservation.addAdditionalService(service);
	                        } else {
	                            JOptionPane.showMessageDialog(null, "Dodatna usluga sa navedenim nazivom nije pronađena.");
	                        }
	                    }
	                    addMoreServices = JOptionPane.showConfirmDialog(null, "Želite li da dodate još neku dodatnu uslugu?", "Dodavanje dodatnih usluga", JOptionPane.YES_NO_OPTION);
	                }
	                double price = calculateReservationPrice(reservation);
	    	        reservation.setPrice(price);
	                hotelManager.addReservation(reservation);
	                hotelController.addReservation(reservation);
	                JOptionPane.showMessageDialog(null, "Rezervacija uspešno kreirana!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
	            } else {
	                JOptionPane.showMessageDialog(null, "Soba nije dostupna za navedeni period.", "Greška", JOptionPane.ERROR_MESSAGE);
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Kreiranje rezervacije je otkazano.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom kreiranja rezervacije. Proverite unete podatke i pokušajte ponovo.", "Greška", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	    }
	}

	protected void checkInGuest() {
	    try {
	        String username = JOptionPane.showInputDialog("Unesite korisničko ime gosta (e-mail):");
	        if (username == null || username.trim().isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Korisničko ime ne može biti prazno.", "Greška", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        Guest guest = hotelManager.getGuests().FindById(username);

	        if (guest == null) {
	            String name = JOptionPane.showInputDialog("Unesite ime:");
	            String surname = JOptionPane.showInputDialog("Unesite prezime:");
	            String genderStr = JOptionPane.showInputDialog("Unesite pol (M/F):");
	            Gender gender = genderStr.equalsIgnoreCase("M") ? Gender.MALE : Gender.FEMALE;
	            String dateOfBirthStr = JOptionPane.showInputDialog("Unesite datum rođenja (dd.MM.yyyy.):");
	            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	            String phoneNumber = JOptionPane.showInputDialog("Unesite broj telefona:");
	            String address = JOptionPane.showInputDialog("Unesite adresu:");
	            String passportNumber = JOptionPane.showInputDialog("Unesite broj pasoša:");

	            Guest newGuest = new Guest(name, surname, gender, dateOfBirth, phoneNumber, address, username, passportNumber);

	            if (hotelManager.getGuests().FindById(username) != null) {
	                JOptionPane.showMessageDialog(null, "Korisničko ime već postoji. Molimo pokušajte ponovo.", "Greška", JOptionPane.ERROR_MESSAGE);
	                return;
	            } else {
	                hotelManager.addGuest(newGuest);
	                hotelController.addGuest(newGuest);
	                JOptionPane.showMessageDialog(null, "Gost uspešno dodat!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
	                guest = newGuest;
	            }
	        }

	        if (guest != null) {
	            List<Reservation> reservations = new ArrayList<>();
	            for (Reservation reservation : hotelManager.getReservations().get().values()) {
	                if (reservation.getGuest().getUsername().equals(username)) {
	                    reservations.add(reservation);
	                }
	            }

	            if (reservations.isEmpty()) {
	                int createNewReservation = JOptionPane.showConfirmDialog(null, "Nema rezervacija za ovog gosta. Da li želite da napravite novu rezervaciju?", "Nova rezervacija", JOptionPane.YES_NO_OPTION);
	                if (createNewReservation == JOptionPane.YES_OPTION) {
	                    createNewReservation(guest);
	                    Reservation newReservation = null;
	                    for (Reservation reservation : hotelManager.getReservations().get().values()) {
	                        if (reservation.getGuest().getUsername().equals(username)) {
	                            newReservation = reservation;
	                        }
	                    }
	                    if (newReservation != null) {
	                        newReservation.setReservationStatus(ReservationStatus.ACTIVE);
	                        hotelController.updateReservation(newReservation.getStartDate(), newReservation.getEndDate(), newReservation.getRoom().getRoomNumber(), newReservation);
	                        Room room = newReservation.getRoom();
	                        if (room != null) {
	                            if (room.getRoomStatus() == RoomStatus.OCCUPIED) {
	                                JOptionPane.showMessageDialog(null, "Soba je zauzeta", "Greška", JOptionPane.ERROR_MESSAGE);
	                            } else if (room.getRoomStatus() == RoomStatus.CLEANING) {
	                                JOptionPane.showMessageDialog(null, "Soba se čisti.", "Greška", JOptionPane.ERROR_MESSAGE);
	                            } else if (room.getRoomStatus() == RoomStatus.VACANT) {
	                                room.setRoomStatus(RoomStatus.OCCUPIED);
	                                hotelController.updateRoom(room);
	                                JOptionPane.showMessageDialog(null, "Gost je uspešno prijavljen za novu rezervaciju.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
	                            }
	                        } else {
	                            JOptionPane.showMessageDialog(null, "Soba nije pronađena.", "Greška", JOptionPane.ERROR_MESSAGE);
	                        }
	                    }
	                } else {
	                    return;
	                }
	            } else {
	                String[] reservationOptions = new String[reservations.size()];
	                for (int i = 0; i < reservations.size(); i++) {
	                    reservationOptions[i] = "Datum početka: " + reservations.get(i).getStartDate() 
	                            + ", Datum kraja: " + reservations.get(i).getEndDate()
	                            + ", Broj sobe: " + reservations.get(i).getRoom().getRoomNumber();
	                }

	                String selectedReservationStr = (String) JOptionPane.showInputDialog(
	                        null,
	                        "Izaberite rezervaciju za prijavu gosta:",
	                        "Izbor rezervacije",
	                        JOptionPane.QUESTION_MESSAGE,
	                        null,
	                        reservationOptions, reservationOptions[0]
	                );

	                if (selectedReservationStr != null) {
	                    String[] reservationDetails = selectedReservationStr.split(",");
	                    String startDate = reservationDetails[0].split(":")[1].trim();
	                    String endDate = reservationDetails[1].split(":")[1].trim();
	                    String roomNumber = reservationDetails[2].split(":")[1].trim();
	                    String id = startDate + "_" + endDate + "_" + roomNumber;
	                    Reservation reservation = hotelManager.getReservations().FindById(id);
	                    if (reservation == null) {
	                        JOptionPane.showMessageDialog(null, "Rezervacija nije pronađena.", "Greška", JOptionPane.ERROR_MESSAGE);
	                    } else {
	                        Room room = reservation.getRoom();
	                        if (room == null) {
	                            JOptionPane.showMessageDialog(null, "Soba nije pronađena.", "Greška", JOptionPane.ERROR_MESSAGE);
	                        } else if (room.getRoomStatus() == RoomStatus.OCCUPIED) {
	                            JOptionPane.showMessageDialog(null, "Soba je zauzeta", "Greška", JOptionPane.ERROR_MESSAGE);
	                        } else if (room.getRoomStatus() == RoomStatus.CLEANING) {
	                            JOptionPane.showMessageDialog(null, "Soba se čisti.", "Greška", JOptionPane.ERROR_MESSAGE);
	                        } else if (room.getRoomStatus() == RoomStatus.VACANT) {
	                            reservation.setReservationStatus(ReservationStatus.ACTIVE);
	                            room.setRoomStatus(RoomStatus.OCCUPIED);
	                            hotelController.updateRoom(room);

	                            int addMoreServices = JOptionPane.YES_OPTION;
	                            while (addMoreServices == JOptionPane.YES_OPTION) {
	                                String serviceName = JOptionPane.showInputDialog("Unesite naziv dodatne usluge (ostavite prazno ako ne želite da dodate):");
	                                if (serviceName != null && !serviceName.trim().isEmpty()) {
	                                    AdditionalService service = hotelManager.getAdditionalServices().FindById(serviceName.trim());
	                                    if (service != null) {
	                                        reservation.addAdditionalService(service);
	                                    } else {
	                                        JOptionPane.showMessageDialog(null, "Dodatna usluga sa navedenim nazivom nije pronađena.");
	                                    }
	                                }
	                                addMoreServices = JOptionPane.showConfirmDialog(null, "Želite li da dodate još neku dodatnu uslugu?", "Dodavanje dodatnih usluga", JOptionPane.YES_NO_OPTION);
	                            }

	                            double price = calculateReservationPrice(reservation);
	                            reservation.setPrice(price);
	                            hotelController.updateReservation(reservation.getStartDate(), reservation.getEndDate(), reservation.getRoom().getRoomNumber(), reservation);
	                            JOptionPane.showMessageDialog(null, "Gost je uspešno prijavljen za rezervaciju: " + selectedReservationStr, "Uspeh", JOptionPane.INFORMATION_MESSAGE);
	                        }
	                    }
	                }
	            }
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom prijave gosta. Proverite unete podatke i pokušajte ponovo.", "Greška", JOptionPane.ERROR_MESSAGE);
	        e.printStackTrace();
	    }
	}

	
	protected void checkOutGuest() {
	    String username = JOptionPane.showInputDialog("Unesite korisničko ime gosta (e-mail) za odjavu:");
	    Guest guest = hotelManager.getGuests().FindById(username);
	    if (guest == null) {
	        JOptionPane.showMessageDialog(null, "Gost nije pronađen. Molimo proverite korisničko ime.", "Greška", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    List<Reservation> reservations = new ArrayList<>();
	    for (Reservation reservation : hotelManager.getReservations().get().values()) {
	        if (reservation.getReservationStatus().equals(ReservationStatus.ACTIVE) &&
	                reservation.getGuest().getUsername().equals(username)) {
	            reservations.add(reservation);
	        }
	    }

	    if (reservations.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Nema aktivnih rezervacija za ovog gosta.", "Informacija", JOptionPane.INFORMATION_MESSAGE);
	        return;
	    }

	    String[] reservationOptions = new String[reservations.size()];
	    for (int i = 0; i < reservations.size(); i++) {
	        reservationOptions[i] = "Datum početka: " + reservations.get(i).getStartDate() 
	                + ", Datum kraja: " + reservations.get(i).getEndDate()
	                + ", Broj sobe: " + reservations.get(i).getRoom().getRoomNumber();
	    }

	    String selectedReservationStr = (String) JOptionPane.showInputDialog(
	            null,
	            "Izaberite rezervaciju za odjavu gosta:",
	            "Izbor rezervacije",
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            reservationOptions, reservationOptions[0]
	    );

	    if (selectedReservationStr != null) {
	        String[] reservationDetails = selectedReservationStr.split(",");
	        String startDate = reservationDetails[0].split(":")[1].trim();
	        String endDate = reservationDetails[1].split(":")[1].trim();
	        String roomNumber = reservationDetails[2].split(":")[1].trim();
	        String id = startDate + "_" + endDate + "_" + roomNumber;
	        Reservation reservation = hotelManager.getReservations().FindById(id);

	        if (reservation == null) {
	            JOptionPane.showMessageDialog(null, "Rezervacija nije pronađena.", "Greška", JOptionPane.ERROR_MESSAGE);
	        } else {
	            Room room = reservation.getRoom();
	            if (room == null) {
	                JOptionPane.showMessageDialog(null, "Soba nije pronađena.", "Greška", JOptionPane.ERROR_MESSAGE);
	            } else if (room.getRoomStatus() != RoomStatus.OCCUPIED) {
	                JOptionPane.showMessageDialog(null, "Soba nije zauzeta.", "Greška", JOptionPane.ERROR_MESSAGE);
	            } else {
	                room.setRoomStatus(RoomStatus.CLEANING);
	                hotelController.updateRoom(room);
	                reservation.setReservationStatus(ReservationStatus.COMPLETED);
	                Housekeeper leastBusyHousekeeper = findLeastBusyHousekeeper();
	                RoomCleaningRecord roomCleaningRecord = new RoomCleaningRecord(LocalDate.now(), leastBusyHousekeeper, room, CleaningStatus.CLEANING);
	                hotelManager.addRoomCleaningRecord(roomCleaningRecord.getID(), roomCleaningRecord);
	                hotelController.addRoomCleaningRecord(roomCleaningRecord);
	                hotelController.updateReservation(reservation.getStartDate(), reservation.getEndDate(), reservation.getRoom().getRoomNumber(), reservation);
	                JOptionPane.showMessageDialog(null, "Gost je uspešno odjavljen iz sobe: " + roomNumber, "Uspeh", JOptionPane.INFORMATION_MESSAGE);
	            }
	        }
	    }
	}

	private Housekeeper findLeastBusyHousekeeper() {
		int minAssignedRooms = Integer.MAX_VALUE;
		Housekeeper leastBusyHousekeeper = null;
		for(Housekeeper housekeeper: hotelManager.getHousekeepers().get().values()) {
			if(housekeeper.getAssignedRoomsCount() < minAssignedRooms) {
				minAssignedRooms = housekeeper.getAssignedRoomsCount();
				leastBusyHousekeeper = housekeeper;
			}
		}
		return leastBusyHousekeeper;
	}

	protected void showCleaningRoom() {
        Map<String, Room> roomMap = hotelManager.getRooms().get();
        List<Room> rooms = new ArrayList<>();
        for (Room room : roomMap.values()) {
            if (room.getRoomStatus().equals(RoomStatus.CLEANING)) {
                rooms.add(room);
            }
        }

        if (rooms.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Trenutno nema soba na čišćenju", "Poruka", JOptionPane.INFORMATION_MESSAGE);
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

        for (Room room : rooms) {
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

	protected void showOccupiedRoom() {
		Map<String, Room> roomMap = hotelManager.getRooms().get();
        List<Room> rooms = new ArrayList<>();
        for (Room room : roomMap.values()) {
            if (room.getRoomStatus().equals(RoomStatus.OCCUPIED)) {
                rooms.add(room);
            }
        }

        if (rooms.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Trenutno nema zauzetih soba", "Poruka", JOptionPane.INFORMATION_MESSAGE);
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

        for (Room room : rooms) {
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

	protected void showVacantRoom() {
		Map<String, Room> roomMap = hotelManager.getRooms().get();
        List<Room> rooms = new ArrayList<>();
        for (Room room : roomMap.values()) {
            if (room.getRoomStatus().equals(RoomStatus.VACANT)) {
                rooms.add(room);
            }
        }

        if (rooms.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Trenutno nema soba slobodnih soba", "Poruka", JOptionPane.INFORMATION_MESSAGE);
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

        for (Room room : rooms) {
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
	
	protected void dailyRoomOccupancy() {
	    List<Room> filteredRooms = new ArrayList<>();
	    Map<String, Room> rooms = hotelManager.getRooms().get();
	    for (Room room : rooms.values()) {
	        if (room.getRoomStatus().equals(RoomStatus.CLEANING) ||
	            room.getRoomStatus().equals(RoomStatus.OCCUPIED)) {
	            filteredRooms.add(room);
	        }
	    }

	    if (filteredRooms.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Trenutno nema zauzetih soba", "Poruka", JOptionPane.INFORMATION_MESSAGE);
	        return;
	    }

	    String[] columnNames = {
	        "Broj sobe", "Tip sobe", "Status"
	    };

	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    for (Room room : filteredRooms) {
	        Object[] row = {
	            room.getRoomNumber(),
	            room.getRoomType(),
	            room.getRoomStatus()
	        };
	        tableModel.addRow(row);
	    }
	    double occupancyRate = (double) filteredRooms.size() / rooms.size() * 100;
        JOptionPane.showMessageDialog(null, "Procenat zauzetosti: " + String.format("%.2f", occupancyRate) + "%", "Poruka", JOptionPane.INFORMATION_MESSAGE);
	    JTable table = new JTable(tableModel);
	    JScrollPane scrollPane = new JScrollPane(table);

	    contentPane.removeAll();
	    contentPane.add(scrollPane, BorderLayout.CENTER);
	    contentPane.revalidate();
	    contentPane.repaint();
	}


	protected void dailyDepartures() {
		LocalDate today = LocalDate.now();
		List<Reservation> filteredReservations = new ArrayList<>();
		Map<String,Reservation> reservations = hotelManager.getReservations().get();
		for (Reservation reservation: reservations.values()) {
			if(reservation.getReservationStatus().equals(ReservationStatus.COMPLETED) &&
			   reservation.getEndDate().equals(today)) {
				filteredReservations.add(reservation);
			}
		}
		
		if (filteredReservations.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Trenutno nema dnevnih odlazaka", "Poruka", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
		
		

        String[] columnNames = {
            "Korisničko ime", "Broj sobe", "Tip sobe", "Broj gostiju", "Datum odlaska"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Reservation reservation: filteredReservations) {
            Object[] row = {
            	reservation.getGuest().getUsername(),
            	reservation.getRoom().getRoomNumber(),
            	reservation.getRoom().getRoomType(),
            	reservation.getNumberOfGuests(),
            	reservation.getEndDate()	
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

	protected void dailyArrivals() {
		LocalDate today = LocalDate.now();
		List<Reservation> filteredReservations = new ArrayList<>();
		Map<String,Reservation> reservations = hotelManager.getReservations().get();
		for (Reservation reservation: reservations.values()) {
			if(reservation.getReservationStatus().equals(ReservationStatus.ACTIVE) &&
			   reservation.getStartDate().equals(today)) {
				filteredReservations.add(reservation);
			}
		}
		
		if (filteredReservations.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Trenutno nema dnevnih dolazaka", "Poruka", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnNames = {
            "Korisničko ime", "Broj sobe", "Tip sobe", "Broj gostiju", "Datum dolaska"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Reservation reservation: filteredReservations) {
            Object[] row = {
            	reservation.getGuest().getUsername(),
            	reservation.getRoom().getRoomNumber(),
            	reservation.getRoom().getRoomType(),
            	reservation.getNumberOfGuests(),
            	reservation.getStartDate()	
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
}