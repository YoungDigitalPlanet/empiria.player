package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerDownHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerMoveHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.pointer.PointerUpHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touch.TouchEndHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touch.TouchMoveHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touch.TouchStartHandlerOnImage;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageEndHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageMoveHandler;
import eu.ydp.empiria.player.client.module.img.events.handlers.touchonimage.TouchOnImageStartHandler;

public interface TouchHandlerFactory {
	PointerMoveHandlerOnImage createPointerMoveHandlerOnImage(TouchOnImageMoveHandler touchOnMoveHandler);

	PointerDownHandlerOnImage createPointerDownHandlerOnImage(TouchOnImageStartHandler touchOnStartHandler);

	PointerUpHandlerOnImage createPointerUpHandlerOnImage(TouchOnImageEndHandler touchOnEndHandler);

	TouchMoveHandlerOnImage createTouchMoveHandlerOnImage(TouchOnImageMoveHandler touchOnMoveHandler);

	TouchStartHandlerOnImage createTouchStartHandlerOnImage(TouchOnImageStartHandler touchStartHandler);

	TouchEndHandlerOnImage createTouchEndHandlerOnImage(TouchOnImageEndHandler touchEndHandler);

}
