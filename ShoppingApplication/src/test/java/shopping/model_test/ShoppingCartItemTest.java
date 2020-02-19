package shopping.model_test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import shopping.model.ShoppingCartItem;

public class ShoppingCartItemTest {

	private ShoppingCartItem item1;
	private ShoppingCartItem item2;
	private ShoppingCartItem item3;

	@Before
	public void setUp() throws Exception {
		item1 = new ShoppingCartItem(1,1);
		item2 = new ShoppingCartItem(1,1);
		item3 = new ShoppingCartItem(2,1);

	}

	@Test
	public void testEquals() {
		assertEquals(true, item1.equals(item2));
		assertEquals(false, item1.equals(item3));
		assertEquals(false, item2.equals(item3));
	}

}
