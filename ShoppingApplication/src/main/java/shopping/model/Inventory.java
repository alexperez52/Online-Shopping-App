package shopping.model;

import java.util.HashMap;

public class Inventory { //holds all store's items
	HashMap<Integer, Item> inventory;
	
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
