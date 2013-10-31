package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.DragDropObjectFactory;

public class DragDropHelperImpl implements DragDropHelper {

	@Inject
	protected DragDropObjectFactory obFactory;

	@SuppressWarnings("unchecked")
	@Override
	public DraggableObject<Widget> enableDragForWidget(Widget widget) {
		return obFactory.getEmulatedDragDrop(widget, DragDropType.DRAG, false);
	}

	@Override
	public <W extends Widget> DroppableObject<W> enableDropForWidget(W widget) {
		return enableDropForWidget(widget, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <W extends Widget> DroppableObject<W> enableDropForWidget(W widget, boolean disableAutoBehavior) {
		return (DroppableObject) obFactory.getEmulatedDragDrop(widget, DragDropType.DROP, disableAutoBehavior);
	}

}
