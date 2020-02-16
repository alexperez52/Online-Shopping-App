package shopping.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ShoppingCart implements Serializable {
	private ArrayList<ShoppingCartItem> cart; // holds ShoppingCartItems as user shops

	public ShoppingCart() {
		cart = new ArrayList<ShoppingCartItem>();
	}

	public ArrayList<ShoppingCartItem> getCartItems() {
		return cart;
	}

	public void setCart(ArrayList<ShoppingCartItem> cart) {
		this.cart = cart;
	}

	public void clearCart() { // takes out items from cart
		cart.clear();
	}

	public String lookUpShoppingCartItems(Inventory inventory) { // gets string info of actual item based off shopping
																	// cart item id
		String cartSummary = "";
		for (ShoppingCartItem item : this.getCartItems()) {
			Item inventoryItem = inventory.getInventory().get(item.getId());
			cartSummary += "\nItem: " + inventoryItem.getName() + "\t\t\tQuantity: " + item.getItemQuantity() + "\t\t\tPrice: $"
					+ shopping.utils.DataFormatter.formatAmount(item.getItemQuantity() * inventoryItem.getPrice()); //rounds to $ 0.##
		}
		return cartSummary;

	}

	@Override
	public String toString() {
		return "ShoppingCart [cart=" + cart + "]";
	}

}
