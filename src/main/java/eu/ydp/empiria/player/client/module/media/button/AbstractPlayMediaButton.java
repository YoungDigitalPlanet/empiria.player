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

package eu.ydp.empiria.player.client.module.media.button;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;

public abstract class AbstractPlayMediaButton extends AbstractMediaButton {

    @Inject
    protected EventsBus eventsBus;
    @Inject
    private PageScopeFactory pageScopeFactory;

    public AbstractPlayMediaButton(String baseStyleName) {
        super(baseStyleName);
    }

    protected abstract MediaEvent createMediaEvent();

    protected abstract boolean initButtonStyleChangeHandlersCondition();

    @Override
    public void init() {
        super.init();
        if (initButtonStyleChangeHandlersCondition()) {
            initButtonStyleChangeHandlers();
        }
    }

    @Override
    public boolean isSupported() {
        return getMediaAvailableOptions().isPlaySupported();
    }

    protected void initButtonStyleChangeHandlers() {
        MediaEventHandler handler = createButtonActivationHandler();
        CurrentPageScope scope = createCurrentPageScope();
        addMediaEventHandlers(handler, scope);
    }

    protected CurrentPageScope createCurrentPageScope() {
        return pageScopeFactory.getCurrentPageScope();
    }

    @Override
    protected void onClick() {
        MediaEvent mediaEvent = createMediaEvent();
        eventsBus.fireEventFromSource(mediaEvent, getMediaWrapper());
    }

    private void addMediaEventHandlers(MediaEventHandler handler, CurrentPageScope scope) {
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PAUSE), getMediaWrapper(), handler, scope);
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), getMediaWrapper(), handler, scope);
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_STOP), getMediaWrapper(), handler, scope);
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), getMediaWrapper(), handler, scope);
    }

    private MediaEventHandler createButtonActivationHandler() {
        return new MediaEventHandler() {
            @Override
            public void onMediaEvent(MediaEvent event) {
                if (event.getType() == MediaEventTypes.ON_PLAY) {
                    setActive(true);
                } else {
                    setActive(false);
                }
                changeStyleForClick();
            }
        };
    }
}
