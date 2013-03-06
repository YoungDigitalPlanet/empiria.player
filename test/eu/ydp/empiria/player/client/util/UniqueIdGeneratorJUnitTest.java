package eu.ydp.empiria.player.client.util;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

@SuppressWarnings("PMD")
public class UniqueIdGeneratorJUnitTest {
	UniqueIdGenerator instance = new UniqueIdGenerator();

	@Test
	public void createIdTest() {
		for (int x = 0; x < 10; ++x) {
			assertFalse(instance.createUniqueId() == instance.createUniqueId());
		}
	}
}
