package eu.ydp.empiria.player.client.controller.window;

import com.google.gwt.event.logical.shared.*;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.proxy.WindowDelegate;

public class WindowResizeController implements ResizeHandler {

	private final WindowResizeTimer windowResizeTimer;

	@Inject
	public WindowResizeController(WindowResizeTimer windowResizeTimer, WindowDelegate windowDelegate) {
		this.windowResizeTimer = windowResizeTimer;
		windowDelegate.addResizeHandler(this);
	}

	@Override
	public void onResize(ResizeEvent event) {
		windowResizeTimer.schedule(250);
	}
}
