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

package eu.ydp.empiria.player.client.controller.data;

import com.google.common.base.Optional;
import com.google.gwt.xml.client.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.communication.AssessmentData;
import eu.ydp.empiria.player.client.controller.data.events.AssessmentDataLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.events.SkinDataLoaderListener;
import eu.ydp.empiria.player.client.controller.data.library.LibraryLink;
import eu.ydp.empiria.player.client.controller.style.StyleLinkDeclaration;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.util.localisation.LocalePublisher;
import eu.ydp.empiria.player.client.util.localisation.LocaleVariable;
import eu.ydp.gwtutil.client.debug.log.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class AssessmentDataSourceManager implements SkinDataLoaderListener {

    private static final String ASSESSMENT_ITEM_NODE = "assessmentItem";
    private static final String TEST_VIEW_NODE = "testView";
    private static final String ASSESSMENT_ITEM_REF_NODE = "assessmentItemRef";
    private static final String ASSESSMENT_TEST_NODE = "assessmentTest";
    private static final String EXTENSIONS_LIBRARY_NODE = "extensionsLibrary";
    private static final String STYLE_DECLARATION_NODE = "styleDeclaration";
    private static final String GTM_ATTR = "gtm";
    private static final String HREF_ATTR = "href";
    private static final String TITLE_ATTR = "title";
    private static final String IDENTIFIER_ATTR = "identifier";
    private static final String CLASS_ATTR = "class";

    public AssessmentDataSourceManager() {
        itemsCount = -1;
        errorMessage = "";
        skinData = new SkinDataSourceManager(this);
    }

    private XmlData data;
    private AssessmentData assessmentData;
    private AssessmentDataLoaderEventListener listener;
    private StyleLinkDeclaration styleDeclaration;
    private int itemsCount;
    private String errorMessage;
    private LibraryLink libraryLink;
    private final SkinDataSourceManager skinData;
    private boolean isDefaultData;
    private List<Element> items = null;

    @Inject
    private WorkModeParserForAssessment workModeParser;
    @Inject
    private PlayerWorkModeService playerWorkModeService;
    @Inject
    private Logger logger;

    public void setSkinListener(AssessmentDataLoaderEventListener listener) {
        this.listener = listener;
    }

    public void initializeAssessmentData(XmlData data) {
        if (isItemDocument(data.getDocument())) {
            isDefaultData = true;
            initializeDefaultData();
        } else {
            isDefaultData = false;
            initializeData(data);
        }
    }

    public void setAssessmentData(XmlData data) {
        this.data = data;
        this.assessmentData = new AssessmentData(data, null);
    }

    public void setAssessmentLoadingError(String err) {
        String detail = "";
        if (err != null && err.contains(":")) {
            detail = err.substring(0, err.indexOf(':'));
        }
        errorMessage = LocalePublisher.getText(LocaleVariable.ERROR_ASSESSMENT_FAILED_TO_LOAD) + detail;
    }

    private void initializeDefaultData() {
        data = new XmlData(XMLParser.parse("<assessmentTest title=\"\"/>"), "");
        itemsCount = 1;
        styleDeclaration = new StyleLinkDeclaration(data.getDocument()
                .getElementsByTagName(STYLE_DECLARATION_NODE), data.getBaseURL());
        listener.onAssessmentDataLoaded();
    }

    private void initializeData(XmlData xmlData) {
        String skinUrl = getSkinUrl(xmlData.getDocument());

        data = xmlData;
        itemsCount = -1;
        styleDeclaration = new StyleLinkDeclaration(data.getDocument()
                .getElementsByTagName(STYLE_DECLARATION_NODE), data.getBaseURL());
        libraryLink = new LibraryLink(data.getDocument()
                .getElementsByTagName(EXTENSIONS_LIBRARY_NODE), data.getBaseURL());
        setWorkMode();

        if (skinUrl == null) {
            assessmentData = new AssessmentData(data, null);
            listener.onAssessmentDataLoaded();
        } else {
            skinUrl = data.getBaseURL()
                    .concat(skinUrl);

            skinData.load(skinUrl);
        }
    }

    private void setWorkMode() {
        Optional<PlayerWorkMode> workMode = workModeParser.parse(data);
        if (workMode.isPresent()) {
            playerWorkModeService.tryToUpdateWorkMode(workMode.get());
        }
    }

    public AssessmentData getAssessmentData() {
        return assessmentData;
    }

    public boolean isError() {
        return errorMessage.length() > 0;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean hasLibrary() {
        return libraryLink.hasLink();
    }

    public String getLibraryLink() {
        return libraryLink.getLink();
    }

    public boolean isDefaultData() {
        return isDefaultData;
    }

    public String getAssessmentTitle() {
        String title = "";
        if (data != null) {
            try {
                Element rootNode = getFirstElementByName(ASSESSMENT_TEST_NODE);
                title = rootNode.getAttribute(TITLE_ATTR);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return title;
    }

    public String[] getItemUrls() {

        String[] itemUrls = new String[0];

        if (data != null) {
            try {
                NodeList nodes = data.getDocument()
                        .getElementsByTagName(ASSESSMENT_ITEM_REF_NODE);
                String[] tmpItemUrls = new String[nodes.getLength()];
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node itemRefNode = nodes.item(i);

                    if (((Element) itemRefNode).getAttribute(HREF_ATTR)
                            .startsWith("http")) {
                        tmpItemUrls[i] = ((Element) itemRefNode).getAttribute(HREF_ATTR);
                    } else {
                        tmpItemUrls[i] = data.getBaseURL() + ((Element) itemRefNode).getAttribute(HREF_ATTR);
                    }
                }
                itemUrls = tmpItemUrls;
            } catch (Exception e) {
                logger.error(e);
            }
        }

        return itemUrls;
    }

    public Map<String, String> getPageIdToStyleMap() {
        Map<String, String> map = new HashMap<>();

        NodeList itemsList = getItemsList();
        for (int i = 0; i < itemsList.getLength(); i++) {
            Element item = (Element) itemsList.item(i);
            String identifier = item.getAttribute(IDENTIFIER_ATTR);
            String style = item.getAttribute(CLASS_ATTR);
            map.put(identifier, style);
        }
        return map;
    }

    private NodeList getItemsList() {
        return data.getDocument().getElementsByTagName(ASSESSMENT_ITEM_REF_NODE);
    }

    /**
     * Zwraca wezel assessmentItemRef o wskazanym id
     *
     * @param index index wezla
     * @return Element lub null gdy element o podanym indeksie nie istnieje
     */
    public Element getItem(int index) {
        if (data != null && items == null) {
            try {
                items = new ArrayList<>();
                NodeList nodes = data.getDocument()
                        .getElementsByTagName(ASSESSMENT_ITEM_REF_NODE);
                for (int x = 0; x < nodes.getLength(); ++x) {
                    Node n = nodes.item(x);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        items.add((Element) n);
                    }
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
        if (items != null && index < items.size() && index > -1) {
            return items.get(index);
        }
        return null;
    }

    public int getItemsCount() {
        if (itemsCount == -1) {
            itemsCount = getItemUrls().length;
        }

        return itemsCount;
    }

    public List<String> getStyleLinksForUserAgent(String userAgent) {
        List<String> declarations = new ArrayList<>();

        if (styleDeclaration != null) {
            declarations.addAll(styleDeclaration.getStyleLinksForUserAgent(userAgent));
        }

        declarations.addAll(skinData.getStyleLinksForUserAgent(userAgent));

        return declarations;
    }

    @Override
    public void onSkinLoad() {
        assessmentData = new AssessmentData(data, skinData.getSkinData());
        listener.onAssessmentDataLoaded();
    }

    @Override
    public void onSkinLoadError() {
        assessmentData = new AssessmentData(data, null);
        listener.onAssessmentDataLoaded();
    }

    private String getSkinUrl(Document document) {
        String url = null;

        try {
            Node testViewNode = document.getElementsByTagName(TEST_VIEW_NODE)
                    .item(0);
            url = testViewNode.getAttributes()
                    .getNamedItem(HREF_ATTR)
                    .getNodeValue();
        } catch (Exception e) {
            logger.error(e);
        }

        return url;
    }

    private boolean isItemDocument(Document doc) {
        try {
            return doc.getDocumentElement()
                    .getNodeName()
                    .equals(ASSESSMENT_ITEM_NODE);
        } catch (Exception e) {
            logger.error(e);
        }
        return true;
    }

    public Optional<String> getAssessmentGtm() {
        Element rootElement = getFirstElementByName(ASSESSMENT_TEST_NODE);
        if (rootElement.hasAttribute(GTM_ATTR)) {
            String gtmAttribute = rootElement.getAttribute(GTM_ATTR);
            return Optional.of(gtmAttribute);
        }
        return Optional.absent();
    }

    private Element getFirstElementByName(String attributeName) {
        return (Element) data.getDocument()
                .getElementsByTagName(attributeName)
                .item(0);
    }
}
