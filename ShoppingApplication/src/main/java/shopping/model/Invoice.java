package shopping.model;

public class Invoice {// holds user's billing information such as address/amount due
	User user; 
	Integer id; //each invoice has an id so it can be searched in user's invoice log.
	static Integer uniqueNumber = 0;
	double bill; //total of purchased goods
	String information; //list of purchased goods

	public Invoice(User user, double bill) {
		super();
		this.id = uniqueNumber++;
		this.user = user;
		this.bill = bill; // gets total from checkout controller
		this.information = user.getCart().toString(); // displays items purchased
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

}
