package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.IModule;

public interface DragDropHelper {

	public abstract <W extends Widget> DraggableObject<W> enableDragForWidget(W widget, IModule module);

	public abstract <W extends Widget & HasValue<?> & HasChangeHandlers> DroppableObject<W> enableDropForWidget(W widget, IModule module);

	public abstract <W extends Widget & HasValue<?> & HasChangeHandlers> DroppableObject<W> enableDropForWidget(W widget, IModule module, boolean disableAutoBehavior);
}