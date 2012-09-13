package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.dom.client.NativeEvent;

public interface TouchHandler {

	public abstract void onTouchMove(NativeEvent event);

	public abstract void onTouchEnd(NativeEvent event);

	public abstract void onTouchStart(NativeEvent event);

}