package eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;

public class PointerNativeMethods {

    public static final PointerNativeMethods methods = GWT.create(PointerNativeMethods.class);

    public native String pointerType(NativeEvent elem) /*-{
        return elem.pointerType;
    }-*/;

    public native boolean isPrimary(NativeEvent elem) /*-{
        return elem.isPrimary;
    }-*/;

    public native Integer getPointerId(NativeEvent elem) /*-{
        return elem.pointerId;
    }-*/;
}
