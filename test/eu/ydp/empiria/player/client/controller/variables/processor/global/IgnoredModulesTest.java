package eu.ydp.empiria.player.client.controller.variables.processor.global;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Test;

public class IgnoredModulesTest {

	private final IgnoredModules testObj = new IgnoredModules();

	@Test
	public void shouldReturnTrue_whenListContainsId() {
		// given
		String id = "sample_id";
		testObj.addIgnoredID(id);

		// when
		boolean result = testObj.isIgnored(id);

		// then
		assertThat(result).isTrue();
	}

	@Test
	public void shouldReturnFalse_whenListNotContainsId() {
		// given
		String id = "sample_id";

		// when
		boolean result = testObj.isIgnored(id);

		// then
		assertThat(result).isFalse();
	}
}
