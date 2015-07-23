package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;

public class SourceListViewItemContentFactoryGWTTestCase extends AbstractEmpiriaPlayerGWTTestCase {
    SourceListViewItemContentFactory instance = new SourceListViewItemContentFactory();

    public void testWidgetShouldBeImageType() throws Exception {
        String imageUrl = "http://j/j.jpg";
        IsWidget widget = instance.createSourceListContentWidget(SourcelistItemType.IMAGE, imageUrl, null);
        assertTrue(widget instanceof Image);
        assertEquals(imageUrl, ((Image) widget).getUrl());
    }

    public void testWidgetShouldBeLabelType() {
        String text = "text";
        IsWidget widget = instance.createSourceListContentWidget(SourcelistItemType.TEXT, text, null);
        assertTrue(widget instanceof Label);
        assertEquals(text, ((Label) widget).getText());
    }
}
