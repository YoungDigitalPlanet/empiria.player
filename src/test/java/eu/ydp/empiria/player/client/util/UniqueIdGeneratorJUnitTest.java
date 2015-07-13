package eu.ydp.empiria.player.client.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

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
