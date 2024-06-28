package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import manager.ExpenseManager;
import entity.Expense;
import entity.Guest;
import entity.RoomType;
import entity.Gender; 
import java.time.LocalDate;
import java.util.List;

public class ExpenseManagerTest{
	private ExpenseManager expenseManager;
	
	@Before
    public void setUp() {
        expenseManager = new ExpenseManager();
    }
	
	@Test
	public void testAddExpense() {
		Guest guest = new Guest("Petar", "Petrović", Gender.MALE, LocalDate.of(2000, 1, 1),
				"+381640000000", "Nikola Tesla 12 - Novi Sad", "petar_petrovic", "petar123");
		Expense expense = new Expense(guest, RoomType.SINGLE, LocalDate.now(), 100.0);
		expenseManager.addExpense(expense);
		List<Expense> expenses = expenseManager.getExpenses();
		assertEquals(1,expenses.size());
		assertTrue(expenses.contains(expense));
	}
	
	@Test
	public void testRemoveExpenses() {
		Guest guest = new Guest("Petar", "Petrović", Gender.MALE, LocalDate.of(2000, 1, 1),
				"+381640000000", "Nikola Tesla 12 - Novi Sad", "petar_petrovic", "petar123");
		Expense expense = new Expense(guest, RoomType.SINGLE, LocalDate.now(), 100.0);
		expenseManager.addExpense(expense);
		assertTrue(expenseManager.removeExpense(expense));
		List<Expense> expenses = expenseManager.getExpenses();
		assertEquals(0,expenses.size());
		assertFalse(expenses.contains(expense));
	}
	
	@Test
	public void testGetExpenses() {
		Guest first_guest = new Guest("Petar", "Petrović", Gender.MALE, LocalDate.of(2000, 1, 1),
				"+381640000000", "Nikola Tesla 12 - Novi Sad", "petar_petrovic", "petar123");
		Expense first_expense = new Expense(first_guest, RoomType.SINGLE, LocalDate.now(), 200.0);
		expenseManager.addExpense(first_expense);
		Guest second_guest = new Guest("Milica", "Petrović", Gender.FEMALE, LocalDate.of(1990, 1, 1),
				"+381640000000", "Nikola Tesla 12 - Novi Sad", "milica_petrovic", "milica123");
		Expense second_expense = new Expense(second_guest, RoomType.SINGLE, LocalDate.now(), 100.0);
		expenseManager.addExpense(second_expense);
		List<Expense> expenses = expenseManager.getExpenses();
		assertEquals(2,expenses.size());
		assertTrue(expenses.contains(first_expense));
		assertTrue(expenses.contains(second_expense));
		
	}

	
}