/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
