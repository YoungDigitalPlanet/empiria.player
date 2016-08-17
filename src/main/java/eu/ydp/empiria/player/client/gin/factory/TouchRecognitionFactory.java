package eu.ydp.empiria.player.client.gin.factory;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.util.events.internal.emulate.handlers.HasTouchHandlers;

public interface TouchRecognitionFactory {

    /**
     * @param listenOn
     * @param emulateClickAsTouch czy emulowac operacje myszka jako touche
     * @return
     */
    HasTouchHandlers getTouchRecognition(@Assisted("listenOn") Widget listenOn, @Assisted("emulateClickAsTouch") Boolean emulateClickAsTouch);

    HasTouchHandlers getTouchRecognition(@Assisted("listenOn") Widget listenOn, @Assisted("emulateClickAsTouch") Boolean emulateClickAsTouch,
                                         @Assisted("globalTouchEnd") Boolean globalTouchEnd);

}
