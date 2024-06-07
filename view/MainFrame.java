package view;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controller.HotelController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import manager.HotelManager;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private HotelManager hotelManager;
	private HotelController hotelController;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setTitle("Hotel Management System - Login");
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		this.hotelManager = new HotelManager();
		this.hotelController = new HotelController(this.hotelManager);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(600, 300, 500, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel usernameLabel = new JLabel("Korisničko ime: ");
        usernameLabel.setBounds(50, 50, 120, 25);
        contentPane.add(usernameLabel);
        
        JTextField usernameField = new JTextField();
        usernameField.setBounds(166, 50, 154, 25);
        contentPane.add(usernameField);
                
        JLabel passwordLabel = new JLabel("Lozinka: ");
        passwordLabel.setBounds(50, 100, 80, 25);
        contentPane.add(passwordLabel);
        
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(166, 100, 154, 25);
        contentPane.add(passwordField);
        
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	String username = usernameField.getText();
                	String password = String.valueOf(passwordField.getPassword());
                	if(getUserType(username)!= null && validPassword(username,password)) {
                		String userType = getUserType(username);
                		switch(userType) {
                			case "administrator":
                				AdministratorFrame administratorFrame = new AdministratorFrame(hotelManager,hotelController);
                				administratorFrame.setVisible(true);
                				dispose(); 
                				break;
                			case "receptionist":
                				ReceptionistFrame receptionistFrame = new ReceptionistFrame(hotelManager,hotelController, username);
                				receptionistFrame.setVisible(true);
                				dispose(); 
                				break;               				
                			case "housekeeper":
                				HousekeeperFrame housekeeperFrame = new HousekeeperFrame(hotelManager,hotelController, username);
                				housekeeperFrame.setVisible(true);
                				dispose(); 
                				break;
                			case "guest":
                				GuestFrame guestFrame = new GuestFrame(hotelManager,hotelController,username);
                				guestFrame.setVisible(true);
                				dispose(); 
                				break;
                			default:
                                JOptionPane.showMessageDialog(MainFrame.this, "Nepoznat tip korisnika: " + userType, "Greška", JOptionPane.ERROR_MESSAGE);
                		}
                	}
                	else {
                		if(getUserType(username)== null) {
                			JOptionPane.showMessageDialog(MainFrame.this, "Uneto korisničko ime ne postoji", "Neuspešna prijava", JOptionPane.ERROR_MESSAGE);
                		}
                		else {
                			JOptionPane.showMessageDialog(MainFrame.this, "Uneta lozinka je pogrešna", "Neuspešna prijava", JOptionPane.ERROR_MESSAGE);
                		}
                	}
                }
        });
        loginButton.setBounds(166, 150, 100, 25);
        contentPane.add(loginButton);

	}
	
	public String getUserType(String username) {
		return hotelManager.getUserType(username);
	}
	
	private boolean validPassword(String username, String password) {
		return hotelManager.validPassword(username,password);
	}
}