package tests;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.Administrator;
import entity.Gender;
import entity.Guest;
import entity.Housekeeper;
import entity.PriceList;
import entity.QualificationLevel;
import entity.Receptionist;
import entity.Reservation;
import entity.Room;
import entity.RoomStatus;
import entity.RoomType;
import manager.HotelManager;

import static org.junit.Assert.*;

public class HotelManagerTest {
    
    private HotelManager hotelManager;
    private Administrator admin;
    private Guest guest;
    private Receptionist receptionist;
    private Housekeeper housekeeper;

    @BeforeEach
    public void setUp() {
        hotelManager = new HotelManager();
     	guest = new Guest("Petar", "Petrović", Gender.MALE, LocalDate.of(1985, 5, 15),
    	        "+381640000001", "Nikola Tesla 1 - Beograd", "petar_p", "password123");
     	receptionist = new Receptionist("Ana", "Petrović", Gender.FEMALE, LocalDate.of(2000, 1, 1),
        		"+381640000000","Nikola Tesla 11 - Novi Sad", "ana_p", "ana123", 10, 1200,  QualificationLevel.ADVANCED);
     	housekeeper = new Housekeeper("Ana", "Petrović", Gender.FEMALE, LocalDate.of(2000, 1, 1),
        		"+381640000000","Nikola Tesla 11 - Novi Sad", "ana_petrovic", "ana123", 10, 1200,  QualificationLevel.ADVANCED);
     	admin = new Administrator("Petar", "Petrović", Gender.MALE, LocalDate.of(2000, 1, 1),
        		"+381640000000", "Nikola Tesla 11 - Novi Sad", "petar_petrovic", "petar123");

        
        hotelManager.addAdministrator(admin);
        hotelManager.addGuest(guest);
        hotelManager.addReceptionist(receptionist);
        hotelManager.addHousekeeper(housekeeper);
    }

    @Test
    public void testGetUserType_Administrator() {
        String userType = hotelManager.getUserType(admin.getUsername());
        assertEquals("administrator", userType);
    }

    @Test
    public void testGetUserType_Guest() {
        String userType = hotelManager.getUserType(guest.getUsername());
        assertEquals("guest", userType);
    }

    @Test
    public void testGetUserType_Receptionist() {
        String userType = hotelManager.getUserType(receptionist.getUsername());
        assertEquals("receptionist", userType);
    }

    @Test
    public void testGetUserType_Housekeeper() {
        String userType = hotelManager.getUserType(housekeeper.getUsername());
        assertEquals("housekeeper", userType);
    }

    @Test
    public void testGetUserType_NonExistentUser() {
        String userType = hotelManager.getUserType("nonExistentUsername");
        assertNull(userType);
    }
    
    @Test
    public void testValidPassword() {
        assertTrue(hotelManager.validPassword(admin.getUsername(), "petar123"));
        assertTrue(hotelManager.validPassword(guest.getUsername(), "password123"));
        assertTrue(hotelManager.validPassword(receptionist.getUsername(), "ana123"));
        assertTrue(hotelManager.validPassword(housekeeper.getUsername(), "ana123"));

        assertFalse(hotelManager.validPassword(admin.getUsername(), "wrongpassword"));
        assertFalse(hotelManager.validPassword(guest.getUsername(), "wrongpassword"));
        assertFalse(hotelManager.validPassword(receptionist.getUsername(), "wrongpassword"));
        assertFalse(hotelManager.validPassword(housekeeper.getUsername(), "wrongpassword"));
    }
    
