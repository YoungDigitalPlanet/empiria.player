package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.event.dom.client.DragEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.DragDropObjectFactory;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent;

public class DragDropHelperImpl implements DragDropHelper {

	@Inject
	protected DragDropObjectFactory obFactory;

	private boolean isNativeDragSupported(){
		//ie sprawie za duzo problemow przy dragu domyslnie emulowany
		return DragEvent.isSupported() && !UserAgentChecker.isUserAgent(UserAgent.IE8,UserAgent.IE9);
	}
	@SuppressWarnings("unchecked")
	@Override
	public DraggableObject<Widget> enableDragForWidget(Widget widget, IModule module) {
		return (isNativeDragSupported()) ? obFactory.getHTML5DragDrop(widget, module, DragDropType.DRAG, false)
										 : obFactory.getEmulatedDragDrop(widget,module, DragDropType.DRAG, false);
	}

	@Override
	public <W extends Widget & HasValue<?> & HasChangeHandlers> DroppableObject<W> enableDropForWidget(W widget, IModule module) {
		return enableDropForWidget(widget,module, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <W extends Widget & HasValue<?> & HasChangeHandlers> DroppableObject<W> enableDropForWidget(W widget, IModule module, boolean disableAutoBehavior) {
		return (DroppableObject) ((isNativeDragSupported())
									? obFactory.getHTML5DragDrop(widget, module, DragDropType.DROP, disableAutoBehavior)
								 	: obFactory.getEmulatedDragDrop(widget,module, DragDropType.DROP,disableAutoBehavior));
	}

}
