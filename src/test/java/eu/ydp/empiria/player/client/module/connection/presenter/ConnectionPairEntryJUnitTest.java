package eu.ydp.empiria.player.client.module.connection.presenter;

import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("PMD")
public class ConnectionPairEntryJUnitTest {

    @Test
    public void equalsTest() {
        String target = "target";
        String source = "source";
        ConnectionPairEntry<String, String> entry = new ConnectionPairEntry<String, String>(source, target);
        ConnectionPairEntry<String, String> entry2 = new ConnectionPairEntry<String, String>(target, source);
        assertTrue(entry.equals(entry2));
        assertTrue(entry.equals(entry));
        assertTrue(entry2.equals(entry));

    }

    @Test
    public void notEqualsTest() {
        String target = "target";
        String source = "source";
        ConnectionPairEntry<String, String> entry = new ConnectionPairEntry<String, String>(source, target);
        ConnectionPairEntry<String, String> entry2 = new ConnectionPairEntry<String, String>("xxx", source);
        assertFalse(entry.equals(entry2));
        assertFalse(entry.equals(null));
    }

    @Test
    public void hashcodeCorrectTest() {
        String target = "target";
        String source = "source";
        ConnectionPairEntry<String, String> entry = new ConnectionPairEntry<String, String>(source, target);
        ConnectionPairEntry<String, String> entry2 = new ConnectionPairEntry<String, String>(target, source);

        assertEquals(entry.hashCode(), entry2.hashCode());
    }

    @Test
    public void hashcodeNotCorrectTest() {
        String target = "target";
        String source = "source";
        ConnectionPairEntry<String, String> entry = new ConnectionPairEntry<String, String>(source, target);
        ConnectionPairEntry<String, String> entry2 = new ConnectionPairEntry<String, String>("xxx", source);

        assertFalse(entry.hashCode() == entry2.hashCode());
    }

}
