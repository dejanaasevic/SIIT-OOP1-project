package entity;
import java.time.LocalDate;

import manager.HotelManager;


public class main {

	 public static void main(String[] args) {
		HotelManager hotelManager = new HotelManager();
		
		// Kreirati jednog administratora sistema (Pera Perić).
		Administrator administrator1 = new Administrator("Pera", "Perić", Gender.MALE,
				LocalDate.of(1989,12,13), "+381640012345", "Zlatne grede 10, Novi Sad", "pera_peric", "peraperic89");
		hotelManager.addAdministrator(administrator1);
		
		
		//Pera Perić dodaje recepcionere Miku Mikića i Nikolu Nikolića u sistem, kao i sobaricu Janu Janić.
		Receptionist receptionist1 = new Receptionist("Mika", "Mikić", Gender.MALE,
				LocalDate.of(1990, 5, 15), "+381641234567", "Njegoševa 12, Novi Sad",
				"mika_mikic", "mikamikic90", 10, 2500.0,QualificationLevel.ADVANCED);
		
		Receptionist receptionist2 = new Receptionist("Nikola", "Nikolić", Gender.MALE,
				LocalDate.of(1988, 9, 22), "+381641234567", "Stražilovska 8, Novi Sad",
				"nikola_nikolic", "nikolanikolic88", 8, 2300.0, QualificationLevel.INTERMEDIATE);

		hotelManager.addReceptionist(receptionist1);
		hotelManager.addReceptionist(receptionist2);
				
		Housekeeper housekeeper1 = new Housekeeper("Jana", "Janić", Gender.FEMALE,
		    LocalDate.of(1995, 8, 25), "+381641234567", "Branka Radičevića 15, Novi Sad",
		    "jana_janic", "janajanic95", 1, 1000.0, QualificationLevel.BEGINNER);

		hotelManager.addHousekeeper(housekeeper1);
		
		//Prikazati pregled svih zaposlenih.
		hotelManager.displayAllEmployee();
		
		// Administrator uklanja Nikolu Nikolića iz sistema jer nije više zaposlen u hotelu.
		hotelManager.removeReceptionist(receptionist2);

		//Mika dodaje nove goste Milicu Milić i Anu Anić.
		Guest guest1 = new Guest("Milica", "Milić", Gender.FEMALE,
		        LocalDate.of(1990, 7, 10), "+381641234567", "Kneza Mihaila 1, Beograd",
		        "milica.milic@gmail.com", "123456789");

		Guest guest2 = new Guest("Ana", "Anić", Gender.FEMALE,
		        LocalDate.of(1995, 4, 20), "+381641234567", "Terazije 10, Beograd",
		        "ana.anic@gmail.com", "987654321");

		hotelManager.addGuest(guest1);
		hotelManager.addGuest(guest2);
		
		
		// Dodati nekoliko soba u sistem, tako da postoji bar jedna jednokrevetna (1),
		//dvokrevetna (2), dvokrevetna (1+1) i trokrevetna (2+1) soba.
		Room room1 = new Room(RoomType.SINGLE, "101", RoomStatus.VACANT);
		Room room2 = new Room(RoomType.DOUBLE, "201", RoomStatus.VACANT);
		Room room3 = new Room(RoomType.TWIN, "301", RoomStatus.VACANT);
		Room room4 = new Room(RoomType.TRIPLE, "401", RoomStatus.VACANT);

		hotelManager.addRoom(room1);
		hotelManager.addRoom(room2);
		hotelManager.addRoom(room3);
		hotelManager.addRoom(room4);
		
		
		// Jedna dvokrevetna soba (2) postaje trokrevetna (2+1).
		room3.setRoomType(RoomType.TRIPLE);
		hotelManager.updateRoom(room3);

		//Kreirati dodatne usluge: doručak, ručak, večera, bazen, spa centar.
		AdditionalService breakfast = new AdditionalService("Doručak", 10.0);
		AdditionalService lunch = new AdditionalService("Ručak", 15.0);
		AdditionalService dinner = new AdditionalService("Večera", 20.0);
		AdditionalService pool = new AdditionalService("Bazen", 30.0);
		AdditionalService spa = new AdditionalService("Spa centar", 50.0);
		
		hotelManager.addAdditionalService(breakfast);
		hotelManager.addAdditionalService(lunch);
		hotelManager.addAdditionalService(dinner);
		hotelManager.addAdditionalService(pool);
		hotelManager.addAdditionalService(spa);

		//Uklanja se dodatna usluga za spa centar.
		hotelManager.removeAdditionalService(spa);
		
		//Cenovnik se kreira za sve tipove soba i dodatne usluge
		//koji važi u periodu od 01.01.2024. do 13.12.2024.
		PriceList priceList = new PriceList(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 13));
		
		priceList.addRoomPrice(RoomType.SINGLE, 100.0);
		priceList.addRoomPrice(RoomType.DOUBLE, 150.0);
		priceList.addRoomPrice(RoomType.TWIN, 150.0);
		priceList.addRoomPrice(RoomType.TRIPLE, 200.0);
		
		priceList.addAdditionalServicePrice(breakfast,7.0);
		priceList.addAdditionalServicePrice(lunch, 15.0);
		priceList.addAdditionalServicePrice(dinner, 12.0);
		priceList.addAdditionalServicePrice(pool, 10.0);
		hotelManager.addPriceList(priceList);
		
		//Cena doručka se menja u prethodno kreiranom cenovniku.
		hotelManager.updatePriceList(priceList, breakfast,8.0);
		
		//Prikazati koji tipovi sobe su dostupni u terminima od 01.08.2024. do 31.08.2024.
		hotelManager.displayAvailableRoomTypes(LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 31));
		
		//Za Milicu Milić se kreira rezervacija u periodu od 13.08.2024. do 23.8.2024
		//za trokrevetnu sobu (2+1). Prilikom rezervacije izabrati kao dodatnu 
		//uslugu doručak i večeru.

		// Kreiranje zahteva za rezervaciju
		ReservationRequest reservationRequest1 = new ReservationRequest(RoomType.TRIPLE, 3, LocalDate.of(2024, 8, 1),
				LocalDate.of(2024, 8, 23), guest1);
		reservationRequest1.addAdditionalService(breakfast);
		reservationRequest1.addAdditionalService(dinner);
		hotelManager.addReservationRequest(reservationRequest1);
		
		// Kreiranje rezervacije od zahteva i sobe koju je recepcioner dodelio zahtevu
		Reservation reservation1 = new Reservation(reservationRequest1, room4);
		hotelManager.addReservation(reservation1);
		
		//Prikazati koji tipovi sobe su dostupni u terminima od 01.06.2024. do 30.06.2024.
		hotelManager.displayAvailableRoomTypes(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30));
		
		//Kreirati rezervaciju za Anu Anić u periodu
		//od 6.6.2024. do 12.6.2024. za dvokrevetnu (1+1) sobu.
		ReservationRequest reservationRequest2 = new ReservationRequest(RoomType.TWIN, 2, LocalDate.of(2024, 6, 6),
				LocalDate.of(2024, 6, 12), guest2);
		hotelManager.addReservationRequest(reservationRequest2);
		
		// Kreiranje rezervacije od zahteva i sobe koju je recepcioner dodelio zahtevu
		Reservation reservation2 = new Reservation(reservationRequest2, room3);
		hotelManager.addReservation(reservation2);
		
	 
		//Prikazati sve rezervacije Milice Milić sa aktuelnim statusom.
		hotelManager.displayGuestReservations(guest1);
	 
	 }
}
