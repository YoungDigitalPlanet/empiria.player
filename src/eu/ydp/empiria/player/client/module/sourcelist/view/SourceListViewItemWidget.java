package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;

public class SourceListViewItemWidget extends FlowPanel {
	private final SourceListViewItemContentFactory contentFactory = new SourceListViewItemContentFactory();

	public SourceListViewItemWidget(SourcelistItemType sourcelistItemType, String itemContent, String styleName) {
		setStyleName(styleName);
		IsWidget content = contentFactory.getSourceListViewItemContent(sourcelistItemType, itemContent);
		add(content);
	}

	public int getWidth() {
		return getElement().getOffsetWidth();
	}

	public int getHeight() {
		return getElement().getOffsetHeight();
	}

}
