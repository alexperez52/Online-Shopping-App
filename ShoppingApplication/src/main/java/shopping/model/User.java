package shopping.model;

import java.util.TreeMap;

public class User {
	private Name name;
	private Address address;// holds user's address info for shipping
	private String username;
	private String email;
	private Payment payment; // payment method as either card or paypal and holds financial information
	private ShoppingCart cart; // user's unique shopping cart
	private boolean isAdmin; // decides if user has admin access
	private TreeMap<Integer, Invoice> invoiceLog; // holds log of user's invoices

	public User(Name name, Address address, String username, String email) {
		this.name = name;
		this.address = address;
		this.username = username;
		this.email = email;
		this.cart = new ShoppingCart(); // each user gets a new shopping cart
		this.isAdmin = false; // user is not admin, by default
		this.invoiceLog = new TreeMap<Integer, Invoice>(); // each user gets a new invoice log
	}

	public User(Name name, Address address, String username, String email, Boolean isAdmin) {
		this.name = name;
		this.address = address;
		this.username = username;
		this.email = email;
		this.cart = new ShoppingCart(); // each user gets a new shopping cart
		this.isAdmin = isAdmin; // constructor to make admins
		this.invoiceLog = new TreeMap<Integer, Invoice>(); // each user gets a new invoice log
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public ShoppingCart getCart() {
		return cart;
	}

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public TreeMap<Integer, Invoice> getInvoiceLog() {
		return invoiceLog;
	}

	public void setInvoiceLog(TreeMap<Integer, Invoice> invoiceLog) {
		this.invoiceLog = invoiceLog;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", address=" + address + ", username=" + username + ", email=" + email
				+ ", payment=" + payment + ", cart=" + cart + ", isAdmin=" + isAdmin + ", invoiceLog=" + invoiceLog
				+ "]";
	}

}
