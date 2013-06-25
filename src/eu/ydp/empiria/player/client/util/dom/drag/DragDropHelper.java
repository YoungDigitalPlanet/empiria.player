package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public interface DragDropHelper {

	abstract <W extends Widget> DraggableObject<W> enableDragForWidget(W widget);

	abstract <W extends Widget & HasValue<?> & HasChangeHandlers> DroppableObject<W> enableDropForWidget(W widget);

	abstract <W extends Widget & HasValue<?> & HasChangeHandlers> DroppableObject<W> enableDropForWidget(W widget, boolean disableAutoBehavior);
}