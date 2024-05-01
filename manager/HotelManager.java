package manager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import hotel.AdditionalService;
import hotel.Administrator;
import hotel.Guest;
import hotel.Housekeeper;
import hotel.PriceList;
import hotel.Receptionist;
import hotel.Reservation;
import hotel.ReservationRequest;
import hotel.ReservationStatus;
import hotel.Room;

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

    public ReservationManager getReservations() {
        return reservations;
    }

    public RoomManager getRooms() {
        return rooms;
    }

    public UserManager getUsers() {
        return users;
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

	public void updatePriceList(PriceList priceList, AdditionalService service, double price) {
		if(priceLists.FindById(priceList.generateID()).updateAdditionalServicePrice(service, price)) {
			System.out.printf("Uspešno ste promenili cenu usluge: %s u %.2f \n\n", service.getName(), price);
		}
		else {
			System.out.printf("Dodatna usluga %s ne postoji u sistemu.\n\n", service.getName());
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
		for (Reservation  reservation : reservations.get().values()) {
			if (!(endDate.isBefore(reservation.getStartDate()) || startDate.isAfter(reservation.getEndDate()))) {
                return false; 
            }
        }
		return true; 
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
}