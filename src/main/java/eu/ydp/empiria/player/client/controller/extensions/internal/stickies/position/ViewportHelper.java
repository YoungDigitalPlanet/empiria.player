package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position;

import com.google.gwt.user.client.Window;

import eu.ydp.gwtutil.client.geom.Rectangle;

public class ViewportHelper {

	Rectangle getViewport() {
		return new Rectangle(Window.getScrollLeft(), Window.getScrollTop(), Window.getClientWidth(), Window.getClientHeight());
	}
}
