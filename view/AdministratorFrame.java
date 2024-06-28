package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
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

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;

import controller.HotelController;
import entity.AdditionalService;
import entity.Employee;
import entity.Expense;
import entity.Gender;
import entity.Guest;
import entity.Housekeeper;
import entity.PriceList;
import entity.QualificationLevel;
import entity.Receptionist;
import entity.Reservation;
import entity.ReservationRequest;
import entity.ReservationStatus;
import entity.Revenue;
import entity.Room;
import entity.RoomCleaningRecord;
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
        setBounds(100, 20, 1500, 1000);
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
        JMenuItem updateReservation = new JMenuItem("Izmeni rezervacije");
        reservationMenu.add(updateReservation);
        JMenuItem deleteReservation = new JMenuItem("Brisanje rezervacije"); 
        reservationMenu.add(deleteReservation);
        

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
        JMenuItem roomsPerHousekeeper = new JMenuItem("Broj očišćenih soba po sobarici"); 
        reportMenu.add(roomsPerHousekeeper);
        JMenuItem countReservationsByStaus = new JMenuItem("Broj rezervacija po statusu"); 
        reportMenu.add(countReservationsByStaus);
        JMenuItem numberOfNightsAndRoomIncome = new JMenuItem("Broj noćenja i prihodi sobe"); 
        reportMenu.add(numberOfNightsAndRoomIncome);
        JMenuItem incomeByRoomTypeChart = new JMenuItem("Prihod po tipu sobe prethodnih godinu dana");
        reportMenu.add(incomeByRoomTypeChart);
        JMenuItem roomsPerHousekeeperChart = new JMenuItem("Opterećenje sobarica prethodnih 30 dana");
        reportMenu.add(roomsPerHousekeeperChart);
        JMenuItem reservationStatusChart = new JMenuItem("Status rezervacija prethodnih 30 dana");
        reportMenu.add(reservationStatusChart);
        
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
        
        showReservations.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showReservations();
            }
        }); 
        
        addReservation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	addReservation();
            }
        });
        updateReservation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	updateReservation();
             }
         });
        
        deleteReservation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	deleteReservation();
             }
         });
        
        incomeExpenses.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	incomeExpenses();
             }
         });
        
        signOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	signOut();
             }
         });
        
        occupancyRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showRoomOccupancy();
             }
         });
        
        roomsPerHousekeeper.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	roomsPerHousekeeper();
             }
         });
        
        countReservationsByStaus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	countReservationsByStaus();
             }
         });
        
        numberOfNightsAndRoomIncome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	numberOfNightsAndRoomIncome();
             }
         });
        
        incomeByRoomTypeChart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	incomeByRoomTypeChart();
             }
         });
        
        roomsPerHousekeeperChart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	roomsPerHousekeeperChart();
             }
         });
        
        reservationStatusChart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	reservationStatusChart();
             }
         });
        
    }

	protected void signOut() {
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		dispose(); 
	}

	protected void incomeExpenses() {
	    String startDateStr = JOptionPane.showInputDialog("Unesite datum početka analize (dd.MM.yyyy.):");
	    if(startDateStr == null || startDateStr.trim().isEmpty()) {
	    	JOptionPane.showMessageDialog(null, "Datum nije unesen.");
		    return;
	    }
	    LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	    String endDateStr = JOptionPane.showInputDialog("Unesite datum kraja analize (dd.MM.yyyy.):");
	    if(endDateStr == null || endDateStr.trim().isEmpty()) {
	    	JOptionPane.showMessageDialog(null, "Datum nije unesen.");
		    return;
	    }
	    LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));

	    Period period = Period.between(startDate, endDate);
	    int monthsBetween = period.getYears() * 12 + period.getMonths();
	    
	    if (monthsBetween <= 0) {
	        JOptionPane.showMessageDialog(contentPane, "Period mora biti duži od 0 meseci.", "Greška", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    List<Expense> expenseList = hotelManager.getExpenses().getExpenses();
	    List<Revenue> revenueList = hotelManager.getRevenues().getRevenues();
	    double income = 0;
	    double outcome = 0;
	    double salaryOutcome = 0;

	    for (Employee employee : hotelManager.getEmployees().get().values()) {
	        salaryOutcome += employee.getSalary();
	    }
	    
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	    String[] columnNames = {
	        "Datum", "Gost", "Iznos"
	    };
	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; 
	        }
	    };
	    
	    for (Expense expense : expenseList) {
	        String formattedAmount = String.format("%.2f", expense.getAmount() * (-1));
	        Object[] row = {
	            expense.getDate().format(formatter),
	            expense.getGuest().getUsername(),
	            formattedAmount
	        };
	        if (!startDate.isAfter(expense.getDate()) && !endDate.isBefore(expense.getDate())) {
	            tableModel.addRow(row);
	            outcome += expense.getAmount();
	        }
	    }

	    for (Revenue revenue : revenueList) {
	        String formattedAmount = String.format("%.2f", revenue.getAmount());
	        Object[] row = {
	            revenue.getDate().format(formatter),
	            revenue.getGuest().getUsername(),
	            formattedAmount
	        };
	        if (!startDate.isAfter(revenue.getDate()) && !endDate.isBefore(revenue.getDate())) {
	            tableModel.addRow(row);
	            income += revenue.getAmount();
	        }
	    }
	    
	    double totalSalaryOutcome = salaryOutcome * monthsBetween;
	    for (int i = 0; i < monthsBetween; i++) {
	        LocalDate salaryDate = startDate.plusMonths(i);
	        String formattedAmount = String.format("%.2f", salaryOutcome * (-1));
	        Object[] row = {
	            salaryDate.format(formatter),
	            "Plata",
	            formattedAmount
	        };
	        tableModel.addRow(row);
	    }

	    JTable table = new JTable(tableModel);
	    JScrollPane scrollPane = new JScrollPane(table);
	    contentPane.removeAll();
	    contentPane.add(scrollPane, BorderLayout.CENTER);
	    contentPane.revalidate();
	    contentPane.repaint();
	    
	    double netIncome = income + outcome - totalSalaryOutcome; 
	    double monthlyIncome = income / monthsBetween;
	    double monthlyOutcome = (outcome + totalSalaryOutcome) / monthsBetween;
	    
	    String message = String.format("Prihodi: %.2f\nRashodi: %.2f\nNeto prihod: %.2f\n\nMesečni prihodi: %.2f\nMesečni rashodi: %.2f\n\nUkupno plate: %.2f", 
	                                    income, outcome + totalSalaryOutcome, netIncome, monthlyIncome, monthlyOutcome, totalSalaryOutcome);
	    JOptionPane.showMessageDialog(contentPane, message, "Rezultat analize", JOptionPane.INFORMATION_MESSAGE);
	}
	
	protected void roomsPerHousekeeper() {
	    String startDateStr = JOptionPane.showInputDialog("Unesite datum početka analize (dd.MM.yyyy.):");
	    if(startDateStr == null || startDateStr.trim().isEmpty()) {
	    	JOptionPane.showMessageDialog(null, "Datum nije unesen.");
		    return;
	    }
	    LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	    String endDateStr = JOptionPane.showInputDialog("Unesite datum kraja analize (dd.MM.yyyy.):");
	    if(endDateStr == null || endDateStr.trim().isEmpty()) {
	    	JOptionPane.showMessageDialog(null, "Datum nije unesen.");
		    return;
	    }
	    LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	    
	    Map<String, RoomCleaningRecord> cleaningRecordsMap = hotelManager.getRoomCleaningRecordManager().get();
	    Map<String, Integer> roomPerHousekeeper = new HashMap<>();
	    
	    for (Housekeeper housekeeper : hotelManager.getHousekeepers().get().values()) {
	        roomPerHousekeeper.put(housekeeper.getUsername(), 0);
	    }

	    boolean recordsFound = false;
	    
	    for (RoomCleaningRecord cleaningRecord : cleaningRecordsMap.values()) {
	        LocalDate cleaningDate = cleaningRecord.getDate();
	        if ((cleaningDate.isEqual(startDate) || cleaningDate.isEqual(endDate) ||
	             (cleaningDate.isAfter(startDate) && cleaningDate.isBefore(endDate)))) {
	            recordsFound = true;
	            String housekeeperUsername = cleaningRecord.getHousekeeper().getUsername();
	            int housekeeperCleanedRoom = roomPerHousekeeper.get(housekeeperUsername) + 1;
	            roomPerHousekeeper.put(housekeeperUsername, housekeeperCleanedRoom);
	        }
	    }

	    if (!recordsFound) {
	        JOptionPane.showMessageDialog(null, "Nema podataka o čišćenju soba za odabrani period.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
	        return;
	    }
	    
	    String[] columnNames = { "Sobarica", "Broj soba" };
	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; 
	        }
	    };
	    
	    for (String housekeeperUsername : roomPerHousekeeper.keySet()) {
	        int numberOfCleanedRooms = roomPerHousekeeper.get(housekeeperUsername);
	        Object[] row = { housekeeperUsername, numberOfCleanedRooms };
	        tableModel.addRow(row);
	    }

	     JTable table = new JTable(tableModel);
	     JScrollPane scrollPane = new JScrollPane(table);

	     contentPane.removeAll();
	     contentPane.add(scrollPane, BorderLayout.CENTER);
	     contentPane.revalidate();
	     contentPane.repaint();
	}
	
	protected void countReservationsByStaus() {
		String startDateStr = JOptionPane.showInputDialog("Unesite datum početka analize (dd.MM.yyyy.):");
	    if(startDateStr == null || startDateStr.trim().isEmpty()) {
	    	JOptionPane.showMessageDialog(null, "Datum nije unesen.");
		    return;
	    }
	    LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	    String endDateStr = JOptionPane.showInputDialog("Unesite datum kraja analize (dd.MM.yyyy.):");
	    if(endDateStr == null || endDateStr.trim().isEmpty()) {
	    	JOptionPane.showMessageDialog(null, "Datum nije unesen.");
		    return;
	    }
	    LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	    
	    Map<String, Reservation> reservationMap = hotelManager.getReservations().get();
	    List<ReservationRequest> ReservationRequestList = hotelManager.getReservationRequests().getReservationRequests();
	    Map<ReservationStatus, Integer> countReservationsStatus = new HashMap<>();
	    
	    countReservationsStatus.put(ReservationStatus.CONFIRMED, 0);
	    countReservationsStatus.put(ReservationStatus.CANCELLED, 0);
	    countReservationsStatus.put(ReservationStatus.REJECTED, 0);
	    boolean reservationFound = false;
	    boolean reservationRequestFound = false;
	    
	    for (Reservation reservation : reservationMap.values()) {
	    	reservationFound = true;
	        if (reservation.getReservationStatus() == ReservationStatus.CONFIRMED &&
	                ((reservation.getStartDate().isEqual(startDate) || reservation.getStartDate().isAfter(startDate)) &&
	                 (reservation.getEndDate().isEqual(endDate) || reservation.getEndDate().isBefore(endDate)))) {
	        		  int statusCount  = countReservationsStatus.get(ReservationStatus.CONFIRMED)+1;
	        		  countReservationsStatus.put(ReservationStatus.CONFIRMED, statusCount);
	        }
	        else if (reservation.getReservationStatus() == ReservationStatus.ACTIVE &&
	                ((reservation.getStartDate().isEqual(startDate) || reservation.getStartDate().isAfter(startDate)) &&
	                 (reservation.getEndDate().isEqual(endDate) || reservation.getEndDate().isBefore(endDate)))) {
	        		  int statusCount  = countReservationsStatus.get(ReservationStatus.CONFIRMED)+1;
	        		  countReservationsStatus.put(ReservationStatus.CONFIRMED, statusCount);
	        }
	        
	        else if (reservation.getReservationStatus() == ReservationStatus.CANCELLED &&
	                ((reservation.getStartDate().isEqual(startDate) || reservation.getStartDate().isAfter(startDate)) &&
	                 (reservation.getEndDate().isEqual(endDate) || reservation.getEndDate().isBefore(endDate)))) {
	        		  int statusCount  = countReservationsStatus.get(ReservationStatus.CANCELLED)+1;
	        		  countReservationsStatus.put(ReservationStatus.CANCELLED , statusCount);
	        }
	    }
	    
	    for (ReservationRequest reservationRequest : ReservationRequestList) {
	    	reservationRequestFound = true;
	    	 if (reservationRequest.getReservationStatus() == ReservationStatus.REJECTED &&
		                ((reservationRequest.getStartDate().isEqual(startDate) || reservationRequest.getStartDate().isAfter(startDate)) &&
		                 (reservationRequest.getEndDate().isEqual(endDate) || reservationRequest.getEndDate().isBefore(endDate)))) {
		        		  int statusCount  = countReservationsStatus.get(ReservationStatus.REJECTED)+1;
		        		  countReservationsStatus.put(ReservationStatus.REJECTED, statusCount);
		        }
	    }
	    
	    
	    
	    if (!reservationRequestFound && !reservationFound) {
	        JOptionPane.showMessageDialog(null, "Nema podataka o rezervacijama.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
	        return;
	    }
	    	    
	    String[] columnNames = { "Status", "Broj rezervacija" };
	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; 
	        }
	    };
	    
	    for (ReservationStatus reservationStatus : countReservationsStatus.keySet()) {
	        int statusCount = countReservationsStatus.get(reservationStatus);
	        Object[] row = { reservationStatus, statusCount};
	        tableModel.addRow(row);
	    }

	     JTable table = new JTable(tableModel);
	     JScrollPane scrollPane = new JScrollPane(table);

	     contentPane.removeAll();
	     contentPane.add(scrollPane, BorderLayout.CENTER);
	     contentPane.revalidate();
	     contentPane.repaint();
	    
	}

	private void displayAllEmployees() {
        Map<String, Employee> employeeMap = hotelManager.getEmployees().get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        String[] columnNames = {
            "Ime", "Prezime", "Pol", "Datum rođenja",
            "Telefon", "Adresa", "Korisničko ime", "Lozinka", "Iskustvo",
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
            String formattedSalary = String.format("%.2f", employee.getSalary());
            Object[] row = {
                employee.getName(),
                employee.getSurname(),
                employee.getGender().toString(),
                employee.getDateOfBirth().format(formatter),
                phoneNumber,
                employee.getAdress(),
                employee.getUsername(),
                employee.getPassword(),
                employee.getExperience(),
                formattedSalary,
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
	        String genderStr = JOptionPane.showInputDialog("Unesite pol (M/Ž):");
	        Gender gender = genderStr.equalsIgnoreCase("M") ? Gender.MALE : Gender.FEMALE;
	        String dateOfBirthStr = JOptionPane.showInputDialog("Unesite datum rođenja (dd.MM.yyyy.):");
	        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	        String phoneNumber = JOptionPane.showInputDialog("Unesite broj telefona:");
	        String address = JOptionPane.showInputDialog("Unesite adresu:");
	        String username = JOptionPane.showInputDialog("Unesite korisničko ime:");
	        String password = JOptionPane.showInputDialog("Unesite lozinku:");
	        String experienceStr = JOptionPane.showInputDialog("Unesite godine iskustva:");
	        int experience = Integer.parseInt(experienceStr);
	        String[] qualificationLevelsArray = {"BEGINNER", "INTERMEDIATE", "ADVANCED", "EXPERT",
	                "MASTER", "SPECIALIST", "NO_QUALIFICATION"};
	        String newQualificationLevelStr = (String) JOptionPane.showInputDialog(null,
	                "Unesite ili izaberite novi nivo kvalifikacije:",
	                "Nivo kvalifikacije",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                qualificationLevelsArray,
	                qualificationLevelsArray[0]);

	        if (hotelManager.getEmployees().FindById(username) != null) {
	            JOptionPane.showMessageDialog(null, "Korisničko ime već postoji u sistemu.");
	            return;
	        }

	        if (newQualificationLevelStr != null) {
	            QualificationLevel qualificationLevel = QualificationLevel.valueOf(newQualificationLevelStr.toUpperCase());

	            if (receptionistButton.isSelected()) {
	                Double salary = calculateReceptionistSalary(experience, newQualificationLevelStr);
	                Receptionist newReceptionist = new Receptionist(name, surname, gender, dateOfBirth, phoneNumber, address, username, password, experience, salary, qualificationLevel);
	                hotelManager.addReceptionist(newReceptionist);
	                hotelController.addReceptionist(newReceptionist);
	            } else if (housekeeperButton.isSelected()) {
	                Double salary = calculateHousekeeperSalary(experience, newQualificationLevelStr);
	                Housekeeper newHousekeeper = new Housekeeper(name, surname, gender, dateOfBirth, phoneNumber, address, username, password, experience, salary, qualificationLevel);
	                hotelManager.addHousekeeper(newHousekeeper);
	                hotelController.addHousekeeper(newHousekeeper);
	            }

	            JOptionPane.showMessageDialog(null, "Novi zaposleni je uspešno dodat.");
	            displayAllEmployees();
	        }
	    }
	}
 
    private Double calculateHousekeeperSalary(int experience, String qualificationLevelStr) {
		double baseSalary = 500;
		double experienceCoefficient = 0.03;
		double qualificationCoefficient = getQualificationCoefficient(qualificationLevelStr);
        return baseSalary * (1 + experienceCoefficient * experience) * qualificationCoefficient;
	}
    
	private Double calculateReceptionistSalary(int experience, String qualificationLevelStr) {
		double baseSalary = 700;
		double experienceCoefficient = 0.03;
		double qualificationCoefficient = getQualificationCoefficient(qualificationLevelStr);
        return baseSalary * (1 + experienceCoefficient * experience) * qualificationCoefficient;
    }
    
    private static double getQualificationCoefficient(String qualification) {
        switch (qualification.toLowerCase()) {
            case "no qualification":
                return 1.0;
            case "beginner":
                return 1.1;
            case "intermediate":
                return 1.2;
            case "advanced":
                return 1.3;
            case "expert":
                return 1.5;
            case "master":
                return 1.7;
            case "specialist":
                return 2.0;
            default:
                throw new IllegalArgumentException("Unknown qualification: " + qualification);
        }
    }

    protected void deleteEmployee() {
        String username = JOptionPane.showInputDialog("Unesite korisničko ime zaposlenog koga želite da uklonite");
        Employee employeeToDelete = hotelManager.getEmployees().FindById(username);
        
        if (employeeToDelete != null) {
            int confirmation = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete zaposlenog sa korisničkim imenom: " + username + "?", "Potvrda brisanja", JOptionPane.YES_NO_OPTION);
            
            if (confirmation == JOptionPane.YES_OPTION) {
                String userType = hotelManager.getUserType(employeeToDelete.getUsername());
                
                if (userType != null) {
                    if (userType.equals("receptionist")) {
                        hotelController.deleteReceptionist((Receptionist) employeeToDelete);
                    } else if (userType.equals("housekeeper")) {
                        hotelController.deleteHousekeeper((Housekeeper) employeeToDelete);
                    }
                    hotelManager.deleteEmployee(employeeToDelete);
                    JOptionPane.showMessageDialog(null, "Informacije o zaposlenom su uspešno obrisane.");
                    displayAllEmployees();
                } else {
                    JOptionPane.showMessageDialog(null, "Nije moguće odrediti tip korisnika.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Brisanje zaposlenog je otkazano.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nije pronađen zaposleni sa unetim korisničkim imenom.");
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
        String genderStr = JOptionPane.showInputDialog("Unesite pol (M/Ž):");
        Gender gender = genderStr.equalsIgnoreCase("M") ? Gender.MALE : Gender.FEMALE;
        String dateOfBirthStr = JOptionPane.showInputDialog("Unesite datum rođenja (dd.MM.yyyy.):");
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        String phoneNumber = JOptionPane.showInputDialog("Unesite broj telefona:");
        String address = JOptionPane.showInputDialog("Unesite adresu:");
        String username = JOptionPane.showInputDialog("Unesite korisničko ime:");
        String password = JOptionPane.showInputDialog("Unesite lozinku:");

        
        if (hotelManager.getGuests().FindById(username) != null) {
            JOptionPane.showMessageDialog(null, "Korisničko ime već postoji. Molimo vas da izaberete drugo korisničko ime.");
            return;
        }

        Guest newGuest = new Guest(name, surname, gender, dateOfBirth, phoneNumber, address, username, password);
        hotelManager.addGuest(newGuest);
        hotelController.addGuest(newGuest);
        JOptionPane.showMessageDialog(null, "Gost je uspešno dodat.");
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
            int confirm = JOptionPane.showConfirmDialog(null, 
                    "Da li ste sigurni da želite da uklonite ovog gosta: " + username + "?", 
                    "Potvrda brisanja", 
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                hotelController.deleteGuest(guestToDelete);
                hotelManager.deleteGuest(guestToDelete);
                JOptionPane.showMessageDialog(null, "Gost je uspešno obrisan");
                displayAllGuests();
            } else {
                JOptionPane.showMessageDialog(null, "Brisanje gosta je otkazano.");
            }
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
            "Broj Sobe", "Tip Sobe", "Status Sobe", "Atributi"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Room room : roomMap.values()) {
            String attributes = String.join("; ", room.getRoomAttributes());
            Object[] row = {
                room.getRoomNumber(),
                room.getRoomType(),
                room.getRoomStatus(),
                attributes
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
        if (hotelManager.getRooms().FindById(roomNumber) != null) {
            JOptionPane.showMessageDialog(null, "Soba sa brojem " + roomNumber + " već postoji.");
            return;
        }

        String[] roomTypesArray = {"TWIN", "DOUBLE", "SINGLE", "TRIPLE"};
        String roomTypeString = (String) JOptionPane.showInputDialog(null,
                "Unesite ili izaberite tip sobe:",
                "Tip sobe",
                JOptionPane.QUESTION_MESSAGE,
                null,
                roomTypesArray,
                roomTypesArray[0]);
        
        String[] roomStatusesArray = {"OCCUPIED", "VACANT", "CLEANING"};
        String roomStatusString = (String) JOptionPane.showInputDialog(null,
                "Unesite ili izaberite status sobe:",
                "Status sobe",
                JOptionPane.QUESTION_MESSAGE,
                null,
                roomStatusesArray,
                roomStatusesArray[0]);

        if (roomTypeString != null && roomStatusString != null) {
            RoomType roomType = RoomType.valueOf(roomTypeString.toUpperCase());
            RoomStatus roomStatus = RoomStatus.valueOf(roomStatusString.toUpperCase());
            JCheckBox airConditioning = new JCheckBox("Klima uređaj");
            JCheckBox tv = new JCheckBox("TV");
            JCheckBox balcony = new JCheckBox("Balkon");
            JCheckBox nonSmoking = new JCheckBox("Ne-pušačka");
            JCheckBox smoking = new JCheckBox("Pušačka");
            JCheckBox safe = new JCheckBox("Sef");
            JCheckBox miniBar = new JCheckBox("Mini-bar");

            JPanel attributePanel = new JPanel();
            attributePanel.setLayout(new BoxLayout(attributePanel, BoxLayout.Y_AXIS));
            attributePanel.add(new JLabel("Izaberite dodatne atribute sobe:"));
            attributePanel.add(airConditioning);
            attributePanel.add(tv);
            attributePanel.add(balcony);
            attributePanel.add(nonSmoking);
            attributePanel.add(smoking);
            attributePanel.add(safe);
            attributePanel.add(miniBar);

            int result = JOptionPane.showConfirmDialog(null, attributePanel, 
                     "Dodatni atributi", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                List<String> roomAttributes = new ArrayList<>();
                if (airConditioning.isSelected()) roomAttributes.add("Klima uređaj");
                if (tv.isSelected()) roomAttributes.add("TV");
                if (balcony.isSelected()) roomAttributes.add("Balkon");
                if (nonSmoking.isSelected()) roomAttributes.add("Ne-pušačka");
                if (smoking.isSelected()) roomAttributes.add("Pušačka");
                if (safe.isSelected()) roomAttributes.add("Sef");
                if (miniBar.isSelected()) roomAttributes.add("Mini-bar");

                Room newRoom = new Room(roomType, roomNumber, roomStatus);
                newRoom.setRoomAttributes(roomAttributes);
                hotelManager.addRoom(newRoom);
                hotelController.addRoom(newRoom);
                JOptionPane.showMessageDialog(null, "Soba je uspešno dodata.");
                showRooms();
            } else {
                JOptionPane.showMessageDialog(null, "Dodavanje sobe je otkazano.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Dodavanje sobe je otkazano.");
        }
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
            int confirmation = JOptionPane.showConfirmDialog(null,
                    "Da li ste sigurni da želite da obrišete sobu sa brojem: " + number + "?",
                    "Potvrda brisanja",
                    JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                hotelController.deleteRoom(roomToDelete);
                hotelManager.deleteRoom(roomToDelete);
                JOptionPane.showMessageDialog(null, "Soba je uspešno obrisana");
                showRooms();
            } else {
                JOptionPane.showMessageDialog(null, "Brisanje sobe je otkazano.");
            }
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
        if (hotelManager.getAdditionalServices().FindById(name) != null) {
            JOptionPane.showMessageDialog(null, "Usluga sa imenom \"" + name + "\" već postoji.");
            return;
        }
        try {
            double servicePrice = Double.parseDouble(price);
            AdditionalService newAdditionalService = new AdditionalService(name, servicePrice);
            hotelManager.addAdditionalService(newAdditionalService);
            hotelController.addAdditionalService(newAdditionalService);
            JOptionPane.showMessageDialog(null, "Usluga je uspešno dodata.");
            showAdditionalServices();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cena usluge nije validna. Molimo unesite numeričku vrednost.");
        }
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
            int confirmation = JOptionPane.showConfirmDialog(null,
                    "Da li ste sigurni da želite da obrišete uslugu \"" + name + "\"?",
                    "Potvrda brisanja",
                    JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                hotelController.deleteAdditionalService(serviceToDelete);
                hotelManager.deleteAdditionalService(serviceToDelete);
                JOptionPane.showMessageDialog(null, "Usluga je uspešno obrisana");
                showAdditionalServices();
            } else {
                JOptionPane.showMessageDialog(null, "Brisanje usluge je otkazano.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nije pronađena usluga sa unetim imenom.");
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

	        if (hotelManager.getPriceListByDates(startDate, endDate) != null) {
	            JOptionPane.showMessageDialog(null, "Već postoji cenovnik koji pokriva unete datume.");
	            return;
	        }

	        PriceList priceList = new PriceList(startDate, endDate);

	        String[] roomTypes = {"SINGLE", "DOUBLE", "TWIN", "TRIPLE"};
	        for (String type : roomTypes) {
	            String roomPriceStr = JOptionPane.showInputDialog("Unesite cenu za tip sobe " + type + ":");
	            double roomPrice = Double.parseDouble(roomPriceStr);
	            RoomType roomType = RoomType.valueOf(type);
	            priceList.addRoomPrice(roomType, roomPrice);
	        }

	        Map<String, AdditionalService> allServices = hotelManager.getAdditionalServices().get();
	        for (AdditionalService service : allServices.values()) {
	            String servicePriceStr = JOptionPane.showInputDialog("Unesite cenu za uslugu " + service.getName() + ":");
	            double servicePrice = Double.parseDouble(servicePriceStr);
	            priceList.addAdditionalServicePrice(service, servicePrice);
	        }

	        hotelManager.addPriceList(priceList);
	        hotelController.addPriceList(priceList);
	        JOptionPane.showMessageDialog(null, "Cenovnik je uspešno dodat.");
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom kreiranja cenovnika.");
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
	        int option = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da želite da obrišete ovaj cenovnik?",
	                "Brisanje cenovnika", JOptionPane.YES_NO_OPTION);
	        if (option == JOptionPane.YES_OPTION) {
	            hotelController.deletePriceList(priceList);
	            hotelManager.deletePriceList(priceList);
	            JOptionPane.showMessageDialog(null, "Cenovnik je uspešno obrisan");
	            showAdditionalServices();
	        } else {
	            JOptionPane.showMessageDialog(null, "Brisanje cenovnika je otkazano.");
	        }
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

	protected void deleteReservation() {
		String startDateStr = JOptionPane.showInputDialog("Unesite datum početka rezervacije(dd.MM.yyyy.):");
		LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		String endDateStr = JOptionPane.showInputDialog("Unesite datum kraja rezervacije(dd.MM.yyyy.):");
		LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		String roomStr = JOptionPane.showInputDialog("Unesite broj sobe:");
		String id = startDate.toString() + "_" + endDate.toString() + "_" + roomStr;
		System.out.println(id);
		Reservation reservation = hotelManager.getReservations().FindById(id);

		if (reservation != null) {
		    int response = JOptionPane.showConfirmDialog(null, "Jeste li sigurni da želite obrisati rezervaciju?", "Potvrda brisanja", JOptionPane.YES_NO_OPTION);
		    if (response == JOptionPane.YES_OPTION) {
		        hotelController.deleteReservation(reservation);
		        hotelManager.deleteReservation(reservation);
		        JOptionPane.showMessageDialog(null, "Rezervacija je uspešno obrisana");
		    }
		} else {
		    JOptionPane.showMessageDialog(null, "Nije pronađena ni jedna rezervacija sa unetim podacima.");
		}
		showReservations();
	}

	protected void updateReservation() {
	    try {
	        String startDateStr = JOptionPane.showInputDialog("Unesite datum početka rezervacije (dd.MM.yyyy.):");
	        LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	        String endDateStr = JOptionPane.showInputDialog("Unesite datum kraja rezervacije (dd.MM.yyyy.):");
	        LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	        String roomStr = JOptionPane.showInputDialog("Unesite broj sobe:");
	        
	        String id = startDate.toString() + "_" + endDate.toString() + "_" + roomStr;
	        Reservation reservation = hotelManager.getReservations().FindById(id);
	        System.out.println(id);
	        if (reservation != null) {
	            JOptionPane.showMessageDialog(null, "Trenutne informacije o rezervaciji:\n" + reservation.toString());

	            String newStartDateStr = JOptionPane.showInputDialog("Unesite novi datum početka rezervacije (ostavite prazno ako ne želite da menjate):");
	            if (!newStartDateStr.isEmpty()) {
	                LocalDate newStartDate = LocalDate.parse(newStartDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	                reservation.setStartDate(newStartDate);
	            }

	            String newEndDateStr = JOptionPane.showInputDialog("Unesite novi datum kraja rezervacije (ostavite prazno ako ne želite da menjate):");
	            if (!newEndDateStr.isEmpty()) {
	                LocalDate newEndDate = LocalDate.parse(newEndDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	                reservation.setEndDate(newEndDate);
	            }

	            String newNumberOfGuestsStr = JOptionPane.showInputDialog("Unesite novi broj gostiju (ostavite prazno ako ne želite da menjate):");
	            if (!newNumberOfGuestsStr.isEmpty()) {
	                int newNumberOfGuests = Integer.parseInt(newNumberOfGuestsStr);
	                reservation.setNumberOfGuests(newNumberOfGuests);
	            }

	            int modifyServices = JOptionPane.YES_OPTION;
	            while (modifyServices == JOptionPane.YES_OPTION) {
	                String serviceName = JOptionPane.showInputDialog("Unesite naziv dodatne usluge koju želite da uklonite (ostavite prazno ako ne želite ništa da uklonite):");
	                if (!serviceName.isEmpty()) {
	                    AdditionalService service = hotelManager.getAdditionalServices().FindById(serviceName.trim());
	                    if (service != null) {
	                        reservation.removeAdditionalService(service);
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Dodatna usluga sa navedenim nazivom nije pronađena.");
	                    }
	                }
	                modifyServices = JOptionPane.showConfirmDialog(null, "Želite li da uklonite još neku dodatnu uslugu?", "Uklanjanje dodatnih usluga", JOptionPane.YES_NO_OPTION);
	            }
	            hotelController.updateReservation(startDate, endDate, roomStr, reservation);
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Došlo je do greške prilikom ažuriranja rezervacije. Proverite unete podatke i pokušajte ponovo.");
	        e.printStackTrace();
	    }
	    showReservations();
	}

	private void addReservation() {
	    try {
	        String startDateStr = JOptionPane.showInputDialog("Unesite datum početka rezervacije (dd.MM.yyyy.):");
	        LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	        LocalDate creationDate = LocalDate.now();
	        String endDateStr = JOptionPane.showInputDialog("Unesite datum kraja rezervacije (dd.MM.yyyy.):");
	        LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	        String username = JOptionPane.showInputDialog("Unesite korisničko ime korisnika");
	        Guest guest = hotelManager.getGuests().FindById(username);
	        if (guest == null) {
	            JOptionPane.showMessageDialog(null, "Ne postoji gost sa tim korisničkim imenom.", "Greška", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	        
	        JCheckBox airConditioning = new JCheckBox("Klima uređaj");
	        JCheckBox tv = new JCheckBox("TV");
	        JCheckBox balcony = new JCheckBox("Balkon");
	        JCheckBox nonSmoking = new JCheckBox("Ne-pušačka soba");
	        JCheckBox smoking = new JCheckBox("Pušačka soba");
	        JCheckBox safe = new JCheckBox("Sef");
	        JCheckBox miniBar = new JCheckBox("Mini-bar");

	        JPanel attributePanel = new JPanel();
	        attributePanel.setLayout(new BoxLayout(attributePanel, BoxLayout.Y_AXIS));
	        attributePanel.add(new JLabel("Izaberite dodatne atribute sobe:"));
	        attributePanel.add(airConditioning);
	        attributePanel.add(tv);
	        attributePanel.add(balcony);
	        attributePanel.add(nonSmoking);
	        attributePanel.add(smoking);
	        attributePanel.add(safe);
	        attributePanel.add(miniBar);

	        int result = JOptionPane.showConfirmDialog(null, attributePanel, 
	                "Dodatni atributi", JOptionPane.OK_CANCEL_OPTION);
	        if (result != JOptionPane.OK_OPTION) {
	            return;
	        }

	        List<String> roomAttributes = new ArrayList<>();
	        if (airConditioning.isSelected()) roomAttributes.add("Klima uređaj");
	        if (tv.isSelected()) roomAttributes.add("TV");
	        if (balcony.isSelected()) roomAttributes.add("Balkon");
	        if (nonSmoking.isSelected()) roomAttributes.add("Ne-pušačka");
	        if (smoking.isSelected()) roomAttributes.add("Pušačka");
	        if (safe.isSelected()) roomAttributes.add("Sef");
	        if (miniBar.isSelected()) roomAttributes.add("Mini-bar");

	        String[] roomTypesArray = {"TWIN", "DOUBLE", "SINGLE", "TRIPLE"};
	        String roomTypeString = (String) JOptionPane.showInputDialog(null,
	                "Unesite ili izaberite tip sobe:",
	                "Tip sobe",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                roomTypesArray,
	                roomTypesArray[0]);

	        RoomType roomType = RoomType.valueOf(roomTypeString.trim().toUpperCase());

	        List<Room> availableRooms = hotelManager.getAvailableRooms(roomType, startDate, endDate, roomAttributes);
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

	                Reservation reservation = new Reservation(roomType, numberOfGuests, startDate, endDate, selectedRoom, guest, creationDate);
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
	                LocalDate today = LocalDate.now();
	                Revenue revenue = new Revenue(guest,roomType,today,price);
	    	        hotelManager.addRevenue(revenue);
	    	        hotelController.addRevenue(revenue);
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
	
	private double calculateReservationPrice(Reservation reservation) {
	    double totalPrice = 0;
	    LocalDate date = reservation.getStartDate();

	    while (!date.isAfter(reservation.getEndDate())) {
	        PriceList priceListForDate = hotelManager.getPriceListByDate(date);
	        
	        if (priceListForDate != null) {
	            Double roomPrice = priceListForDate.findRoomPrice(reservation.getRoomType());
	            if (roomPrice != null) {
	                totalPrice += roomPrice;
	            } else {
	                System.out.println("Room price not found for date: " + date);
	            }

	            List<AdditionalService> additionalServices = reservation.getAdditionalService();
	            for (AdditionalService service : additionalServices) {
	                Double servicePrice = priceListForDate.findAdditionalServicePrice(service.getName());
	                if (servicePrice != null) {
	                    totalPrice += servicePrice;
	                } else {
	                    System.out.println("Service price not found for " + service.getName() + " on date: " + date);
	                }
	            }
	        } else {
	            System.out.println("Price list not found for date: " + date);
	        }
	        date = date.plusDays(1);
	    }
	    return totalPrice;
	}
	
	protected void showRoomOccupancy() {
	    List<Room> filteredRooms = new ArrayList<>();
	    Map<String, Room> rooms = hotelManager.getRooms().get();
	    for (Room room : rooms.values()) {
	    	filteredRooms.add(room);
	    }

	    if (filteredRooms.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Trenutno nema zauzetih soba", "Poruka", JOptionPane.INFORMATION_MESSAGE);
	        return;
	    }

	    String[] columnNames = {
	        "Broj sobe", "Tip sobe"
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
	
	protected void numberOfNightsAndRoomIncome() {
	    String startDateStr = JOptionPane.showInputDialog("Unesite datum početka analize (dd.MM.yyyy.):");
	    if (startDateStr == null || startDateStr.trim().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Datum nije unesen.");
	        return;
	    }
	    LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	    String endDateStr = JOptionPane.showInputDialog("Unesite datum kraja analize (dd.MM.yyyy.):");
	    if (endDateStr == null || endDateStr.trim().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Datum nije unesen.");
	        return;
	    }
	    LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy."));

	    if (startDate.isAfter(endDate)) {
	        JOptionPane.showMessageDialog(contentPane, "Početni datum mora biti pre krajnjeg datuma.", "Greška", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    Map<String, Room> rooms = hotelManager.getRooms().get();
	    Map<String, Reservation> reservations = hotelManager.getReservations().get();
	    if (rooms.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Trenutno nema zauzetih soba", "Poruka", JOptionPane.INFORMATION_MESSAGE);
	        return;
	    }

	    String[] columnNames = {
	        "Broj sobe", "Tip sobe", "Broj noćenja", "Prihodi"
	    };

	    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    for (Room room : rooms.values()) {
	        int roomNumberOfNights = 0;
	        int roomIncome = 0;
	        for (Reservation reservation : reservations.values()) {
	            if (reservation.getRoom().getRoomNumber().equals(room.getRoomNumber())
	                    && (reservation.getStartDate().isEqual(startDate) || reservation.getStartDate().isAfter(startDate))
	                    && (reservation.getEndDate().isEqual(endDate) || reservation.getEndDate().isBefore(endDate))) {
	                int daysBetween = Period.between(reservation.getEndDate(), reservation.getStartDate()).getDays();
	                roomNumberOfNights += daysBetween;
	                roomIncome += reservation.getPrice();
	            }
	        }
	        Object[] row = {
	            room.getRoomNumber(),
	            room.getRoomType(),
	            roomNumberOfNights,
	            roomIncome
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
	

    protected void reservationStatusChart() {
    	 Map<ReservationStatus, Integer> statusCounts = new HashMap<>();
         for (ReservationStatus status : ReservationStatus.values()) {
             statusCounts.put(status, 0);
         }

         LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);

         for (Reservation reservation : hotelManager.getReservations().get().values()) {
             if (reservation.getCreationDate().isAfter(thirtyDaysAgo)) {
                 ReservationStatus status = reservation.getReservationStatus();
                 statusCounts.put(status, statusCounts.get(status) + 1);
             }
         }

         for (ReservationRequest reservationRequest : hotelManager.getReservationRequests().getReservationRequests()) {
             if (reservationRequest.getCreationDate().isAfter(thirtyDaysAgo)) {
                 ReservationStatus status = reservationRequest.getReservationStatus();
                 statusCounts.put(status, statusCounts.get(status) + 1);
             }
         }

         PieChart chart = new PieChartBuilder().width(800).height(600).title("Status rezervacija u prethodnih 30 dana").build();

         chart.getStyler().setLegendVisible(true);
         chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

         for (Map.Entry<ReservationStatus, Integer> entry : statusCounts.entrySet()) {
             if (entry.getValue() > 0) {
                 chart.addSeries(entry.getKey().toString(), entry.getValue());
             }
         }

         XChartPanel<PieChart> chartPanel = new XChartPanel<>(chart);

         JFrame frame = new JFrame("Reservation Status Chart");
         frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         frame.setSize(800, 600);
         frame.setLocationRelativeTo(null);  
         frame.getContentPane().add(chartPanel);
         frame.setVisible(true);
     }
    

	protected void roomsPerHousekeeperChart() {
		Map<String, RoomCleaningRecord> roomCleaningRecords = hotelManager.getRoomCleaningRecordManager().get();
		Map<String,Housekeeper> housekeepers = hotelManager.getHousekeepers().get();
		Map<String, Integer> cleaningCounts = new HashMap<>();
		LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
		for(Housekeeper housekeeper: housekeepers.values()) {
			cleaningCounts.put(housekeeper.getUsername(), 0);
		}
		
		for(RoomCleaningRecord roomCleaningRecord: roomCleaningRecords.values()) {
			if(roomCleaningRecord.getDate().isAfter(thirtyDaysAgo)) {
				int value =  cleaningCounts.get(roomCleaningRecord.getHousekeeper().getUsername());
				cleaningCounts.put(roomCleaningRecord.getHousekeeper().getUsername(), value+1);
			}
			
		}
		
		PieChart chart = new PieChartBuilder().width(800).height(600).title("Opterećenje sobarica u prethodnih 30 dana").build();

        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

        for (Map.Entry<String, Integer> entry : cleaningCounts.entrySet()) {
            if (entry.getValue() > 0) {
                chart.addSeries(entry.getKey(), entry.getValue());
            }
        }

        XChartPanel<PieChart> chartPanel = new XChartPanel<>(chart);

        JFrame frame = new JFrame("Rooms per housekeeper Chart");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);  
        frame.getContentPane().add(chartPanel);
        frame.setVisible(true);
	}

	protected void incomeByRoomTypeChart() {
	    Map<Integer, String> monthLabels = new HashMap<>();
	    monthLabels.put(0, "Januar");
	    monthLabels.put(1, "Februar");
	    monthLabels.put(2, "Mart");
	    monthLabels.put(3, "April");
	    monthLabels.put(4, "Maj");
	    monthLabels.put(5, "Jun");
	    monthLabels.put(6, "Jul");
	    monthLabels.put(7, "Avgust");
	    monthLabels.put(8, "Septembar");
	    monthLabels.put(9, "Oktobar");
	    monthLabels.put(10, "Novembar");
	    monthLabels.put(11, "Decembar");

	    LocalDate lastYearStartDate = LocalDate.now().minusYears(1);

	    double[] xData = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}; 
	    double[] singleRoom = new double[12];
	    double[] doubleRoom = new double[12];
	    double[] twinRoom = new double[12];
	    double[] tripleRoom = new double[12];
	    double[] total = new double[12];

	    // Iterate through revenues
	    for (Revenue revenue : hotelManager.getRevenues().getRevenues()) {
	        LocalDate revenueDate = revenue.getDate();

	        if (revenueDate.isAfter(lastYearStartDate) || revenueDate.isEqual(lastYearStartDate)) {
	            int revenueMonth = revenueDate.getMonthValue() - 1;
	            
	            switch (revenue.getRoomType()) {
	                case SINGLE:
	                    singleRoom[revenueMonth] += revenue.getAmount();
	                    break;
	                case DOUBLE:
	                    doubleRoom[revenueMonth] += revenue.getAmount();
	                    break;
	                case TWIN:
	                    twinRoom[revenueMonth] += revenue.getAmount();
	                    break;
	                case TRIPLE:
	                    tripleRoom[revenueMonth] += revenue.getAmount();
	                    break;
	                default:
	                    break;
	            }
	            
	            total[revenueMonth] += revenue.getAmount();
	        }
	    }

	    XYChart chart = new XYChartBuilder().width(800).height(600).title("Prihodi po tipu sobe").xAxisTitle("Mesec").yAxisTitle("Prihodi").build();

	    chart.getStyler().setLegendVisible(true);
	    chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
	    chart.getStyler().setDecimalPattern("#");

	    chart.addSeries("SINGLE", xData, singleRoom);
	    chart.addSeries("TWIN", xData, twinRoom);
	    chart.addSeries("DOUBLE", xData, doubleRoom);
	    chart.addSeries("TRIPLE", xData, tripleRoom);
	    chart.addSeries("TOTAL", xData, total);


	    chart.setCustomXAxisTickLabelsFormatter(number -> {
	        int index = number.intValue();
	        return monthLabels.getOrDefault(index, "");
	    });

	    XChartPanel<XYChart> chartPanel = new XChartPanel<>(chart);

	    JFrame frame = new JFrame("Prihodi po tipu sobe");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setSize(800, 600);
	    frame.setLocationRelativeTo(null);
	    frame.getContentPane().add(chartPanel);
	    frame.setVisible(true);
	}
}