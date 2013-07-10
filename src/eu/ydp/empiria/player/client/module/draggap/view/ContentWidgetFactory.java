package eu.ydp.empiria.player.client.module.draggap.view;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;

public class ContentWidgetFactory {

	public Widget createContentWidgetById(SourcelistItemValue item) {
		SourcelistItemType type = item.getType();
		
		if(type == SourcelistItemType.IMAGE) {
			return new Image(item.getContent());
		} else {
			return new HTMLPanel(item.getItemId());
		}
	}
	
}
