package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGenerator;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;

public class SourceListViewItemContentFactoryGWTTestCase extends AbstractEmpiriaPlayerGWTTestCase {
    SourceListViewItemContentFactory instance = new SourceListViewItemContentFactory();

    public void testWidgetShouldBeImageType() throws Exception {
        String imageUrl = "http://j/j.jpg";
        IsWidget widget = instance.getSourceListViewItemContent(SourcelistItemType.IMAGE, imageUrl, null);
        assertTrue(widget instanceof Image);
        assertEquals(imageUrl, ((Image) widget).getUrl());
    }

    public void testWidgetShouldBeInlineHtmlType() {
        String text = "text";
        IsWidget widget = instance.getSourceListViewItemContent(SourcelistItemType.TEXT, text, null);
        assertTrue(widget instanceof InlineHTML);
        assertEquals(text, ((InlineHTML) widget).getText());
    }
}
