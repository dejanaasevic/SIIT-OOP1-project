package manager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import entity.AdditionalService;
import entity.Administrator;
import entity.Employee;
import entity.Expense;
import entity.Guest;
import entity.Housekeeper;
import entity.PriceList;
import entity.Receptionist;
import entity.Reservation;
import entity.ReservationRequest;
import entity.ReservationStatus;
import entity.Revenue;
import entity.Room;
import entity.RoomCleaningRecord;
import entity.RoomType;

public class HotelManager {
    private AdministratorManager administrators;
    private EmployeeManager employees;
    private GuestManager guests;
    private HousekeeperManager housekeepers;
    private ReceptionistManager receptionists;
    private AdditionalServiceManager additionalServices;
    private PriceListManager priceLists;
    private ReservationManager reservations;
    private RoomManager rooms;
    private UserManager users;
    private ReservationRequestManager reservationRequests;
    private RoomCleaningRecordManager roomCleaningRecords;
    private ExpenseManager expenses;
    private RevenueManager revenues;

    public HotelManager() {
    	this.administrators = new AdministratorManager();
        this.employees = new EmployeeManager();
        this.guests = new GuestManager();
        this.housekeepers = new HousekeeperManager();
        this.receptionists = new ReceptionistManager();
        this.additionalServices = new AdditionalServiceManager();
        this.priceLists = new PriceListManager();
        this.reservations = new ReservationManager();
        this.rooms = new RoomManager();
        this.users = new UserManager();
        this.reservationRequests = new ReservationRequestManager();
        this.roomCleaningRecords = new RoomCleaningRecordManager();
        this.expenses = new ExpenseManager();
        this.revenues = new RevenueManager();
    
    }
    
    public ExpenseManager getExpenses() {
    	return expenses;
    }
    
    public RevenueManager getRevenues() {
    	return revenues;
    }

	public AdministratorManager getAdministrators() {
        return administrators;
    }

    public EmployeeManager getEmployees() {
        return employees;
    }

    public GuestManager getGuests() {
        return guests;
    }
    
    public HousekeeperManager getHousekeepers() {
        return housekeepers;
    }

    public ReceptionistManager getReceptionists() {
        return receptionists;
    }

    public AdditionalServiceManager getAdditionalServices() {
        return additionalServices;
    }

    public PriceListManager getPriceLists() {
        return priceLists;
    }
    
    public ReservationRequestManager getReservationRequests() {
    	return reservationRequests;
    }

    public ReservationManager getReservations() {
        return reservations;
    }

    public RoomManager getRooms() {
        return rooms;
    }

    public UserManager getUsers() {
        return users;
    }
    
    public RoomCleaningRecordManager getRoomCleaningRecordManager() {
        return roomCleaningRecords;
    }
    
	public String getUserType(String username) {
		if(administrators.isExists(username)) {
			return "administrator";
		}
		else if (guests.isExists(username)) {
			return "guest";
		}
		else if (housekeepers.isExists(username)) {
			return "housekeeper";
		}
		else if (receptionists.isExists(username)) {
			return "receptionist";
		}
		return null;
	}

	public boolean validPassword(String username, String password) {
		String userType = this.getUserType(username);
		if (userType.equals("administrator")) {
			Administrator administrator = administrators.FindById(username);
			if(administrator.getPassword().equals(password)){
				return true;
			}
			return false;
		}
		
		else if (userType.equals("housekeeper")) {
			Housekeeper housekeeper = housekeepers.FindById(username);
			if(housekeeper.getPassword().equals(password)) {
				return true;
			}
			return false;
		}
		
		else if (userType.equals("guest")) {
			Guest guest = guests.FindById(username);
			if(guest.getPassword().equals(password)) {
				return true;
			}
			return false;
		}
		
		else if (userType.equals("receptionist")) {
			Receptionist receptionist = receptionists.FindById(username);
			if(receptionist.getPassword().equals(password)) {
				return true;
			}
			return false;
		}
		
		return false;
	}
    
	public void addAdministrator(Administrator administrator) {
		if (administrators.add(administrator.getUsername(), administrator)){
			System.out.printf("Uspešno ste dodali administratora: %s %s\n\n",
					administrator.getName(),administrator.getSurname());
		}
		else {
			System.out.printf("Administrator %s %s već postoji u sistemu. \n\n",
					administrator.getName(),administrator.getName());			
		}
		
	}

