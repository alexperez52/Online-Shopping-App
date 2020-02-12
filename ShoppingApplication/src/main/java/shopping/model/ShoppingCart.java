package shopping.model;

import java.util.ArrayList;

public class ShoppingCart {
	ArrayList<ShoppingCartItem> cart; //holds ShoppingCartItems as user shops
	
	public ShoppingCart() {
		cart = new ArrayList<ShoppingCartItem>();
	}

	public ArrayList<ShoppingCartItem> getCart() {
		return cart;
	}

	public void setCart(ArrayList<ShoppingCartItem> cart) {
		this.cart = cart;
	}
	
	public void clearCart() { //takes out items from cart
		cart.clear();
	}
	
	
}
