package manager;

import java.util.ArrayList;
import java.util.List;

import entity.Expense;

public class ExpenseManager {
    private List<Expense> expenses;

    public ExpenseManager() {
        this.expenses = new ArrayList<>();
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public boolean removeExpense(Expense expenseToRemove) {
        return expenses.remove(expenseToRemove);
    }
}
