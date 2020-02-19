package shopping.model_test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import shopping.model.Name;

public class NameTest {

	private Name name;

	@Before
	public void setUp() throws Exception {
		name = new Name("Nikolas", "Efstratios", "Kinalis");
	}

	@Test
	public void testFullName() {
		String fullName = "Nikolas Efstratios Kinalis";
		assertEquals(fullName, name.getFullName());
	}

}
