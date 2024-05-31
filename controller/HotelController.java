package controller;

import java.time.LocalDate;

import entity.AdditionalService;
import entity.Guest;
import entity.Housekeeper;
import entity.PriceList;
import entity.Receptionist;
import entity.Reservation;
import entity.ReservationRequest;
import entity.Room;
import manager.HotelManager;

public class HotelController {

	private HotelManager hotelManager;
	private UserController userController;
	private RoomController roomController;
	private AdditionalServiceController additionalServiceController;
	private PriceListController priceListController;
	private ReservationRequestController reservationRequestController;
	private ReservationController reservationController;

	public HotelController(HotelManager hotelManager) {
        this.hotelManager = hotelManager;
        this.userController = new UserController(hotelManager);
        this.roomController = new RoomController(hotelManager);
        this.additionalServiceController = new AdditionalServiceController(hotelManager);
        this.priceListController = new PriceListController(hotelManager);
        this.reservationRequestController = new ReservationRequestController(hotelManager);
        this.reservationController = new ReservationController(hotelManager);
        initialize();
    }

	private void initialize() {
		userController.readUsersFromCSV();
		roomController.readRoomDataFromCSV();
		additionalServiceController.readAdditionalServiceDataFromCSV();
		priceListController.readPriceListDataFromCSV();
		reservationRequestController.readReservationRequestsDataFromCSV();
		reservationController.readReservationDataFromCSV();
	}
	
	public void addHousekeeper(Housekeeper housekeeper) {
		if(userController.writeEmployeeToCSV(housekeeper)) {
			System.out.println("Podaci o sobarici su uspešno dodati u CSV fajl.");
		}
		else {
			System.out.println("Greška prilikom dodavanja podataka o sobarici u CSV fajl.");
		}	
	}
	public void addReceptionist(Receptionist receptionist) {
		if(userController.writeEmployeeToCSV(receptionist)) {
			System.out.println("Podaci o recepcioneru su uspešno dodati u CSV fajl.");
		}
		else {
			System.out.println("Greška prilikom dodavanja podataka o recepcioneru u CSV fajl.");
		}
		
	}
	
	public void addGuest(Guest guest) {
		if(userController.writeGuestToCSV(guest)) {
			System.out.println("Podaci o gostu su uspešno dodati u CSV fajl.");
		}
		else {
			System.out.println("Greška prilikom dodavanja podataka o gostu u CSV fajl.");
		}
	}
	
	public void addRoom(Room room) {
		if(roomController.writeRoomToCSV(room)) {
			System.out.println("Podaci o sobi su uspešno dodati u CSV fajl.");
		}
		else {
			System.out.println("Greška prilikom dodavanja podataka o sobi u CSV fajl.");
		}
	}
	
	public void addAdditionalService(AdditionalService additionalService) {
		if(additionalServiceController.writeAdditionalServiceToCSV(additionalService)) {
			System.out.println("Podaci o dodatnoj usluzi su uspešno dodati u CSV fajl.");
		}
		else {
			System.out.println("Greška prilikom dodavanja podataka o dodatnoj usluzi u CSV fajl.");
		}
	}
	public void addReservationRequest(ReservationRequest reservationRequest) {
		if(reservationRequestController.writeReservationRequestToCSV(reservationRequest)) {
			System.out.println("Podaci o zahtevu rezervacije su uspešno dodati u CSV fajl.");
		}
		else {
			System.out.println("Greška prilikom dodavanja podataka o zahtevu rezervacije u CSV fajl.");
		}	
	}
	
	public void addPriceList(PriceList priceList) {
		if(priceListController.writePiceListToCSV(priceList)) {
			System.out.println("Podaci o cenovniku su uspešno dodati u CSV fajl.");
		}
		else {
			System.out.println("Greška prilikom dodavanja podataka o cenovniku rezervacije u CSV fajl.");
		}	
	}
	public void addReservation(Reservation reservation) {
		if(reservationController.writePiceListToCSV(reservation)) {
			System.out.println("Podaci o rezervaciji su uspešno dodati u CSV fajl.");
		}
		else {
			System.out.println("Greška prilikom dodavanja podataka o rezervaciji rezervacije u CSV fajl.");
		}	
		
	}

	public void updateReceptionist(Receptionist receptionist) {
		if(userController.updateEmployeeInCSV(receptionist)) {
			System.out.println("Podaci o recepcioneru su uspešno ažurirani u CSV fajl.");
		}
		else {
			System.out.println("Greška prilikom ažuriranja podataka o recepcioneru u CSV fajlu.");
		}
	}

