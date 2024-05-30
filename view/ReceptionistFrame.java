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

public class ReceptionistFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HotelManager hotelManager;
	private HotelController hotelController;
	private String username;
	
	public ReceptionistFrame(HotelManager hotelManager, HotelController hotelController,String username) {
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
		this.setTitle("Hotel Management System - Receptionist Panel");
		this.setVisible(true);
		this.setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu guestsMenu = new JMenu("Gosti");
        menuBar.add(guestsMenu);
        JMenuItem showAllGuests = new JMenuItem("Prikaz svih gostiju");
        guestsMenu.add(showAllGuests);
        JMenuItem addNewGuest = new JMenuItem("Dodaj novog gosta:");
        guestsMenu.add(addNewGuest);
        JMenuItem checkInGuest = new JMenuItem("Check-in gosta");
        guestsMenu.add(checkInGuest);
        JMenuItem checkOutGuest = new JMenuItem("Check-out gosta");
        guestsMenu.add(checkOutGuest);
        
        
        JMenu reservationRequestMenu = new JMenu("Rezervacije");
        menuBar.add(reservationRequestMenu);
        JMenuItem showreservationRequests = new JMenuItem("Pregledaj sve zahteve rezervacija");
        reservationRequestMenu.add(showreservationRequests);
        JMenuItem showreservations = new JMenuItem("Pregledaj sve rezervacije");
        reservationRequestMenu.add(showreservations);
        JMenuItem processReservationRequest  = new JMenuItem("Potvrdi/Odbij rezervaciju");
        reservationRequestMenu.add(processReservationRequest);
        JMenuItem addReservation  = new JMenuItem("Dodaj rezervaciju");
        reservationRequestMenu.add(addReservation);
        JMenuItem cancelReservation  = new JMenuItem("Otkazi rezervaciju");
        reservationRequestMenu.add(cancelReservation);
        
        
        JMenu roomMenu = new JMenu("Sobe");
        menuBar.add(roomMenu);
        JMenuItem showVacantRoom = new JMenuItem("Prikaz slobodnih soba");
        roomMenu.add(showVacantRoom);
        JMenuItem showOccupiedRoom = new JMenuItem("Prikaz zauzetih soba");
        roomMenu.add(showOccupiedRoom);
        JMenuItem showCleaningRoom = new JMenuItem("Prikaz soba na spermanju");
        roomMenu.add(showCleaningRoom);
        
        showreservationRequests.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showUserReservationRequests();
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
	    	"Korisnik",
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
	    contentPane.add(scrollPane);
	    contentPane.revalidate();
	    contentPane.repaint();
	}
}
