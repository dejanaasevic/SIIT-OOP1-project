package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import manager.ReservationRequestManager;
import entity.ReservationRequest;
import entity.RoomType;
import entity.Guest;
import entity.Gender; 
import entity.AdditionalService; 
import entity.ReservationStatus; 
import java.time.LocalDate;
import java.util.List;

public class ReservationRequestManagerTest { 
	private ReservationRequestManager manager;
	
	@Before
	public void setUp() {
		manager = new ReservationRequestManager();
	}
	
	@Test
	public void testAddReservationRequest() {
		Guest guest = new Guest("Petar", "Petrović", Gender.MALE, LocalDate.of(2000, 1, 1),
				"+381640000000", "Nikola Tesla 12 - Novi Sad", "petar_petrovic", "petar123");
		ReservationRequest reservationRequest = new ReservationRequest(RoomType.DOUBLE, 2, LocalDate.of(2024, 06, 29),
				 LocalDate.of(2024, 06, 30),guest,LocalDate.now());
		manager.addReservationRequest(reservationRequest);
		List<ReservationRequest> requests = manager.getReservationRequests();
		assertTrue(requests.contains(reservationRequest));
		assertEquals(1, requests.size());
	}
	
	@Test
	public void testRemoveReservationRequest() {
		Guest guest = new Guest("Petar", "Petrović", Gender.MALE, LocalDate.of(2000, 1, 1),
				"+381640000000", "Nikola Tesla 12 - Novi Sad", "petar_petrovic", "petar123");
		ReservationRequest reservationRequest = new ReservationRequest(RoomType.DOUBLE, 2, LocalDate.of(2024, 06, 29),
				 LocalDate.of(2024, 06, 30),guest,LocalDate.now());
		manager.addReservationRequest(reservationRequest);
		assertTrue(manager.removeReservationRequest(reservationRequest));
		List<ReservationRequest> requests = manager.getReservationRequests();
		assertEquals(0, requests.size());
		assertFalse(requests.contains(reservationRequest));
		
	}
	
	@Test
	public void testContainsReservationRequest() {
		Guest guest = new Guest("Petar", "Petrović", Gender.MALE, LocalDate.of(2000, 1, 1),
				"+381640000000", "Nikola Tesla 12 - Novi Sad", "petar_petrovic", "petar123");
		ReservationRequest reservationRequest = new ReservationRequest(RoomType.DOUBLE, 2, LocalDate.of(2024, 06, 29),
				 LocalDate.of(2024, 06, 30),guest,LocalDate.now());
		manager.addReservationRequest(reservationRequest);
		assertTrue(manager.containsReservationRequest(reservationRequest));
		manager.removeReservationRequest(reservationRequest);
		assertFalse(manager.containsReservationRequest(reservationRequest));
	}

	@Test
	public void testUpdateReservationRequest() {
		Guest guest = new Guest("Petar", "Petrović", Gender.MALE, LocalDate.of(2000, 1, 1),
				"+381640000000", "Nikola Tesla 12 - Novi Sad", "petar_petrovic", "petar123");
		ReservationRequest reservationRequest = new ReservationRequest(RoomType.DOUBLE, 2, LocalDate.of(2024, 06, 29),
				 LocalDate.of(2024, 06, 30),guest,LocalDate.now());
		ReservationRequest newReservationRequest = new ReservationRequest(RoomType.TWIN, 3, LocalDate.of(2024, 07, 29),
				 LocalDate.of(2024, 06, 30),guest,LocalDate.now());
		manager.addReservationRequest(reservationRequest);
		assertTrue(manager.updateReservationRequest(reservationRequest, newReservationRequest));
		List<ReservationRequest> requests = manager.getReservationRequests();
		assertFalse(requests.contains(reservationRequest));
		assertTrue(requests.contains(newReservationRequest));
	}
}
