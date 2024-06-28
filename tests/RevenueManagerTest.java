package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import entity.Guest;
import entity.Revenue;
import entity.RoomType;
import manager.RevenueManager;
import entity.Gender; 
import java.time.LocalDate;
import java.util.List;

public class RevenueManagerTest {
	private RevenueManager revenueManager;
	
	@Before
    public void setUp() {
		revenueManager = new RevenueManager();
    }
	
	@Test
	public void testAddRevenue() {
		Guest guest = new Guest("Petar", "Petrović", Gender.MALE, LocalDate.of(2000, 1, 1),
				"+381640000000", "Nikola Tesla 12 - Novi Sad", "petar_petrovic", "petar123");
		Revenue revenue = new Revenue(guest, RoomType.SINGLE, LocalDate.now(), 100.0);
		revenueManager.addRevenue(revenue);
		List<Revenue> revenues = revenueManager.getRevenues();
		assertEquals(1, revenues.size());
		assertTrue(revenues.contains(revenue));
	}
	
	@Test
	public void testRemoveRevenue() { 
		Guest guest = new Guest("Petar", "Petrović", Gender.MALE, LocalDate.of(2000, 1, 1),
				"+381640000000", "Nikola Tesla 12 - Novi Sad", "petar_petrovic", "petar123");
		Revenue revenue = new Revenue(guest, RoomType.SINGLE, LocalDate.now(), 100.0);
		revenueManager.addRevenue(revenue);
		assertTrue(revenueManager.removeRevenue(revenue));
		List<Revenue> revenues = revenueManager.getRevenues();
		assertEquals(0, revenues.size());
		assertFalse(revenues.contains(revenue));
	}
	
	@Test
	public void testGetRevenues() {
		Guest first_guest = new Guest("Petar", "Petrović", Gender.MALE, LocalDate.of(2000, 1, 1),
				"+381640000000", "Nikola Tesla 12 - Novi Sad", "petar_petrovic", "petar123");
		Revenue first_revenue = new Revenue(first_guest, RoomType.SINGLE, LocalDate.now(), 200.0);
		revenueManager.addRevenue(first_revenue);
		
		Guest second_guest = new Guest("Milica", "Petrović", Gender.FEMALE, LocalDate.of(1990, 1, 1),
				"+381640000000", "Nikola Tesla 12 - Novi Sad", "milica_petrovic", "milica123");
		Revenue second_revenue = new Revenue(second_guest, RoomType.SINGLE, LocalDate.now(), 100.0);
		revenueManager.addRevenue(second_revenue);
		
		List<Revenue> revenues = revenueManager.getRevenues();
		assertEquals(2, revenues.size());
		assertTrue(revenues.contains(first_revenue));
		assertTrue(revenues.contains(second_revenue));
	}
	
	@Test
	public void testRemoveNonExistentRevenue() {
		Guest guest = new Guest("Petar", "Petrović", Gender.MALE, LocalDate.of(2000, 1, 1),
				"+381640000000", "Nikola Tesla 12 - Novi Sad", "petar_petrovic", "petar123");
		Revenue revenue = new Revenue(guest, RoomType.SINGLE, LocalDate.now(), 100.0);
		assertFalse(revenueManager.removeRevenue(revenue));  // Attempting to remove a revenue that doesn't exist
		List<Revenue> revenues = revenueManager.getRevenues();
		assertEquals(0, revenues.size());
	}
}
