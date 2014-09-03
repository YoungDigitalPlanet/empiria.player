package eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer;

import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.PointerEventsCoordinates;

public abstract class PointerEvent<H extends EventHandler> extends MouseEvent<H> {

	@Inject
	private PointerEventsCoordinates pointerEventsCoordinates;

	public boolean isTouchEvent() {
		return PointerNativeMethods.methods.pointerType(this.getNativeEvent()).equals(PointerEventsConstants.POINTER_TYPE_TOUCH);
	}

	public boolean isPrimary() {
		return PointerNativeMethods.methods.isPrimary(this.getNativeEvent());
	}

	public Long getPointerId() {
		return PointerNativeMethods.methods.getPointerId(this.getNativeEvent());
	}

	public PointerEventsCoordinates getTouchesManager() {
		return this.pointerEventsCoordinates;
	}

	@Override
	public int hashCode() {
		int code = 1;
		code = 31 * code + this.getClientX();
		code = 31 * code + this.getClientY();
		return code;
	}

}
