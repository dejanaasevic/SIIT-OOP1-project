package manager;

import java.util.ArrayList;
import java.util.List;

import entity.ReservationRequest;

public class ReservationRequestManager {
	private List<ReservationRequest> reservationRequests;
	
	public ReservationRequestManager() {
		this.reservationRequests = new ArrayList<>();
	}
	 
	public List<ReservationRequest> getReservationRequests() {
		return reservationRequests;
	}
	
	public void addReservationRequest(ReservationRequest request) {
		reservationRequests.add(request);
	}
	
	public boolean removeReservationRequest(ReservationRequest request) {
        if(this.containsReservationRequest(request)) {
        	reservationRequests.remove(request);
        	return true;
        }
        return false;
	}

	public boolean containsReservationRequest(ReservationRequest request) {
		 return reservationRequests.contains(request);
	}
	
	public boolean updateReservationRequest(ReservationRequest currentRequest, ReservationRequest updatedRequest) {
	    if (!this.containsReservationRequest(currentRequest)) {
	        return false;
	    } 
	    else {
	        int index = reservationRequests.indexOf(currentRequest);
	        reservationRequests.set(index, updatedRequest);
	        return true;
	    }
	}
	
	public void displayRequest(ReservationRequest request) {
		System.out.println(request);
		System.out.println();
	}
	
	public void displayAllRequest() {
		for(ReservationRequest request: this.reservationRequests) {
			System.out.println(request);
			System.out.println();
		}
	}
}