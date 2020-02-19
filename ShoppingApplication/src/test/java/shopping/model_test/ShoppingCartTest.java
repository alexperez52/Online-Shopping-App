package shopping.model_test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shopping.model.Inventory;
import shopping.model.Item;
import shopping.model.ShoppingCart;
import shopping.model.ShoppingCartItem;

public class ShoppingCartTest {

	private ShoppingCart cart;
	private Inventory inventory;
	private Item item;
	private ShoppingCartItem cartItem;

	@Before
	public void setUpBeforeClass() throws Exception {
		cart = new ShoppingCart();
		inventory = new Inventory();

		item = new Item("item1", 100, "item in inventory", 10);
		inventory.getInventory().put(item.getId(), item);

		cartItem = new ShoppingCartItem(item.getId(), 1);
		cart.getCartItems().add(cartItem);
	}

	@Test
	public void testLookUpShoppingCartItems() {
		String cartSummary = "\nItem: " + item.getName() + "\t\t\tQuantity: " + cartItem.getItemQuantity()
				+ "\t\t\tPrice: $"
				+ shopping.utils.DataFormatter.formatAmount(cartItem.getItemQuantity() * item.getPrice());
		assertEquals(cartSummary, cart.lookUpShoppingCartItems(inventory));
	}

	@Test
	public void testClearCart() {
		cart.clearCart();
		assertEquals(new ArrayList<ShoppingCartItem>(), cart.getCartItems());
	}

}
