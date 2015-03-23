package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.user.client.ui.*;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;

public class SourceListViewItemContentFactory {

	public IsWidget getSourceListViewItemContent(SourcelistItemType type, String content) {
		IsWidget widget;
		if (type == SourcelistItemType.IMAGE) {
			widget = new Image(content);
		} else {
			widget = new InlineHTML(content);
		}
		return widget;
	}
}