	public void addReceptionist(Receptionist receptionist) {
		if (receptionists.add(receptionist.getUsername(), receptionist)
				&& employees.add(receptionist.getUsername(), receptionist)) {
			System.out.printf("Uspešno ste dodali recepcionera: %s %s\n\n",
					receptionist.getName(),receptionist.getSurname());
		}
		else {
			System.out.printf("Recepcioner %s %s već postoji u sistemu.\n\n",
					receptionist.getName(), receptionist.getName());
			
		}
			
	}
	public void addHousekeeper(Housekeeper housekeeper) {
		if(housekeepers.add(housekeeper.getUsername(), housekeeper) &&
		employees.add(housekeeper.getUsername(), housekeeper)) {
			System.out.printf("Uspešno ste dodali sobaricu: %s %s\n\n",
					housekeeper.getName(), housekeeper.getSurname());
			
		}
		else {
			System.out.printf("Sobarica %s %s već postoji u sistemu.\n\n"
					, housekeeper.getName(), housekeeper.getSurname());
		}
	}

	public void displayAllEmployee() {
		System.out.println("Prikaz svih zaposlenih \n");
		employees.displayAll();
	}

	public void removeReceptionist(Receptionist receptionist) {
		if(receptionists.remove(receptionist.getUsername()) 
		 && employees.remove(receptionist.getUsername())){
			System.out.printf("Uspešno ste uklonili recepcionera: %s %s.\n\n"
					, receptionist.getName(), receptionist.getSurname());
			
		}
		else {
			System.out.printf("Recepcionera %s %s ne postoji u sistemu. \n\n"
					, receptionist.getName(), receptionist.getSurname());
		}
	}

	public void addGuest(Guest guest) {
		if (guests.add(guest.getUsername(), guest)) {
			System.out.printf("Uspešno ste dodali gosta: %s %s \n\n", guest.getName(), guest.getSurname());
		}
		else {
			System.out.printf("Gost %s %s već postoji u sistemu. \n\n", guest.getName(), guest.getSurname());
		}
		
	}

	public void addRoom(Room room) {
		if(rooms.add(room.getRoomNumber(),room)) {
			System.out.printf("Uspešno ste dodali sobu %s \n\n",room.getRoomNumber());
		}
		else {
			System.out.printf("Soba %s već postoji.\n\n",room.getRoomNumber());
		}
	}

	public void updateRoom(Room updatedRoom) {
		if(rooms.update(updatedRoom.getRoomNumber(),updatedRoom)) {
			System.out.printf("Uspešno ste izmenili sobu %s \n\n",updatedRoom.getRoomNumber());
		}
		else {
			System.out.printf("Neuspešna izmena. Soba %s ne postoji.\n\n",updatedRoom.getRoomNumber());
		}
	}

	public void addAdditionalService(AdditionalService service) {
		if(additionalServices.add(service.getName(), service)) {
			System.out.printf("Uspešno ste dodali dodatnu uslugu: %s \n\n", service.getName());
		}
		else {
			System.out.printf("Dodatna usluga %s već postoji. \n\n", service.getName());
		}
	}

	public void removeAdditionalService(AdditionalService service) {
		if(additionalServices.remove(service.getName())){
			System.out.printf("Uspešno ste uklonili dodatnu uslugu: %s \n\n", service.getName());
		}
		else {
			System.out.printf("Dodatna usluga %s ne postoji u sistemu.\n\n", service.getName());
		}
	}

	public void addPriceList(PriceList priceList) {
		if(priceLists.add(priceList.generateID(), priceList)) {
			System.out.println("Uspešno ste dodali cenovnik.\n");
		}
		else {
			System.out.println("Cenovnik već postoji.\n");
		}
	}

	public void displayAvailableRoomTypes(LocalDate startDate, LocalDate endDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		System.out.println("Dostupni tipovi soba u periodu od "
							+ startDate.format(formatter) + "do " + endDate.format(formatter));
		 for(Room room: rooms.get().values()) {
			 if(isRoomAvailable(room, startDate, endDate)) {
				 System.out.println(room.getRoomType().getDescription());
			 }
		 }
		 System.out.println();
	}

	private boolean isRoomAvailable(Room room, LocalDate startDate, LocalDate endDate) {
	    for (Reservation reservation : reservations.get().values()) {
	        if (reservation.getRoom().equals(room)) {
	            if (!(endDate.isBefore(reservation.getStartDate()) || startDate.isAfter(reservation.getEndDate()))) {
	                return false; 
	            }
	        }
	    }
	    return true; 
	}
	
