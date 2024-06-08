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
import entity.Gender;
import entity.Guest;
import entity.PriceList;
import entity.Reservation;
import entity.ReservationRequest;
import entity.ReservationStatus;
import entity.Revenue;
import entity.RoomType;
import manager.HotelManager;

public class GuestFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HotelManager hotelManager;
	private HotelController hotelController;
	private String username;
	
	public GuestFrame(HotelManager hotelManager, HotelController hotelController, String username) {
	    this.hotelManager = hotelManager;
	    this.hotelController = hotelController;
	    this.username = username;
	    initializeUI();
	}

	private void initializeUI() {
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(100, 50, 1500, 1000);
	    contentPane = new JPanel();
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    contentPane.setLayout(new BorderLayout());
	    setContentPane(contentPane);
	    this.setTitle("Hotel Management System - Guest Panel");
	    this.setVisible(true);
	    this.setResizable(false);

	    JMenuBar menuBar = new JMenuBar();
	    setJMenuBar(menuBar);

	    JMenu reservationRequestMenu = new JMenu("Zahtev Rezervacije");
	     menuBar.add(reservationRequestMenu);
	    JMenuItem showreservationRequests = new JMenuItem("Pregledaj sve zahteve rezervacija");  //oky
	    reservationRequestMenu.add(showreservationRequests);
	    JMenuItem addreservationRequests = new JMenuItem("Dodaj novi zahtev rezervacije");  //oky
	    reservationRequestMenu.add(addreservationRequests);
	    //JMenuItem updatereservationRequests = new JMenuItem("Izmeni zahtev rezervacije");
	    //reservationRequestMenu.add(updatereservationRequests);
	    //JMenuItem deletereservationRequests = new JMenuItem("Obriši zahtev rezervacije");
	    //reservationRequestMenu.add(deletereservationRequests);

	    JMenu reservationMenu = new JMenu("Rezervacije");
	    menuBar.add(reservationMenu);
	    JMenuItem showReservation = new JMenuItem("Prikaz svih rezervacija"); //oky
	    reservationMenu.add(showReservation);
	    JMenuItem cancelReservation = new JMenuItem("Otkaži rezervaciju"); //oky
	    reservationMenu.add(cancelReservation);
	    
	    JMenu settingsMenu = new JMenu("Postavke");
	    menuBar.add(settingsMenu);
	    JMenuItem changePassword = new JMenuItem("Promena lozinke");
	    settingsMenu.add(changePassword);
	    JMenuItem signOut = new JMenuItem("Odjava iz sistema"); //oky
	    settingsMenu.add(signOut);


	    showreservationRequests.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            showUserReservationRequests();
	        }
	    });

	    addreservationRequests.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            addreservationRequests();
	        }
	    });

	    showReservation.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            showUserReservations();
	        }
	    });
	    
	    cancelReservation.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	cancelReservation();
	        }
	    });
	    
	    signOut.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		signOut();
	       }
	    });
	}
	
	protected void signOut() {
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		dispose(); 
	}

	protected void showUserReservationRequests() {
	    List<ReservationRequest> reservationRequests = hotelManager.getReservationRequests().getReservationRequests();
	    if (reservationRequests == null || reservationRequests.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Nema dostupnih rezervacija za prikaz.");
	        return;
	    }
	    String[] columnNames = {
	        "Tip Sobe", 
	        "Broj Gostiju", 
	        "Datum Početka", 
	        "Datum Kraja", 
	        "Status Rezervacije", 
	        "Dodatne Usluge",
	        "Cena"
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
	        if (!reservationRequest.getGuest().getUsername().equals(username)) {
	          continue;
	        }
	        hasUserReservations = true;
	        StringBuilder additionalServices = new StringBuilder();
	        for (AdditionalService service : reservationRequest.getAdditionalServices()) {
	            additionalServices.append(service.getName()).append("; ");
	        }
	        if (additionalServices.length() > 0) {
	            additionalServices.setLength(additionalServices.length() - 2);
	        }
	        Object[] row = {
	        		reservationRequest.getRoomType().name(),
		            reservationRequest.getNumberOfGuests(),
		            reservationRequest.getStartDate().format(formatter),
		            reservationRequest.getEndDate().format(formatter),
		            reservationRequest.getReservationStatus().name(),
		            additionalServices.toString(),
		            reservationRequest.getPrice()
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
	    contentPane.add(scrollPane);
	    contentPane.revalidate();
	    contentPane.repaint();
	}

	protected void addreservationRequests() {
	    try {
	    	String startDateStr = JOptionPane.showInputDialog("Unesite datum dolaska (dd.MM.yyyy.):");
	    	if(startDateStr == null || startDateStr.trim().isEmpty()) {
	    	    JOptionPane.showMessageDialog(null, "Datum nije unesen.");
	    	    return;
	    	}
	        LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	        String endDateStr = JOptionPane.showInputDialog("Unesite datum odlaska (dd.MM.yyyy.):");
	        if(endDateStr == null || endDateStr.trim().isEmpty()) {
	    	    JOptionPane.showMessageDialog(null, "Datum nije unesen.");
	    	    return;
	    	}
	        LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	        if(endDate.isBefore(startDate)) {
	            JOptionPane.showMessageDialog(null, "Datum odlaska ne može biti pre datuma dolaska.");
	            return;
	        }
	        List<String> roomTypesList = hotelManager.getAvailableRoomTypes(startDate, endDate);
	        String[] roomTypes = new String [roomTypesList.size()];
	        for(int i = 0; i<roomTypesList.size(); i++ ) {
	        	roomTypes[i] = roomTypesList.get(i);
	        }
	        String roomTypeStr = (String) JOptionPane.showInputDialog(null, "Izaberite tip sobe:", "Tip Sobe", 
	                JOptionPane.QUESTION_MESSAGE, null, roomTypes, roomTypes[0]);
	        RoomType roomType = RoomType.valueOf(roomTypeStr);
	        
	        int numberOfGuests = Integer.parseInt(JOptionPane.showInputDialog("Unesite broj gostiju:"));
	        if(numberOfGuests < 0) {
	            JOptionPane.showMessageDialog(null, "Broj gostiju ne može biti manji od nula.");
	            return;
	        }

	        Guest currentGuest = hotelManager.getGuests().FindById(username);
	        
	        if (currentGuest == null) {
	            JOptionPane.showMessageDialog(null, "Nema prijavljenog korisnika. Molimo prijavite se pre kreiranja rezervacije.");
	            return;
	        }
	        ReservationRequest reservationRequest = new ReservationRequest(roomType, numberOfGuests, startDate, endDate, currentGuest);
	        
	        List<String> additionalServicesOptions = new ArrayList<>();
            for (String additionalService : hotelManager.getAdditionalServices().get().keySet()) {
                additionalServicesOptions.add(additionalService);
            }
	        int addMoreServices = JOptionPane.YES_OPTION;
            while (addMoreServices == JOptionPane.YES_OPTION) {
                String[] optionsArray = additionalServicesOptions.toArray(new String[0]);
                String serviceName = (String) JOptionPane.showInputDialog(null,
                        "Izaberite dodatnu uslugu:",
                        "Dodatne usluge",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        optionsArray,
                        optionsArray[0]);

                if (serviceName != null && !serviceName.trim().isEmpty()) {
                    AdditionalService service = hotelManager.getAdditionalServices().FindById(serviceName.trim());
                    if (service != null) {
                    	reservationRequest.addAdditionalService(service);
                    } else {
                        JOptionPane.showMessageDialog(null, "Dodatna usluga sa navedenim nazivom nije pronađena.");
                    }
                }
                addMoreServices = JOptionPane.showConfirmDialog(null, "Želite li da dodate još neku dodatnu uslugu?", "Dodavanje dodatnih usluga", JOptionPane.YES_NO_OPTION);
            }
	        double price =  calculateReservationRequestPrice(reservationRequest);
	        reservationRequest.setPrice(price);
	        hotelManager.addReservationRequest(reservationRequest);
	        hotelController.addReservationRequest(reservationRequest);
	        
	        Guest guest = hotelManager.getGuests().FindById(username);
	        LocalDate today = LocalDate.now();
	        Revenue revenue = new Revenue(guest,roomType,today,price);
	        hotelManager.addRevenue(revenue);
	        hotelController.addRevenue(revenue);
	        
	        showUserReservationRequests(); 
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom kreiranja rezervacije. Proverite unete podatke i pokušajte ponovo.");
	        e.printStackTrace();
	    }
	    showUserReservationRequests();
	}
	
	protected void showUserReservations() {
	    Map<String, Reservation> reservationMap = hotelManager.getReservations().get();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	    String[] columnNames = {
	        "Datum početka", "Datum kraja", "Broj sobe", "Tip sobe", "Broj Gostiju", 
	        "Status", "Dodatne usluge", "Cena"
	    };
	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; 
	        }
	    };
	    for (Reservation reservation : reservationMap.values()) {
	        if (reservation.getGuest().getUsername().equals(this.username)) {
	            List<AdditionalService> additionalServices = reservation.getAdditionalService();
	            StringBuilder servicesString = new StringBuilder();            
	            for (int i = 0; i < additionalServices.size(); i++) {
	                servicesString.append(additionalServices.get(i).getName());
	                if (i < additionalServices.size() - 1) {
	                    servicesString.append(";");
	                }
	            }        
	            Object[] row = {
	                reservation.getStartDate().format(formatter),
	                reservation.getEndDate().format(formatter),
	                reservation.getRoom().getRoomNumber(),
	                reservation.getRoomType(),
	                reservation.getNumberOfGuests(),
	                reservation.getReservationStatus(),
	                servicesString.toString(),
	                reservation.getTotalPrice()
	            };
	            tableModel.addRow(row);
	        }
	    }
	    JTable table = new JTable(tableModel);
	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setBounds(30, 30, 1400, 900); 
	    contentPane.removeAll(); 
	    contentPane.setLayout(new BorderLayout()); 
	    contentPane.add(scrollPane, BorderLayout.CENTER);
	    contentPane.revalidate();
	    contentPane.repaint();
	}
	
	private double calculateReservationRequestPrice(ReservationRequest reservationRequest) {
	    double totalPrice = 0;
	    LocalDate date = reservationRequest.getStartDate();

	    while (!date.isAfter(reservationRequest.getEndDate())) {
	        PriceList priceListForDate = hotelManager.getPriceListByDate(date);
	        if (priceListForDate != null) {
	            Double roomPrice = priceListForDate.findRoomPrice(reservationRequest.getRoomType());
	            if (roomPrice != null) {
	                totalPrice += roomPrice;
	            }

	            List<AdditionalService> additionalServices = reservationRequest.getAdditionalServices();
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


	protected void cancelReservation() {
	    Map<String, Reservation> reservationMap = hotelManager.getReservations().get();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	    String[] columnNames = {
	        "Datum početka", "Datum kraja", "Broj sobe", "Tip sobe", "Broj Gostiju", 
	        "Status", "Dodatne usluge", "Cena"
	    };
	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; 
	        }
	    };

	    for (Reservation reservation : reservationMap.values()) {
	        if (reservation.getGuest().getUsername().equals(this.username)) {
	            List<AdditionalService> additionalServices = reservation.getAdditionalService();
	            StringBuilder servicesString = new StringBuilder();
	            
	            for (int i = 0; i < additionalServices.size(); i++) {
	                servicesString.append(additionalServices.get(i).getName());
	                if (i < additionalServices.size() - 1) {
	                    servicesString.append(";");
	                }
	            }
	            
	            Object[] row = {
	                reservation.getStartDate().format(formatter),
	                reservation.getEndDate().format(formatter),
	                reservation.getRoom().getRoomNumber(),
	                reservation.getRoomType(),
	                reservation.getNumberOfGuests(),
	                reservation.getReservationStatus(),
	                servicesString.toString(),
	                reservation.getTotalPrice()
	            };
	            tableModel.addRow(row);
	        }
	    }

	    JTable table = new JTable(tableModel);
	    JScrollPane scrollPane = new JScrollPane(table);
	    scrollPane.setBounds(30, 30, 1400, 900);

	    contentPane.removeAll();
	    contentPane.setLayout(new BorderLayout());
	    contentPane.add(scrollPane, BorderLayout.CENTER);
	    contentPane.revalidate();
	    contentPane.repaint();
	    
	    JButton processButton = new JButton("Otkaži izabranu rezervaciju");
	    processButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int selectedRow = table.getSelectedRow();
	            if (selectedRow != -1) {
	            	String startDateStr = (String) table.getValueAt(selectedRow, 0);
	            	String endDateStr = (String) table.getValueAt(selectedRow, 1);	            	   
	            	DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	            	LocalDate startDate = LocalDate.parse(startDateStr, inputFormatter);
	            	LocalDate endDate = LocalDate.parse(endDateStr, inputFormatter);

	            	DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            	String formattedStartDate = startDate.format(outputFormatter);
	            	String formattedEndDate = endDate.format(outputFormatter);

	            	String roomNumber = (String) table.getValueAt(selectedRow, 2);
	            	String id = formattedStartDate + "_" + formattedEndDate + "_" + roomNumber;

	                Reservation reservationToCancel = hotelManager.getReservations().FindById(id);
	                System.out.println(reservationToCancel);
	                if (reservationToCancel != null) {
	                    cancelSelectedReservation(reservationToCancel);
	                } else {
	                    JOptionPane.showMessageDialog(null, "Rezervacija nije pronađena.", "Greška", JOptionPane.ERROR_MESSAGE);
	                }
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

	protected void cancelSelectedReservation(Reservation reservationToCancel) {
	    int confirmation = JOptionPane.showConfirmDialog(
	        null, 
	        "Da li ste sigurni da želite da otkažete ovu rezervaciju?", 
	        "Potvrda otkazivanja", 
	        JOptionPane.YES_NO_OPTION
	    );

	    if (confirmation == JOptionPane.YES_OPTION) {
	        reservationToCancel.setReservationStatus(ReservationStatus.CANCELLED);
	        hotelController.updateReservation(
	            reservationToCancel.getStartDate(), 
	            reservationToCancel.getEndDate(), 
	            reservationToCancel.getRoom().getRoomNumber(), 
	            reservationToCancel
	        );

	        JOptionPane.showMessageDialog(
	            null, 
	            "Rezervacija je uspešno otkazana.", 
	            "Otkazivanje uspešno", 
	            JOptionPane.INFORMATION_MESSAGE
	        );
	    } else {
	        JOptionPane.showMessageDialog(
	            null, 
	            "Otkazivanje rezervacije je otkazano.", 
	            "Otkazivanje otkazano", 
	            JOptionPane.INFORMATION_MESSAGE
	        );
	    }
	    showUserReservations();
	}
}