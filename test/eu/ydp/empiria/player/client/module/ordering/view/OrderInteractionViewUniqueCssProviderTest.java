package eu.ydp.empiria.player.client.module.ordering.view;

import static org.junit.Assert.*;

import org.junit.Test;

public class OrderInteractionViewUniqueCssProviderTest {

	private final OrderInteractionViewUniqueCssProvider testObj = new OrderInteractionViewUniqueCssProvider();

	@Test
	public void getNextTest() {
		// when
		String result1 = testObj.getNext();
		String result2 = testObj.getNext();
		String result3 = testObj.getNext();

		// then
		assertEquals("qp-ordered-unique-1", result1);
		assertEquals("qp-ordered-unique-2", result2);
		assertEquals("qp-ordered-unique-3", result3);

	}

}
