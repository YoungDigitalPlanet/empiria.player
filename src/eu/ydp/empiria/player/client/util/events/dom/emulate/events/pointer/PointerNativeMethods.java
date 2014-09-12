package eu.ydp.empiria.player.client.util.events.dom.emulate.events.pointer;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.NativeEvent;

public class PointerNativeMethods {

	public static final PointerNativeMethods methods = GWT.create(PointerNativeMethods.class);

	public native String pointerType(NativeEvent elem) /*-{
		return elem.pointerType;
	}-*/;

	public native boolean isPrimary(NativeEvent elem) /*-{
		return elem.isPrimary;
	}-*/;

	public native Long getPointerId(NativeEvent elem) /*-{
		return elem.pointerId;
	}-*/;
}
