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

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.HandlerRegistration;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public abstract class AbstractMediaScroll extends AbstractMediaController {
    private boolean pressed = false;
    private boolean mediaReady = false;
    private boolean initialized;
    private HandlerRegistration durationchangeHandlerRegistration;
    @Inject
    private EventsBus eventsBus;
    @Inject
    private PageScopeFactory pageScopeFactory;

    /**
     * metoda wywolywana gdy pojawi sie jedno z obslugiwanych zdarzen
     *
     * @param event
     */
    protected abstract void setPosition(NativeEvent event);

    @Override
    public void onBrowserEvent(com.google.gwt.user.client.Event event) {
        switch (event.getTypeInt()) {
            case Event.ONMOUSEDOWN:
            case Event.ONTOUCHSTART:
                // rezerwujemy touch dla siebie nic innego nie powinno obslugiwac tego zdarzenia np TouchPageSwitch
                eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));
                pressed = true;
                setPosition(event);
                break;
            case Event.ONTOUCHEND:
            case Event.ONMOUSEUP:
                pressed = false;
                setPosition(event);
                break;
            case Event.ONTOUCHMOVE:
            case Event.ONMOUSEMOVE:
                setPosition(event);
            default:
                break;
        }
        super.onBrowserEvent(event);
    }

    @Override
    public void init() {
        if (!initialized) {
            initialized = true;
            if (isSupported()) {
                if (UserAgentChecker.isMobileUserAgent()) {
                    sinkEvents(Event.TOUCHEVENTS);
                } else {
                    sinkEvents(Event.ONMOUSEMOVE | Event.ONMOUSEDOWN | Event.ONMOUSEUP);
                }
                RootPanel.get().addDomHandler(new MouseUpHandler() {
                    @Override
                    public void onMouseUp(MouseUpEvent event) {
                        if (isPressed()) { // NOPMD
                            pressed = false;
                            setPosition(event.getNativeEvent());
                            event.stopPropagation();
                        }
                    }
                }, MouseUpEvent.getType());
            }
            // PlayerEventsBus.
            // czekamy na informacje na temat dlugosci utworu
            MediaEventHandler handler = new MediaEventHandler() {
                @Override
                public void onMediaEvent(MediaEvent event) {
                    mediaReady = true;
                    durationchangeHandlerRegistration.removeHandler();
                }
            };
            durationchangeHandlerRegistration = eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), getMediaWrapper(),
                    handler, pageScopeFactory.getCurrentPageScope());
        }

    }

    /**
     * Czy przycisk myszy jest wcisniety
     *
     * @return
     */
    public boolean isPressed() {
        return pressed;
    }

    /**
     * Czy multimedia zostaly zaladowane
     *
     * @return
     */
    public boolean isMediaReady() {
        return mediaReady || getMediaWrapper().canPlay();
    }
}
