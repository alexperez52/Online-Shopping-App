package shopping.model;

public class Item {// item that actually exists in inventory. Exists in limited amount and each's id
					// is used in user's shopping cart to search for item in inventory @checkout
	private String name;
	private Integer id;
	private static Integer uniqueNumber = 0; //each item has a unique id for searching
	private double price; //price of 1 quantity of the item
	private String description; //item details
	private Integer quantity; //how many are in stock

	public Item(String name, double price, String description, Integer quantity) {
		super();
		this.id = uniqueNumber++;
		this.name = name;
		this.price = price;
		this.description = description;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
