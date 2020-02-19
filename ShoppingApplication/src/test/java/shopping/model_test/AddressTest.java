package shopping.model_test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import shopping.model.Address;
import shopping.model.Name;

public class AddressTest {
	private Address address;

	@Before
	public void setUp() throws Exception {
		address = new Address("1", "Candy Street", "Islip", "NY", "US");
	}

	@Test
	public void testFullName() {
		String fullAddress = "1 Candy Street Islip NY US";
		assertEquals(fullAddress, address.getFullAddress());
	}


}