    @Test
    public void testGetAvailableRooms() {
        Room room1 = new Room(RoomType.SINGLE, "101", RoomStatus.VACANT);
        room1.setRoomAttributes(Arrays.asList("balkon", "mini-bar"));
        Room room2 = new Room(RoomType.DOUBLE, "102", RoomStatus.VACANT);
        room2.setRoomAttributes(Collections.singletonList("balkon"));

        Guest guest = new Guest("Petar", "Petrović", Gender.MALE, LocalDate.of(1985, 5, 15),
                                "+381640000001", "Nikola Tesla 1 - Beograd", "petar_p", "password123");
        Reservation reservation1 = new Reservation(RoomType.SINGLE, 1, LocalDate.of(2024, 6, 1),
                                                   LocalDate.of(2024, 6, 5), room1, guest, LocalDate.now());

        HotelManager hotelManager = new HotelManager();
        hotelManager.getRooms().add("101", room1);
        hotelManager.getRooms().add("102", room2);
        hotelManager.getReservations().add(reservation1.getID(), reservation1);

        List<String> attributes1 = Arrays.asList("balkon", "mini-bar");
        List<Room> availableRooms1 = hotelManager.getAvailableRooms(RoomType.SINGLE, LocalDate.of(2024, 6, 6),
                                                                    LocalDate.of(2024, 6, 7), attributes1);
        assertEquals(1, availableRooms1.size());
        assertEquals(room1, availableRooms1.get(0));

        List<String> attributes2 = Collections.singletonList("balkon");
        List<Room> availableRooms2 = hotelManager.getAvailableRooms(RoomType.DOUBLE, LocalDate.of(2024, 6, 16),
                                                                    LocalDate.of(2024, 6, 17), attributes2);
        assertEquals(1, availableRooms2.size());
        assertEquals(room2, availableRooms2.get(0));
    }
    
    @Test
    public void testGetAvailableRoomTypes() {
        Room room1 = new Room(RoomType.SINGLE, "101", RoomStatus.VACANT);
        room1.setRoomAttributes(Arrays.asList("balkon", "mini-bar"));
        Room room2 = new Room(RoomType.DOUBLE, "102", RoomStatus.VACANT);
        room2.setRoomAttributes(Collections.singletonList("balkon"));

        Guest guest = new Guest("Petar", "Petrović", Gender.MALE, LocalDate.of(1985, 5, 15),
                                "+381640000001", "Nikola Tesla 1 - Beograd", "petar_p", "password123");
        Reservation reservation1 = new Reservation(RoomType.SINGLE, 1, LocalDate.of(2024, 6, 1),
                                                   LocalDate.of(2024, 6, 5), room1, guest, LocalDate.now());

        HotelManager hotelManager = new HotelManager();
        hotelManager.getRooms().add("101", room1);
        hotelManager.getRooms().add("102", room2);
        hotelManager.getReservations().add(reservation1.getID(), reservation1);

        List<String> attributes = Arrays.asList("balkon", "mini-bar");
        List<String> availableRoomTypes = hotelManager.getAvailableRoomTypes(LocalDate.of(2024, 6, 6),
                                                                            LocalDate.of(2024, 6, 7), attributes);
        assertEquals(1, availableRoomTypes.size());
        assertTrue(availableRoomTypes.contains(RoomType.SINGLE.toString()));
    }

    
    @Test
    public void testGetPriceListByDate() {
        PriceList priceList1 = new PriceList(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30));
        HotelManager hotelManager = new HotelManager();
        hotelManager.getPriceLists().add(priceList1.getId(),priceList1);

        PriceList foundPriceList1 = hotelManager.getPriceListByDate(LocalDate.of(2024, 6, 15));
        assertEquals(priceList1, foundPriceList1);

        PriceList notFoundPriceList = hotelManager.getPriceListByDate(LocalDate.of(2024, 7, 1));
        assertNull(notFoundPriceList);
    }

    
    @Test
    public void testGetPriceListByDates() {
        PriceList priceList1 = new PriceList(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 30));
        HotelManager hotelManager = new HotelManager();
        hotelManager.getPriceLists().add(priceList1.getId(),priceList1);

        PriceList foundPriceList1 = hotelManager.getPriceListByDates(LocalDate.of(2024, 6, 15), LocalDate.of(2024, 6, 20));
        assertEquals(priceList1, foundPriceList1);

        PriceList notFoundPriceList = hotelManager.getPriceListByDates(LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 10));
        assertNull(notFoundPriceList);
    }
}