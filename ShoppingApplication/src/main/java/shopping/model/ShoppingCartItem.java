package shopping.model;

public class ShoppingCartItem { // holds the id and amount of an inventory item to be held in user's cart and
								// for reference to inventory at checkout
	private Integer id = 0;
	private Integer itemQuantity = 0;

	public ShoppingCartItem(Integer id, Integer itemQuantity) {
		super();
		this.id = id;
		this.itemQuantity = itemQuantity;
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

}
