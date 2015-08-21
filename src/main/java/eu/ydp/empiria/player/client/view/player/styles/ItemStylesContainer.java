package eu.ydp.empiria.player.client.view.player.styles;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

import java.util.HashMap;
import java.util.Map;

import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.ASSESSMENT_LOADED;

@Singleton
public class ItemStylesContainer implements PlayerEventHandler {

    private static final String IDENTIFIER = "identifier";
    private static final String CLASS ="class";
    private static final String ASSESSMENT_ITEM_REF = "assessmentItemRef";

    private final Map<String, String> pageStyles;
    private final DataSourceManager dataManager;
    private final StyleNameConstants styleNameConstants;

    @Inject
    public ItemStylesContainer(EventsBus eventsBus, DataSourceManager dataManager, StyleNameConstants styleNameConstants) {
        this.dataManager = dataManager;
        this.styleNameConstants = styleNameConstants;
        this.pageStyles = new HashMap<>();

        eventsBus.addHandler(PlayerEvent.getTypes(ASSESSMENT_LOADED), this);
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        NodeList itemsList = getItemsList();
        for (int i = 0; i < itemsList.getLength(); i++) {
            Element item = (Element) itemsList.item(i);
            String identifier = item.getAttribute(IDENTIFIER);
            String style = item.getAttribute(CLASS);
            mapItemToStyle(identifier, style);
        }
    }

    private NodeList getItemsList() {
        XmlData xmlData = dataManager.getAssessmentData().getData();
        return xmlData.getDocument().getElementsByTagName(ASSESSMENT_ITEM_REF);
    }

    private void mapItemToStyle(String identifier, String style) {
        if (!Strings.isNullOrEmpty(style)) {
            String correctStyle = constructStyle(style);
            pageStyles.put(identifier, correctStyle);
        }
    }

    public Optional<String> getStyle(String itemIdentifier) {
        String style = pageStyles.get(itemIdentifier);
        return Optional.fromNullable(style);
    }
    private String constructStyle(String string) {
        return styleNameConstants.QP_PAGE_TEMPLATE() + "-" + string;
    }
}
