package shopping.model;

import java.io.Serializable;

public class ShoppingCartItem implements Serializable{ // holds the id and amount of an inventory item to be held in user's cart and
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
	
	
	

	@Override
	public String toString() {
		return "ShoppingCartItem [id=" + id + ", itemQuantity=" + itemQuantity + "]";
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
	


}
