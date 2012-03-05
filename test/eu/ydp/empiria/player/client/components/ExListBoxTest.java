package eu.ydp.empiria.player.client.components;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.InlineHTML;

public class ExListBoxTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "eu.ydp.empiria.player.Player";
	}

	public void testSelected(){
		ExListBox elb = new ExListBox();
		elb.addOption(new InlineHTML(), new InlineHTML());
		elb.addOption(new InlineHTML(), new InlineHTML());
		elb.setSelectedIndex(0);
		assertEquals(0, elb.getSelectedIndex());
		elb.setSelectedIndex(1);
		assertEquals(1, elb.getSelectedIndex());
		elb.setSelectedIndex(2);
		assertEquals(1, elb.getSelectedIndex());
		elb.setSelectedIndex(-1);
		assertEquals(-1, elb.getSelectedIndex());
	}

	public void testEnabled(){
		ExListBox elb = new ExListBox();
		elb.addOption(new InlineHTML(), new InlineHTML());
		elb.addOption(new InlineHTML(), new InlineHTML());
		elb.setSelectedIndex(0);
		elb.setEnabled(true);
		assertTrue(elb.getEnabled());
		elb.setEnabled(false);
		assertFalse(elb.getEnabled());
		elb.setEnabled(true);
		assertTrue(elb.getEnabled());
		elb.setEnabled(false);
		assertFalse(elb.getEnabled());
	}
}
