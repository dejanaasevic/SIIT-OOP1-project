package manager;

import java.util.ArrayList;
import java.util.List;

import entity.Revenue;

public class RevenueManager {
    private List<Revenue> revenues;

    public RevenueManager() {
        this.revenues = new ArrayList<>();
    }

    public List<Revenue> getRevenues() {
        return revenues;
    }

    public void addRevenue(Revenue revenue) {
        revenues.add(revenue);
    }

    public boolean removeRevenue(Revenue revenueToRemove) {
        return revenues.remove(revenueToRemove);
    }
}
