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

package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import com.google.gwt.user.client.ui.HasWidgets;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller.StickieDragController;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller.StickieDragHandlersManager;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller.StickieMinimizeMaximizeController;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.IStickiePresenter;

public interface StickieFactory {

    IStickieView createStickieView(HasWidgets parent, IStickiePresenter presenter, StickieDragHandlersManager stickieDragController);

    IStickiePresenter createStickiePresenter(IStickieProperties stickieProperties, StickieMinimizeMaximizeController minimizeMaximizeController,
                                             StickieRegistration stickieRegistration);

    StickieMinimizeMaximizeController createStickieMinimizeMaximizeController(IStickieProperties stickieProperties);

    StickieDragHandlersManager createStickieDragHandlerManager(StickieDragController stickieDragController);

    StickieDragController createStickieDragController(IStickiePresenter presenter, IStickieProperties properties);
}
