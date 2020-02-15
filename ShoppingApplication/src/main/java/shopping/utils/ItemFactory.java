package shopping.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import shopping.model.Inventory;
import shopping.model.Item;

public class ItemFactory {
	
	public static Inventory importItemData(Inventory inventory) { //generates initial warehouse inventory
		Inventory testInventory = inventory;
		Random randNumber = new Random();
		Scanner scanner;
		try {
			File file = new File("src/main/java/data/itemData.txt");
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String[] tokens = scanner.nextLine().split("!"); //name/description separated by !
				String name = tokens[0];
				String description = tokens[1];
				double price = randNumber.nextDouble() * 1000; //random price
				Integer quantity = 50; //assuming all items are initially stocked at 50 each
				Item item = new Item(name, price, description, quantity);
				inventory.getInventory().put(item.getId(), item); //dumps item into inventory 
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		inventory.getInventory().entrySet().forEach(entry -> {
			System.out.println(entry.getValue());
		});
		return testInventory;
	}
}
