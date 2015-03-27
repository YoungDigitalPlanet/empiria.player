package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;

public class SourceListViewItemContentFactoryTest extends AbstractEmpiriaPlayerGWTTestCase {

	public void testSourceListViewItemContent() throws Exception {
		SourceListViewItemContentFactory instance = new SourceListViewItemContentFactory();
		String imageUrl = "http://j/j.jpg";
		IsWidget widget = instance.getSourceListViewItemContent(SourcelistItemType.IMAGE, imageUrl);
		assertTrue(widget instanceof Image);
		assertEquals(imageUrl, ((Image) widget).getUrl());

		String text = "text";
		widget = instance.getSourceListViewItemContent(SourcelistItemType.TEXT, text);
		assertTrue(widget instanceof Label);
		assertEquals(text, ((Label) widget).getText());

	}

}
