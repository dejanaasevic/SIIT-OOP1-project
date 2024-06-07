package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
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
import entity.CleaningStatus;
import entity.Room;
import entity.RoomCleaningRecord;
import entity.RoomStatus;
import manager.HotelManager;

public class HousekeeperFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private HotelManager hotelManager;
    private HotelController hotelController;
    private String username;
    private JTable table;
    private DefaultTableModel tableModel;

    public HousekeeperFrame(HotelManager hotelManager, HotelController hotelController, String username) {
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
        setTitle("Hotel Management System - Housekeeper Panel");
        setVisible(true);
        setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu reservationMenu = new JMenu("Sobe");
        menuBar.add(reservationMenu);
        JMenuItem viewAllAssignedRooms = new JMenuItem("Danas dodeljene sobe za čišćenje"); //oky
        reservationMenu.add(viewAllAssignedRooms);
        JMenuItem viewAssignedRooms = new JMenuItem("Dodeljene sobe za čišćenje"); //oky
        reservationMenu.add(viewAssignedRooms);
        JMenuItem confirmRoomCleaning = new JMenuItem("Potvrdi čišćenje sobe"); //oky
        reservationMenu.add(confirmRoomCleaning);
        
	    JMenu settingsMenu = new JMenu("Postavke");
	    menuBar.add(settingsMenu);
	    JMenuItem changePassword = new JMenuItem("Promena lozinke");
	    settingsMenu.add(changePassword);
	    JMenuItem signOut = new JMenuItem("Odjava iz sistema"); //oky
	    settingsMenu.add(signOut);

        viewAssignedRooms.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewAssignedRooms();
            }
        });

        viewAllAssignedRooms.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	viewAllAssignedRooms();
            }
        });
        confirmRoomCleaning.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmRoomCleaning();
            }
        });
        
        signOut.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		signOut();
           }
        });

        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    protected void signOut() {
    	MainFrame mainFrame = new MainFrame();
    	mainFrame.setVisible(true);
    	dispose(); 
    }

    protected void viewAssignedRooms() {
		List<Room> assignedRooms = hotelManager.getHousekeepers().FindById(username).getAssignedRooms();
		if (assignedRooms.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Trenutno nema dodeljenih soba za čišćenje", "Poruka", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnNames = {
            "Datum", "Soba", "Sobarica", "Status"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        LocalDate today =  LocalDate.now();
        for(Room room:assignedRooms) {
        	String roomNumber = room.getRoomNumber();
        	String id = today.toString() + "_" + roomNumber;
        	RoomCleaningRecord roomCleaningRecord = hotelManager.getRoomCleaningRecordManager().FindById(id);
        	if(roomCleaningRecord == null) {
        		continue;
        	}
        	else if(roomCleaningRecord.getStatus().equals(CleaningStatus.CLEANING)) {
        		 Object[] rowData = {
        				 roomCleaningRecord.getDate(),
        				 roomCleaningRecord.getRoom().getRoomNumber(),
        				 roomCleaningRecord.getHousekeeper().getUsername(),
        				 roomCleaningRecord.getStatus()
                 };
        		 tableModel.addRow(rowData);
        	}
        }
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        contentPane.removeAll();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();	
	}

    protected void viewAllAssignedRooms() {
		List<Room> assignedRooms = hotelManager.getHousekeepers().FindById(username).getAssignedRooms();
		if (assignedRooms.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Trenutno nema dodeljenih soba za čišćenje", "Poruka", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnNames = {
            "Datum", "Soba", "Sobarica", "Status"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        LocalDate today =  LocalDate.now();
        for(Room room:assignedRooms) {
        	String roomNumber = room.getRoomNumber();
        	String id = today.toString() + "_" + roomNumber;
        	RoomCleaningRecord roomCleaningRecord = hotelManager.getRoomCleaningRecordManager().FindById(id);
        	if(roomCleaningRecord == null) {
        		continue;
        	}
        	else {
        		 Object[] rowData = {
        				 roomCleaningRecord.getDate(),
        				 roomCleaningRecord.getRoom().getRoomNumber(),
        				 roomCleaningRecord.getHousekeeper().getUsername(),
        				 roomCleaningRecord.getStatus()
                 };
        		 tableModel.addRow(rowData);
        	}
        }
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        contentPane.removeAll();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();	
	}
    
    protected void confirmRoomCleaning() {
        List<Room> assignedRooms = hotelManager.getHousekeepers().FindById(username).getAssignedRooms();
        Map<String, RoomCleaningRecord> roomCleaningRecords = hotelManager.getRoomCleaningRecordManager().get();
        if (assignedRooms.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Trenutno nema dodeljenih soba za čišćenje", "Poruka", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnNames = {
            "Datum", "Soba", "Sobarica", "Status"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        LocalDate today = LocalDate.now();
        Map<Integer, RoomCleaningRecord> rowToRecordMap = new HashMap<>(); 
        int rowIndex = 0;

        for (Room room : assignedRooms) {
            String roomNumber = room.getRoomNumber();
            String id = today.toString() + "_" + roomNumber;
            RoomCleaningRecord roomCleaningRecord = hotelManager.getRoomCleaningRecordManager().FindById(id);
            if (roomCleaningRecord == null) {
                continue;
            } else if (roomCleaningRecord.getStatus().equals(CleaningStatus.CLEANING)) {
                Object[] rowData = {
                    roomCleaningRecord.getDate(),
                    roomCleaningRecord.getRoom().getRoomNumber(),
                    roomCleaningRecord.getHousekeeper().getUsername(),
                    roomCleaningRecord.getStatus()
                };
                tableModel.addRow(rowData);
                rowToRecordMap.put(rowIndex, roomCleaningRecord); 
                rowIndex++;
            }
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        contentPane.removeAll();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();    

        JButton processButton = new JButton("Obradi izabranu sobu");
        processButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    RoomCleaningRecord selectedRoomCleaningRecord = rowToRecordMap.get(selectedRow);
                    processSelectedRoomCleaningRecord(selectedRoomCleaningRecord);
                } else {
                    JOptionPane.showMessageDialog(null, "Molimo izaberite sobu za obradu.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(processButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.revalidate();
        contentPane.repaint();
    }

    protected void processSelectedRoomCleaningRecord(RoomCleaningRecord selectedRoomCleaningRecord) {
        Room room = selectedRoomCleaningRecord.getRoom();
        int result = JOptionPane.showConfirmDialog(
            null,
            "Da li želite da potvrdite čišćenje sobe " + room.getRoomNumber() + "?",
            "Potvrda čišćenja",
            JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            selectedRoomCleaningRecord.setStatus(CleaningStatus.CLEANED);
            hotelController.updateRoomCleaningRecord(selectedRoomCleaningRecord);
            room.setRoomStatus(RoomStatus.VACANT);
            hotelController.updateRoom(room);
            viewAllAssignedRooms();
        }
    }
}