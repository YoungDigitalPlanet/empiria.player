package eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer;

import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.shared.EventHandler;

public abstract class PointerEvent<H extends EventHandler> extends MouseEvent<H> {

	public boolean isTouchEvent() {
		return PointerNativeMethods.methods.pointerType(this.getNativeEvent()).equals(PointerEventsConstants.POINTER_TYPE_TOUCH);
	}

	public boolean isPrimary() {
		return PointerNativeMethods.methods.isPrimary(this.getNativeEvent());
	}

	public Integer getPointerId() {
		return PointerNativeMethods.methods.getPointerId(this.getNativeEvent());
	}
}
