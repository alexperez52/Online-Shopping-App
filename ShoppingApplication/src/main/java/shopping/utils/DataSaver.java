package shopping.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.TreeMap;

import shopping.app.App;
import shopping.model.Inventory;
import shopping.model.Invoice;
import shopping.model.Item;
import shopping.model.User;
import shopping.model.UserBag;

public class DataSaver {

	/**
	 * Static method that takes an Inventory and UserBag object to fill with stored
	 * data for those objects, if they exist. It calls each argument's unique
	 * restore method
	 * 
	 * @param userBag   A UserBag object to be filled with existing User objects
	 * @param inventory An Inventory object to be filled with existing Item Objects
	 * @see DataSaver
	 */
	public static void restore(UserBag userBag, Inventory inventory) {
		userBag.setUsers(restoreUserBag().getUsers());
		inventory.setInventory(restoreInventory().getInventory());

	}

	/**
	 * Static method that returns an Inventory object filled with stored data, if
	 * they exist.
	 * 
	 * @return inventory An Inventory object filled with previously stored Item
	 *         objects
	 * @see DataSaver
	 */
	private static Inventory restoreInventory() {
		FileInputStream fis = null;
		Inventory inventory = new Inventory();
		File file = new File("src/main/java/backups/inventory.dat");
		if (file.exists()) {
			try {
				fis = new FileInputStream("src/main/java/backups/inventory.dat");
				ObjectInputStream ois = new ObjectInputStream(fis);
				inventory = (Inventory) (ois.readObject());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return inventory;
	}

	/**
	 * Static method that returns a UserBag object filled with stored data, if they
	 * exist.
	 * 
	 * @return userBag A UserBag object filled with previously stored User objects
	 * @see DataSaver
	 */
	private static UserBag restoreUserBag() {
		FileInputStream fis = null;
		UserBag userBag = new UserBag();
		File file = new File("src/main/java/backups/userBag.dat");
		if (file.exists()) {
			try {
				fis = new FileInputStream("src/main/java/backups/userBag.dat");
				ObjectInputStream ois = new ObjectInputStream(fis);
				userBag = (UserBag) (ois.readObject());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return userBag;
	}

	/**
	 * Static method that restores the last unique Item id to keep new Item id's
	 * from overlapping, if previous data exists.
	 * 
	 * @return lastId An Integer object to be used as the starting value value for new
	 *         Items to avoid duplicate id generation
	 * @see DataSaver
	 */
	public static Integer restoreItemId() {
		FileInputStream fis = null;
		Integer lastId = 1;
		File file = new File("src/main/java/backups/lastItemId.dat");
		if (file.exists()) {
			try {
				fis = new FileInputStream("src/main/java/backups/lastItemId.dat");
				DataInputStream dis = new DataInputStream(fis);
				lastId = dis.readInt();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lastId;
	}

	/**
	 * Static method that restores the last unique Invoice id to keep new Invoice
	 * id's from overlapping, if previous data exists.
	 * 
	 * @return lastId An Integer object to be used as the starting value value for new
	 *         Invoices to avoid duplicate id generation
	 * @see DataSaver
	 */
	public static Integer restoreInvoiceId() {
		FileInputStream fis = null;
		int lastId = 1;
		File file = new File("src/main/java/backups/lastInvoiceId.dat");
		if (file.exists()) {
			try {
				fis = new FileInputStream("src/main/java/backups/lastInvoiceId.dat");
				DataInputStream dis = new DataInputStream(fis);
				lastId = dis.readInt();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lastId;
	}

	/**
	 * Static method that takes an Inventory and UserBag object to save their Item
	 * and User objects, respectively. It calls each argument's unique backup method
	 * 
	 * @param userBag   A UserBag object to save their User objects
	 * @param inventory An Inventory object to save their Item Objects
	 * @see DataSaver
	 */
	public static void backup(UserBag userBag, Inventory inventory) {
		backupUserBag(userBag);
		backupInventory(inventory);
		backupItemId(Item.getUniqueNumber());
		backupInvoiceId(Invoice.getUniqueNumber());

	}

	/**
	 * Static method that takes an Inventory object to save stored data.
	 * 
	 * @param inventory An Inventory object to save Item Objects
	 * @see DataSaver
	 */
	private static void backupInventory(Inventory inventory) {
		try {
			FileOutputStream fos = new FileOutputStream("src/main/java/backups/inventory.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(inventory);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Static method that takes an UserBag object to save stored data.
	 * 
	 * @param userBag A UserBag object to save User Objects
	 * @see DataSaver
	 */
	private static void backupUserBag(UserBag userBag) {
		try {
			FileOutputStream fos = new FileOutputStream("src/main/java/backups/userBag.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(userBag);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Static method that takes an Integer object to save the last used Item object
	 * id to avoid future Item objects from having duped id's.
	 * 
	 * @param id An Integer object to save last Item id
	 * @see DataSaver
	 */
	private static void backupItemId(Integer id) {
		try {
			FileOutputStream fos = new FileOutputStream("src/main/java/backups/lastItemId.dat");
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeInt(id);
			dos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		backupInvoiceId(new Integer(5));
	}

	/**
	 * Static method that takes an Integer object to save the last used Invoice
	 * object id to avoid future Invoice objects from having duped id's.
	 * 
	 * @param id An Integer object to save last Invoice id
	 * @see DataSaver
	 */
	private static void backupInvoiceId(Integer id) {
		try {
			FileOutputStream fos = new FileOutputStream("src/main/java/backups/lastInvoiceId.dat");
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeInt(id);
			dos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
