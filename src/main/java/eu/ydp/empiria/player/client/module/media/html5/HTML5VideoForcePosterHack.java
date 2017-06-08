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

package eu.ydp.empiria.player.client.module.media.html5;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.AbstractHTML5MediaExecutor;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

/**
 * Safari for iOS has a known problem with the poster attribute on the video tags. Potentially this solution can be used also for other browsers, on which the
 * similar problem will be appearing.
 */
public class HTML5VideoForcePosterHack implements MediaEventHandler {

    private Element posterImageElement;
    private boolean isPosterCreated = false;
    private final MediaBase mediaBase;
    private final HTML5MediaExecutorDelegator mediaExecutorDelegator;

    public HTML5VideoForcePosterHack(MediaBase mediaBase, HTML5MediaExecutorDelegator mediaExecutor) {
        this.mediaBase = mediaBase;
        this.mediaExecutorDelegator = mediaExecutor;
    }

    @Override
    public void onMediaEvent(MediaEvent event) {
        MediaEventTypes eventType = event.getType();
        if (MediaEventTypes.ON_PLAY.equals(eventType)) {
            hidePosterImage();
        } else if (MediaEventTypes.SUSPEND.equals(eventType) || MediaEventTypes.CAN_PLAY.equals(eventType)) {
            createPosterImageOnParentElementOnce();
        }
    }

    private void createPosterImageOnParentElementOnce() {
        if (isPosterCreated) {
            return;
        }

        Widget parent = mediaBase.getParent();
        Element parentElement = parent.getElement();

        if (parentElement != null) {
            AbstractHTML5MediaExecutor executor = mediaExecutorDelegator.getExecutor();
            BaseMediaConfiguration baseMediaConfiguration = executor.getBaseMediaConfiguration();
            createPosterImageLayer(parentElement, baseMediaConfiguration);
            isPosterCreated = true;
        }
    }

    private void hidePosterImage() {
        posterImageElement.getStyle().setVisibility(Visibility.HIDDEN);
    }

    private void createPosterImageLayer(Element mediaContainerElement, BaseMediaConfiguration baseMediaConfiguration) {
        posterImageElement = DOM.createImg();
        DOM.setImgSrc(posterImageElement, baseMediaConfiguration.getPoster());
        posterImageElement.getStyle().setLeft(0, Unit.PX);
        posterImageElement.getStyle().setTop(0, Unit.PX);
        posterImageElement.getStyle().setPosition(Position.ABSOLUTE);
        posterImageElement.getStyle().setWidth(baseMediaConfiguration.getWidth(), Unit.PX);
        posterImageElement.getStyle().setHeight(baseMediaConfiguration.getHeight(), Unit.PX);
        posterImageElement.getStyle().setPadding(0, Unit.PX);
        posterImageElement.getStyle().setMargin(0, Unit.PX);
        posterImageElement.getStyle().setVisibility(Visibility.VISIBLE);

        mediaContainerElement.appendChild(posterImageElement);
    }
}
