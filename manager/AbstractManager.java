package manager;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractManager<T> {
	protected Map<String, T> items;
	
	public AbstractManager() {
		this.items = new HashMap<>();
	}
	
	public Map<String, T> get() {
	    return items;
	}

	public boolean add(String id, T item) {
		if(this.isExists(id)) {
			return false;
		}
		else {
			items.put(id,item);
			return true;
		}
	}
	
	public boolean remove(String id) {
		if(!this.isExists(id)) {
			return false;
		}
		else {
			items.remove(id);	
			return true;
		}
	}
	
	public T FindById(String id) {
		return items.get(id);
	}
	
	public boolean isExists(String id) {
		 return items.containsKey(id);
	}
	
	public boolean update(String id, T updatedItem) {
		if(!this.isExists(id)) {
			return false;
		}
		else {
			items.put(id, updatedItem);
			return true;
		}
	}
	
	public void display(String id) {		
		System.out.println(FindById(id));
	}
	
	public void displayAll() {
		for(T item : items.values()) {
			System.out.println(item);
		}
	}

}
