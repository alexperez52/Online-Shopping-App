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

	/**
	 * Removes all ShoppingCartItem objects from this.ShoppingCart. Does not set
	 * ShoppingCart to null; it simply removes all objects from cart in ShoppingCart.
	 * @see ShoppingCart
	 */
	public void clearCart() { // takes out items from cart
		cart.clear();
	}

	/**
	 * Returns an String object that displays a formatted view of Item objects based
	 * off ShoppingCartItem objects in this.ShoppingCart. The inventory argument is
	 * a HashMap of Item objects that are searched for by their unique id's held in
	 * this.ShoppingCart
	 * <p>
	 * This method finds Item objects and writes out each's name, quantity, and
	 * price on a new line
	 *
	 * @param inventory an Inventory that holds a HashMap of Item objects that the
	 *                  cart bases it's ShoppingCartItem object id's on.
	 * @return string Summary of Item objects in ShoppingCart
	 * @see ShoppingCart
	 */
	public String lookUpShoppingCartItems(Inventory inventory) { // gets string info of actual item based off shopping
																	// cart item id
		String cartSummary = "";
		for (ShoppingCartItem item : this.getCartItems()) {
			Item inventoryItem = inventory.getInventory().get(item.getId());
			cartSummary += "\nItem: " + inventoryItem.getName() + "\t\t\tQuantity: " + item.getItemQuantity()
					+ "\t\t\tPrice: $"
					+ shopping.utils.DataFormatter.formatAmount(item.getItemQuantity() * inventoryItem.getPrice()); // rounds
																													// to
																													// $
																													// 0.##
		}
		return cartSummary;

	}

	@Override
	public String toString() {
		return "ShoppingCart [cart=" + cart + "]";
	}

}
