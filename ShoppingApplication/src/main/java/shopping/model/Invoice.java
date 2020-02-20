package shopping.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Invoice implements Serializable {// holds user's billing information such as address/amount due
	private User user;
	private Integer id; // each invoice has an id so it can be searched in user's invoice log.
	private static Integer uniqueNumber = shopping.utils.DataSaver.restoreInvoiceId();
	private double bill; // total of purchased goods
	private String information; // list of purchased goods
	private LocalDateTime dateCreated;
	private transient DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	public Invoice(User user, double bill, Inventory inventory) {
		super();
		this.id = uniqueNumber++;
		this.user = user;
		this.bill = bill; // gets total from checkout controller
		this.information = user.getCart().lookUpShoppingCartItems(inventory); // displays items purchased
		this.dateCreated = LocalDateTime.now(); // marks time of purchase

	}

	public String getDateCreated() {
		return dateFormatter.format(dateCreated);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public double getBill() {
		return bill;
	}

	public void setBill(double bill) {
		this.bill = bill;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public static Integer getUniqueNumber() {
		return uniqueNumber;
	}

	@Override
	public String toString() {
		return id + ".     " + user + "     " + dateCreated;
	}
	

}
