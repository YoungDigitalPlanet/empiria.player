package eu.ydp.empiria.player.client.module.textentry;

import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;

public class DragContentController {

    public String getTextFromItemAppropriateToType(SourcelistItemValue item) {
        SourcelistItemType itemType = item.getType();

        if (itemType == SourcelistItemType.IMAGE) {
            return item.getItemId();
        } else {
            return item.getContent();
        }
    }

}
