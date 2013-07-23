package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;

import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;

public class SourceListViewItemContentFactory {

	public IsWidget getSourceListViewItemContent(SourcelistItemType type, String content) {
		IsWidget widget;
		if (type == SourcelistItemType.IMAGE) {
			widget = new Image(content);
		} else {
			widget = new Label(content);
		}
		return widget;
	}
}
