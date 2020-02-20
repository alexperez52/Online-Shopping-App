package shopping.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import shopping.model.Electronics;
import shopping.model.Inventory;
import shopping.model.Item;

public class ItemFactory {

	/**
	 * Static method that takes an Inventory object to dump randomly generated
	 * Item objects into. Uses itemData.txt to pull information for Item objects.
	 * 
	 * @param inventory An Inventory object to populate with generated Item objects
	 * @return inventory An Inventory object filled with randomly generated Item objects
	 * @see ItemFactory
	 */
	public static Inventory importItemData(Inventory inventory) { // generates initial warehouse inventory
		Inventory testInventory = inventory;
		Random randNumber = new Random();
		Scanner scanner;
		int electronicInt = 0;
		try {
			File file = new File("src/main/java/data/itemData.txt");
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String[] tokens = scanner.nextLine().split("!"); // name/description separated by !
				String name = tokens[0];
				String description = tokens[1];
				double price = randNumber.nextDouble() * 1000; // random price
				Integer quantity = 50; // assuming all items are initially stocked at 50 each
				Electronics electronicType = null;
				if (electronicInt < 14) {
					electronicType = Electronics.CPU;
				} else if (electronicInt < 28 && electronicInt > 15) {
					electronicType = Electronics.GPU;
				} else if (electronicInt < 35 && electronicInt > 15) {
					electronicType = Electronics.MEMORY;
				} else if (electronicInt > 34) {
					electronicType = Electronics.MOTHERBOARD;
				}

				Item item = new Item(name, price, description, quantity, electronicType);
				inventory.getInventory().put(item.getId(), item); // dumps item into inventory
				electronicInt++;
				System.out.println(item);
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
