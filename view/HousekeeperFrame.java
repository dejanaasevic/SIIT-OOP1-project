package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.HotelController;
import manager.HotelManager;

public class HousekeeperFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HotelManager hotelManager;
	private HotelController hotelController;
	private String username;
	
	public HousekeeperFrame(HotelManager hotelManager, HotelController hotelController, String username) {
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
		this.setTitle("Hotel Management System - Housekeeper Panel");
		this.setVisible(true);
		this.setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
		JMenu reservationMenu = new JMenu("Sobe");
        menuBar.add(reservationMenu);
        JMenuItem viewAssignedRooms = new JMenuItem("Prikaz soba dodeljenih za čišćenje");
        reservationMenu.add(viewAssignedRooms);
        JMenuItem confirmRoomCleaning = new JMenuItem("Potvrdi čišćenje sobe");
        reservationMenu.add(confirmRoomCleaning);
	}
}
