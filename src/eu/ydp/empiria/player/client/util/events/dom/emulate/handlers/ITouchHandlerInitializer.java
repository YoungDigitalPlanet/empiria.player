package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnCancelHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;

public interface ITouchHandlerInitializer<E> {
	public void addTouchMoveHandler(final TouchOnMoveHandler<E> touchOnMoveHandler, Widget listenOn);

	public void addTouchStartHandler(final TouchOnStartHandler<E> touchStartHandler, Widget listenOn);

	public void addTouchEndHandler(final TouchOnEndHandler<E> touchEndHandler, Widget listenOn);

	public void addTouchCancelHandler(final TouchOnCancelHandler<E> touchCancelHandler, Widget listenOn);
}
