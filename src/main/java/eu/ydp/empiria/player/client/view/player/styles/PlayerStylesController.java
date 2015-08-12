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
    private final StyleNameConstants styleNameConstants;

    @Inject
    public PlayerStylesController(PlayerContentView playerView, CurrentItemStyleProvider styleProvider, StyleNameConstants styleNameConstants, EventsBus eventsBus) {
        this.playerView = playerView;
        this.styleProvider = styleProvider;
        this.styleNameConstants = styleNameConstants;

        eventsBus.addHandler(PlayerEvent.getTypes(PAGE_LOADED, BEFORE_FLOW), this);
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        switch (event.getType()) {
            case BEFORE_FLOW:
                removeTemplateStyle();
                break;
            case PAGE_LOADED:
                addTemplateStyle();
                break;
        }
    }

    private void addTemplateStyle() {
        Optional<String> template = styleProvider.getCurrentItemStyle();
        if (template.isPresent()) {
            String style = fixStyle(template.get());
            playerView.addStyleName(style);
        }
    }

    private void removeTemplateStyle() {
        Optional<String> template = styleProvider.getCurrentItemStyle();
        if (template.isPresent()) {
            String style = fixStyle(template.get());
            playerView.removeStyleName(style);
        }
    }

    private String fixStyle(String string) {
        return styleNameConstants.QP_PAGE_TEMPLATE() + "-" + string;
    }
}
