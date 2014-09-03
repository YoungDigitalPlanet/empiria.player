package eu.ydp.empiria.player.client.module.img.handlers;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.img.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.module.img.handlers.touchonimage.TouchOnImageStartHandler;

public interface ITouchHandlerOnImageInitializer {
	public void addTouchMoveHandler(final TouchOnImageMoveHandler touchOnMoveHandler, Widget listenOn);

	public void addTouchStartHandler(final TouchOnImageStartHandler touchStartHandler, Widget listenOn);

	public void addTouchEndHandler(final TouchOnImageEndHandler touchEndHandler, Widget listenOn);
}
