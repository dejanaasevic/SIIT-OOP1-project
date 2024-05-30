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
        
        JMenu reservationRequestMenu = new JMenu("Zahtevi rezervacije");
        menuBar.add(reservationRequestMenu);
        JMenuItem showreservationRequests = new JMenuItem("Pregledaj sve zahteve rezervacija");
        reservationRequestMenu.add(showreservationRequests);
        
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
