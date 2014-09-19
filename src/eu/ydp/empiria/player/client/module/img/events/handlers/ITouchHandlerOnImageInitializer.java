package eu.ydp.empiria.player.client.module.img.events.handlers;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;

public interface ITouchHandlerOnImageInitializer {
	public void addTouchOnImageMoveHandler(final TouchOnImageMoveHandler touchOnImageMoveHandler, Widget listenOn);

	public void addTouchOnImageStartHandler(final TouchOnImageStartHandler touchOnImageStartHandler, Widget listenOn);

	public void addTouchOnImageEndHandler(final TouchOnImageEndHandler touchOnImageEndHandler, Widget listenOn);
}
