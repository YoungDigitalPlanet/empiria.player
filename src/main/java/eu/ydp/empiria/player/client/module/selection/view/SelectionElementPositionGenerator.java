package eu.ydp.empiria.player.client.module.selection.view;

import eu.ydp.empiria.player.client.module.selection.model.SelectionGridElementPosition;

public interface SelectionElementPositionGenerator {
    SelectionGridElementPosition getButtonElementPositionFor(int itemIndex, int choiceIndex);

    SelectionGridElementPosition getChoiceLabelElementPosition(int choiceIndex);

    SelectionGridElementPosition getItemLabelElementPosition(int itemIndex);

}
