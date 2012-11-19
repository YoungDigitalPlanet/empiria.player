package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.event.dom.client.DragEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.DragDropObjectFactory;
import eu.ydp.empiria.player.client.module.IModule;

public class DragDropHelperImpl implements DragDropHelper {

	@Inject
	protected DragDropObjectFactory obFactory;

	@Override
	public DraggableObject<Widget> enableDragForWidget(Widget widget, IModule module) {
		return (DragEvent.isSupported()) ? obFactory.getHTML5DragDrop(widget, module, DragDropType.DRAG, false)
										 : obFactory.getEmulatedDragDrop(widget, DragDropType.DRAG, false);
	}

	@Override
	public <W extends Widget & HasValue<?> & HasChangeHandlers> DroppableObject<W> enableDropForWidget(W widget, IModule module) {
		return enableDropForWidget(widget,module, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <W extends Widget & HasValue<?> & HasChangeHandlers> DroppableObject<W> enableDropForWidget(W widget, IModule module, boolean disableAutoBehavior) {
		return (DroppableObject)  ((DragEvent.isSupported())
								 ? obFactory.getHTML5DragDrop(widget, module, DragDropType.DROP, disableAutoBehavior)
								 : obFactory.getEmulatedDragDrop(widget, DragDropType.DROP,disableAutoBehavior));
	}

}
