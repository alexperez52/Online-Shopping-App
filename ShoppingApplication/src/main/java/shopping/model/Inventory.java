package shopping.model;

import java.io.Serializable;
import java.util.HashMap;

public class Inventory implements Serializable{ //holds all store's items
	private HashMap<Integer, Item> inventory;
	
	public Inventory() {
		this.inventory = new HashMap<Integer, Item>();
	}

	public HashMap<Integer, Item> getInventory() {
		return inventory;
	}

	public void setInventory(HashMap<Integer, Item> inventory) {
		this.inventory = inventory;
	}
	
	
}