	public void updateHousekeeper(Housekeeper housekeeper) {
		if(userController.updateEmployeeInCSV(housekeeper)) {
			System.out.println("Podaci o sobarici su uspešno ažurirani u CSV fajlu.");
		}
		else {
			System.out.println("Greška prilikom ažuriranja podataka o sobarici u CSV fajlu.");
		}	
	}

	public void updateGuest(Guest guest) {
		if(userController.updateGuestInCSV(guest)) {
			System.out.println("Podaci o gostu su uspešno ažurirani u CSV fajlu.");
		}
		else {
			System.out.println("Greška prilikom ažuriranja podataka o gostu u CSV fajlu.");
		}	
	}
	
	public void updateRoom(Room room) {
		if(roomController.updateRoomInCSV(room)) {
			System.out.println("Podaci o sobi su uspešno ažurirani u CSV fajlu.");
		}
		else {
			System.out.println("Greška prilikom ažuriranja podataka o sobi u CSV fajlu.");
		}	
		
	}
	
	public void updateAdditionalService(AdditionalService additionalServices) {
		if(additionalServiceController.updateAdditionalServiceInCSV(additionalServices)) {
			System.out.println("Podaci o dodatnoji usluzi su uspešno ažurirani u CSV fajlu.");
		}
		else {
			System.out.println("Greška prilikom ažuriranja podataka o dodatnoj usluzi u CSV fajlu.");
		}	
	}
	
	public void updatePriceList(LocalDate startDate, LocalDate endDate, PriceList priceList) {
		if(priceListController.updatePriceListFromCSV(startDate,endDate, priceList)) {
			 System.out.println("Podaci o cenovniku su uspešno ažurirani iz CSV fajla.");
		}
		else {
			System.out.println("Greška prilikom ažurirani podataka o cenovniku iz CSV fajla.");
		}	
	}
	
	public void updateReservation(LocalDate startDate, LocalDate endDate,String roomNumber, Reservation reservation) {
		if(reservationController.updateReservationInCSV(startDate,endDate,roomNumber, reservation)) {
			 System.out.println("Podaci o cenovniku su uspešno ažurirani iz CSV fajla.");
		}
		else {
			System.out.println("Greška prilikom ažurirani podataka o cenovniku iz CSV fajla.");
		}	
		
	}
	

	
	public void deleteReceptionist(Receptionist receptionist) {
		if(userController.deleteEmployeeFromCSV(receptionist)) {
			System.out.println("Podaci o recepcioneru su uspešno obrisani iz CSV fajla.");
		}
		else {
            System.out.println("Greška prilikom brisanja podataka o recepcioneru iz CSV fajla.");
		}		
	}

	public void deleteHousekeeper(Housekeeper housekeeper) {
		if(userController.deleteEmployeeFromCSV(housekeeper)) {
			 System.out.println("Podaci o sobarici su uspešno obrisani iz CSV fajla.");
		}
		else {
			System.out.println("Greška prilikom brisanja podataka o sobarici iz CSV fajla.");
		}	
	}
	
	public void deleteGuest(Guest guest) {
		if(userController.deleteGuestFromCSV(guest)) {
			 System.out.println("Podaci o gostu su uspešno obrisani iz CSV fajla.");
		}
		else {
			System.out.println("Greška prilikom brisanja podataka o gostu iz CSV fajla.");
		}	
	}

	public void deleteRoom(Room room) {
		if(roomController.deleteRoomFromCSV(room)) {
			 System.out.println("Podaci o sobi su uspešno obrisani iz CSV fajla.");
		}
		else {
			System.out.println("Greška prilikom brisanja podataka o sobi iz CSV fajla.");
		}	
	}

	public void deleteAdditionalService(AdditionalService additionalService) {
		if(additionalServiceController.deleteAdditionalServiceFromCSV(additionalService)) {
			 System.out.println("Podaci o dodatnoj usluzi su uspešno obrisani iz CSV fajla.");
		}
		else {
			System.out.println("Greška prilikom brisanja podataka o dodatnoj usluzi iz CSV fajla.");
		}			
	}

	public void deletePriceList(PriceList priceList) {
		if(priceListController.deletePriceListFromCSV(priceList)) {
			 System.out.println("Podaci o cenovniku su uspešno obrisani iz CSV fajla.");
		}
		else {
			System.out.println("Greška prilikom brisanja podataka o cenovniku iz CSV fajla.");
		}	
		
	}
	public void deleteReservation(Reservation reservation) {
		if(reservationController.deleteReservationFromCSV(reservation)) {
			 System.out.println("Podaci o rezervaciji su uspešno obrisani iz CSV fajla.");
		}
		else {
			System.out.println("Greška prilikom brisanja podataka o rezervaciji iz CSV fajla.");
		}	
		
	}


	



	

	
}