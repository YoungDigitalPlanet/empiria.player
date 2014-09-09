package eu.ydp.empiria.player.client.controller.variables.processor.global;

import static org.junit.Assert.*;

import org.junit.Test;

public class IgnoredModulesTest {

	private final IgnoredModules testObj = new IgnoredModules();

	@Test
	public void shouldReturnTrue_whenListContainsId() {
		// given
		String id = "sample_id";
		testObj.addIgnoredID(id);
		boolean expected = true;

		// when
		boolean result = testObj.isIgnored(id);

		// then
		assertEquals(expected, result);
	}

	@Test
	public void shouldReturnFalse_whenListNotContainsId() {
		// given
		String id = "sample_id";
		boolean expected = false;

		// when
		boolean result = testObj.isIgnored(id);

		// then
		assertEquals(expected, result);
	}
}
