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

package eu.ydp.empiria.player.client.module.accordion.controller;

import com.google.common.base.Optional;
import com.google.gwt.query.client.impl.ConsoleBrowser;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.accordion.presenter.AccordionSectionPresenter;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;

import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.PAGE_CONTENT_DECREASED;
import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.PAGE_CONTENT_GROWN;
import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.WINDOW_RESIZED;

public class AccordionController<T extends AccordionHideController> implements PlayerEventHandler {

    private Optional<AccordionSectionPresenter> currentSection;
    private T hideController;

    @Inject
    public AccordionController(T hideController, EventsBus eventsBus, CurrentPageScope currentPageScope) {
        this.hideController = hideController;
        currentSection = Optional.absent();
        eventsBus.addHandler(PlayerEvent.getTypes(WINDOW_RESIZED, PAGE_CONTENT_GROWN, PAGE_CONTENT_DECREASED), this, currentPageScope);
    }

    private void show(AccordionSectionPresenter section) {
        if (currentSection.isPresent()) {
            hideController.hide(currentSection.get());
        }

        section.show();
        currentSection = Optional.of(section);
    }

    public void onClick(AccordionSectionPresenter section) {
        if (isCurrentSection(section)) {
            hideController.hide(currentSection.get());
            currentSection = Optional.absent();
        } else {
            show(section);
        }
    }

    private boolean isCurrentSection(AccordionSectionPresenter section) {
        return currentSection.isPresent() && currentSection.get() == section;
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        if (currentSection.isPresent()) {
            currentSection.get().show();
        }
    }
}
