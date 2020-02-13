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

import shopping.model.Inventory;
import shopping.model.Invoice;
import shopping.model.Item;
import shopping.model.User;
import shopping.model.UserBag;

public class DataSaver {
	public static void restore(UserBag userBag, Inventory inventory) {
		userBag.setUsers(restoreUserBag().getUsers());
		inventory.setInventory(restoreInventory().getInventory());
		
	}

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

	public static void backup(UserBag userBag, Inventory inventory) {
		backupUserBag(userBag);
		backupInventory(inventory);
		backupItemId(Item.getUniqueNumber());
		backupInvoiceId(Invoice.getUniqueNumber());

	}

	private static void backupInventory(Inventory inventory) {
		try {
			FileOutputStream fos = new FileOutputStream("src/main/java/backups/inventory.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(inventory);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void backupUserBag(UserBag userBag) {
		try {
			FileOutputStream fos = new FileOutputStream("src/main/java/backups/userBag.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(userBag);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void backupItemId(Integer id) {
		try {
			FileOutputStream fos = new FileOutputStream("src/main/java/backups/lastItemId.dat");
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeInt(id);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void backupInvoiceId(Integer id) {
		try {
			FileOutputStream fos = new FileOutputStream("src/main/java/backups/lastInvoiceId.dat");
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeInt(id);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
