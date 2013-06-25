package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.event.dom.client.DragEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.DragDropObjectFactory;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class DragDropHelperImpl implements DragDropHelper {

	@Inject
	protected DragDropObjectFactory obFactory;

	private final static boolean isNativeDragSupported =    DragEvent.isSupported()
														&& !UserAgentChecker.isIE()
														&& !UserAgentChecker.isMobileUserAgent();

	@SuppressWarnings("unchecked")
	@Override
	public DraggableObject<Widget> enableDragForWidget(Widget widget) {
		return (isNativeDragSupported) ? obFactory.getHTML5DragDrop(widget, DragDropType.DRAG, false)
										 : obFactory.getEmulatedDragDrop(widget, DragDropType.DRAG, false);
	}

	@Override
	public <W extends Widget & HasValue<?> & HasChangeHandlers> DroppableObject<W> enableDropForWidget(W widget) {
		return enableDropForWidget(widget, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <W extends Widget & HasValue<?> & HasChangeHandlers> DroppableObject<W> enableDropForWidget(W widget,boolean disableAutoBehavior) {
		return (DroppableObject) ((isNativeDragSupported)
									? obFactory.getHTML5DragDrop(widget, DragDropType.DROP, disableAutoBehavior)
								 	: obFactory.getEmulatedDragDrop(widget, DragDropType.DROP,disableAutoBehavior));
	}

}
