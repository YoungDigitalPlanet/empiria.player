package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.FlowPanel;

import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.gwtutil.client.collections.KeyValue;

@SuppressWarnings("PMD")
public class PanelCacheTest extends GWTTestCase {
	@Override
	public String getModuleName(){
		 return "eu.ydp.empiria.player.Player";
	}

	public void testPageViewWithSwipeAndWithout() {
		PanelCache cache =  PlayerGinjector.INSTANCE.getPanelCache();
		assertTrue(cache.isEmpty());
		KeyValue<FlowPanel, FlowPanel> value = cache.getOrCreateAndPut(0);
		Style style = value.getKey().getElement().getStyle();
		assertTrue(style.getPosition().equals(Position.ABSOLUTE.getCssName()));
		assertTrue(style.getTop().equals("0px"));
		assertEquals("0.0%", style.getLeft());
		assertEquals("100.0%", style.getWidth());
		value = cache.getOrCreateAndPut(2);
		style = value.getKey().getElement().getStyle();
		assertEquals(Position.ABSOLUTE.getCssName(), style.getPosition());
		assertEquals("0px", style.getTop());
		assertEquals("200.0%", style.getLeft());
		assertEquals("100.0%", style.getWidth());
		cache.setSwipeDisabled(true);
		value = cache.getOrCreateAndPut(3);
		assertNotSame(Position.ABSOLUTE.getCssName(), style.getPosition());
		assertNotSame("0px", style.getTop());
		assertNotSame("200.0%", style.getLeft());
		assertNotSame("100.0%", style.getWidth());

	}

}
