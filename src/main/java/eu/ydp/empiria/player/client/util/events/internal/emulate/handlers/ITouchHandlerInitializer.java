package eu.ydp.empiria.player.client.util.events.internal.emulate.handlers;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnCancelHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.touchon.TouchOnStartHandler;

public interface ITouchHandlerInitializer {
	public void addTouchMoveHandler(final TouchOnMoveHandler touchOnMoveHandler, Widget listenOn);

	public void addTouchStartHandler(final TouchOnStartHandler touchStartHandler, Widget listenOn);

	public void addTouchEndHandler(final TouchOnEndHandler touchEndHandler, Widget listenOn);

	public void addTouchCancelHandler(final TouchOnCancelHandler touchCancelHandler, Widget listenOn);
}
