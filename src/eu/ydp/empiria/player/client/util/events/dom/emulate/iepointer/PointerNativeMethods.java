package eu.ydp.empiria.player.client.util.events.dom.emulate.iepointer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;

public class PointerNativeMethods {

	static final PointerNativeMethods methods = GWT.create(PointerNativeMethods.class);

	public native String pointerType(NativeEvent elem) /*-{
		return elem.pointerType;
	}-*/;
}