	public List<Room> getAvailableRooms(RoomType roomType, LocalDate startDate, LocalDate endDate, List<String> attributes) {
		List<Room> availableRooms = new ArrayList<>();
		for(Room room: rooms.get().values()) {
			if(isRoomAvailable(room, startDate, endDate) && room.getRoomType().equals(roomType)
					&&  room.getRoomAttributes().containsAll(attributes) ) {
				availableRooms.add(room);
			}
		}
		return availableRooms;
	}

	public void displayGuestReservations(Guest guest) {
		System.out.printf("Prikaz svih aktivnih rezervacija gosta: %s %s \n",
						   guest.getName(), guest.getSurname());
		boolean foundreservation = false;
		int numberOfReservation = 1;
		for(Reservation reservation: reservations.get().values()) {
			if(reservation.getGuest().getUsername() == guest.getUsername()
					&& reservation.getReservationStatus() == ReservationStatus.CONFIRMED) {
				System.out.printf("- REZERVACIJA %d - \n", numberOfReservation);
				System.out.println(reservation);
				foundreservation = true;
			}
		}
		if(!foundreservation) {
			System.out.println("Trenutno nema ni jedne potvrđene rezervacije.\n");
		}
	}
	
	public void addReservationRequest(ReservationRequest reservationRequest) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		String startDate = reservationRequest.getStartDate().format(formatter);
		String endDate = reservationRequest.getEndDate().format(formatter);
		reservationRequests.addReservationRequest(reservationRequest);
		System.out.printf("Uspešno ste dodali zahtev za rezervaciju: %s u periodu od %s do %s \n",
				reservationRequest.getRoomType().getDescription(), startDate, endDate);
	}
	
	public void addReservation(Reservation reservation) {
		if(reservations.add(reservation.getID(), reservation)) {
			System.out.println("Uspešno ste dodali rezervaciju.\n");
		}
		else {
			System.out.println("Rezervacija već postoji.\n");
		}
	}
	
	public void addRoomCleaningRecord(RoomCleaningRecord roomCleaningRecord) {
		if(roomCleaningRecords.add(roomCleaningRecord.getID(), roomCleaningRecord)) {
			System.out.println("Uspešno ste dodali RoomCleaningRecord.\n");
		}
		else {
			System.out.println("RoomCleaningRecord već postoji.\n");
		}
		
	}
	


	public void updateEmployee(Employee employeeToUpdate) {
	    String userType = getUserType(employeeToUpdate.getUsername());
	    
	    if (userType.equals("receptionist")) {
	    	Receptionist receptionist = (Receptionist)employeeToUpdate;
	        if (receptionists.update(receptionist.getUsername(), receptionist)) {
	            System.out.println("Podaci o recepcionistu su uspešno ažurirani.");
	        } else {
	            System.out.println("Greška prilikom ažuriranja podataka o recepcionistu.");
	        }
	    }
	    
	    if (userType.equals("housekeeper")) {
	    	Housekeeper housekeeper = (Housekeeper)employeeToUpdate;
	        if (housekeepers.update(housekeeper.getUsername(), housekeeper)) {
	            System.out.println("Podaci o sobarici su uspešno ažurirani.");
	        } else {
	            System.out.println("Greška prilikom ažuriranja podataka o sobarici.");
	        }
	    }
	}

	public void deleteEmployee(Employee employeeToDelete) {
	    String userType = getUserType(employeeToDelete.getUsername());
	    if (employees.remove(employeeToDelete.getUsername())) {
	    	System.out.println("Podaci o zaposlenom su uspešno obrisani.");
	    }
	    if (userType.equals("receptionist")) {
	    	Receptionist receptionist = (Receptionist)employeeToDelete;
	        if (receptionists.remove(receptionist.getUsername())) {
	            System.out.println("Podaci o recepcionistu su uspešno obrisani.");
	        } else {
	            System.out.println("Greška prilikom brisanja podataka o recepcionistu.");
	        }
	    }
	    
	    if (userType.equals("housekeeper")) {
	    	Housekeeper housekeeper = (Housekeeper)employeeToDelete;
	        if (housekeepers.remove(housekeeper.getUsername())) {
	            System.out.println("Podaci o sobarici su uspešno obrisani.");
	        } else {
	            System.out.println("Greška prilikom brisanja podataka o sobarici.");
	        }
	    }
		
	}

	public void deleteGuest(Guest guestToDelete) {
		if (guests.remove(guestToDelete.getUsername())) {
	    	System.out.println("Podaci o gostu su uspešno obrisani.");
	    }
		
	}

	public void deleteRoom(Room roomToDelete) {
		if (rooms.remove(roomToDelete.getRoomNumber())) {
	    	System.out.println("Podaci o sobi su uspešno obrisani.");
	    }
	}

	public void deleteAdditionalService(AdditionalService serviceToDelete) {
		if (additionalServices.remove(serviceToDelete.getName())) {
	    	System.out.println("Podaci o usluzi su uspešno obrisani.");
	    }
	}

	public void deletePriceList(PriceList priceList) {
		if (priceLists.remove(priceList.getId())) { // ovde je bilo generateID() AKO NE RADI !!!!
	    	System.out.println("Podaci o cenovniku su uspešno obrisani.");
	    }
	}
	
	public void deleteReservationRequest(ReservationRequest selectedRequest) {
		if (reservationRequests.removeReservationRequest(selectedRequest)){
	    	System.out.println("Podaci o zahtevu rezervacije su uspešno obrisani.");
	    }
	}
		

	public void deleteReservation(Reservation reservation) {
		if (reservations.remove(reservation.getID())) {
	    	System.out.println("Podaci o rezervaciji su uspešno obrisani.");
	    }
	}

	public List<String> getAvailableRoomTypes(LocalDate startDate, LocalDate endDate, List<String> roomAttributes) {
	    List<String> availableRoomTypes = new ArrayList<>();
	    for (Room room : rooms.get().values()) {
	        if (isRoomAvailable(room, startDate, endDate) && !availableRoomTypes.contains(room.getRoomType().toString()) &&
	        		hasAllRoomAttributest(room, roomAttributes)) {
	            availableRoomTypes.add(room.getRoomType().toString());
	        }
	    }
	    return availableRoomTypes;
	}

	private boolean hasAllRoomAttributest(Room room, List<String> roomAttributes) {
		for(String attribute :roomAttributes ) {
			if(!room.getRoomAttributes().contains(attribute)) {
				return false;
			}
		}
		return true;
	}

	public void addRoomCleaningRecord(String id, RoomCleaningRecord roomCleaningRecord) {
		if (roomCleaningRecords.add(roomCleaningRecord.getID(), roomCleaningRecord)) {
	    	System.out.println("Podaci o RoomCleaningRecord su uspešno dodati.");
	    }		
	}

	public PriceList getPriceListByDate(LocalDate date) {
	    Collection<PriceList> allPriceLists = priceLists.get().values();
	    
	    for (PriceList priceList : allPriceLists) {
	        LocalDate startDate = priceList.getStartDate();
	        LocalDate endDate = priceList.getEndDate();
	        

	        if ((startDate.isEqual(date) || startDate.isBefore(date)) && 
	            (endDate.isEqual(date) || endDate.isAfter(date))) {
	            return priceList;
	        }
	    }
	    return null;
	}


	public void updateEmployee(Employee employeeToUpdate, Employee employeeCopy) {
		String userType = getUserType(employeeCopy.getUsername());
	    if (userType.equals("receptionist")) {
	    	Receptionist receptionist = (Receptionist)employeeToUpdate;
	        if (receptionists.update(receptionist.getUsername(), receptionist)) {
	            System.out.println("Podaci o recepcionistu su uspešno ažurirani.");
	        } else {
	            System.out.println("Greška prilikom ažuriranja podataka o recepcionistu.");
	        }
	    }
	    
	    if (userType.equals("housekeeper")) {
	    	Housekeeper housekeeper = (Housekeeper)employeeToUpdate;
	        if (housekeepers.update(housekeeper.getUsername(), housekeeper)) {
	            System.out.println("Podaci o sobarici su uspešno ažurirani.");
	        } else {
	            System.out.println("Greška prilikom ažuriranja podataka o sobarici.");
	        }
	    }
	}

	public PriceList getPriceListByDates(LocalDate startDate, LocalDate endDate) {
	    for (PriceList priceList : priceLists.get().values()) {
	        LocalDate priceListStartDate = priceList.getStartDate();
	        LocalDate priceListEndDate = priceList.getEndDate();
	        if (!startDate.isAfter(priceListEndDate) && !endDate.isBefore(priceListStartDate)) {
	            return priceList;
	        }
	    }
	    return null; 
	}

	public void addRevenue(Revenue revenue) {
		revenues.addRevenue(revenue);
	}
		
	public void addExpense(Expense expense) {
		expenses.addExpense(expense);
	}
}