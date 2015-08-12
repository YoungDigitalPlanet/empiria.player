package eu.ydp.empiria.player.client.view.player.styles;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

import java.util.HashMap;
import java.util.Map;

import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.ASSESSMENT_LOADED;

public class ItemStylesContainer implements PlayerEventHandler {

    private final Map<String, String> pageStyles;
    private final DataSourceManager dataManager;

    @Inject
    public ItemStylesContainer(EventsBus eventsBus, DataSourceManager dataManager) {
        this.dataManager = dataManager;
        this.pageStyles = new HashMap<>();

        eventsBus.addHandler(PlayerEvent.getTypes(ASSESSMENT_LOADED), this);
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        NodeList itemsList = getItemsList();
        for (int i = 0; i < itemsList.getLength(); i++) {
            Element item = (Element) itemsList.item(i);
            String identifier = item.getAttribute("identifier");
            String style = item.getAttribute("class");
            mapItemToStyle(identifier, style);
        }
    }

    private NodeList getItemsList() {
        XmlData xmlData = dataManager.getAssessmentData().getData();
        return xmlData.getDocument().getElementsByTagName("assessmentItemRef");
    }

    private void mapItemToStyle(String identifier, String style) {
        if (!Strings.isNullOrEmpty(style)) {
            pageStyles.put(identifier, style);
        }
    }

    public Optional<String> getStyle(String itemIdentifier) {
        String style = pageStyles.get(itemIdentifier);
        return Optional.fromNullable(style);
    }
}
