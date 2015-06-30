package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.user.client.ui.Widget;

public interface DragDropHelper {

    abstract <W extends Widget> DraggableObject<W> enableDragForWidget(W widget);

    abstract <W extends Widget> DroppableObject<W> enableDropForWidget(W widget);

    abstract <W extends Widget> DroppableObject<W> enableDropForWidget(W widget, boolean disableAutoBehavior);
}
