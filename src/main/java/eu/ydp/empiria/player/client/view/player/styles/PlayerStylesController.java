package eu.ydp.empiria.player.client.view.player.styles;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
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
            setStyles(event, style);
        }
    }

    private void setStyles(PlayerEvent event, String style) {
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
