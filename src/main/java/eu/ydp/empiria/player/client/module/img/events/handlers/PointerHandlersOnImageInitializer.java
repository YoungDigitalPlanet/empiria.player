package eu.ydp.empiria.player.client.module.img.events.handlers;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.TouchHandlerFactory;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerDownHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerMoveHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerUpHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerDownEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerMoveEvent;
import eu.ydp.empiria.player.client.util.events.internal.emulate.events.pointer.PointerUpEvent;

public class PointerHandlersOnImageInitializer implements ITouchHandlerOnImageInitializer {

	@Inject
	private TouchHandlerFactory touchHandlerFactory;

	@Override
	public void addTouchOnImageMoveHandler(final TouchOnImageMoveHandler touchOnImageMoveHandler, Widget listenOn) {
		PointerMoveHandlerOnImage pointerOnImageMoveHandlerOnImage = touchHandlerFactory.createPointerMoveHandlerOnImage(touchOnImageMoveHandler);
		listenOn.addDomHandler(pointerOnImageMoveHandlerOnImage, PointerMoveEvent.getType());
	}

	@Override
	public void addTouchOnImageStartHandler(final TouchOnImageStartHandler touchOnImageStartHandler, Widget listenOn) {
		PointerDownHandlerOnImage pointerDownHandlerOnImage = touchHandlerFactory.createPointerDownHandlerOnImage(touchOnImageStartHandler);
		listenOn.addDomHandler(pointerDownHandlerOnImage, PointerDownEvent.getType());
	}

	@Override
	public void addTouchOnImageEndHandler(final TouchOnImageEndHandler touchOnImageEndHandler, Widget listenOn) {
		PointerUpHandlerOnImage pointerUpHandlerOnImage = touchHandlerFactory.createPointerUpHandlerOnImage(touchOnImageEndHandler);
		listenOn.addDomHandler(pointerUpHandlerOnImage, PointerUpEvent.getType());
	}
}
