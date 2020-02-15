package shopping.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import shopping.model.Address;
import shopping.model.Name;
import shopping.model.User;
import shopping.model.UserBag;

public class UserFactory {

	public static UserBag importUserData(UserBag userBag) { // generates initial warehouse inventory
		UserBag testUserBag = userBag;
		Random randNumber = new Random();
		Scanner addressScanner;
		Scanner nameScanner;

		try {
			File addressFile = new File("src/main/java/data/addressData.txt");
			File nameFile = new File("src/main/java/data/nameData.txt");
			addressScanner = new Scanner(addressFile);
			nameScanner = new Scanner(nameFile);

			while (addressScanner.hasNextLine()) {
				String[] addressTokens = addressScanner.nextLine().split(" ");
				String[] nameTokens = nameScanner.nextLine().split(" ");
				Name name = new Name(nameTokens[randNumber.nextInt(nameTokens.length)],
						nameTokens[randNumber.nextInt(nameTokens.length)],
						nameTokens[randNumber.nextInt(nameTokens.length)]);
				Address address = new Address(addressTokens[randNumber.nextInt(addressTokens.length)],
						addressTokens[randNumber.nextInt(addressTokens.length)],
						addressTokens[randNumber.nextInt(addressTokens.length)],
						addressTokens[randNumber.nextInt(addressTokens.length)],
						addressTokens[randNumber.nextInt(addressTokens.length)]);
				String username = nameTokens[0] + "" + randNumber.nextInt(99); // just using firstname and digit for
																				// username
				String password = nameTokens[1] + "" + randNumber.nextInt(99);//uses lastname and digit for password
				String email = nameTokens[nameTokens.length - 1] + "@gmail.com"; // formatting emails with lastnames
																					// just for display purpose

				User user = new User(name, address, username, password, email);
				testUserBag.getUsers().put(username, user);
				// invoiceLog, shoopingCart, payment, and admin status are all predetermined for
				// each user until they add payment info, buy items, etc.
			}
			addressScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		testUserBag.getUsers().entrySet().forEach(entry -> {
			System.out.println(entry.getValue());
		});
		
		return testUserBag;
	}

	// all address
	// info is
	// randomly
	// assigned to a
	// specific
	// field based
	// on random
	// info per
	// address line

	// first, middle, and last name
	// are just a random pick of the
	// two names per line
}
