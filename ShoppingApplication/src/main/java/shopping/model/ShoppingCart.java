package shopping.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ShoppingCart implements Serializable {
	private ArrayList<ShoppingCartItem> cart; //holds ShoppingCartItems as user shops
	
	public ShoppingCart() {
		cart = new ArrayList<ShoppingCartItem>();
	}

	public ArrayList<ShoppingCartItem> getCartItems() {
		return cart;
	}

	public void setCart(ArrayList<ShoppingCartItem> cart) {
		this.cart = cart;
	}
	
	public void clearCart() { //takes out items from cart
		cart.clear();
	}

	@Override
	public String toString() {
		return "ShoppingCart [cart=" + cart + "]";
	}
	
	
}
