package eu.ydp.empiria.player.client.view.player.styles;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.data.AssessmentDataSourceManager;
import eu.ydp.empiria.player.client.resources.PageStyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;

import java.util.HashMap;
import java.util.Map;

import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.ASSESSMENT_LOADED;

@Singleton
public class ItemStylesContainer implements PlayerEventHandler {

    private final Map<String, String> pageStyles = new HashMap<>();
    private final PageStyleNameConstants pageStyleNameConstants;
    private final AssessmentDataSourceManager dataSourceManager;

    @Inject
    public ItemStylesContainer(EventsBus eventsBus, AssessmentDataSourceManager dataSourceManager, PageStyleNameConstants pageStyleNameConstants) {
        this.dataSourceManager = dataSourceManager;
        this.pageStyleNameConstants = pageStyleNameConstants;

        eventsBus.addHandler(PlayerEvent.getTypes(ASSESSMENT_LOADED), this);
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        Map<String, String> styles = dataSourceManager.getPageIdToStyleMap();
        for (Map.Entry<String, String> entry : styles.entrySet()) {
            String id = entry.getKey();
            String style = entry.getValue();
            mapItemToStyle(id, style);
        }
    }

    public Optional<String> getStyle(String itemIdentifier) {
        String style = pageStyles.get(itemIdentifier);
        return Optional.fromNullable(style);
    }

    private void mapItemToStyle(String id, String style) {
        if (!Strings.isNullOrEmpty(style)) {
            String correctStyle = constructStyle(style);
            pageStyles.put(id, correctStyle);
        }
    }

    private String constructStyle(String string) {
        return pageStyleNameConstants.QP_PAGE_TEMPLATE() + "-" + string;
    }
}
