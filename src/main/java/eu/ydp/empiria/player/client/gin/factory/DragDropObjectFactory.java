package eu.ydp.empiria.player.client.gin.factory;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropType;
import eu.ydp.empiria.player.client.util.dom.drag.emulate.EmulatedDragDrop;

public interface DragDropObjectFactory {
    public EmulatedDragDrop<Widget> getEmulatedDragDrop(@Assisted("widget") Widget widget, @Assisted("type") DragDropType type,
                                                        @Assisted("disableAutoBehavior") boolean disableAutoBehavior);
}
