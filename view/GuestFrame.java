package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
import entity.ReservationRequest;
import entity.RoomType;
import manager.HotelManager;

public class GuestFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HotelManager hotelManager;
	private HotelController hotelController;
	private String username;
	
	public GuestFrame(HotelManager hotelManager, HotelController hotelController,String username) {
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
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("Hotel Management System - Guest Panel");
		this.setVisible(true);
		this.setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu reservationRequestMenu = new JMenu("Rezervacije");
        menuBar.add(reservationRequestMenu);
        JMenuItem showreservationRequests = new JMenuItem("Pregledaj sve zahteve rezervacija");
        reservationRequestMenu.add(showreservationRequests);
        JMenuItem addreservationRequests = new JMenuItem("Dodaj novi zahtev rezervacije");
        reservationRequestMenu.add(addreservationRequests);
        JMenuItem updatereservationRequests = new JMenuItem("Izmeni zahtev rezervacije");
        reservationRequestMenu.add(updatereservationRequests);
        JMenuItem deletereservationRequests = new JMenuItem("Obriši zahtev rezervacije");
        reservationRequestMenu.add(deletereservationRequests);
		
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
	        "Dodatne Usluge"
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
	    contentPane.add(scrollPane);
	    contentPane.revalidate();
	    contentPane.repaint();
	}

	protected void addreservationRequests() {
	    try {
	    	String startDateStr = JOptionPane.showInputDialog("Unesite datum dolaska (dd.MM.yyyy.):");
	        LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	        String endDateStr = JOptionPane.showInputDialog("Unesite datum odlaska (dd.MM.yyyy.):");
	        LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	        
	        String[] roomTypes = {"SINGLE", "DOUBLE", "TWIN", "TRIPLE"};
	        String roomTypeStr = (String) JOptionPane.showInputDialog(null, "Izaberite tip sobe:", "Tip Sobe", 
	                JOptionPane.QUESTION_MESSAGE, null, roomTypes, roomTypes[0]);
	        RoomType roomType = RoomType.valueOf(roomTypeStr);
	        
	        int numberOfGuests = Integer.parseInt(JOptionPane.showInputDialog("Unesite broj gostiju:"));

	        Guest currentGuest = hotelManager.getGuests().FindById(username);
	        
	        if (currentGuest == null) {
	            JOptionPane.showMessageDialog(null, "Nema prijavljenog korisnika. Molimo prijavite se pre kreiranja rezervacije.");
	            return;
	        }

	        ReservationRequest reservationRequest = new ReservationRequest(roomType, numberOfGuests, startDate, endDate, currentGuest);

	        int addMoreServices = JOptionPane.YES_OPTION;
	        while (addMoreServices == JOptionPane.YES_OPTION) {
	            String serviceName = JOptionPane.showInputDialog("Unesite naziv dodatne usluge:");
	            System.out.print(serviceName);
	            
	            AdditionalService service = hotelManager.getAdditionalServices().FindById(serviceName);
	            if (service != null) {
	                reservationRequest.addAdditionalService(service);
	            } else {
	                JOptionPane.showMessageDialog(null, "Dodatna usluga sa navedenim nazivom nije pronađena.");
	            }

	            addMoreServices = JOptionPane.showConfirmDialog(null, "Želite li dodati još jednu dodatnu uslugu?", "Dodavanje dodatnih usluga", JOptionPane.YES_NO_OPTION);
	        }

	        hotelManager.addReservationRequest(reservationRequest);
	        hotelController.addReservationRequest(reservationRequest);
	       
	        
	        showUserReservationRequests(); 
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom kreiranja rezervacije. Proverite unete podatke i pokušajte ponovo.");
	        e.printStackTrace();
	    }
	}
}
