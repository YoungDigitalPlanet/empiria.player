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

package eu.ydp.empiria.player.client.view.player.styles;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.view.player.PlayerContentView;

import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.BEFORE_FLOW;
import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.PAGE_LOADED;

public class PlayerStylesController implements PlayerEventHandler {
    private final PlayerContentView playerView;
    private final CurrentItemStyleProvider styleProvider;

    @Inject
    public PlayerStylesController(PlayerContentView playerView, CurrentItemStyleProvider styleProvider, EventsBus eventsBus) {
        this.playerView = playerView;
        this.styleProvider = styleProvider;

        eventsBus.addHandler(PlayerEvent.getTypes(PAGE_LOADED, BEFORE_FLOW), this);
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        Optional<String> template = styleProvider.getCurrentItemStyle();
        if (template.isPresent()) {
            String style = template.get();
            toggleStyle(event, style);
        }
    }

    private void toggleStyle(PlayerEvent event, String style) {
        switch (event.getType()) {
            case BEFORE_FLOW:
                playerView.removeStyleName(style);
                break;
            case PAGE_LOADED:
                playerView.addStyleName(style);
                break;
        }
    }
}
