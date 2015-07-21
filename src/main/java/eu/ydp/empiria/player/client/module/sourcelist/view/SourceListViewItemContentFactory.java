package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;

public class SourceListViewItemContentFactory {

    public IsWidget getSourceListViewItemContent(SourcelistItemType type, String content) {
        if (type == SourcelistItemType.IMAGE) {
            return new Image(content);
        } else {
            InlineHTML inlineHTML = new InlineHTML(content);
            inlineHTML.addStyleName("gwt-Label");
            return inlineHTML;
        }
    }
}
