package eu.ydp.empiria.player.client.module.draggap;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.gap.DropZoneGuardian;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;

public interface DragGapModuleFactory {

	DropZoneGuardian createDropZoneGuardian(@Assisted DroppableObject<?> droppable, @Assisted Widget moduleWidget);
}
