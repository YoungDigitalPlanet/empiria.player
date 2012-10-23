package eu.ydp.empiria.player.client.util.events.multiplepair;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

@SuppressWarnings("PMD")
public class PairConnectEventTest {
	@Test
	public void typeTest() {
		PairConnectEvent event = new PairConnectEvent(PairConnectEventTypes.CONNECTED);
		assertEquals(PairConnectEventTypes.CONNECTED, event.getType());
		assertNull(event.getSourceItem());
		assertNull(event.getTargetItem());
	}

	@Test
	public void sourceTargetTest() {
		String source = "source", target = "target";
		PairConnectEvent event = new PairConnectEvent(PairConnectEventTypes.CONNECTED, source, target);
		assertEquals(PairConnectEventTypes.CONNECTED, event.getType());
		assertEquals(event.getSourceItem(), source);
		assertEquals(event.getTargetItem(), target);
		assertNotNull(event.toString());

	}

}
