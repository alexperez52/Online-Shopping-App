package shopping.utils;

import shopping.model.Address;
import shopping.model.Inventory;
import shopping.model.Item;
import shopping.model.Name;
import shopping.model.User;
import shopping.model.UserBag;

public class Demo {

	public static void main(String[] args) {
		
		Inventory inventory = new Inventory(); //factory to create inventory
		inventory = ItemFactory.importItemData(inventory);

		UserBag testUserBag = new UserBag(); //factory to create items
		testUserBag = UserFactory.importUserData(testUserBag);
		
		testUserBag.getUsers().entrySet().forEach(entry -> {
			System.out.println(entry.getValue());
		});
		
		inventory.getInventory().entrySet().forEach(entry -> {
			System.out.println(entry.getValue());
		});

		shopping.utils.DataSaver.backup(testUserBag, inventory); //saving bags
		
		Inventory loadedInventory = new Inventory();
		UserBag loadedUserBag = new UserBag();

		shopping.utils.DataSaver.restore(loadedUserBag, loadedInventory); //loading bags
		
		loadedUserBag.getUsers().entrySet().forEach(entry -> {
			System.out.println(entry.getValue());
		});
		
		loadedInventory.getInventory().entrySet().forEach(entry -> {
			System.out.println(entry.getValue());
		});
		
		
		
	}

}
