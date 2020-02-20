package shopping.model;

import java.io.Serializable;

public class ShoppingCartItem implements Serializable{ // holds the id and amount of an inventory item to be held in user's cart and
								// for reference to inventory at checkout
	private Integer id = 0;
	private Integer itemQuantity = 0;
	private String description;
	private double price;

	public ShoppingCartItem(Integer id, Integer itemQuantity, String description, double price) {
		super();
		this.id = id;
		this.itemQuantity = itemQuantity;
		this.setDescription(description);
		this.setPrice(price);
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public ShoppingCartItem(Integer id, Integer itemQuantity) {
		super();
		this.id = id;
		this.itemQuantity = itemQuantity;
		this.description = null;
	}

	public Integer getId() {
		return id;
	}

	public Integer getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	

	//padding for formatting the toString to be viewed properly in listView
	private String rightpad(String text, int length) {
	    return String.format("%-" + length + "." + length + "s", text);
	}

	@Override
	public String toString() {
		String quant = rightpad(String.valueOf(this.itemQuantity), 25);
		String desc = rightpad(this.description, 50);
		String pric = rightpad(shopping.utils.DataFormatter.formatAmount(this.price), 45);
		return "\t\t" + quant + pric + desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShoppingCartItem other = (ShoppingCartItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	


}
