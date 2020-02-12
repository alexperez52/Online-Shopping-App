package shopping.utils;

import shopping.model.Inventory;
import shopping.model.UserBag;

public class Demo {

	public static void main(String[] args) {
		Inventory inventory = new Inventory();
		inventory = ItemFactory.importItemData(inventory);

		inventory.getInventory().entrySet().forEach(entry -> {
			System.out.println(entry.getValue());
		});
		
		UserBag testUserBag = new UserBag();
		testUserBag = UserFactory.importUserData(testUserBag);
		
		testUserBag.getUsers().entrySet().forEach(entry -> {
			System.out.println(entry.getValue());
		});
	}

}
