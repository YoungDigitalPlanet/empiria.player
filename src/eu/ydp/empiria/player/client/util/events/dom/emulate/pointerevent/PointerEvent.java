package eu.ydp.empiria.player.client.util.events.dom.emulate.pointerevent;

import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.shared.EventHandler;

public abstract class PointerEvent<H extends EventHandler> extends MouseEvent<H> {

	public boolean isTouchEvent() {
		return PointerNativeMethods.methods.pointerType(this.getNativeEvent()).equals(PointerEventsConstants.POINTER_TYPE_TOUCH);
	}

}
